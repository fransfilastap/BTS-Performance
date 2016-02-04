/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.huawei.gsm.service;

import com.huawei.gsm.entity.Alarm;
import com.huawei.gsm.entity.AlarmSummary;
import com.huawei.gsm.entity.Cell;
import com.huawei.gsm.entity.Site;
import com.huawei.gsm.repository.AlarmRepositoryContract;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author franspotter
 */
@Service
public class AlarmService implements AlarmServiceContract{
    
    private @Autowired AlarmRepositoryContract alarmRepository;

    @Override
    public List<Alarm> getAlarms() {
        return alarmRepository.getAlarms();
    }

    @Override
    public List<Alarm> getAlarmBySite(Site site) {
        return alarmRepository.getAlarmBySite(site);
    }

    @Override
    public List<Alarm> getAlarmByCell(Cell cell) {
        return alarmRepository.getAlarmByCell(cell);
    }

    @Override
    public AlarmSummary getSiteSummary(Site site) {
        return alarmRepository.getSiteSummary(site);
    }

    @Override
    public List<Alarm> getAlarmBySiteId(String id) {
        return alarmRepository.getAlarmBySiteId(id);
    }

    @Override
    public List<Alarm> getAlarmByCellId(String id) {
        return alarmRepository.getAlarmByCellId(id);
    }

    @Override
    public AlarmSummary getSectorSummary(List<String> cells) {

        StringBuilder builder = new StringBuilder();
        Iterator<String> cellIter = cells.iterator();
        
        while ( cellIter.hasNext() ) {
            builder.append("\""+cellIter.next()+"\",");
        }
        return alarmRepository.getSectorSummary( builder.deleteCharAt( builder.length()-1 ).toString() );
    }

    @Override
    public List<Alarm> getAlarmByCells(List<String> cells) {

        StringBuilder builder = new StringBuilder();
        Iterator<String> cellIter = cells.iterator();
        
        while ( cellIter.hasNext() ) {
            builder.append("\""+cellIter.next()+"\",");
        }
        
        return alarmRepository.getAlarmByCell(builder.deleteCharAt( builder.length()-1 ).toString());
    }

}
