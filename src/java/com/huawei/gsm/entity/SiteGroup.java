/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.entity;

/**
 *
 * @author fransfilastap
 */
public enum SiteGroup {
    GOLDEN_SITE ("GOLDEN_SITE"), EVENT_SITE("EVENT_SITE");
    
    private final String groupName;
    
    private SiteGroup(String groupName){
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return this.groupName;
    }
    
    
    
}
