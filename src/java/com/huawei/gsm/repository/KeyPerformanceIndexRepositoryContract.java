/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.repository;

import com.huawei.gsm.entity.KPI;
import com.huawei.gsm.entity.KPIType;
import com.huawei.gsm.entity.Site;
import com.huawei.gsm.entity.WorstCell;
import com.huawei.gsm.util.response.gis.LineChart;
import com.huawei.gsm.util.response.gis.LineChartSerie;
import java.util.List;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */
public interface KeyPerformanceIndexRepositoryContract {
    
    public List<WorstCell> get2GTop5WorstCellCallSetupSuccessRate();
    public List<WorstCell> get2GTop5WorstCellCallMinutesBetweenDrop();
    public List<WorstCell> get3GTop5WorstCellCallSetupSuccessRate();
    public List<WorstCell> get3GTop5WorstCellCallMinutesBetweenDrop();
    public List<WorstCell> get3GTop5WorstCellRANPSAccessibility();
    public List<WorstCell> get3GTop5WorstCellMinutesBetweenPSAbnormalReleases();
    public List<WorstCell> get4GTopWorstCellEstablishmentSuccessRate();
    public List<WorstCell> get4GTopWorstCellMinutesBetweenERABAbnormalReleases();
    public KPI getGroupKPI();
    public KPI getSectorKPI(String cells);
    public Double getSiteCSTrafic( Site site );
    public Double getSitePSTrafic(Site site);
    public Double getSectorCSTraffic( String cells );
    public Double getSectorPSTraffic( String cells );    
    public LineChart getSectorKPIValueForGoingDay(String cells, KPIType type);
}
