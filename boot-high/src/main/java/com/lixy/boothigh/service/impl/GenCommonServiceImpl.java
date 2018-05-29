package com.lixy.boothigh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lixy.boothigh.bean.DataBaseConfig;
import com.lixy.boothigh.dao.DataBaseConfigMapper;
import com.lixy.boothigh.excep.ServiceException;
import com.lixy.boothigh.service.GenCommonService;
import com.lixy.boothigh.utils.GenDBUtils;
import com.lixy.boothigh.vo.page.ColumnInfoVO;
import com.lixy.boothigh.vo.page.SandPageViewVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: MR LIS
 * @Description:公共服务接口实现
 * @Date: Create in 14:46 2018/5/25
 * @Modified By:
 */
@Transactional
@Service
public class GenCommonServiceImpl implements GenCommonService {

    private Logger logger = LoggerFactory.getLogger(GenCommonServiceImpl.class);

    @Autowired
    private DataBaseConfigMapper configMapper;

    @Override
    public List<ColumnInfoVO> getAllColumnInfo(Integer dbId, String tableName) throws ServiceException {
        DataBaseConfig sandDataBaseConfig = configMapper.selectOne(dbId);
        //表头
        List<ColumnInfoVO> columnInfoVOList = GenDBUtils.getAllColumnInfo(sandDataBaseConfig, tableName);
        logger.info("dbId={},tableName={},columnInfoVOList = {}", dbId, tableName, JSONObject.toJSONString(columnInfoVOList));
        return columnInfoVOList;
    }

    @Override
    public SandPageViewVO executePageQuery(Integer dbId, String tableName, int pageNum, int pageSize) throws ServiceException {
        DataBaseConfig sandDataBaseConfig = configMapper.selectOne(dbId);
        //表分页数据
        SandPageViewVO sandPageViewVO = GenDBUtils.executePageQuery(sandDataBaseConfig, tableName, pageNum, pageSize);
        logger.info("dbId={},tableName={},sandPageViewVO = {}", dbId, tableName, JSONObject.toJSONString(sandPageViewVO));
        return sandPageViewVO;
    }

    @Override
    public List<List<Object>> executePageQueryNotCount(Integer dbId, String tableName, Integer pageNum, Integer pageSize) throws ServiceException {
        int start = (pageNum-1)*pageSize;
        int end = pageSize*pageNum;
        DataBaseConfig sandDataBaseConfig = configMapper.selectOne(dbId);
        Map<String, String> map = GenDBUtils.pagingSql(sandDataBaseConfig.getDbType(), tableName,sandDataBaseConfig.getDbTableSchema(), pageSize, start, end);
        return GenDBUtils.executePageRecord(sandDataBaseConfig, map.get(GenDBUtils.PAGE_QUERY_SQL));
    }
}
