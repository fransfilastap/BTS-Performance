/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.repository;

import com.huawei.gsm.entity.Frequency;
import com.huawei.gsm.util.response.gis.SectorMarker;
import com.huawei.gsm.util.response.gis.SiteMarker;
import java.util.List;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */
public interface SiteMonitoringRepositoryContract {
    
    public List<SiteMarker> getSitesCondition();
    public List<SectorMarker> getCellConditionByFrequency(Frequency frequency);

}
