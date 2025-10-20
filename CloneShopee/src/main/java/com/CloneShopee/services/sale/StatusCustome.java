package com.CloneShopee.services.sale;

import java.util.Map;

public class StatusCustome {
    private Integer statusId;
    private Map statusTags;

    public StatusCustome(Integer id, Map tags) {
        this.statusId = id;
        statusTags = tags;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Map getStatusTags() {
        return statusTags;
    }

    public void setStatusTags(Map statusTags) {
        this.statusTags = statusTags;
    }

}
