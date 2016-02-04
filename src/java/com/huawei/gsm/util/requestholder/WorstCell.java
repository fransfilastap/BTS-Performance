/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.util.requestholder;

import com.huawei.gsm.entity.Cell;

/**
 *
 * @author Administrator
 */
public class WorstCell {
    
    private Cell cell;
    private float value;

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public WorstCell() {
    }
    
    
    
}
