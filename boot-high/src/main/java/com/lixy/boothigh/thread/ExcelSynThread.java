package com.lixy.boothigh.thread;

import com.lixy.boothigh.bean.SysFilePath;
import com.lixy.boothigh.constants.BConstant;
import com.lixy.boothigh.enums.DbDataTypeEnum;
import com.lixy.boothigh.excep.ServiceException;
import com.lixy.boothigh.service.RedisService;
import com.lixy.boothigh.utils.ExcelSAXParserUtil;
import com.lixy.boothigh.utils.IPUtil;
import com.lixy.boothigh.utils.RegexUtils;
import com.lixy.boothigh.utils.SpringContextUtils;
import com.lixy.boothigh.vo.TaskHandleVO;
import com.lixy.boothigh.vo.TaskScanVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: MR LIS
 * @Description:excel数据添加到本地数据库及es,此次代码为简写，不能直接使用，需根据业务需求实现业务逻辑
 * @Date: Create in 17:41 2018/4/11
 * @Modified By:
 */
@Service(value = "excelSynThread")
/*@Scope(value = "prototype")默认为单例，*/
public class ExcelSynThread implements Runnable {

    private final static Logger logger = LoggerFactory.getLogger(ExcelSynThread.class);

    @Autowired
    private RedisService redisService;
    @Autowired
    private RedisTemplate redisTemplate;

    //excel列号对应模板字段的字段元素表id
    private Map<Integer, Integer> columnIndexToElementIdMap = new HashMap<>();
    //excel列序号对应的模板属性名
    private Map<Integer, String> columnIndexToEnameMap = new HashMap<>();
    //从redis中获取对应的元素id对应的正则表达式
    private Map<Integer, String> elementIdToRegexMap = new HashMap<>();
    //excel列序号对应的模板字段数据类型
    private Map<Integer, String> columnIndexToDataFieldTypeMap = new HashMap<>();
    //定义线程池
    private ExecutorService threadPool = Executors.newFixedThreadPool(5);
    /**
     * 重入锁
     */
    private ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        ListOperations<String, TaskScanVO> opsList = redisTemplate.opsForList();
        elementIdToRegexMap = null/*(Map<Integer, String>) redisService.getValueByKeyMap(RealityRelConstant.DATA_ELEMENT_MAP_KEY)*/;
        while (true) {
            try {
                lock.lock();

                SysFilePath sysFilePath = null;/*filePathService.getRecordWaitHandlerOne();*/
                //如果不存在就等待60s
                if (sysFilePath == null) {
                    Thread.sleep(60_000);
                    continue;
                }

                //定义任务
                TaskHandleVO taskVO = new TaskHandleVO(sysFilePath.getId());
                TaskScanVO scanVO = new TaskScanVO(sysFilePath.getId(), IPUtil.getLocalIP());

                //数据集
                ExcelSAXParserUtil parserUtil = new ExcelSAXParserUtil();
                parserUtil.processOneSheet(sysFilePath.getPath());
                List<String> dataList = parserUtil.getDataList();
                //没有数据直接返回，设置任务为已完成
                if (dataList == null || dataList.isEmpty()) {
                    taskVO.setEsFlag(true);
                    taskVO.setTiFlag(true);
                    taskVO.setUploadPathId(sysFilePath.getId());
                    redisService.setRedisKeyToTaskVO(BConstant.TEMPLATE_REDIS_HANDLER_KEY + sysFilePath.getId(), taskVO, BConstant.TEMPLATE_EXPIRE_NUM, TimeUnit.DAYS);
                    opsList.leftPush(BConstant.TEMPLATE_REDIS_TASK_SCAN_KEY, scanVO);
                    continue;
                }


                logger.info("上传文件id为[{}]的excel总记录数为：{}", sysFilePath.getId(), dataList.size());
                long begin = System.currentTimeMillis();
                List<String> newDataList = new ArrayList<>(dataList.size());
                List<String> failDataList = new ArrayList<>();

                List<Integer> columnIndexList = new ArrayList<>();

                //去除多余不符合条件的数据
                for (String data : dataList) {
                    if (validateExcelColumn(data, columnIndexList)) {
                        newDataList.add(data);
                    } else {
                        failDataList.add(data);
                    }
                }
                dataList.clear();
                logger.info("上传文件id为[{}]的excel满足要求的记录为：{}，不满足要求为：{}，过滤不符合条件的花费时间：{}秒", sysFilePath.getId(), newDataList.size(), failDataList.size(), (System.currentTimeMillis() - begin) / 1000);
                logger.info("【数据导入】开始数据导入 ！");

                long l = System.currentTimeMillis();
                int size = newDataList.size();


                /**
                 * 根据有无数据，判断同步任务的扫描设置
                 */
                //数据量不为空
                if (size > 0) {
                    taskVO.setUploadPathId(sysFilePath.getId());
                    redisService.setRedisKeyToTaskVO(BConstant.TEMPLATE_REDIS_HANDLER_KEY + sysFilePath.getId(), taskVO, BConstant.TEMPLATE_EXPIRE_NUM, TimeUnit.DAYS);
                    opsList.leftPush(BConstant.TEMPLATE_REDIS_TASK_SCAN_KEY, scanVO);
                } else {
                    //没有数据，直接返回，这种任务状态为已完成
                    taskVO.setEsFlag(true);
                    taskVO.setTiFlag(true);
                    taskVO.setUploadPathId(sysFilePath.getId());
                    redisService.setRedisKeyToTaskVO(BConstant.TEMPLATE_REDIS_HANDLER_KEY + sysFilePath.getId(), taskVO, BConstant.TEMPLATE_EXPIRE_NUM, TimeUnit.DAYS);
                    opsList.leftPush(BConstant.TEMPLATE_REDIS_TASK_SCAN_KEY, scanVO);
                    continue;
                }

                int num = BConstant.EXCEL_TO_LOCAL_NUM; // 每批次处理记录数
                int handleNum = (int) Math.ceil((float) size / num);
                List<Map<String, Object>> totalDataList = new ArrayList<>();
                List<Map<String, Object>> datas = new ArrayList<>();
                Map<String, Object> map = null;
                for (int i = 0; i < handleNum; i++) {
                    List<String> subList = null;
                    if (i == handleNum - 1) {
                        subList = newDataList.subList(i * num, size);
                    } else {
                        subList = newDataList.subList(i * num, i * num + num);
                    }

                    for (String singleRecord : subList) {
                        String[] eColumArr = singleRecord.split(",", -1);
                        map = new HashMap<>();
                        for (Integer columnIndex : columnIndexList) {
                            //解析数据为对应的类型
                            Object o = parseDataType(columnIndex, eColumArr[columnIndex]);
                            map.put(columnIndexToEnameMap.get(columnIndex), o);
                        }
                        datas.add(map);
                    }

                    //有数据进行数据处理
                    if (!datas.isEmpty()) {

                        //===========================同步到es,neo4j==============================
                        logger.info("【数据导入】同步到es和neo4j : 第{}批次放入队列开始！", i + 1);
                        //subList数据同步到es逻辑，同步完成后会更新任务es同步状态，此处省略
                        logger.info("【数据导入】同步到es和neo4j : 第{}批次放入队列完成！", i + 1);
                    }

                }


                //===========================同步到tidb==============================
                int tSize = newDataList.size();
                int tNum = BConstant.DB_HANDLE_BATCH_NUM; // 每个线程处理的个数
                int threadNum = (int) Math.ceil((float) tSize / tNum);
                //这个请求所有的执行完成
                logger.info("【数据导入】同步到tidb --->导入开始！");
                List<Future<Integer>> futures = new ArrayList<>();
                if (!totalDataList.isEmpty()) {
                    for (int j = 0; j < threadNum; j++) {
                        DataInsertThread dataInsertThread = (DataInsertThread) SpringContextUtils.getBean("dataInsertThread");
                        List<Map<String, Object>> subDataList = null;
                        if (j == threadNum - 1) {
                            subDataList = totalDataList.subList(j * tNum, tSize);
                            //当前插入记录的最后
                            dataInsertThread.setIsLastBatch(true);
                        } else {
                            subDataList = totalDataList.subList(j * tNum, j * tNum + tNum);
                        }

                        //参数值这儿随意设置，实际情况根据业务来处理
                        dataInsertThread.setSubDataList(subDataList);
                        dataInsertThread.setSql("sql");
                        dataInsertThread.setColNum(1);//数据库列的数量
                        dataInsertThread.setDbIndexToFieldNameMap(null);
                        dataInsertThread.setUploadPathId(1);
                        futures.add(threadPool.submit(dataInsertThread));
                    }

                    for (Future<Integer> future : futures) {
                        if (future.get() == 1) {
                            logger.info("【数据导入】同步到tidb --->导入完成！");
                        }
                    }
                }

                //更新tidb同步状态
                TaskHandleVO toTaskVO = redisService.getRedisKeyToTaskVO(BConstant.TEMPLATE_REDIS_HANDLER_KEY + sysFilePath.getId());
                toTaskVO.setTiFlag(true);
                redisService.setRedisKeyToTaskVO(BConstant.TEMPLATE_REDIS_HANDLER_KEY + toTaskVO.getUploadPathId(), toTaskVO, BConstant.TEMPLATE_EXPIRE_NUM, TimeUnit.DAYS);
                //====================tidb同步完成进行状态更新==========================================

                //更新为已导入完成,并删除本地文件

                //清空
                columnIndexToElementIdMap.clear();
                columnIndexToEnameMap.clear();
                columnIndexToDataFieldTypeMap.clear();
                totalDataList.clear();

                logger.info("【数据导入】导入耗时{}秒", (System.currentTimeMillis() - l) / 1000);

                lock.unlock();
            } catch (ServiceException e) {
                logger.error("ExcelSynThread 处理异常：" + e.getMessage());
            } catch (InterruptedException e) {
                logger.error("ExcelSynThread 处理异常：" + e.getMessage());
            } catch (Exception e) {
                logger.error("ExcelSynThread 处理异常：" + e.getMessage());
            } finally {
                lock.unlock();
                //清空
                columnIndexToElementIdMap.clear();
                columnIndexToEnameMap.clear();
                columnIndexToDataFieldTypeMap.clear();
                //关闭线程池
                closePool();
            }

        }


    }


    /**
     * @return
     * @Author: MR LIS
     * @Description: 单条记录正则校验
     * @Date: 17:02 2018/4/16
     */
    private boolean validateExcelColumn(String singleRecord, List<Integer> columnIndexList) throws ServiceException {
        String[] eColumArr = singleRecord.split(",", -1);
        for (int i = 0; i < eColumArr.length; i++) {

            //如果excel的列是不会导入的数据，不进行后续的判断
            if (!columnIndexList.contains(i)) {
                continue;
            }

            //获取excel列对应的字段元素的id
            Integer elementId = columnIndexToElementIdMap.get(i);

            /**
             * 如果正则元素不为空，进行正则校验，否则进行字段类型判断
             */
            String regex = elementIdToRegexMap.get(elementId);
            //元素为空，验证字段类型
            if (elementId == null || StringUtils.isBlank(regex)) {

                String dataType = columnIndexToDataFieldTypeMap.get(i);
                //根据不同的数据类型进行判断
                if (!validateDataType(dataType, eColumArr[i])) {
                    return false;
                }

            } else {//正则存在

                //有一个验证不通过，返回false
                if (!RegexUtils.validate(eColumArr[i], regex)) {
                    return false;
                }
            }


        }
        return true;
    }

    /**
     * 验证数据类型
     *
     * @param dataType
     */
    private boolean validateDataType(String dataType, String value) {

        //如果数据类型是string,直接返回true
        if (DbDataTypeEnum.STRING.getType().equals(dataType)) {
            return true;
        }

        if (DbDataTypeEnum.NUMBER.getType().equals(dataType)) {

            return RegexUtils.validateAllNumbers(value);

        } else if (DbDataTypeEnum.FLOAT.getType().equals(dataType)) {
            return RegexUtils.validateFloat(value);

        } else if (DbDataTypeEnum.DATE.getType().equals(dataType)) {
            return RegexUtils.validateDate(value);

        }

        return true;

    }

    /**
     * 解析对应数据为为该有的类型
     *
     * @param columnIndex
     * @param value
     * @return
     */
    private Object parseDataType(Integer columnIndex, String value) {

        String dataType = columnIndexToDataFieldTypeMap.get(columnIndex);
        if (DbDataTypeEnum.NUMBER.getType().equals(dataType)) {
            return Long.parseLong(value);

        } else if (DbDataTypeEnum.FLOAT.getType().equals(dataType)) {
            return Float.parseFloat(value);

        } else if (DbDataTypeEnum.DATE.getType().equals(dataType)) {
          /*  String[] arr = value.split(" ");
            if (arr.length == 1) {
                return DateTimeUtil.parse(value, DateTimeUtil.FORTER_DATE);
            }else{
                return DateTimeUtil.parse(value, DateTimeUtil.FORMAT_TIME);
            }*/
            return value;
        }

        return value;
    }


    //关闭线程池
    private void closePool() {
        threadPool.shutdown();  //关闭线程池
    }

}
