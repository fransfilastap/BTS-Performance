/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.util.response.gis;

import java.util.List;

/**
 *
 * @author Administrator
 */
public class LineChartSerie {
    
    private String name;
    private List<ChartValue> data;

    public LineChartSerie() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChartValue> getData() {
        return data;
    }

    public void setData(List<ChartValue> data) {
        this.data = data;
    }
    
}
