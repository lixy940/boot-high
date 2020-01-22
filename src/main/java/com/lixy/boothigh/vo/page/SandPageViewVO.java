package com.lixy.boothigh.vo.page;

import java.util.List;

/**
 * @Author: MR LIS
 * @Description:分页展示对象
 * @Date: Create in 10:29 2018/5/25
 * @Modified By:
 */
public class SandPageViewVO {
    /**
     * 总记录数
     */
    private int totalCount;

    /**
     * 数据列表
     */
    private List<List<Object>> dataList;

    public SandPageViewVO() {
    }

    public SandPageViewVO(int totalCount, List<List<Object>> dataList) {
        this.totalCount = totalCount;
        this.dataList = dataList;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<List<Object>> getDataList() {
        return dataList;
    }

    public void setDataList(List<List<Object>> dataList) {
        this.dataList = dataList;
    }
}
