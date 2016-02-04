/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.repository;

import com.huawei.gsm.entity.AccessType;
import com.huawei.gsm.entity.Cell;
import com.huawei.gsm.entity.KQI;
import com.huawei.gsm.entity.KQIType;
import com.huawei.gsm.entity.WorstCell;
import com.huawei.gsm.util.response.gis.LineChart;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */
public interface KeyQualityIndexRepositoryContract {
    
    public KQI getGoldenSiteKQI();
    public KQI getCellKQI(Cell cell);
    public Map<KQIType,LineChart> getSectorKQI(String cells);
    public List<WorstCell> getTop5PageBrowsingSuccessRate(AccessType accessType);
    public List<WorstCell> getTop5PageBrowsingDelay(AccessType accessType);
    public List<WorstCell> getTop5VideoStreamingStartSuccessRate(AccessType accessType);
    public List<WorstCell> getTop5VideoStreamingStartDelay(AccessType accessType);
    public LineChart getCellKQI(String listToString, KQIType type);
    
    
}
