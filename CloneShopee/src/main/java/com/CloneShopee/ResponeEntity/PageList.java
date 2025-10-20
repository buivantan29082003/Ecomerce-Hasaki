package com.CloneShopee.ResponeEntity;

public class PageList {
    private Integer totalPage;
    private Long totalElements;
    private Object content;

    public PageList() {

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
