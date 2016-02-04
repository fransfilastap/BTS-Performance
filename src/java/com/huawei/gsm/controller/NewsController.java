/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.huawei.gsm.controller;

import com.huawei.gsm.entity.AccessType;
import com.huawei.gsm.entity.Alarm;
import com.huawei.gsm.entity.AlarmSummary;
import com.huawei.gsm.entity.KPI;
import com.huawei.gsm.entity.KQI;
import com.huawei.gsm.entity.KQIType;
import com.huawei.gsm.entity.Site;
import com.huawei.gsm.entity.WorstCell;
import com.huawei.gsm.service.AlarmServiceContract;
import com.huawei.gsm.service.KeyPerformanceIndexServiceContract;
import com.huawei.gsm.service.KeyQualityIndexServiceContract;
import com.huawei.gsm.util.requestholder.SectorKQIChartRequestHolder;
import com.huawei.gsm.util.response.gis.LineChart;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author franspotter
 */
@Controller
public class NewsController {
    
    private @Autowired AlarmServiceContract alarmService;
    private @Autowired KeyQualityIndexServiceContract kqiService;
    private @Autowired KeyPerformanceIndexServiceContract kpiService;
    
    @RequestMapping(value = {"/news/alarm"},method = RequestMethod.GET)
    public @ResponseBody List<Alarm> updateAlarm(){
        return alarmService.getAlarms();
    }
    
    @RequestMapping(value = {"/sitealarm/summary/{siteId}"}, produces = { "application/json" } , method = RequestMethod.GET)
    public @ResponseBody AlarmSummary getSiteSummary( @PathVariable String siteId ){
        return alarmService.getSiteSummary( new Site(siteId, siteId) );
    }
    
    @RequestMapping(value = {"/sitealarm/detail/{siteId}"}, produces = { "application/json" } , method = RequestMethod.GET)
    public @ResponseBody List<Alarm> getSiteAlarmDetail( @PathVariable String siteId ){
        return alarmService.getAlarmBySiteId( siteId );
    }
    
    @RequestMapping( value = {"/news/kqi"}, produces = {"application/json"} , method = RequestMethod.GET )
    public @ResponseBody KQI getKQINews(){
        return kqiService.getGoldenSiteKQI();
    
    }
    
    @RequestMapping( value = {"/news/kqi/{type}/{ne}"}, produces = {"application/json"} , method = RequestMethod.GET )
    public @ResponseBody List<WorstCell> getKQIWorstCells(@PathVariable String type, @PathVariable String ne){
        AccessType accType = null;
        if( ne.equalsIgnoreCase("AccessType_2G") ){
            accType = AccessType.AccessType_2G;
        }else if( ne.equalsIgnoreCase("AccessType_3G") ){
            accType = AccessType.AccessType_3G;
        }else{
            accType = AccessType.AccessType_4G;
        }
        
        final List<WorstCell> worstCells = new ArrayList<WorstCell>();
        
        if( type.equalsIgnoreCase("pbsr") ){
            List<WorstCell> cell = kqiService.getTop5PageBrowsingSuccessRate( accType );
            cell.stream().forEachOrdered( c -> {
                worstCells.add(c);
                
            } );            
        }else if( type.equalsIgnoreCase("pbd") ){
            List<WorstCell> cell = kqiService.getTop5PageBrowsingDelay( accType );
            cell.stream().forEachOrdered( c -> {
                worstCells.add(c);
                
            } );        
        }else if( type.equalsIgnoreCase("vsssr") ){
            List<WorstCell> cell = kqiService.getTop5VideoStreamingStartSuccessRate( accType );
            cell.stream().forEachOrdered( c -> {
                worstCells.add(c);
                
            } );        
        }else if( type.equalsIgnoreCase("vssd") ){
            List<WorstCell> cell = kqiService.getTop5VideoStreamingStartDelay( accType );
            cell.stream().forEachOrdered( c -> {
                worstCells.add(c);
            } );
            
        }
        
        return worstCells;
    }

    @RequestMapping( value = {"/news/kpi/{type}"}, produces = {"application/json"} , method = RequestMethod.GET )
    public @ResponseBody List<WorstCell> getKPIWorstCells(@PathVariable String type){
        
        final List<WorstCell> worstCells = new ArrayList<>();
        
        if( type.equalsIgnoreCase("rcsur2g") ){
            List<WorstCell> cell = kpiService.get2GTop5WorstCellCallSetupSuccessRate();
            cell.stream().forEachOrdered( c -> {
                worstCells.add(c);
                
            } );            
        }else if( type.equalsIgnoreCase("cmbtd2g") ){
            List<WorstCell> cell = kpiService.get2GTop5WorstCellCallMinutesBetweenDrop();
            cell.stream().forEachOrdered( c -> {
                worstCells.add(c);
                
            } );        
        }else if( type.equalsIgnoreCase("rcsur3g") ){
            List<WorstCell> cell = kpiService.get3GTop5WorstCellCallSetupSuccessRate();
            cell.stream().forEachOrdered( c -> {
                worstCells.add(c);
                
            } );        
        }else if( type.equalsIgnoreCase("ranpsacc3g") ){
            List<WorstCell> cell = kpiService.get3GTop5WorstCellRANPSAccessibility();
            cell.stream().forEachOrdered( c -> {
                worstCells.add(c);
            } );
            
        }else if( type.equalsIgnoreCase("cmbd3g") ){
            List<WorstCell> cell = kpiService.get3GTop5WorstCellCallMinutesBetweenDrop();
            cell.stream().forEachOrdered( c -> {
                worstCells.add(c);
            } );
            
        }else if( type.equalsIgnoreCase("mbps3g") ){
            List<WorstCell> cell = kpiService.get3GTop5WorstCellMinutesBetweenPSAbnormalReleases();
            cell.stream().forEachOrdered( c -> {
                worstCells.add(c);
            } );
            
        }else if( type.equalsIgnoreCase("esr4g") ){
            List<WorstCell> cell = kpiService.get4GTopWorstCellEstablishmentSuccessRate();
            cell.stream().forEachOrdered( c -> {
                worstCells.add(c);
            } );
            
        }else if( type.equalsIgnoreCase("4gmbear") ){
            List<WorstCell> cell = kpiService.get4GTopWorstCellMinutesBetweenERABAbnormalReleases();
            cell.stream().forEachOrdered( c -> {
                worstCells.add(c);
            } );
        }
        return worstCells;
    }    
    
    @RequestMapping(value = { "/news/kpi" }, produces = { "application/json" } , method = RequestMethod.GET )
    public @ResponseBody KPI getGroupKPI(){
        return kpiService.getGroupKPI();
    }
    
    
    @RequestMapping( value = { "/sectoralarm" } , produces = { "application/json" }, consumes = "application/json", method = RequestMethod.POST  )
    public @ResponseBody List<Alarm> getSectorALarm( @RequestBody List<String> cellid ){
        if( cellid.isEmpty() ){
            return new ArrayList<>();
        }
        return alarmService.getAlarmByCells( cellid );
    }
    
    @RequestMapping( value = { "/sectorAlarmSummary" } , produces = { "application/json" }, consumes = "application/json", method = RequestMethod.POST  )
    public @ResponseBody AlarmSummary getSectorSummary( @RequestBody List<String> cellid ){
        if( cellid.isEmpty() ){
            return new AlarmSummary();
        }
        
        return alarmService.getSectorSummary(cellid );
    }
    
    
    @RequestMapping( value={"/cellkqichart"}, produces={"application/json"}, method=RequestMethod.POST )
    public @ResponseBody LineChart getSectorKQIValuesForGoingDay( @RequestBody SectorKQIChartRequestHolder sectorKQIChartRequestHolder){
        return kqiService.getCellKQI( sectorKQIChartRequestHolder.getCells(),sectorKQIChartRequestHolder.getType() );
    }
   
    
    
    
    

}
