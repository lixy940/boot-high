package com.lixy.boothigh.vo.page;

import java.io.Serializable;

/**
 * @Author: MR LIS
 * @Description:分页对象
 * @Date: Create in 12:43 2018/4/18
 * @Modified By:
 */
public class Pagination implements Serializable{
    /**
     * 总条数
     */
    private Integer total;

    /**
     * 当前页
     */
    private Integer pageNo;

    /**
     * 每页大小
     */
    private Integer pageSize;

    /**
     * 总页数
     */
    private Integer pages;


    public Pagination() {
        this.pageNo = 1;
        this.pageSize = 20;
    }

    public Pagination(Integer total, Integer pageNo, Integer pageSize) {
        this.total = total;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        Integer pages_temp = (total) / pageSize;
        this.pages = (total % pageSize == 0) ? pages_temp : (pages_temp + 1);
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }
}
