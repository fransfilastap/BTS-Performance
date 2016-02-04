/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.entity;

import com.huawei.gsm.entity.Cell;
import com.huawei.gsm.entity.KPI;

/**
 *
 * @author Administrator
 */
public class CellKPI {
    
    private Cell cell;
    private KPI kpi;

    public CellKPI() {
    }

    
    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public KPI getKpi() {
        return kpi;
    }

    public void setKpi(KPI kpi) {
        this.kpi = kpi;
    }
    
    
}
