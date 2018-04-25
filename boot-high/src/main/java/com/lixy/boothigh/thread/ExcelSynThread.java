package com.lixy.boothigh.thread;

import com.lixy.boothigh.bean.SysFileExcel;
import com.lixy.boothigh.excep.ServiceException;
import com.lixy.boothigh.service.RedisService;
import com.lixy.boothigh.utils.ExcelSAXParserUtil;
import com.lixy.boothigh.utils.SpringContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: MR LIS
 * @Description:excel数据添加到本地数据库
 * @Date: Create in 17:41 2018/4/11
 * @Modified By:
 */
@Service(value = "excelSynThread")
@Scope(value = "prototype")
public class ExcelSynThread implements Runnable {

    private final static Logger logger = LoggerFactory.getLogger(ExcelSynThread.class);

    @Autowired
    private RedisService redisService;

    //数据库插入时的列号对应excel的列号
    Map<Integer, Integer> dbToExcelMap = new HashMap<>();
    //excel列号对应模板字段的字段元素表id
    Map<Integer, Integer> indexToElementIdMap = new HashMap<>();
    //从redis中获取对应的元素id对应的正则表达式
    static Map<Integer, String> elementIdToRegexMap = new HashMap<>();
    /**
     * 同步excel到本地库的一次记录数
     */
    private static int EXCEL_TO_LOCAL_NUM = 100000;
    /**
     * 重入锁
     */
    private ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        try {

//            elementIdToRegexMap = (Map<Integer, String>) redisService.getValueByKeyMap(RealityRelConstant.DATA_ELEMENT_MAP_KEY);

            while (true) {

                lock.lock();

//              SysFileExcel fileExcel = excelService.getExcelRecordWaitHandlerOne();
                SysFileExcel fileExcel = null;
                //表示没数据,休眠五分钟
                if (fileExcel == null) {
                    Thread.sleep(300_000);
                    lock.unlock();
                    continue;
                }
                  String filePath = fileExcel.getPath();


                //文件解析
                ExcelSAXParserUtil parserUtil = new ExcelSAXParserUtil();
                parserUtil.processOneSheet(filePath);
                List<String> dataList = parserUtil.getDataList();
                logger.info("excel总记录数为：{}",  dataList.size());

                //根据实际情况
                String sql = "";

                int size = dataList.size();
                int num = EXCEL_TO_LOCAL_NUM; // 每个线程处理的个数
                int threadNum = (int) Math.ceil((float) size / num);
                //这个请求所有的执行完成
                CountDownLatch countDownLatch = new CountDownLatch(threadNum);
                //每个线程都有个自己的线程池 使用完销毁
                ExecutorService threadPool = Executors.newFixedThreadPool(4);
                for (int i = 0; i < threadNum; i++) {
                    DataInsertThread dataInsertThread = (DataInsertThread) SpringContextUtils.getBean("dataInsertThread");
                    List<String> subList = null;
                    if (i == threadNum - 1) {
                        subList = dataList.subList(i * num, size);
                    } else {
                        subList = dataList.subList(i * num, i * num + num);
                    }
                    dataInsertThread.setSubDatas(subList);
                    dataInsertThread.setSql(sql);
                    dataInsertThread.setColNum(dataList.size());//数据库列的数量
                    dataInsertThread.setDbToExcelMap(dbToExcelMap);
                    dataInsertThread.setCountDownLatch(countDownLatch);
                    threadPool.execute(dataInsertThread);
                }
                //线程池 执行任务等待所有任务完成
                countDownLatch.await();

                threadPool.shutdown();  //关闭线程池
                threadPool = null;


                lock.unlock();
                //清空
                dbToExcelMap.clear();
                indexToElementIdMap.clear();
            }
        } catch (ServiceException e) {
            logger.error("ExcelSynThread 处理异常：" + e.getMessage());
        } catch (InterruptedException e) {
            logger.error("ExcelSynThread 处理异常：" + e.getMessage());
        } catch (Exception e) {
            logger.error("ExcelSynThread 处理异常：" + e.getMessage());
        } finally {
            lock.unlock();
            //清空
            dbToExcelMap.clear();
            indexToElementIdMap.clear();
        }
    }




}
