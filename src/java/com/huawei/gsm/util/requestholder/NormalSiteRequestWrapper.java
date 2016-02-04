/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.util.requestholder;

import java.util.List;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */
public class NormalSiteRequestWrapper {

    private SiteJson site;
    private List<CellJson> cells;

    public NormalSiteRequestWrapper() {
    }

    public SiteJson getSite() {
        return site;
    }

    public void setSite(SiteJson site) {
        this.site = site;
    }

    public List<CellJson> getCells() {
        return cells;
    }

    public void setCells(List<CellJson> cells) {
        this.cells = cells;
    }

    
}
