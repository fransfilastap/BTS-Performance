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
public class LineChart {
    
    private List<LineChartSerie> series;
    private List<LineChartSerie> baseLines;

    public LineChart() {
    }
    
    public List<LineChartSerie> getSeries() {
        return series;
    }

    public void setSeries(List<LineChartSerie> series) {
        this.series = series;
    }

    public List<LineChartSerie> getBaseLines() {
        return baseLines;
    }

    public void setBaseLines(List<LineChartSerie> baseLines) {
        this.baseLines = baseLines;
    }


    
    
    
}
