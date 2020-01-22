package com.lixy.boothigh.service;


import com.lixy.boothigh.excep.ServiceException;
import com.lixy.boothigh.vo.SourceDataInfoShowVO;

import java.util.List;

/**
 * @Author: MR LIS
 * @Description:持久化数据结果服务
 * @Date: Create in 14:06 2018/5/25
 * @Modified By:
 */
public interface PersistentDataResultService {
    /**
     * @Author: MR LIS
     * @Description: 获取数据库持久化结果的总记录数
     * @Date: 14:09 2018/5/25
     * @return
     */
    int getTotalCount()throws ServiceException;
    /**
     * @Author: MR LIS
     * @Description: 获取所有数据结果需要展示列表
     * @Date: 14:12 2018/5/25
     * @return
     */
    List<SourceDataInfoShowVO> getDataResultShow()throws ServiceException;

    /**
     * @Author: MR LIS
     * @Description: 保存一条持久化数据结果
     * @param tempResultId 临时数据库结果id
     * @Date: 14:37 2018/5/25
     * @return
     */
    void savePersistentDataResult(Integer tempResultId)throws ServiceException;
}
