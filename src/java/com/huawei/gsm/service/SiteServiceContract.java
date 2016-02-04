/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.service;

import com.huawei.gsm.entity.Frequency;
import com.huawei.gsm.entity.Site;
import com.huawei.gsm.util.response.SiteExcelWrapper;
import com.huawei.gsm.util.response.gis.CellMarker;
import java.util.List;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */
public interface SiteServiceContract {
    public Site getSite(int id);
    public int updateSite(int id, Site site);
    public int deleteSite(Site delete);
    public List<Site> getAllSite();
    public List<Site> getListByRequest(String keyword, int page, int row,String columnOrder,String order);
    public int getTotalRecords();
    public int getTotalFilteredRecords(String filter);   
    public int saveSite(Site site);
    public List<SiteExcelWrapper> exportToExcel();
    public void saveSiteBatch(List<Site> sites);
}
