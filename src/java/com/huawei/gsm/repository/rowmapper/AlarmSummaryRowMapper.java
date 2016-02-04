/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.repository.rowmapper;

import com.huawei.gsm.entity.AlarmSummary;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */
public class AlarmSummaryRowMapper implements RowMapper<AlarmSummary>{

    @Override
    public AlarmSummary mapRow(ResultSet rs, int i) throws SQLException {
        AlarmSummary alarmSummary = new AlarmSummary();
                     alarmSummary.setCritical( rs.getInt("Critical") );
                     alarmSummary.setMajor( rs.getInt("Major") );
                     alarmSummary.setMinor( rs.getInt("Minor") );
        
        return alarmSummary;
    }
    
}
