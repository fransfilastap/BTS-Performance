/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.controller;

import com.huawei.gsm.entity.Frequency;
import com.huawei.gsm.entity.KPI;
import com.huawei.gsm.entity.KQI;
import com.huawei.gsm.entity.KQIType;
import com.huawei.gsm.entity.Site;
import com.huawei.gsm.service.KeyPerformanceIndexServiceContract;
import com.huawei.gsm.service.KeyQualityIndexServiceContract;
import com.huawei.gsm.service.SiteMonitoringServiceContract;
import com.huawei.gsm.service.SiteServiceContract;
import com.huawei.gsm.util.requestholder.SectorKPIChartRequestHolder;
import com.huawei.gsm.util.requestholder.SectorKPIRequestHolder;
import com.huawei.gsm.util.response.gis.LineChart;
import com.huawei.gsm.util.response.gis.SectorMarker;
import com.huawei.gsm.util.response.gis.SiteMarker;
import com.huawei.gsm.util.response.gis.Traffic;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */
@Controller
public class MapController {
    
    
    private @Autowired SiteServiceContract siteService;
    private @Autowired SiteMonitoringServiceContract siteMonitoringService;
    private @Autowired KeyPerformanceIndexServiceContract keyPerformanceIndexService;
    private @Autowired KeyQualityIndexServiceContract keyQualityIndexService;
    //@Autowired
    //CellServiceContract cellService;
    
    @RequestMapping( value={"/goldensites","/golden-sites"}, method = GET, produces={"application/json"} )
    public @ResponseBody List<SiteMarker> goldenSite(){
        List<SiteMarker> sites = siteMonitoringService.getSitesCondition();
        return sites;
    }
    
    @RequestMapping(value={"/cells/{frequency}"},method = GET,produces={"application/json"})
    @ResponseBody
    public List<SectorMarker> getCells(@PathVariable("frequency") String freq ){
        
        Frequency frequency;
        if( freq.equalsIgnoreCase("gsm") ) frequency = Frequency.GSM;
        else if( freq.equalsIgnoreCase("umts") ) frequency = Frequency.UMTS;
        else if( freq.equalsIgnoreCase("lte") ) frequency = Frequency.LTE;
        else frequency = Frequency.LTE;
        
        List<SectorMarker> sites = siteMonitoringService.getCellConditionByFrequency(frequency );
        return sites;
    }

    @RequestMapping( value = { "/sitetraffic/{site}" }, produces = {"application/json"}, method = RequestMethod.GET  )
    public @ResponseBody Traffic getSiteTraffic( @PathVariable String site ){
        return keyPerformanceIndexService.getSiteTrafic(new Site(site, site));
    }
    
    @RequestMapping( value = {"/sectortraffic"}, produces = {"application/json"}, method = RequestMethod.POST, consumes = "application/json" )
    public @ResponseBody Traffic getSectorTraffic( @RequestBody List<String> cells ){
        return keyPerformanceIndexService.getSectorTraffic(cells);
    }
    
    @RequestMapping( value = {"/sectorkpi"}, produces = "application/json", method = RequestMethod.POST, consumes = "application/json" )
    public @ResponseBody KPI getSectorKPI( @RequestBody SectorKPIRequestHolder sector ){
    
        if( sector.getTechnology() == Frequency.GSM ){
            Traffic traffic = keyPerformanceIndexService.getSectorTraffic( sector.getCells() );
            KPI kpi = keyPerformanceIndexService.getCellKPI( sector.getCells() );
            kpi.getKpi2g().setCsTraffic( traffic.getCsTraffic() );
            kpi.getKpi2g().setPsTraffic( traffic.getPsTraffic() );
            
            return kpi;            
        }else if( sector.getTechnology() == Frequency.UMTS ){
            Traffic traffic = keyPerformanceIndexService.getSectorTraffic( sector.getCells() );
            KPI kpi = keyPerformanceIndexService.getCellKPI( sector.getCells() );
            kpi.getKpi3g().setCsTraffic( traffic.getCsTraffic() );
            kpi.getKpi3g().setPsTraffic( traffic.getPsTraffic() );
            
            return kpi;
        }else if( sector.getTechnology() == Frequency.LTE ){
            Traffic traffic = keyPerformanceIndexService.getSectorTraffic( sector.getCells() );
            KPI kpi = keyPerformanceIndexService.getCellKPI( sector.getCells() );
            
            return kpi;            
        }
        
        return new KPI();
    }
    
    @RequestMapping( value = {"/sectorkpichart"} , produces = {"application/json"}, method = RequestMethod.POST )
    public @ResponseBody LineChart  getSectorKPIValueForGoingDay( @RequestBody SectorKPIChartRequestHolder sector  ){
        
        return keyPerformanceIndexService.getSectorKPIValueForGoingDay(sector.getCells(), sector.getType());
        
    }
    
    @RequestMapping( value={"/sectorkqichart"}, produces={"application/json"}, method=RequestMethod.POST )
    public @ResponseBody Map<KQIType, LineChart> getSectorKQIValuesForGoingDay( @RequestBody List<String> sector ){
        return keyQualityIndexService.getSectorKQI( sector );
    }
   
}
