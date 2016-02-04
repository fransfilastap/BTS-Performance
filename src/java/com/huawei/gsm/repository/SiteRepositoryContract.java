/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.repository;

import com.huawei.gsm.entity.Frequency;
import com.huawei.gsm.entity.Site;
import com.huawei.gsm.util.response.SiteExcelWrapper;
import java.util.List;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */
public interface SiteRepositoryContract {
    
    public static String ORDER_ASC = "ASC";
    public static String ORDER_DESC = "DESC";
    public Site getSite(int id);
    public int deleteSite(Site delete);
    public int saveSite(Site Site);
    public int updateSite(Site site);
    public List<Site> getAllSites(); 
    public int getTotalRecords();
    public List<Site> getListByRequest(String keyword, int page, int row,String columnOrder,String order);
    public int getFilteredSize(String keyword);
    public List<SiteExcelWrapper> exportToExcel();
    public void saveSiteBatch(List<Site> sites);
    
}
