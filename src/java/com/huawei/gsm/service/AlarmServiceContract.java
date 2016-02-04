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
import java.util.List;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */
public interface AlarmServiceContract {
    
    public List<Alarm> getAlarms();
    public List<Alarm> getAlarmBySite(Site site);
    public List<Alarm> getAlarmBySiteId(String id);
    public List<Alarm> getAlarmByCellId(String id);
    public List<Alarm> getAlarmByCell(Cell cell);
    public List<Alarm> getAlarmByCells(List<String> cellsId);
    public AlarmSummary getSiteSummary(Site site);
    public AlarmSummary getSectorSummary( List<String> cellsId );
    
}
