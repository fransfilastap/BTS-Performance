/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.service;

import com.huawei.gsm.entity.Frequency;
import com.huawei.gsm.entity.Site;
import com.huawei.gsm.repository.SiteMonitoringRepositoryContract;
import com.huawei.gsm.util.response.gis.SectorMarker;
import com.huawei.gsm.util.response.gis.SiteMarker;
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
public class SiteMonitoringService implements SiteMonitoringServiceContract{

    private @Autowired SiteMonitoringRepositoryContract siteMonitoringRepo;
    
    @Override
    public List<SiteMarker> getSitesCondition() {
        return siteMonitoringRepo.getSitesCondition();
    }

    @Override
    public List<SectorMarker> getCellConditionByFrequency(Frequency frequency) {
        return siteMonitoringRepo.getCellConditionByFrequency(frequency);
    }


    
}
