/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.util.requestholder;

import org.springframework.stereotype.Component;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */
@Component
public class CellJson {
    
    private String cellIndex;
    private String cellName;
    private String frequency;

    public CellJson() {
    }


    public String getCellIndex() {
        return cellIndex;
    }

    public void setCellIndex(String cellIndex) {
        this.cellIndex = cellIndex;
    }

    public String getCellName() {
        return cellName;
    }

    public void setCellName(String CellName) {
        this.cellName = CellName;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
    
    
}
