/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.util.response.gis;

import com.huawei.gsm.entity.Cell;
import com.huawei.gsm.entity.Severity;

/**
 *
 * @author Administrator
 */
public class CellMarker extends Cell{
    
    private Severity severity;

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }  
    
}
