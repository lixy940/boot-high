package com.lixy.boothigh.vo;

/**
 * Created by on 2018/5/24.
 *
 * 数据展示
 */
public class SourceDataInfoShowVO {

    /**
     *数据源基本信息
     */
    private SourceDataInfoVO sourceDataInfoVO;

    /**
     * 行数
     */
    private  Integer count;



    public SourceDataInfoVO getSourceDataInfoVO() {
        return sourceDataInfoVO;
    }

    public void setSourceDataInfoVO(SourceDataInfoVO sourceDataInfoVO) {
        this.sourceDataInfoVO = sourceDataInfoVO;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
