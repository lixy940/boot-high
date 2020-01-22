package com.lixy.boothigh.service;


import com.lixy.boothigh.excep.ServiceException;
import com.lixy.boothigh.vo.ConditionCountVo;
import com.lixy.boothigh.vo.ConditionPageVo;
import com.lixy.boothigh.vo.page.ColumnInfoVO;

import java.util.List;

/**
 * @Author: MR LIS
 * @Description:数据库连接公共服务接口
 * @Date: Create in 14:45 2018/5/25
 * @Modified By:
 */
public interface GenCommonService {
    /**
     * @param dbId      数据库id
     * @param tableName 表名
     * @return
     * @Author: MR LIS
     * @Description:获取数据库表的字段名、注释、数据类型
     * @Date: 14:50 2018/5/25
     */
    List<ColumnInfoVO> getAllColumnInfo(Integer dbId, String tableName) throws ServiceException;

    /**
     * @return
     * @Author: MR LIS
     * @Description: 根据dbId，tableName获取总记录数
     * @Date: 14:54 2018/5/25
     */
    int executePageTotalCount(Integer dbId, String tableName) throws ServiceException;

    /**
     * @return
     * @Author: MR LIS
     * @Description: 根据dbId，tableName执行分页查询，不进行总记录数的查询
     * @Date: 14:54 2018/5/25
     */
    List<List<Object>> executePageQueryNotCount(Integer dbId, String tableName, Integer pageNum, Integer pageSize) throws ServiceException;

    /**
     * @Description: 根据dbId，tableName获取带条件总记录数
     * @return
     * @Author: MR LIS
     * @Date: 14:54 2018/5/25
     */
    int executePageTotalCountWithCondition(ConditionCountVo countVo) throws ServiceException;

    /**
     * @return
     * @Author: MR LIS
     * @Description: 根据dbId，tableName执行带条件分页查询，不进行总记录数的查询
     * @Date: 14:54 2018/5/25
     */
    List<List<Object>> executePageQueryNotCountWithCondition(ConditionPageVo pageVo,String columnArr) throws ServiceException;
    /**
     * @return
     * @Author: MR LIS
     * @Description: 根据dbId，tableName执行分页查询，不进行总记录数的查询,并返回指定列的数据
     * @Date: 14:54 2018/5/25
     */
    List<List<Object>> executePageQueryColumnRecord(Integer dbId, String tableName, String columnArr, Integer pageNum, Integer pageSize) throws ServiceException;

    /**
     * @return
     * @Author: MR LIS
     * @Description: 根据库进行制定表的删除
     * @Date: 10:44 2018/6/1
     */
    void dropTable(Integer dbId, String tableName) throws ServiceException;

    /**
     * 判断指定表名在对应库或者模式中的个数
     * @param dbId
     * @param tableName
     * @return
     * @throws ServiceException
     * @Author: MR LIS
     * @Date: 13:05 2018/9/3
     */
    int getIsTableExistCount(Integer dbId, String tableName) throws ServiceException;
}
