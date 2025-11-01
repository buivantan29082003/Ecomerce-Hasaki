package com.CloneShopee.ResponeEntity;

import java.util.ArrayList;

import org.springframework.data.domain.Page;

public class PageList {
    private Integer totalPage;
    private Long totalElements;
    private Object content;

    public PageList() {
        content = new ArrayList<>();
    }

    public PageList(Page page) {
        this.totalElements = page.getTotalElements();
        this.totalPage = page.getTotalPages();
        this.content = page.getContent();
    }

    public PageList(Integer totalPage, Long totalElements, Object content) {
        this.totalElements = totalElements;
        this.totalPage = (totalPage);
        this.content = content;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

}
