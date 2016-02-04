/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.repository;

import com.huawei.gsm.entity.KPI;
import com.huawei.gsm.entity.KPI.KPI2G;
import com.huawei.gsm.entity.KPI.KPI3G;
import com.huawei.gsm.entity.KPI.KPI4G;
import com.huawei.gsm.entity.KPIBaseLineConstant;
import com.huawei.gsm.entity.KPIType;
import com.huawei.gsm.entity.Site;
import com.huawei.gsm.entity.WorstCell;
import com.huawei.gsm.util.response.gis.ChartValue;
import com.huawei.gsm.util.response.gis.LineChart;
import com.huawei.gsm.util.response.gis.LineChartSerie;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Administrator
 */
@Repository
public class KeyPerformanceIndexRepository implements KeyPerformanceIndexRepositoryContract{
    
    private final static String KPI2G_GOLDENSITE_GROUP = "SELECT `2G_RAN_Call_Set_Up_Success_Rate_Speech`,`2G_Call_Minutes_between_Drop`\n" +
                                                            "FROM `Golden_Site_Cell_Group_GSM_To_GEOL`\n" +
                                                            "WHERE HOUR(`Time`) = HOUR(NOW()) AND DATE(`Time`) = DATE(NOW())";
    private final static String KPI3G_GOLDENSITE_GROUP = "SELECT \n" +
                                                            "`3G_RAN_Call_Set_Up_Success_Rate_Speech`,\n" +
                                                            "`3G_RAN_PS_Accessibility`,\n" +
                                                            "`3G_Call_Minutes_between_Drop`,\n" +
                                                            "`Minutes_between_PS_Abnormal_Releases`\n" +
                                                            "FROM `Golden_Site_Cell_Group_UMTS_To_GEOL`\n" +
                                                            "WHERE HOUR(`Time`) = HOUR(NOW()) AND DATE(`Time`) = DATE(NOW())";
    private final static String KPI4G_GOLDENSITE_GROUP = "SELECT \n" +
                                                        "`4G_Establishment_Success_Rate`,\n" +
                                                        "`minutes_between_E_RAB_abnormal_releases`\n" +
                                                        "FROM `Golden_Site_Cell_Group_LTE_To_GEOL`\n" +
                                                        "WHERE HOUR(`Time`) = HOUR(NOW()) AND DATE(`Time`) = DATE(NOW())";

    private final static String KPI2G_TOP5_WORST_SETUP_CALL_SR = "SELECT `CI`,`Cell` ,`2G_CSSR_Delta_times`,IF( LOCATE(\"/\",`2G_RAN_Call_Set_Up_Success_Rate`) = 0, CAST( `2G_RAN_Call_Set_Up_Success_Rate` AS DECIMAL(20,2) ), 0 ) AS `2G_RAN_Call_Set_Up_Success_Rate`  \n" +
                                                            "FROM `2G_KPI_CELL_TO_GEOL`\n" +
                                                            "ORDER BY \n" +
                                                            "2G_CSSR_Delta_times DESC,`2G_RAN_Call_Set_Up_Success_Rate` ASC LIMIT 0,5";
    private final static String KPI2G_TOP5_WORST_CALL_MINUTES_BETWEEN_DROP = "SELECT `CI`,`Cell`,`CM33_Call_Drops_on_TCH_times`,IF( LOCATE(\"/\",`2G_Call_Minutes_between_Drop`) = 0, CAST( `2G_Call_Minutes_between_Drop` AS DECIMAL(20,2) ), 0 ) AS 2G_Call_Minutes_between_Drop \n" +
                                                            "FROM \n" +
                                                            "2G_KPI_CELL_TO_GEOL\n" +
                                                            "ORDER BY `2G_Call_Minutes_between_Drop` ASC,CM33_Call_Drops_on_TCH_times DESC LIMIT 0,5";
    
    private final static String KPI3G_TOP5_WORST_SETUP_CALL_SR = "SELECT \n" +
                                                            "`RNC`,\n" +
                                                            "`cellName`,\n" +
                                                            "`cellId`,\n" +
                                                            "`3G_CSSR_Delta_times`,\n" +
                                                            "IF( LOCATE(\"/\",`3G_RAN_Call_Set_Up_Success_Rate_Speech`) = 0, CAST( `3G_RAN_Call_Set_Up_Success_Rate_Speech` AS DECIMAL(20,2) ), 0 ) AS `3G_RAN_Call_Set_Up_Success_Rate_Speech`   \n" +
                                                            "FROM `3G_KPI_CELL_TO_GEOL`\n" +
                                                            "ORDER BY `3G_CSSR_Delta_times` DESC,`3G_RAN_Call_Set_Up_Success_Rate_Speech` ASC LIMIT 0,5";
    
    private final static String KPI3G_TOP5_WORST_CALL_MINUTES_BETWEEN_DROP = "SELECT \n" +
                                                                            "`cellName`,\n" +
                                                                            "`cellId`,\n" +
                                                                            "`3G_Call_Minutes_Between_Drop`,\n" +
                                                                            "IF( LOCATE(\"/\",`3G_Drop_Rate`) = 0, CAST( `3G_Drop_Rate` AS DECIMAL(20,2) ), 0 ) AS `3G_Drop_Rate`,\n" +
                                                                            "`3G_Drop_Delta_times`  \n" +
                                                                            "FROM `3G_KPI_CELL_TO_GEOL`\n" +
                                                                            "ORDER BY `3G_Drop_Rate` DESC,`3G_Drop_Delta_times` DESC LIMIT 0,5";
    
    private final static String KPI3G_TOP5_WORST_RAN_PS_AVAILABILITY = "SELECT \n" +
                                                                        "`cellName`,\n" +
                                                                        "`cellId`,\n" +
                                                                        "IF( LOCATE(\"/\",`3G_RAN_PS_Accessibility`) = 0, CAST( `3G_RAN_PS_Accessibility` AS DECIMAL(20,2) ), 0 ) AS `3G_RAN_PS_Accessibility`,\n" +
                                                                        "CAST( `3G_PS_Accesibility_Delta_times` AS DECIMAL(20,2) ) AS `3G_PS_Accesibility_Delta_times`\n" +
                                                                        "FROM `3G_KPI_CELL_TO_GEOL`\n" +
                                                                        "ORDER BY 3G_PS_Accesibility_Delta_times DESC limit 0,5";
    
    private final static String KPI3G_TOP5_WORST_MINUTES_BETWEEN_PS_ABNORMAL = "SELECT \n" +
                                                                            "`cellName`,\n" +
                                                                            "`cellId`,\n" +
                                                                            "`3G_PS_Drop_Delta_times`,\n" +
                                                                            "IF( LOCATE(\"/\",`minutes_between_PS_abnormal_releases_minutes_DD`) = 0, CAST( `minutes_between_PS_abnormal_releases_minutes_DD` AS DECIMAL(20,2) ), 0 ) AS `minutes_between_PS_abnormal_releases_minutes_DD` \n" +
                                                                            "FROM `3G_KPI_CELL_TO_GEOL`\n" +
                                                                            "ORDER BY  3G_PS_Drop_Delta_times DESC LIMIT 0,5";
    
    private final static String KPI4G_TOP5_WORST_ESTABLISHMENT_SR = "SELECT \n" +
                                                                    "`cell_name`,\n" +
                                                                    "`4G_establishment_SR_delta_times`,\n" +
                                                                    "`4G_establishment_success_rate`\n" +
                                                                    "FROM `4G_KPI_CELL_TO_GEOL`\n" +
                                                                    "ORDER BY `4G_establishment_SR_delta_times` DESC LIMIT 0,5";
    
    private final static String KPI4G_TOP5_WORST_MINUTES_BETWEEN_ERAB = "SELECT \n" +
                                                                        "`cell_name`,\n" +
                                                                        "`4G_drops_delta_times`,\n" +
                                                                        "`minutes_between_E_RAB_abnormal_releases`\n" +
                                                                        "FROM `4G_KPI_CELL_TO_GEOL`\n" +
                                                                        "ORDER BY `4G_drops_delta_times` DESC LIMIT 0,5";
    
    private static final String SITE_CS_TRAFFIC = "SELECT CAST( (2gCsTrafic.csTraffic + 3gCsTrafic.csTraffic) AS DECIMAL(20,2) ) AS cstraffic \n" +
                                                "FROM  \n" +
                                                "( \n" +
                                                "SELECT (SUM(`csTraffic`)) AS csTraffic \n" +
                                                "FROM `2G_KPI_CELL_TO_GEOL` \n" +
                                                "INNER JOIN ( SELECT * FROM cells WHERE siteid = ?  ) cells \n" +
                                                "ON cells.cellname = 2G_KPI_CELL_TO_GEOL.cell \n" +
                                                ") 2gCsTrafic,  \n" +
                                                "(   \n" +
                                                "SELECT (SUM(`csTraffic`)) AS csTraffic \n" +
                                                "FROM `3G_KPI_CELL_TO_GEOL` \n" +
                                                "INNER JOIN ( SELECT * FROM cells WHERE siteid = ? ) cells \n" +
                                                "ON cells.cellname = 3G_KPI_CELL_TO_GEOL.cellname \n" +
                                                ") 3gCsTrafic";
    
    private static final String SITE_PS_TRAFFIC = "SELECT CAST((2gPsTrafic.psTraffic + 3gPsTrafic.psTraffic + 4gPsTrafic.psTraffic) AS DECIMAL(20,2)) AS psTraffic\n" +
                                                "FROM \n" +
                                                "(\n" +
                                                "	SELECT (SUM(`T_2G_PS_Traffic_MB`)) AS psTraffic\n" +
                                                "	FROM `2G_KPI_CELL_TO_GEOL`\n" +
                                                "	INNER JOIN\n" +
                                                "	(\n" +
                                                "	   SELECT * FROM cells WHERE siteid = ?\n" +
                                                "	) cells\n" +
                                                "	ON cells.cellname = 2G_KPI_CELL_TO_GEOL.cell\n" +
                                                ") 2gPsTrafic, \n" +
                                                "(  \n" +
                                                "	SELECT (SUM(`3G_PS_Traffic_kbit`/1000)) AS psTraffic\n" +
                                                "	FROM `3G_KPI_CELL_TO_GEOL`\n" +
                                                "	INNER JOIN \n" +
                                                "	(\n" +
                                                "	   SELECT * FROM cells WHERE siteid = ?\n" +
                                                "	) cells\n" +
                                                "	ON cells.cellname = 3G_KPI_CELL_TO_GEOL.cellname\n" +
                                                ") 3gPsTrafic,\n" +
                                                "(  \n" +
                                                "	SELECT (SUM(`PS_traffic_kbit`/1000)) AS psTraffic\n" +
                                                "	FROM `4G_KPI_CELL_TO_GEOL`\n" +
                                                "	INNER JOIN \n" +
                                                "	(\n" +
                                                "	   SELECT * FROM cells WHERE siteid = ?\n" +
                                                "	) cells\n" +
                                                "	ON cells.cellname = `4G_KPI_CELL_TO_GEOL`.cell_name\n" +
                                                "	\n" +
                                                ") 4gPsTrafic";
    
    private @Autowired JdbcTemplate jdbcTemplate;
    
    @Override
    public List<WorstCell> get2GTop5WorstCellCallSetupSuccessRate() {
        return jdbcTemplate.query(KPI2G_TOP5_WORST_SETUP_CALL_SR, new RowMapper<WorstCell>(){

            @Override
            public WorstCell mapRow(ResultSet rs, int i) throws SQLException {
                WorstCell worstCell = new WorstCell();
                worstCell.setCellId( rs.getString("CI") );
                worstCell.setCellName( rs.getString("Cell") );
                worstCell.setValue( rs.getDouble("2G_RAN_Call_Set_Up_Success_Rate") );
                
                return worstCell;
            }
        
        });
    }

    @Override
    public List<WorstCell> get2GTop5WorstCellCallMinutesBetweenDrop() {
        return jdbcTemplate.query(KPI2G_TOP5_WORST_CALL_MINUTES_BETWEEN_DROP, new RowMapper<WorstCell>(){

            @Override
            public WorstCell mapRow(ResultSet rs, int i) throws SQLException {
                
                WorstCell worstCell = new WorstCell();
                worstCell.setCellId( rs.getString("CI") );
                worstCell.setCellName( rs.getString("Cell") );
                worstCell.setValue( rs.getDouble("2G_Call_Minutes_between_Drop") );
                
                return worstCell;
            }
        
        });
    }

    @Override
    public List<WorstCell> get3GTop5WorstCellCallSetupSuccessRate() {
        return jdbcTemplate.query(KPI3G_TOP5_WORST_SETUP_CALL_SR, new RowMapper<WorstCell>(){

            @Override
            public WorstCell mapRow(ResultSet rs, int i) throws SQLException {
                WorstCell worstCell = new WorstCell();
                worstCell.setCellId( rs.getString("cellId") );
                worstCell.setCellName( rs.getString("cellName") );
                worstCell.setValue( rs.getDouble("3G_RAN_Call_Set_Up_Success_Rate_Speech") );
                
                return worstCell;
            }
        
        });
    }

    @Override
    public List<WorstCell> get3GTop5WorstCellCallMinutesBetweenDrop() {
        return jdbcTemplate.query(KPI3G_TOP5_WORST_CALL_MINUTES_BETWEEN_DROP, new RowMapper<WorstCell>(){

            @Override
            public WorstCell mapRow(ResultSet rs, int i) throws SQLException {
                
                WorstCell worstCell = new WorstCell();
                worstCell.setCellId( rs.getString("cellId") );
                worstCell.setCellName( rs.getString("cellName") );
                worstCell.setValue( rs.getDouble("3G_Call_Minutes_Between_Drop") );
                
                return worstCell;
            }
        
        });        
    }

    @Override
    public List<WorstCell> get3GTop5WorstCellRANPSAccessibility() {
        return jdbcTemplate.query(KPI3G_TOP5_WORST_RAN_PS_AVAILABILITY, new RowMapper<WorstCell>(){

            @Override
            public WorstCell mapRow(ResultSet rs, int i) throws SQLException {
                
                WorstCell worstCell = new WorstCell();
                worstCell.setCellId( rs.getString("cellId") );
                worstCell.setCellName( rs.getString("cellName") );
                worstCell.setValue( rs.getDouble("3G_RAN_PS_Accessibility") );
                
                return worstCell;
            }
        
        }); 
    }

    @Override
    public List<WorstCell> get3GTop5WorstCellMinutesBetweenPSAbnormalReleases() {
        return jdbcTemplate.query(KPI3G_TOP5_WORST_MINUTES_BETWEEN_PS_ABNORMAL, new RowMapper<WorstCell>(){

            @Override
            public WorstCell mapRow(ResultSet rs, int i) throws SQLException {
                WorstCell worstCell = new WorstCell();
                worstCell.setCellId( rs.getString("cellId") );
                worstCell.setCellName( rs.getString("cellName") );
                worstCell.setValue( rs.getDouble("minutes_between_PS_abnormal_releases_minutes_DD") );
                
                return worstCell;
            }
        
        });         
    }

    @Override
    public List<WorstCell> get4GTopWorstCellEstablishmentSuccessRate() {
        return jdbcTemplate.query(KPI4G_TOP5_WORST_ESTABLISHMENT_SR, new RowMapper<WorstCell>(){

            @Override
            public WorstCell mapRow(ResultSet rs, int i) throws SQLException {
                WorstCell cell = new WorstCell();
                cell.setCellName( rs.getString("cell_name") );
                cell.setValue( rs.getDouble("4G_establishment_success_rate") );
                
                return cell;
            }
        
        }); 
    }

    @Override
    public List<WorstCell> get4GTopWorstCellMinutesBetweenERABAbnormalReleases() {
        return jdbcTemplate.query(KPI4G_TOP5_WORST_MINUTES_BETWEEN_ERAB, new RowMapper<WorstCell>(){

            @Override
            public WorstCell mapRow(ResultSet rs, int i) throws SQLException {
                WorstCell cell = new WorstCell();
                cell.setCellName( rs.getString("cell_name") );
                cell.setValue( rs.getDouble("minutes_between_E_RAB_abnormal_releases") );

                return cell;
            }
        
        }); 
    }

    @Override
    public KPI getGroupKPI() {
        KPI.KPI2G kpi2g = null;
        
        try{
            kpi2g = jdbcTemplate.queryForObject(KPI2G_GOLDENSITE_GROUP, new RowMapper<KPI2G>() {

                @Override
                public KPI2G mapRow(ResultSet rs, int i) throws SQLException {
                    return new KPI.KPI2G(
                            rs.getDouble("2G_RAN_Call_Set_Up_Success_Rate_Speech"), 
                            rs.getDouble("2G_Call_Minutes_between_Drop"));
                }
            });
        }
        catch( org.springframework.dao.EmptyResultDataAccessException ex){
            kpi2g = new KPI2G(0, 0);
        }
        
        KPI.KPI3G kpi3g = null;
        
        try{
            kpi3g = jdbcTemplate.queryForObject(KPI3G_GOLDENSITE_GROUP, new RowMapper<KPI3G>() {

                @Override
                public KPI3G mapRow(ResultSet rs, int i) throws SQLException {

                    return new KPI3G(
                                rs.getDouble("3G_RAN_PS_Accessibility"), 
                                rs.getDouble("3G_RAN_Call_Set_Up_Success_Rate_Speech"), 
                                rs.getDouble("3G_Call_Minutes_between_Drop"),
                                rs.getDouble("Minutes_between_PS_Abnormal_Releases"));
                }
            });
        }catch( org.springframework.dao.EmptyResultDataAccessException ex ){
            kpi3g = new KPI3G(0, 0, 0,0);
        }
        
        KPI.KPI4G kpi4g = null;
        
        try{
            kpi4g = jdbcTemplate.queryForObject(KPI4G_GOLDENSITE_GROUP, new RowMapper<KPI4G>() {

                @Override
                public KPI4G mapRow(ResultSet rs, int i) throws SQLException {
                    return new KPI4G( 
                            rs.getDouble("4G_Establishment_Success_Rate") , 
                            rs.getDouble("minutes_between_E_RAB_abnormal_releases"));
                }
            });
        }catch( org.springframework.dao.EmptyResultDataAccessException ex ){
            kpi4g = new KPI4G(0, 0);
        }
        
        return new KPI(kpi2g, kpi3g, kpi4g);
        
    }

    @Override
    public KPI getSectorKPI(String cells) {
        KPI.KPI2G kpi2g = null;
        
        try{
            String sql = "SELECT \n" +
                        "IF( SUM( `csTraffic` ) IS NULL, 0.00, SUM( `csTraffic` ) ) AS csTraffic,\n" +
                        "IF( SUM( `T_2G_PS_Traffic_MB` ) IS NULL ,0.00,SUM( `T_2G_PS_Traffic_MB` ) ) AS psTraffic,\n" +
                        "IF( AVG(CAST( IF( LOCATE( \"/\",`2G_RAN_Call_Set_Up_Success_Rate` ) = 0, `2G_RAN_Call_Set_Up_Success_Rate`,0 ) AS DECIMAL(20,2) )) IS NULL ,0.00 ,AVG(CAST( IF( LOCATE( \"/\",`2G_RAN_Call_Set_Up_Success_Rate` ) = 0, `2G_RAN_Call_Set_Up_Success_Rate`,0 ) AS DECIMAL(20,2) ))  ) AS `2G_RAN_Call_Set_Up_Success_Rate`,\n" +
                        "IF( AVG(CAST( IF( LOCATE( \"/\",`2G_Call_Minutes_between_Drop` ) = 0, `2G_Call_Minutes_between_Drop`,0 ) AS DECIMAL(20,2) )) IS NULL ,0.00 ,AVG(CAST( IF( LOCATE( \"/\",`2G_Call_Minutes_between_Drop` ) = 0, `2G_Call_Minutes_between_Drop`,0 ) AS DECIMAL(20,2) ))  ) AS `2G_Call_Minutes_between_Drop`\n" +
                        "FROM `2G_KPI_CELL_TO_GEOL`\n" +
                        "WHERE cell IN("+cells+")";
            
            
            kpi2g = jdbcTemplate.queryForObject(sql, new RowMapper<KPI2G>() {

                @Override
                public KPI2G mapRow(ResultSet rs, int i) throws SQLException {
                    KPI2G kpi2g = new KPI2G();
                    kpi2g.setCsTraffic( rs.getDouble("csTraffic") );
                    kpi2g.setPsTraffic( rs.getDouble("psTraffic") );
                    kpi2g.setCallMinutesBetweenDrop( rs.getDouble("2G_Call_Minutes_between_Drop") );
                    kpi2g.setRANCallSetupSuccessRate( rs.getDouble("2G_RAN_Call_Set_Up_Success_Rate") );
                    return kpi2g;
                }
            });
        }
        catch( org.springframework.dao.EmptyResultDataAccessException ex){
            kpi2g = new KPI2G(0, 0);
        }
        
        KPI.KPI3G kpi3g = null;
        
        try{
            String sql = "SELECT \n" +
                            "IF( SUM(CAST( IF( LOCATE( \"/\",`csTraffic` ) = 0, `csTraffic`,0 ) AS DECIMAL(20,2) )) IS NULL ,0.00 ,SUM(CAST( IF( LOCATE( \"/\",`csTraffic` ) = 0, `csTraffic`,0 ) AS DECIMAL(20,2) ))  ) AS `csTraffic`,\n" +
                            "IF( SUM(CAST( IF( LOCATE( \"/\",`3G_PS_Traffic_kbit` ) = 0, `3G_PS_Traffic_kbit`,0 ) AS DECIMAL(20,2) )) IS NULL ,0.00 ,SUM(CAST( IF( LOCATE( \"/\",`3G_PS_Traffic_kbit` ) = 0, `3G_PS_Traffic_kbit`,0 ) AS DECIMAL(20,2) ))  ) AS psTraffic,\n" +
                            "IF( AVG(CAST( IF( LOCATE( \"/\",`3G_RAN_Call_Set_Up_Success_Rate_Speech` ) = 0, `3G_RAN_Call_Set_Up_Success_Rate_Speech`,0 ) AS DECIMAL(20,2) )) IS NULL ,0.00 ,AVG(CAST( IF( LOCATE( \"/\",`3G_RAN_Call_Set_Up_Success_Rate_Speech` ) = 0, `3G_RAN_Call_Set_Up_Success_Rate_Speech`,0 ) AS DECIMAL(20,2) ))  ) AS 3G_RAN_Call_Set_Up_Success_Rate_Speech,\n" +
                            "IF( SUM(CAST( IF( LOCATE( \"/\",`3G_Call_Minutes_Between_Drop` ) = 0, `3G_Call_Minutes_Between_Drop`,0 ) AS DECIMAL(20,2) )) IS NULL ,0.00 ,SUM(CAST( IF( LOCATE( \"/\",`3G_Call_Minutes_Between_Drop` ) = 0, `3G_Call_Minutes_Between_Drop`,0 ) AS DECIMAL(20,2) ))  ) AS 3G_Call_Minutes_Between_Drop,\n" +
                            "IF( AVG(CAST( IF( LOCATE( \"/\",`3G_RAN_PS_Accessibility` ) = 0, `3G_RAN_PS_Accessibility`,0 ) AS DECIMAL(20,2) )) IS NULL ,0.00 ,AVG(CAST( IF( LOCATE( \"/\",`3G_RAN_PS_Accessibility` ) = 0, `3G_RAN_PS_Accessibility`,0 ) AS DECIMAL(20,2) ))  ) AS 3G_RAN_PS_Accessibility,\n" +
                            "IF( AVG(CAST( IF( LOCATE( \"/\",`minutes_between_PS_abnormal_releases_minutes_DD` ) = 0, `minutes_between_PS_abnormal_releases_minutes_DD`,0 ) AS DECIMAL(20,2) )) IS NULL ,0.00 ,AVG(CAST( IF( LOCATE( \"/\",`minutes_between_PS_abnormal_releases_minutes_DD` ) = 0, `minutes_between_PS_abnormal_releases_minutes_DD`,0 ) AS DECIMAL(20,2) ))  ) AS minutes_between_PS_abnormal_releases_minutes_DD\n" +
                            "FROM `3G_KPI_CELL_TO_GEOL`\n" +
                            "WHERE cellName IN("+cells+")";
            
            kpi3g = jdbcTemplate.queryForObject(sql, new RowMapper<KPI3G>() {

                @Override
                public KPI3G mapRow(ResultSet rs, int i) throws SQLException {
                    KPI3G kpi3g = new KPI3G(
                                rs.getDouble("3G_RAN_PS_Accessibility"), 
                                rs.getDouble("3G_RAN_Call_Set_Up_Success_Rate_Speech"), 
                                rs.getDouble("3G_Call_Minutes_Between_Drop"),
                                rs.getDouble("minutes_between_PS_abnormal_releases_minutes_DD"));
                    kpi3g.setCsTraffic( rs.getDouble("csTraffic") );
                    kpi3g.setCsTraffic( rs.getDouble("psTraffic") );
                    return kpi3g;
                }
            });
        }catch( org.springframework.dao.EmptyResultDataAccessException ex ){
            kpi3g = new KPI3G(0, 0, 0, 0);
        }
        
        KPI.KPI4G kpi4g = null;
        
        try{
            String sql = "SELECT \n" +
                            "CAST( AVG(`PS_traffic_kbit`/1000) AS DECIMAL(20,2) ) AS psTraffic,\n" +
                            "CAST( AVG(`4G_establishment_success_rate`) AS DECIMAL(20,2) ) AS `4G_establishment_success_rate`,\n" +
                            "CAST( AVG(`minutes_between_E_RAB_abnormal_releases`) AS DECIMAL(20,2) ) AS `minutes_between_E_RAB_abnormal_releases`\n" +
                            "FROM \n" +
                            "`4G_KPI_CELL_TO_GEOL`\n" +
                            "WHERE cell_name IN("+cells+")";
            
            kpi4g = jdbcTemplate.queryForObject(sql, new RowMapper<KPI4G>() {

                @Override
                public KPI4G mapRow(ResultSet rs, int i) throws SQLException {
                    KPI4G kpi4g = new KPI4G( 
                            rs.getDouble("4G_establishment_success_rate") , 
                            rs.getDouble("minutes_between_E_RAB_abnormal_releases"));
                    kpi4g.setPsTraffic( rs.getDouble("psTraffic") );
                    return kpi4g;
                }
            });
        }catch( org.springframework.dao.EmptyResultDataAccessException ex ){
            kpi4g = new KPI4G(0, 0);
        }
        
        return new KPI(kpi2g, kpi3g, kpi4g);
        
    }
    
    @Override
    public Double getSiteCSTrafic(Site site) {
        return jdbcTemplate.queryForObject(SITE_CS_TRAFFIC,new Object[]{ site.getSiteId(), site.getSiteId() },new RowMapper<Double>() {

            @Override
            public Double mapRow(ResultSet rs, int i) throws SQLException {
                return rs.getDouble("cstraffic");
            }
        });
    }

    @Override
    public Double getSitePSTrafic(Site site) {
        return jdbcTemplate.queryForObject(SITE_PS_TRAFFIC,new Object[]{ site.getSiteId(), site.getSiteId(), site.getSiteId() },new RowMapper<Double>() {

            @Override
            public Double mapRow(ResultSet rs, int i) throws SQLException {
                return rs.getDouble("pstraffic");
            }
        });
    }

    @Override
    public Double getSectorCSTraffic(String cells) {
        String sql = "SELECT CAST( ( IF(2gCsTrafic.csTraffic IS NULL ,0,2gCsTrafic.csTraffic )  + ( IF( 3gCsTrafic.csTraffic IS NULL , 0 , 3gCsTrafic.csTraffic ) ) ) AS DECIMAL(20,2)) AS cstraffic  \n" +
"                                                FROM   \n" +
"                                                (  \n" +
"                                                SELECT (SUM(`csTraffic`)) AS csTraffic  \n" +
"                                                FROM `2G_KPI_CELL_TO_GEOL`  \n" +
"                                                WHERE cell IN("+cells+")\n" +
"                                                ) 2gCsTrafic,   \n" +
"                                                (    \n" +
"                                                SELECT (SUM(`csTraffic`)) AS csTraffic  \n" +
"                                                FROM `3G_KPI_CELL_TO_GEOL`  \n" +
"                                                WHERE cellName IN("+cells+")\n" +
"                                                ) 3gCsTrafic";
        return jdbcTemplate.queryForObject( sql , new RowMapper<Double>() {

            @Override
            public Double mapRow(ResultSet rs, int i) throws SQLException {
                return rs.getDouble("cstraffic");
            }
        });
    }

    @Override
    public Double getSectorPSTraffic(String cells) {
        String sql = "SELECT CAST(( IF( 2gPsTrafic.psTraffic IS NULL, 0,2gPsTrafic.psTraffic )  + IF( 3gPsTrafic.psTraffic IS NULL, 0,3gPsTrafic.psTraffic ) + IF( 4gPsTrafic.psTraffic IS NULL,0,4gPsTrafic.psTraffic ) ) AS DECIMAL(20,2)) AS psTraffic  \n" +
"                                                FROM   \n" +
"                                                (  \n" +
"                                                	SELECT (SUM(`T_2G_PS_Traffic_MB`)) AS psTraffic  \n" +
"                                                	FROM `2G_KPI_CELL_TO_GEOL`  \n" +
"                                                	WHERE cell IN("+cells+") AND DATE(2G_KPI_CELL_TO_GEOL.TIME) = DATE(NOW())\n" +
"                                                ) 2gPsTrafic,   \n" +
"                                                (    \n" +
"                                                	SELECT (SUM(`3G_PS_Traffic_kbit`/1000)) AS psTraffic  \n" +
"                                                	FROM `3G_KPI_CELL_TO_GEOL`  \n" +
"                                                	WHERE cellname IN("+cells+") AND DATE(3G_KPI_CELL_TO_GEOL.TIME) = DATE(NOW())\n" +
"                                                ) 3gPsTrafic,  \n" +
"                                                (    \n" +
"                                                	SELECT (SUM(`PS_traffic_kbit`/1000)) AS psTraffic  \n" +
"                                                	FROM `4G_KPI_CELL_TO_GEOL`  \n" +
"                                                	WHERE cell_name IN("+cells+") AND DATE(4G_KPI_CELL_TO_GEOL.TIME) = DATE(NOW())\n" +
"                                                	  \n" +
"                                                ) 4gPsTrafic";
        
        return jdbcTemplate.queryForObject( sql , new RowMapper<Double>() {

            @Override
            public Double mapRow(ResultSet rs, int i) throws SQLException {
                return rs.getDouble("pstraffic");
            }
        });
        
       
    }

    @Override
    public LineChart getSectorKPIValueForGoingDay(String cells, KPIType type) {
        Map<String,List<ChartValue>> values = new HashMap();
        RowCallbackHandler rowHandler = (ResultSet rs) -> {
            String cell = rs.getString("cell");
            if( values.get( cell ) ==null ){
                ArrayList<ChartValue> chartValues = new ArrayList<>();
                ChartValue chartValue = new ChartValue();
                chartValue.setTimestamp( rs.getLong("time") );
                chartValue.setValue( rs.getDouble("val") );
                chartValues.add( chartValue );
                values.put(cell, chartValues );
            }else{
                ChartValue chartValue = new ChartValue();
                chartValue.setTimestamp( rs.getLong("time") );
                chartValue.setValue( rs.getDouble("val") );
                values.get(cell).add(chartValue);
            }
        };
        double baseLine = 0.00;
        double baseLine2 = 0.00;
        if( type == KPIType.CS_TRAFFIC_2G ){
            String sqls = "SELECT UNIX_TIMESTAMP(`Time`)*1000 AS TIME,cell,csTraffic AS val\n" +
                            "FROM `2G_KPI_CELL_TO_GEOL`\n" +
                            "WHERE cell IN("+cells+") AND date(2G_KPI_CELL_TO_GEOL.time) = date(NOW())\n" +
                            "GROUP BY `Time`,cell";
            
            jdbcTemplate.query(sqls, rowHandler);
            
            
        }
        else if( type == KPIType.CS_TRAFFIC_3G ){
            String sqls = "SELECT UNIX_TIMESTAMP(`Time`)*1000 AS TIME,cellName as cell,`csTraffic` AS val\n" +
                            "FROM `3G_KPI_CELL_TO_GEOL`\n" +
                            "WHERE cellName IN("+cells+") AND date(3G_KPI_CELL_TO_GEOL.time) = date(NOW())\n" +
                            "GROUP BY `Time`,cellName";
            
            jdbcTemplate.query(sqls, rowHandler);        
        }
        else if( type == KPIType.PS_TRAFFIC_2G ){
            String sqls = "SELECT UNIX_TIMESTAMP(`Time`)*1000 AS TIME,cell,`T_2G_PS_Traffic_MB` AS val\n" +
                            "FROM `2G_KPI_CELL_TO_GEOL`\n" +
                            "WHERE cell IN("+cells+") AND date(2G_KPI_CELL_TO_GEOL.time) = date(NOW())\n" +
                            "GROUP BY `Time`,cell";
            jdbcTemplate.query(sqls, rowHandler); 
        }
        else if( type == KPIType.PS_TRAFFIC_3G ){
            String sqls = "SELECT UNIX_TIMESTAMP(`Time`)*1000 AS TIME,cellName as cell,`3G_PS_Traffic_kbit`/1000 AS val\n" +
                            "FROM `3G_KPI_CELL_TO_GEOL`\n" +
                            "WHERE cellName IN("+cells+") AND date(3G_KPI_CELL_TO_GEOL.time) = date(NOW())\n" +
                            "GROUP BY `Time`,cellName";
            jdbcTemplate.query(sqls, rowHandler); 
        }
        else if( type == KPIType.PS_TRAFFIC_4G ){
            String sqls = "SELECT UNIX_TIMESTAMP(`Time`)*1000 AS TIME,cell_Name as cell,`PS_traffic_kbit`/1000 AS val\n" +
                            "FROM `4G_KPI_CELL_TO_GEOL`\n" +
                            "WHERE cell_Name IN("+cells+") AND date(4G_KPI_CELL_TO_GEOL.time) = date(NOW())\n" +
                            "GROUP BY `Time`,cell_Name";
            jdbcTemplate.query(sqls, rowHandler); 
        }
        else if( type == KPIType.RAN_CALL_SETUP_SUCCESS_RATE_2G ){
            String sqls = "SELECT \n" +
                            "UNIX_TIMESTAMP(`Time`)*1000 AS `time`, cell,\n" +
                            "IF( AVG(CAST( IF( LOCATE( \"/\",`2G_RAN_Call_Set_Up_Success_Rate` ) = 0, `2G_RAN_Call_Set_Up_Success_Rate`,0 ) AS DECIMAL(20,2) )) IS NULL ,0.00 ,AVG(CAST( IF( LOCATE( \"/\",`2G_RAN_Call_Set_Up_Success_Rate` ) = 0, `2G_RAN_Call_Set_Up_Success_Rate`,0 ) AS DECIMAL(20,2) ))  ) AS `val`\n" +
                            "FROM `2G_KPI_CELL_TO_GEOL`\n" +
                            "WHERE cell in("+cells+") and TIME IS NOT NULL AND date(2G_KPI_CELL_TO_GEOL.time) = date(NOW())\n" +
                            "GROUP BY TIME, cell\n" +
                            "ORDER BY `time` ASC";
            
            baseLine = KPIBaseLineConstant.BASELINE2G_1_RAN_CALL_SETUP_SUCCESS_RATE;
            baseLine = KPIBaseLineConstant.BASELINE2G_2_RAN_CALL_SETUP_SUCCESS_RATE;
            
            jdbcTemplate.query(sqls, rowHandler);
        }
        else if( type == KPIType.RAN_CALL_SETUP_SUCCESS_RATE_3G ){
            String sqls = "SELECT \n" +
                            "UNIX_TIMESTAMP(TIME)*1000 AS TIME,cellname as cell,\n" +
                            "IF( AVG(CAST( IF( LOCATE( \"\"/\"\",`3G_RAN_Call_Set_Up_Success_Rate_Speech` ) = 0, `3G_RAN_Call_Set_Up_Success_Rate_Speech`,0 ) AS DECIMAL(20,2) )) IS NULL ,0.00 ,AVG(CAST( IF( LOCATE( \"/\",`3G_RAN_Call_Set_Up_Success_Rate_Speech` ) = 0, `3G_RAN_Call_Set_Up_Success_Rate_Speech`,0 ) AS DECIMAL(20,2) ))  ) AS val\n" +
                            "FROM `3G_KPI_CELL_TO_GEOL`\n" +
                            "WHERE `cellName` IN("+cells+") AND date(3G_KPI_CELL_TO_GEOL.time) = date(NOW())\n" +
                            "GROUP BY TIME,cellName\n" +
                            "ORDER BY TIME ASC";
            baseLine = KPIBaseLineConstant.BASELINE3G_1_RAN_CALL_SETUP_SUCCESS_RATE;
            baseLine2 = KPIBaseLineConstant.BASELINE3G_2_RAN_CALL_SETUP_SUCCESS_RATE;
            jdbcTemplate.query(sqls, rowHandler);
        }
        else if( type == KPIType.CALL_MINUTES_BETWEEN_DROP_2G ){
            String sqls = "SELECT \n" +
                            "UNIX_TIMESTAMP(`time`) AS TIME,cell,\n" +
                            "IF( AVG(CAST( IF( LOCATE( \"/\",`2G_Call_Minutes_between_Drop` ) = 0, `2G_Call_Minutes_between_Drop`,0 ) AS DECIMAL(20,2) )) IS NULL ,0.00 ,AVG(CAST( IF( LOCATE( \"/\",`2G_Call_Minutes_between_Drop` ) = 0, `2G_Call_Minutes_between_Drop`,0 ) AS DECIMAL(20,2) ))  ) AS `val`\n" +
                            "FROM `2G_KPI_CELL_TO_GEOL`\n" +
                            "WHERE cell IN("+cells+") AND date(2G_KPI_CELL_TO_GEOL.time) = date(NOW())\n" +
                            "GROUP BY TIME,cell\n" +
                            "ORDER BY TIME ASC";
            baseLine = KPIBaseLineConstant.BASELINE2G_1_CALL_MINUTES_BETWEEN_DROP;
            baseLine = KPIBaseLineConstant.BASELINE2G_2_CALL_MINUTES_BETWEEN_DROP;
            jdbcTemplate.query(sqls, rowHandler);
        }
        else if( type == KPIType.CALL_MINUTES_BETWEEN_DROP_3G ){
            String sqls = "SELECT \n" +
                            "UNIX_TIMESTAMP(TIME)*1000 AS TIME,cellname as cell,\n" +
                            "IF( AVG(CAST( IF( LOCATE( \"\"/\"\",`3g_call_minutes_between_drop` ) = 0, `3g_call_minutes_between_drop`,0 ) AS DECIMAL(20,2) )) IS NULL ,0.00 ,AVG(CAST( IF( LOCATE( \"/\",`3g_call_minutes_between_drop` ) = 0, `3g_call_minutes_between_drop`,0 ) AS DECIMAL(20,2) ))  ) AS val\n" +
                            "FROM `3G_KPI_CELL_TO_GEOL`\n" +
                            "WHERE `cellName` IN("+cells+") AND date(3G_KPI_CELL_TO_GEOL.time) = date(NOW())\n" +
                            "GROUP BY TIME,cellName\n" +
                            "ORDER BY TIME ASC";
            baseLine = KPIBaseLineConstant.BASELINE3G_1_CALL_MINUTES_BETWEEN_DROP;
            baseLine2 = KPIBaseLineConstant.BASELINE3G_2_CALL_MINUTES_BETWEEN_DROP;
            jdbcTemplate.query(sqls, rowHandler);
        }
        else if( type == KPIType.RAN_PS_ACCESSIBILITY_3G ){
            String sqls = "SELECT \n" +
                            "UNIX_TIMESTAMP(TIME)*1000 AS TIME,cellname as cell,\n" +
                            "IF( AVG(CAST( IF( LOCATE( \"\"/\"\",`3g_ran_ps_accessibility` ) = 0, `3g_ran_ps_accessibility`,0 ) AS DECIMAL(20,2) )) IS NULL ,0.00 ,AVG(CAST( IF( LOCATE( \"/\",`3g_ran_ps_accessibility` ) = 0, `3g_ran_ps_accessibility`,0 ) AS DECIMAL(20,2) ))  ) AS val\n" +
                            "FROM `3G_KPI_CELL_TO_GEOL`\n" +
                            "WHERE `cellName` IN("+cells+") AND date(3G_KPI_CELL_TO_GEOL.time) = date(NOW())\n" +
                            "GROUP BY TIME,cellName\n" +
                            "ORDER BY TIME ASC";
            baseLine = KPIBaseLineConstant.BASELINE3G_1_RAN_PS_ACCESSIBILITY;
            baseLine2 = KPIBaseLineConstant.BASELINE3G_2_RAN_PS_ACCESSIBILITY;
            
            jdbcTemplate.query(sqls, rowHandler);
        }
        else if( type == KPIType.MINUTES_BETWEEN_PS_ABNORMAL_RELEASES_3G ){
            String sqls = "SELECT \n" +
                            "UNIX_TIMESTAMP(TIME)*1000 AS TIME,cellname as cell,\n" +
                            "IF( AVG(CAST( IF( LOCATE( \"\"/\"\",`minutes_between_ps_abnormal_releases_minutes_dd` ) = 0, `minutes_between_ps_abnormal_releases_minutes_dd`,0 ) AS DECIMAL(20,2) )) IS NULL ,0.00 ,AVG(CAST( IF( LOCATE( \"/\",`minutes_between_ps_abnormal_releases_minutes_dd` ) = 0, `minutes_between_ps_abnormal_releases_minutes_dd`,0 ) AS DECIMAL(20,2) ))  ) AS val\n" +
                            "FROM `3G_KPI_CELL_TO_GEOL`\n" +
                            "WHERE `cellName` IN("+cells+") AND date(3G_KPI_CELL_TO_GEOL.time) = date(NOW())\n" +
                            "GROUP BY TIME,cellName\n" +
                            "ORDER BY TIME ASC";
            baseLine = KPIBaseLineConstant.BASELINE3G_1_RAN_MINUTES_BETWEEN_PS_ABNORMAL_RELEASES;
            baseLine2 = KPIBaseLineConstant.BASELINE3G_2_RAN_MINUTES_BETWEEN_PS_ABNORMAL_RELEASES;
            
            jdbcTemplate.query(sqls, rowHandler);
        }
        else if( type == KPIType.ESTABLISHMENT_SUCCESS_RATE_4G ){
            String sqls = "SELECT\n" +
                            "UNIX_TIMESTAMP(`Time`) AS TIME,cell_name as cell,\n" +
                            "CAST( AVG(`4G_establishment_success_rate`) AS DECIMAL(20,2) ) AS `val`\n" +
                            "FROM \n" +
                            "`4G_KPI_CELL_TO_GEOL`\n" +
                            "WHERE cell_name IN("+cells+") AND date(4G_KPI_CELL_TO_GEOL.time) = date(NOW())\n" +
                            "GROUP BY TIME,cell_name\n" +
                            "ORDER BY TIME ASC";
            baseLine = KPIBaseLineConstant.BASELINE4G_1_ESTABLISHMENT_SUCCESS_RATE;
            baseLine2 = KPIBaseLineConstant.BASELINE3G_2_RAN_MINUTES_BETWEEN_PS_ABNORMAL_RELEASES;
            
            jdbcTemplate.query(sqls, rowHandler);
        }                
        else if( type == KPIType.MINUTES_BETWEEN_ERAB_ABNORMAL_RELEASES_4G ){
            String sqls = "SELECT\n" +
                            "UNIX_TIMESTAMP(`Time`) AS TIME,cell_name as cell,\n" +
                            "CAST( AVG(`minutes_between_E_RAB_abnormal_releases`) AS DECIMAL(20,2) ) AS `val`\n" +
                            "FROM \n" +
                            "`4G_KPI_CELL_TO_GEOL`\n" +
                            "WHERE cell_name IN("+cells+") AND date(4G_KPI_CELL_TO_GEOL.time) = date(NOW())\n" +
                            "GROUP BY TIME,cell_name\n" +
                            "ORDER BY TIME ASC";
            baseLine = KPIBaseLineConstant.BASELINE4G_1_MINUTES_BETWEEN_ERAB_ABNORMAL_RELEASES;
            baseLine2 = KPIBaseLineConstant.BASELINE4G_2_MINUTES_BETWEEN_ERAB_ABNORMAL_RELEASES;
            jdbcTemplate.query(sqls, rowHandler);
        }                
       
        List<LineChartSerie> lineCharts = new ArrayList<>();
        
        for (Map.Entry<String, List<ChartValue>> entrySet : values.entrySet()) {
            String key = entrySet.getKey();
            List<ChartValue> value = entrySet.getValue();
            LineChartSerie lChart = new LineChartSerie();
            lChart.setData(value);
            lChart.setName(key);
            lineCharts.add(lChart);
            
        }
        
        LineChart chartData = new LineChart();
        chartData.setSeries(lineCharts);
        
        chartData.setBaseLines(new ArrayList<>());
        boolean isTraffic = false;
        if(  type == KPIType.CS_TRAFFIC_3G ){
           isTraffic = true;
        }
        if(  type == KPIType.CS_TRAFFIC_2G ){
           isTraffic = true;
        }
        
        if(  type == KPIType.PS_TRAFFIC_2G ){
            isTraffic = true;
        }
        
        if(  type == KPIType.PS_TRAFFIC_3G ){
            isTraffic = true;
        }
        
        if(  type == KPIType.PS_TRAFFIC_4G ){
            isTraffic = true;
        }
        
        if( isTraffic == false ){
            LineChartSerie baseLineSerie1 = baselineSerieEngineering("MSL (Baseline 1",baseLine);
            LineChartSerie baseLineSerie2 = baselineSerieEngineering("TSL (Baseline 2",baseLine2);

            List<LineChartSerie> bseries = new ArrayList<>();
            bseries.add(baseLineSerie1);
            bseries.add(baseLineSerie2);

            chartData.setBaseLines( bseries);     
            System.out.println("ZZZZ");
        }

        return chartData;
        
    }
    
    private LineChartSerie baselineSerieEngineering(String serieName,double baseline){
        List<ChartValue> bsVals = new ArrayList<>();
        LineChartSerie serie = new LineChartSerie();
        serie.setName(serieName);
        try {
            Date dt = Calendar.getInstance().getTime();

            long ONE_HOUR_IN_MILIS = (60000*60);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
            long tm = dt.getTime()+(7*ONE_HOUR_IN_MILIS);;
            String currentDte = format.format(dt);
            System.out.println( currentDte );
            Date now = format.parse(currentDte);
            long tmnow =  now.getTime()+(7*ONE_HOUR_IN_MILIS);

            for (; tmnow < tm; ) {
                ChartValue bVal = new ChartValue();
                bVal.setTimestamp(tmnow);
                bVal.setValue(baseline);
                bsVals.add(bVal);
                tmnow += ONE_HOUR_IN_MILIS;            
            }
        } catch (ParseException ex) {
            Logger.getLogger(KeyQualityIndexRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        serie.setData(bsVals);
        return serie;
    }
    
   
}
