/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.util.datatable;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */
public class DataTableColumnHolder {
    private String data;
    private String name;
    private Boolean orderable = true;
    private Boolean searchable = true;
    private DataTableSearchHolder search;

    public DataTableColumnHolder() {
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getOrderable() {
        return orderable;
    }

    public void setOrderable(Boolean orderable) {
        this.orderable = orderable;
    }

    public Boolean getSearchable() {
        return searchable;
    }

    public void setSearchable(Boolean searchable) {
        this.searchable = searchable;
    }

    public DataTableSearchHolder getSearch() {
        return search;
    }

    public void setSearch(DataTableSearchHolder search) {
        this.search = search;
    }
    
    
    
}
