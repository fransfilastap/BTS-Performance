/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.service;

import com.huawei.gsm.entity.Frequency;
import com.huawei.gsm.entity.Site;
import com.huawei.gsm.util.response.gis.CellMarker;
import com.huawei.gsm.util.response.gis.SectorMarker;
import com.huawei.gsm.util.response.gis.SiteMarker;
import com.huawei.gsm.util.response.gis.Traffic;
import java.util.List;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */
public interface SiteMonitoringServiceContract {
    public List<SiteMarker> getSitesCondition();
    public List<SectorMarker> getCellConditionByFrequency(Frequency frequency);

}
