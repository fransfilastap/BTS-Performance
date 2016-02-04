/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.entity;

import java.sql.Timestamp;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */
public class TimeSpan {
    
    private Timestamp start;
    private Timestamp end;

    public TimeSpan(Timestamp start, Timestamp end) {
        this.start = start;
        this.end = end;
    }

    public TimeSpan() {
    }
    
    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }
    
    
    
}
