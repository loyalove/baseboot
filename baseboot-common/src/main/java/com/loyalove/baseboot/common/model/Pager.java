package com.loyalove.baseboot.common.model;

/**
 * @author Loyal
 * @date 2016/5/24 16:31
 */

/**
 * 分页类Pager
 */
public class Pager {

    // 默认每页记录数
    private static final Integer DEFAULT_PAGE_SIZE = 10;
    // 默认当前页码
    private static final Integer DEFAULT_PAGE_NUMBER = 1;
    // 当前页码
    private Integer pageNumber;
    // 每页显示记录数量
    private Integer pageSize;
    // 页码数
    private Integer pageTotal;
    // 记录数
    private Integer recordTotal;
    // 显示的开始记录
    private Integer recordStart;
    // 显示的结束记录
    private Integer recordEnd;

    public Pager() {
        this.pageNumber = DEFAULT_PAGE_NUMBER;
        this.pageSize = DEFAULT_PAGE_SIZE;
    }

    public Pager(Integer pageNumber, Integer pageSize) {
        // pageSize为空判断
        if (pageSize != null && pageSize > 0) {
            this.pageSize = pageSize;
        } else {
            this.pageSize = DEFAULT_PAGE_SIZE;
        }
        // pageIndex为空判断
        if (pageNumber != null && pageNumber > 0) {
            this.pageNumber = pageNumber;
        } else {
            this.pageNumber = DEFAULT_PAGE_NUMBER;
        }
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageTotal() {
        pageTotal = (recordTotal - 1) / pageSize + 1;
        return pageTotal;
    }

    public void setPageTotal(Integer pageTotal) {
        this.pageTotal = pageTotal;
    }

    public Integer getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(Integer recordTotal) {
        this.recordTotal = recordTotal;
    }

    public Integer getRecordStart() {
        recordStart = (pageNumber - 1) * pageSize;
        return recordStart;
    }

    public void setRecordStart(Integer recordStart) {
        this.recordStart = recordStart;
    }

    public Integer getRecordEnd() {
        recordEnd = pageNumber == pageSize ? recordTotal - recordStart : recordStart + pageSize;
        return recordEnd;
    }

    public void setRecordEnd(Integer recordEnd) {
        this.recordEnd = recordEnd;
    }
}
