package com.lixy.boothigh;

import com.alibaba.fastjson.JSONObject;
import com.lixy.boothigh.bean.DataBaseConfig;
import com.lixy.boothigh.dao.DataBaseConfigMapper;
import com.lixy.boothigh.utils.GenDBUtils;
import com.lixy.boothigh.vo.SourceDataInfoShowVO;
import com.lixy.boothigh.vo.page.ColumnInfoVO;
import com.lixy.boothigh.vo.page.SandPageViewVO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/**
 * @Author: MR LIS
 * @Description:持久化数据结果、离线数据单元测试类
 * @Date: Create in 14:44 2018/5/24
 * @Modified By:
 */
public class DataBaseConfigTest extends BootHighApplicationTests {
    @Autowired
    private DataBaseConfigMapper configMapper;


    @Test
    public void test(){
        DataBaseConfig dataBaseConfig = configMapper.selectOne(3);
        //表头
        List<ColumnInfoVO> columnInfoVOList = GenDBUtils.getAllColumnInfo(dataBaseConfig, "USERS");
        System.out.println("columnInfoVOList = " + JSONObject.toJSONString(columnInfoVOList));
        //表分页数据
        SandPageViewVO sys_categoryinfo = GenDBUtils.executePageQuery(dataBaseConfig, "USERS", 9, 10);
        System.out.println("sys_categoryinfo = " + JSONObject.toJSONString(sys_categoryinfo));
    }

    @Test
    public void testGetTables(){
        DataBaseConfig dataBaseConfig = configMapper.selectOne(2);
        List<SourceDataInfoShowVO> showVOList = GenDBUtils.getDbTableInfos(dataBaseConfig);
        Optional.of(showVOList).ifPresent(list->{
            System.out.println("JSONObject.toJSONString(list)= " + JSONObject.toJSONString(list));
        });
        int dbTotalCount = GenDBUtils.getDbTableTotalCount(dataBaseConfig);
        System.out.println("dbTotalCount = " + dbTotalCount);
    }


    @Test
    public void testDeleteTable(){
        DataBaseConfig dataBaseConfig = configMapper.selectOne(6);
        GenDBUtils.dropTable(dataBaseConfig,"xxx");
    }
}
