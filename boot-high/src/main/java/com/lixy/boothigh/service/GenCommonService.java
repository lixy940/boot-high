package com.lixy.boothigh.service;


import com.lixy.boothigh.excep.ServiceException;
import com.lixy.boothigh.vo.page.ColumnInfoVO;

import java.util.List;

/**
 * @Author: MR LIS
 * @Description:沙盘公共服务接口
 * @Date: Create in 14:45 2018/5/25
 * @Modified By:
 */
public interface GenCommonService {
    /**
     * @Author: MR LIS
     * @Description:获取数据库表的字段名、注释、数据类型
     * @param dbId 数据库id
     * @param tableName 表名
     * @Date: 14:50 2018/5/25
     * @return
     */
    List<ColumnInfoVO> getAllColumnInfo(Integer dbId, String tableName) throws ServiceException;

    /**
     * @Author: MR LIS
     * @Description: 根据dbId，tableName获取总记录数
     * @Date: 14:54 2018/5/25
     * @return
     */
    int executePageTotalCount(Integer dbId, String tableName)throws ServiceException;
    /**
     * @Author: MR LIS
     * @Description: 根据dbId，tableName执行分页查询，不进行总记录数的查询
     * @Date: 14:54 2018/5/25
     * @return
     */
    List<List<Object>> executePageQueryNotCount(Integer dbId, String tableName, Integer pageNum, Integer pageSize)throws ServiceException;
    /**
     * @Author: MR LIS
     * @Description: 根据dbId，tableName执行分页查询，不进行总记录数的查询,并返回指定列的数据
     * @Date: 14:54 2018/7/10
     * @return
     */
    List<List<Object>> executePageQueryColumnRecord(Integer dbId, String tableName,String columnArr, Integer pageNum, Integer pageSize)throws ServiceException;
    /**
     * @Author: MR LIS
     * @Description: 根据库进行制定表的删除
     * @Date: 10:22 2018/6/1
     * @return
     */
    void dropTable(Integer dbId, String tableName)throws ServiceException;
}
