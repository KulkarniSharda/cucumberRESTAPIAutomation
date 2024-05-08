package com.agileautomation.pojo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor

public class UsersResponsePojo {
    private Long page;
    private Long per_page;
    private Long total;
    private Long total_pages;
    private List<Map<String, Object>> data;
    private Map<String, String> support;

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Long getPer_page() {
        return per_page;
    }

    public void setPer_page(Long per_page) {
        this.per_page = per_page;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(Long total_pages) {
        this.total_pages = total_pages;
    }

    public List<Map<String, Object>> getData() {
        return data;
    }

    public void setData(List<Map<String, Object>> data) {
        this.data = data;
    }

    public Map<String, String> getSupport() {
        return support;
    }

    public void setSupport(Map<String, String> support) {
        this.support = support;
    }
}

