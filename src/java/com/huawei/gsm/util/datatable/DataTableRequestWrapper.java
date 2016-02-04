/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.util.datatable;

import java.util.List;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */
public class DataTableRequestWrapper {

    private List<DataTableColumnHolder> columns;
    private int draw;
    private int length;
    private List<DataTableColumnOrderHolder> order;
    private DataTableSearchValueHolder search;
    private int start;

    public DataTableRequestWrapper() {
    }

    
    public List<DataTableColumnHolder> getColumns() {
        return columns;
    }

    public void setColumns(List<DataTableColumnHolder> columns) {
        this.columns = columns;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public List<DataTableColumnOrderHolder> getOrder() {
        return order;
    }

    public void setOrder(List<DataTableColumnOrderHolder> order) {
        this.order = order;
    }

    public DataTableSearchValueHolder getSearch() {
        return search;
    }

    public void setSearch(DataTableSearchValueHolder search) {
        this.search = search;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }
    
    
    
    
    
}
