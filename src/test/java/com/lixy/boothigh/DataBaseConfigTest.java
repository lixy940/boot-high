package com.lixy.boothigh;

import com.alibaba.fastjson.JSONObject;
import com.lixy.boothigh.bean.DataBaseConfig;
import com.lixy.boothigh.dao.DataBaseConfigMapper;
import com.lixy.boothigh.service.GenCommonService;
import com.lixy.boothigh.utils.GenDBUtils;
import com.lixy.boothigh.vo.SourceDataInfoShowVO;
import com.lixy.boothigh.vo.page.ColumnInfoVO;
import com.lixy.boothigh.vo.page.SandPageViewVO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
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

    @Autowired
    private GenCommonService  genCommonService;
    @Test
    public void test(){
        DataBaseConfig dtaBaseConfig = configMapper.selectOne(3);
        //表头
        List<ColumnInfoVO> columnInfoVOList = GenDBUtils.getAllColumnInfo(dtaBaseConfig, "USERS");
        System.out.println("columnInfoVOList = " + JSONObject.toJSONString(columnInfoVOList));
        //表分页数据
        List<List<Object>> listList = genCommonService.executePageQueryNotCount(3, "USERS", 9, 10);
        System.out.println("result = " + JSONObject.toJSONString(listList));
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

    @Test
    public void testPageColumn(){
        List<String> columnList = new ArrayList<>();
        columnList.add("idcard");
        columnList.add("username");
        //获取需要查询的列名，并拼接
        String columnArr = String.join(",", columnList);
        DataBaseConfig dtaBaseConfig = configMapper.selectOne(3);
        String tableName = "USERS";
        //获取数据结果
        List<List<Object>> listList = genCommonService.executePageQueryColumnRecord(dtaBaseConfig.getDbId(), tableName, columnArr, 1, Integer.MAX_VALUE);
    }
}
