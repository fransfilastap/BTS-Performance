/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.service;

import com.huawei.gsm.entity.Cell;
import com.huawei.gsm.entity.Site;
import com.huawei.gsm.repository.CellRepositoryContract;
import com.huawei.gsm.repository.SiteRepositoryContract;
import com.huawei.gsm.util.response.SiteExcelWrapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */
@Service
@Transactional
public class SiteService implements SiteServiceContract{
    
    private final SiteRepositoryContract siteRepository;
    private final CellRepositoryContract cellRepository;
    
    @Autowired
    public SiteService( SiteRepositoryContract siteRepository, CellRepositoryContract cellRepository ){
        this.siteRepository = siteRepository;
        this.cellRepository = cellRepository;
    }

    @Override
    public Site getSite(int id) {
        return siteRepository.getSite(id);
    }

    @Override
    public int updateSite(int id, Site site) {
        return siteRepository.updateSite(site);
    }

    @Override
    public int deleteSite(Site delete) {
        return siteRepository.deleteSite(delete);
    }

    @Override
    public List<Site> getAllSite() {
        return siteRepository.getAllSites();
    }

    @Override
    public int saveSite(Site site) {
        List<Cell> cells = site.getCells();
        int generatedKey = siteRepository.saveSite(site);
        site.setId(generatedKey);
        cells.stream().map((get) -> {
            get.setSite(site);
            return get;
        }).forEach((get) -> {
            cellRepository.saveCell(get);
        });
        
        return generatedKey;
        
    }

    @Override
    public int getTotalRecords() {
        return siteRepository.getTotalRecords();
    }

    @Override
    public List<Site> getListByRequest(String keyword, int page, int row, String columnOrder, String order) {
        return siteRepository.getListByRequest(keyword, page, row, columnOrder, order);
    }

    @Override
    public int getTotalFilteredRecords(String filter) {
        return siteRepository.getFilteredSize(filter);
    }

    @Override
    public List<SiteExcelWrapper> exportToExcel() {
        return siteRepository.exportToExcel();
    }

    @Override
    public void saveSiteBatch(List<Site> sites) {
        siteRepository.saveSiteBatch(sites);
    }
    
}
