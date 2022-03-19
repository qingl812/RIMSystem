package com.example.rimsystem.bean;

import lombok.ToString;

import java.util.List;

/**
 * @auther luyu
 */
@ToString
public class PageBean<T> {
//    当前页
    private Integer currentPage=1;
//    总共多少页
    private Integer totalPage;
//    一页最多几条数据
    private Integer pageCount=5;
//  总共多少条数据
    private Integer totalCount;

    private List<T> pageData;

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPage() {
        return totalCount % pageCount == 0 ? totalCount / pageCount : totalCount / pageCount + 1;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<T> getPageData() {
        return pageData;
    }

    public void setPageData(List<T> pageData) {
        this.pageData = pageData;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getTotalCount() {
        return totalCount;
    }
}
