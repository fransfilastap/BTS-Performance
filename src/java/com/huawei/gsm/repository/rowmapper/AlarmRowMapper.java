/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.huawei.gsm.repository.rowmapper;

import com.huawei.gsm.entity.Alarm;
import com.huawei.gsm.entity.Severity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author franspotter
 */
public class AlarmRowMapper implements RowMapper<Alarm>{
    
    private Map<Integer,String> alarmStatus;

    public AlarmRowMapper() {
        
        alarmStatus = new HashMap<>();
        
        alarmStatus.put( 0 , "Not dispatched");
        alarmStatus.put( 1 , "Dispatching");
        alarmStatus.put( 2 , "Dispatch succeed");
        alarmStatus.put( 3 , "Dispatch failed");
        alarmStatus.put( 4 , "TT Planing");
        alarmStatus.put( 5 , "TT Required");
        alarmStatus.put( 6 , "Cancel dispatch");
        alarmStatus.put( 7 , "Canceled");
        alarmStatus.put( 8 , "Closed");
        alarmStatus.put( 9 , "TT Associating");
        alarmStatus.put( 10 , "TT Associate Succeed");
        alarmStatus.put( 11 , "TT Associate Failed");
        alarmStatus.put( 12 , "Deleted");
        alarmStatus.put( 13 , "TT Deassociated Success");
        alarmStatus.put( 14 , "TT Deassociated Failed");
        alarmStatus.put( 20 , "");
        alarmStatus.put( 21 , "Assigned");
        alarmStatus.put( 22 , "WIP");
        alarmStatus.put( 23 , "Pending");
        alarmStatus.put( 24 , "Resolved");
        alarmStatus.put( 100 , "Absorbed");
        alarmStatus.put( 101 , "Deleted");
        alarmStatus.put( 101 , "Completed");
        
    }
    

    @Override
    public Alarm mapRow(ResultSet rs, int i) throws SQLException {
        
        Alarm alarm = new Alarm();
        
        alarm.setAlarmName( rs.getString( "AlarmName" ) );
        alarm.setSiteName( rs.getString( "SiteName" ) );
        alarm.setSiteId( rs.getString( "SiteID" ) );
        alarm.setTicketId(rs.getString("TicketId") );
        alarm.setTicketStatus( alarmStatus.get(rs.getInt("TicketStatus" )) );
        alarm.setCellId( rs.getString( "cellId" ) );
        alarm.setEMSAlarmId( rs.getString( "EMSAlarmId" ) );
        alarm.setSummary( rs.getString( "Summary" ) );
        alarm.setSeverity( rs.getString( "Severity" ).equalsIgnoreCase("critical") ? Severity.CRITICAL : ( rs.getString("Severity").equalsIgnoreCase("major") ? Severity.MAJOR : Severity.MINOR ));
        alarm.setLastOccurence( rs.getTimestamp( "LastOccurrence" ) );
        alarm.setNode( rs.getString( "Node" ) );
        alarm.setNodeAlias( rs.getString( "NodeAlias" ) );
        alarm.setParentNode( rs.getString( "ParentNode" ) );
        
        return alarm;
    
    }
    
}
