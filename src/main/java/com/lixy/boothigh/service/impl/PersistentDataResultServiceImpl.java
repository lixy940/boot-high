package com.lixy.boothigh.service.impl;

import com.lixy.boothigh.bean.PersistentResult;
import com.lixy.boothigh.dao.PersistentResultMapper;
import com.lixy.boothigh.enums.SourceDataTypeEnum;
import com.lixy.boothigh.excep.ServiceException;
import com.lixy.boothigh.service.PersistentDataResultService;
import com.lixy.boothigh.vo.SourceDataInfoShowVO;
import com.lixy.boothigh.vo.SourceDataInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: MR LIS
 * @Description:数据持久化结果服务实现
 * @Date: Create in 14:13 2018/5/25
 * @Modified By:
 */
@Transactional
@Service
public class PersistentDataResultServiceImpl implements PersistentDataResultService {

    private Logger logger = LoggerFactory.getLogger(PersistentDataResultServiceImpl.class);

    @Autowired
    private PersistentResultMapper persistentResultMapper;
    @Override
    public int getTotalCount() throws ServiceException {
        return persistentResultMapper.selectTotalCount();
    }

    @Override
    public List<SourceDataInfoShowVO> getDataResultShow() throws ServiceException {
        List<SourceDataInfoShowVO> showVOList = new ArrayList<>();
        List<PersistentResult> sandPersistentResults = persistentResultMapper.selectAll();
        //没有记录直接返回
        if(null==sandPersistentResults||sandPersistentResults.size()==0) return showVOList;
        //遍历设置结果
        sandPersistentResults.stream().forEach(result -> {
            SourceDataInfoShowVO showVO = new SourceDataInfoShowVO();
            SourceDataInfoVO sourceVO = new SourceDataInfoVO(result.getDbId(),result.getResultEname(),result.getResultCname(), SourceDataTypeEnum.Result.getCode());
            showVO.setSourceDataInfoVO(sourceVO);
            showVO.setCount((long)result.getRowNum());
            showVOList.add(showVO);
        });

        return showVOList;
    }

    @Override
    public void savePersistentDataResult(Integer tempResultId) throws ServiceException {
        PersistentResult persistentResult = new PersistentResult();
        persistentResultMapper.insertOne(persistentResult);
    }
}
