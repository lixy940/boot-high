package com.lixy.boothigh.dao;

import com.lixy.boothigh.bean.PersistentResult;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: MR LIS
 * @Description:
 * @Date: Create in 11:16 2018/5/24
 * @Modified By:
 */
@Repository
public interface PersistentResultMapper {

    /**
     * @Author: MR LIS
     * @Description: 获取所有的持久化数据结果
     * @Date: 14:16 2018/5/24
     * @return
     */
    List<PersistentResult> selectAll();
    /**
     * @Author: MR LIS
     * @Description: 插入一条记录
     * @Date: 14:18 2018/5/24
     * @return
     */
    void insertOne(PersistentResult result);

    /**
     * @Author: MR LIS
     * @Description: 根据主键id查询一条记录
     * @Date: 14:19 2018/5/24
     * @return
     */
    PersistentResult selectOne(Integer resultId);
    /**
     * @Author: MR LIS
     * @Description: 查询总记录数
     * @Date: 14:16 2018/5/25
     * @return
     */
    int selectTotalCount();
}
