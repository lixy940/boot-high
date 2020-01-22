package com.lixy.boothigh.dao;

import com.lixy.boothigh.bean.DataBaseConfig;
import org.springframework.stereotype.Repository;

/**
 * @Author: MR LIS
 * @Description:
 * @Date: Create in 11:14 2018/5/24
 * @Modified By:
 */
@Repository
public interface DataBaseConfigMapper {
    /**
     * @Author: MR LIS
     * @Description: 根据主键id查询一条记录
     * @Date: 14:19 2018/5/24
     * @return
     */
    DataBaseConfig selectOne(Integer dbId);

}
