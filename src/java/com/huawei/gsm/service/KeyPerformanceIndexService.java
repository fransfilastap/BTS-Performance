/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.service;

import com.huawei.gsm.entity.KPI;
import com.huawei.gsm.entity.KPIType;
import com.huawei.gsm.repository.KeyPerformanceIndexRepositoryContract;
import com.huawei.gsm.entity.Site;
import com.huawei.gsm.entity.WorstCell;
import com.huawei.gsm.util.response.gis.LineChart;
import com.huawei.gsm.util.response.gis.LineChartSerie;
import com.huawei.gsm.util.response.gis.Traffic;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */
@Service
public class KeyPerformanceIndexService implements KeyPerformanceIndexServiceContract{

    private @Autowired KeyPerformanceIndexRepositoryContract kpiRepository;
    
    @Override
    public List<WorstCell> get2GTop5WorstCellCallSetupSuccessRate() {
        return kpiRepository.get2GTop5WorstCellCallSetupSuccessRate();
    }

    @Override
    public List<WorstCell> get2GTop5WorstCellCallMinutesBetweenDrop() {
        return kpiRepository.get2GTop5WorstCellCallMinutesBetweenDrop();
    }

    @Override
    public List<WorstCell> get3GTop5WorstCellCallSetupSuccessRate() {
        return kpiRepository.get3GTop5WorstCellCallSetupSuccessRate();
    }

    @Override
    public List<WorstCell> get3GTop5WorstCellCallMinutesBetweenDrop() {
        return kpiRepository.get3GTop5WorstCellCallMinutesBetweenDrop();
    }

    @Override
    public List<WorstCell> get3GTop5WorstCellRANPSAccessibility() {
        return kpiRepository.get3GTop5WorstCellRANPSAccessibility();
    }

    @Override
    public List<WorstCell> get3GTop5WorstCellMinutesBetweenPSAbnormalReleases() {
        return kpiRepository.get3GTop5WorstCellMinutesBetweenPSAbnormalReleases();
    }

    @Override
    public List<WorstCell> get4GTopWorstCellEstablishmentSuccessRate() {
        return kpiRepository.get4GTopWorstCellEstablishmentSuccessRate();
    }

    @Override
    public List<WorstCell> get4GTopWorstCellMinutesBetweenERABAbnormalReleases() {
        return kpiRepository.get4GTopWorstCellMinutesBetweenERABAbnormalReleases();
    }

    @Override
    public KPI getGroupKPI() {
        return kpiRepository.getGroupKPI();
    }

    @Override
    public KPI getCellKPI(List<String> cells) {
        String scells = listToString(cells);
        
        return kpiRepository.getSectorKPI(scells);
    }

    @Override
    public Traffic getSiteTrafic(Site site) {
        Traffic siteTraffic = new Traffic();
        siteTraffic.setCsTraffic( kpiRepository.getSiteCSTrafic(site) );
        siteTraffic.setPsTraffic( kpiRepository.getSitePSTrafic(site) );
        
        return siteTraffic;
    }

    @Override
    public Traffic getSectorTraffic(List<String> cells) {
        Traffic sectorTraffic = new Traffic();
        String scells = listToString(cells);
        
        sectorTraffic.setCsTraffic( kpiRepository.getSectorCSTraffic( scells ) );
        sectorTraffic.setPsTraffic( kpiRepository.getSectorPSTraffic( scells ) );
    
        return sectorTraffic;
    }

    @Override
    public LineChart  getSectorKPIValueForGoingDay(List<String> cells, KPIType type) {

        return kpiRepository.getSectorKPIValueForGoingDay(listToString(cells), type);
    }
    
    
    private String listToString(List<String> cells){
        StringBuilder builder = new StringBuilder();
        Iterator<String> cellIter = cells.iterator();
        
        while ( cellIter.hasNext() ) {
            builder.append("\""+cellIter.next()+"\",");
        }
        
        String scells = builder.deleteCharAt( builder.length()-1 ).toString();
        
        return scells;
    }

    
}
