/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.huawei.gsm.repository;

import com.huawei.gsm.entity.Alarm;
import com.huawei.gsm.entity.AlarmSummary;
import com.huawei.gsm.entity.Cell;
import com.huawei.gsm.entity.Site;
import com.huawei.gsm.repository.rowmapper.AlarmRowMapper;
import com.huawei.gsm.repository.rowmapper.AlarmSummaryRowMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author franspotter
 */

@Repository
public class AlarmRepository implements AlarmRepositoryContract{
    
    private @Autowired JdbcTemplate jdbcTemplate;
    private static final String ALARM_NEWS = "SELECT * FROM alarms INNER JOIN `sites` ON sites.siteid = alarms.siteid \n" +
"WHERE sites.site_group = \"GOLDEN_SITE\" AND severity = 'critical' ";
    private static final String ALARM_BY_SITE = "SELECT * FROM alarms WHERE SiteName = ?";
    private static final String ALARM_BY_SITEID = "SELECT * FROM alarms WHERE SiteId = ?";
    private static final String ALARM_BY_CELL_ID = "SELECT * FROM alarms WHERE nodeAlias IN (?)";
    private static final String ALARM_SUMMARY = "SELECT COUNT(\n" +
                                                        "CASE WHEN severity = \"CRITICAL\" THEN 1 ELSE NULL END\n" +
                                                        ") AS Critical , \n" +
                                                        "COUNT(\n" +
                                                        "CASE WHEN severity = \"Major\" THEN 1 ELSE NULL END\n" +
                                                        ")	 AS Major ,  \n" +
                                                        "COUNT(\n" +
                                                        "CASE WHEN severity = \"Minor\" THEN 1 ELSE NULL END\n" +
                                                        ")         AS Minor FROM alarms\n" +
                                                        "        WHERE siteId = ? GROUP BY siteid";
    private static final String CELL_SUMMARY = "SELECT COUNT(\n" +
                                            "CASE WHEN severity = \"CRITICAL\" THEN 1 ELSE NULL END\n" +
                                            ") AS Critical , \n" +
                                            "COUNT(\n" +
                                            "CASE WHEN severity = \"Major\" THEN 1 ELSE NULL END\n" +
                                            ")	 AS Major ,  \n" +
                                            "COUNT(\n" +
                                            "CASE WHEN severity = \"Minor\" THEN 1 ELSE NULL END\n" +
                                            ")         AS Minor FROM alarms\n" +
                                            "        WHERE nodeAlias IN(?)";
    


    @Override
    public List<Alarm> getAlarms() {
        return jdbcTemplate.query(ALARM_NEWS, new AlarmRowMapper());
    }

    @Override
    public List<Alarm> getAlarmBySite(Site site) {
        return jdbcTemplate.query( ALARM_BY_SITE, new Object[]{ site.getSiteId()}, new AlarmRowMapper());
    }

    @Override
    public List<Alarm> getAlarmByCell(Cell cell) {
        return getAlarmByCellId( cell.getCellId() );
    }

    @Override
    public List<Alarm> getAlarmBySiteId(String id) {
         return jdbcTemplate.query( ALARM_BY_SITEID, new Object[]{ id }, new AlarmRowMapper());
    }

    @Override
    public List<Alarm> getAlarmByCellId(String id) {
        return jdbcTemplate.query( ALARM_BY_CELL_ID, new Object[]{ id }, new AlarmRowMapper());
    }

    @Override
    public AlarmSummary getSiteSummary(Site site) {
        return jdbcTemplate.queryForObject(ALARM_SUMMARY, new Object[]{ site.getSiteId() }, new AlarmSummaryRowMapper() );
    }

    @Override
    public AlarmSummary getSectorSummary(String cells) {
        AlarmSummary summary = null;
        try{
            String cellsum = "SELECT COUNT(\n" +
                                            "CASE WHEN severity = \"CRITICAL\" THEN 1 ELSE NULL END\n" +
                                            ") AS Critical , \n" +
                                            "COUNT(\n" +
                                            "CASE WHEN severity = \"Major\" THEN 1 ELSE NULL END\n" +
                                            ")	 AS Major ,  \n" +
                                            "COUNT(\n" +
                                            "CASE WHEN severity = \"Minor\" THEN 1 ELSE NULL END\n" +
                                            ")         AS Minor FROM alarms\n" +
                                            "        WHERE nodeAlias IN("+cells+")";
            
            summary = jdbcTemplate.queryForObject(cellsum, new AlarmSummaryRowMapper() );
        }
        catch(org.springframework.dao.EmptyResultDataAccessException ex){
            summary = new AlarmSummary();
            summary.setCritical(0);
            summary.setMinor(0);
            summary.setCritical(0);
        }
        return summary;
    }

    @Override
    public List<Alarm> getAlarmByCell(String cells) {
        String asem = "SELECT * FROM alarms WHERE nodeAlias IN ("+cells+")";
        return jdbcTemplate.query( asem, new AlarmRowMapper());
    }
    
    
}
