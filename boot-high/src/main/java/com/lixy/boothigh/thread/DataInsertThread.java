package com.lixy.boothigh.thread;

import com.lixy.boothigh.excep.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: MR LIS
 * @Description:数据批量插入线程
 * @Date: Create in 9:32 2018/4/12
 * @Modified By:
 */
@Service(value = "dataInsertThread")
@Scope(value="prototype")
public class DataInsertThread implements Runnable{
    private final static Logger logger = LoggerFactory.getLogger(DataInsertThread.class);
    /**
     * 插入时的sql
     */
    private String sql;
    /**
     * 数据库表数据插入的数量
     */
    private int colNum;
    /**
     * excel列对应的数据库插入时的列
     */
    private Map<Integer,Integer> dbToExcelMap;
    /**
     * 单个线程要处理的子数据
     */
    private List<String> subDatas;

    /**
     * 控制线程池是否运行完
     */
    private CountDownLatch countDownLatch;

    /**
     * 每多少天记录执行一次插入
     */
    private static Integer BATCH_OPT_NUM = 500;

    @Override
    public void run() {
        try {

            List<Map<Integer, Object>> list = new ArrayList<>();
            Map<Integer, Object> subMap;
            for (String subData : subDatas) {
                subMap = new HashMap<>();
                String[] eValue = subData.split(",",-1);
                for (int i = 1; i <= colNum; i++) {
                    Integer excelIndex = dbToExcelMap.get(i);
                    subMap.put(i, eValue[excelIndex]);
                }
                list.add(subMap);
            }
            logger.info("DataInsertThread 线程{}开始执行插入任务",Thread.currentThread().getName());
            //operateService.batchInsert(sql, colNum, list, BATCH_OPT_NUM);
            logger.info("DataInsertThread 线程{}完成插入任务",Thread.currentThread().getName());
        } catch (ServiceException e) {
            logger.error("DataInsertThread run exception:{}",e.getMessage());
        } finally {
            countDownLatch.countDown();
        }
    }



    public void setSubDatas(List<String> subDatas) {
        this.subDatas = subDatas;
    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public void setColNum(int colNum) {
        this.colNum = colNum;
    }

    public void setDbToExcelMap(Map<Integer, Integer> dbToExcelMap) {
        this.dbToExcelMap = dbToExcelMap;
    }



}
