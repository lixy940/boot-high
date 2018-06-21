package com.lixy.boothigh.thread;

import com.lixy.boothigh.constants.BConstant;
import com.lixy.boothigh.enums.DriverNameEnum;
import com.lixy.boothigh.excep.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * @Author: MR LIS
 * @Description:数据批量插入线程
 * @Date: Create in 9:32 2018/4/12
 * @Modified By:
 */
@Service(value = "dataInsertThread")
@Scope(value="prototype")
public class DataInsertThread  implements Callable<Integer> {
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
     *
     */
    private Integer uploadPathId;
    /**
     * 数据库列对应的字段名
     */
    private Map<Integer,String> dbIndexToFieldNameMap;
    /**
     * 单个线程要处理的子数据
     */
    private List<Map<String, Object>> subDataList;
    /**
     * 是否为最后一批
     */
    private boolean isLastBatch;
    /**
     * 每多少条记录执行一次插入
     */
    private Integer BATCH_OPT_NUM = 500;

    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Integer call() throws Exception {

        try {
            Date date = new Date();
            String currentTimeStr = format.format(date);
            List<Map<Integer, Object>> list = new ArrayList<>();
            Map<Integer, Object> subMap;
            for (Map<String, Object> subData : subDataList) {
                subMap = new HashMap<>();
                //此处i从1开始，数据库出入时的第一个站位序列为1
                for (int i = 1; i <= colNum; i++) {
                    //列索引获取对应的属性名，并通过subData从属性中获取value
                    subMap.put(i, subData.get(dbIndexToFieldNameMap.get(i)));
                }
                //设置上传文件的主键id和当前时间
                subMap.put(colNum + 1, uploadPathId);
                subMap.put(colNum + 2, currentTimeStr);
                list.add(subMap);
            }
            logger.info("DataInsertThread 线程{}开始执行插入任务",Thread.currentThread().getName());
            batchInsert(sql, colNum+2, list, BATCH_OPT_NUM);
            logger.info("DataInsertThread 线程{}完成插入任务",Thread.currentThread().getName());
        } catch (ServiceException e) {
            logger.error("DataInsertThread run exception:{}",e.getMessage());
        }catch (Exception e){
            logger.error("DataInsertThread run exception:{}",e.getMessage());
        }

        return isLastBatch?1:-1;
    }

    public void setSubDataList(List<Map<String, Object>> subDataList) {
        this.subDataList = subDataList;
    }


    public void setSql(String sql) {
        this.sql = sql;
    }

    public void setColNum(int colNum) {
        this.colNum = colNum;
    }

    public void setDbIndexToFieldNameMap(Map<Integer, String> dbIndexToFieldNameMap) {
        this.dbIndexToFieldNameMap = dbIndexToFieldNameMap;
    }

    public void setUploadPathId(Integer uploadPathId) {
        this.uploadPathId = uploadPathId;
    }

    public void setIsLastBatch(boolean lastBatch) {
        isLastBatch = lastBatch;
    }

    /**
     * @Author: MR LIS
     * @Description: 批量插入
     * @Date: 15:48 2018/6/21
     * @return
     */
    public void batchInsert(String sql, Integer columnNum, List<Map<Integer, Object>> list,Integer batchOptNum) throws ServiceException{
        logger.info("正在执行插入，请稍后............");
        Connection conn = null;
        PreparedStatement pstm = null;
        /*Integer dbId =Integer.parseInt(DBPropertyReaderUtils.getProValue("tidb.db.id"))
        SysDBInfo sysDBInfo = sysDBInfoMapper.selectByPrimaryKey(dbId);*/

        try {
            Class.forName(DriverNameEnum.DRIVER_MYSQL.getDriverName());
            conn = DriverManager.getConnection(BConstant.SPRING_DATASOURCE_URL, BConstant.SPRING_DATASOURCE_USERNAME, BConstant.SPRING_DATASOURCE_PASSWORD);

            pstm = conn.prepareStatement(sql);
            Long startTime = System.currentTimeMillis();
            Integer allSize=list.size();
            for (int i = 0; i < allSize; i++) {
                Map<Integer, Object> map = list.get(i);
                for(int j =1 ;j<=columnNum;j++){
                    pstm.setObject(j,map.get(j));
                }
                pstm.addBatch();
                //每BATCH_OPT_NUM执行一次批量操作
                if(i>0&&i%batchOptNum==0)
                    pstm.executeBatch();


            }
            pstm.executeBatch();
            Long endTime = System.currentTimeMillis();
            logger.info("OK,用时：" + (endTime - startTime));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (pstm != null) {
                try {
                    pstm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
