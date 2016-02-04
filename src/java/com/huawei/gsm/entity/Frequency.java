/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.entity;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */
public enum Frequency {
    GSM ("GSM"),
    UMTS ("UMTS"),
    LTE ("LTE");
    
    private final String freq;
    
    private Frequency( String freq ){
        this.freq = freq;
    }

    @Override
    public String toString() {
        return this.freq; 
    }
    
    public boolean equalsName(String otherFreq){
        return ( otherFreq == null ) ? false : otherFreq.equals(freq);
    }
    
}
