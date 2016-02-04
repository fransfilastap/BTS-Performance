/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.repository;

import com.huawei.gsm.entity.Frequency;
import com.huawei.gsm.entity.Severity;
import com.huawei.gsm.entity.Site;
import com.huawei.gsm.entity.SiteGroup;
import com.huawei.gsm.util.SectorDefiner;
import com.huawei.gsm.util.response.gis.CellMarker;
import com.huawei.gsm.util.response.gis.SectorMarker;
import com.huawei.gsm.util.response.gis.SiteMarker;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */
@Repository
public class SiteMonitoringRepository implements SiteMonitoringRepositoryContract{

    private @Autowired JdbcTemplate jdbcTemplate;
    private @Autowired SectorDefiner sectorDefiner;
    
    private final static String SITE_CONDITIONS = "SELECT sites.id,"
            + "sites.siteName,"
            + "sites.siteId,"
            + "address,"
            + "latitude,"
            + "longitude,"
            + "site_group,"
            + "severity "
            + "from sites left join alarms on alarms.SiteID = sites.siteId";
    
    private final String CELL_CONDITION_BY_FREQUENCY = "SELECT `cells`.`siteid`,\n" +
                                                "	cells.`cellid`,\n" +
                                                "	sites.siteid,\n" +
                                                "	sites.sitename,\n" +
                                                "	sites.address,\n" +
                                                "	sites.latitude,\n" +
                                                "	sites.longitude,\n" +
                                                "	`cellindex`,\n" +
                                                "	`cellname`,\n" +
                                                "	`frequency`,\n" +
                                                "	site_group,\n" +
                                                "	IF( `Severity` IS NULL, \"NORMAL\",Severity ) AS severity\n" +
                                                "FROM  \n" +
                                                "( \n" +
                                                "	SELECT siteid,`cellid`,`cellindex`,`cellname`,`frequency` FROM cells\n" +
                                                "	WHERE frequency \n" +
                                                "	LIKE ?  \n" +
                                                "	\n" +
                                                ") cells \n" +
                                                "LEFT JOIN sites ON sites.siteid = cells.siteid\n" +
                                                "LEFT JOIN (\n" +
                                                "	SELECT `CellID`,`NodeAlias`,`SiteID`,`Severity` FROM `alarms`\n" +
                                                ") alarms ON alarms.nodeAlias = cells.cellname\n" +
                                                "\n";
    

    
    
    
    @Override
    public List<SiteMarker> getSitesCondition() {
        
        Map<String,SiteMarker> markers = new HashMap<String, SiteMarker>();
        
        List<SiteMarker> sites = jdbcTemplate.query( SITE_CONDITIONS , new RowMapper<SiteMarker>(){

            @Override
            public SiteMarker mapRow(ResultSet rs, int i) throws SQLException {
                SiteMarker marker = new SiteMarker();
                marker.setId( rs.getInt("id") );
                marker.setSiteId( rs.getString("siteid") );
                marker.setSiteName( rs.getString("sitename") );
                marker.setAddress( rs.getString("address") );
                marker.setLatitude( rs.getDouble( "latitude" ) );
                marker.setLongitude( rs.getDouble( "longitude"  ) );
                marker.setGroup(  rs.getString("site_group").equalsIgnoreCase("EVENT_SITE") ? SiteGroup.EVENT_SITE : SiteGroup.GOLDEN_SITE  );
                if( rs.getString("severity") == null ){
                    marker.setSeverity(Severity.NORMAL);
                }
                else{
                    marker.setSeverity( rs.getString("severity").equalsIgnoreCase("CRITICAL") ? Severity.CRITICAL : ( rs.getString("severity").equalsIgnoreCase("MAJOR") ? Severity.MAJOR : Severity.MINOR ) );
                }
                
                
                return marker;
            }
        });
        
        
        Iterator<SiteMarker> iter = sites.iterator();
        while (iter.hasNext()) {
            SiteMarker marker = iter.next();
            
            if( markers.get( marker.getSiteId() ) == null ){
                markers.put( marker.getSiteId() , marker);
            }
            else{
                if( markers.get( marker.getSiteId() ).getSeverity() != Severity.CRITICAL ){
                    if( marker.getSeverity() == Severity.CRITICAL ){
                        markers.put(marker.getSiteId(), marker);
                    }
                    else{
                        if( marker.getSeverity() == Severity.MAJOR && markers.get( marker.getSiteId() ).getSeverity() != Severity.MAJOR){
                            markers.put( marker.getSiteId() , marker);
                        }
                        else if( marker.getSeverity() == Severity.MINOR && markers.get( marker.getSiteId() ).getSeverity() == null ){
                            markers.put( marker.getSiteId() , marker);
                        }
                    }
                }
            }
            
        }
        
        return new ArrayList<SiteMarker>( markers.values() );
        
    }

    @Override
    public List<SectorMarker> getCellConditionByFrequency(Frequency frequency) {
        
        String freq = frequency.toString().toLowerCase();
        
        if( freq.contains("lte") ){
            freq = "%LTE%";
        }
        else if( freq.contains("umts") ){
            freq = "%UMTS%";
        }
        else{
            freq = "%GSM%";
        }
        
        //to keep list unique
        final Map<String,SiteMarker> map = new HashMap<>();
        final Map<String,Map<Integer,SectorMarker>> sectorMap = new HashMap<>();
        
        jdbcTemplate.query(CELL_CONDITION_BY_FREQUENCY, new Object[]{ freq }, (ResultSet rs) -> {
            
            //site attributes
            String siteId = rs.getString("siteid");
            String sitename = rs.getString("sitename");
            String address = rs.getString("address");
            String site_group = rs.getString("site_group");
            double latitude = rs.getDouble("latitude");
            double longitude = rs.getDouble("longitude");
            SiteMarker siteMarker =  new SiteMarker();
            siteMarker.setAddress(address);
            siteMarker.setGroup( ( site_group.equalsIgnoreCase("EVENT_SITE") ? SiteGroup.EVENT_SITE : SiteGroup.GOLDEN_SITE ) );
            siteMarker.setLatitude(latitude);
            siteMarker.setLongitude(longitude);
            siteMarker.setSiteName(sitename);
            siteMarker.setSiteId(siteId);
            siteMarker.setSeverity(Severity.NORMAL);
            
            if( map.get( siteId ) == null ){
                siteMarker.setCellMarkers(new ArrayList<>());
                map.put(siteId, siteMarker);
            }
            String cellId = rs.getString("cellId");
            Severity severity = null;
            
            switch( rs.getString("Severity").toLowerCase() ){
                case "critical" : 
                    severity = Severity.CRITICAL;
                    break;
                case "major" : 
                    severity = Severity.MAJOR;
                    break;
                case "minor" : 
                    severity = Severity.MINOR;
                    break;                    
                case "normal" :
                    severity = Severity.NORMAL;
                    break;
                default :
                    severity = Severity.NORMAL;
                    break;
            }
            
            String cellIndex = rs.getString("cellIndex");
            String cellName = rs.getString("cellName");
            String frequency1 = rs.getString("frequency");
            
            CellMarker cell = new CellMarker();
            cell.setCellId(cellId);
            cell.setCellIndex(cellIndex);
            cell.setCellName(cellName);
            cell.setFrequency(frequency1);
            cell.setSeverity(severity);
            cell.setSite((Site)siteMarker);
            
            int sector = sectorDefiner.defineAzimut(cellName, frequency );
            
            if( sectorMap.get( siteId ) == null ){
                
                SectorMarker sectorMarker = new SectorMarker();
                sectorMarker.setSite(siteMarker);
                int azimut = ( frequency == Frequency.GSM ? sectorDefiner.getGSM_AZIMUTS().get(sector) : sectorDefiner.getUMTS_LTE_AZIMUTS().get(sector) );
                sectorMarker.setAzimut( azimut );
                sectorMarker.setSectorNumber( sector );
                sectorMarker.setType(frequency);
                sectorMarker.setCells(new ArrayList<>());
                sectorMarker.setCellWithAlarms(new ArrayList<>());
                sectorMarker.setSeverity( cell.getSeverity() );
                
                sectorMarker.getCells().add(cell);
                if( cell.getSeverity() != Severity.NORMAL ){
                    sectorMarker.getCellWithAlarms().add(cell);
                }
                
                Map<Integer,SectorMarker> mSector = new HashMap<>();
                mSector.put(sector, sectorMarker);
                sectorMap.put( siteId , mSector);
            }
            else
            {
                if( sectorMap.get(siteId).get(sector) == null ){
                    
                    SectorMarker sectorMarker = new SectorMarker();
                    sectorMarker.setSite(siteMarker);
                    int azimut = ( frequency == Frequency.GSM ? sectorDefiner.getGSM_AZIMUTS().get(sector) : sectorDefiner.getUMTS_LTE_AZIMUTS().get(sector) );
                    sectorMarker.setAzimut( azimut );
                    sectorMarker.setSectorNumber( sector );
                    sectorMarker.setType(frequency);
                    sectorMarker.setCells(new ArrayList<>());
                    sectorMarker.setCellWithAlarms(new ArrayList<>());
                    sectorMarker.setSeverity( cell.getSeverity() );

                    sectorMarker.getCells().add(cell);
                    if( cell.getSeverity() != Severity.NORMAL ){
                        sectorMarker.getCellWithAlarms().add(cell);
                    }

                    sectorMap.get( siteId ).put(sector, sectorMarker);                    
                }
                else{
                    sectorMap.get(siteId).get(sector).getCells().add(cell);
                    if( cell.getSeverity() != Severity.NORMAL ){
                        sectorMap.get(siteId).get(sector).getCellWithAlarms().add(cell);
                    }

                    if( sectorMap.get(siteId).get(sector).getSeverity() != Severity.CRITICAL ){
                        if( cell.getSeverity() == Severity.CRITICAL ){
                            sectorMap.get(siteId).get(sector).setSeverity( Severity.CRITICAL );
                        }else{
                            if( cell.getSeverity() == Severity.MAJOR && sectorMap.get(siteId).get(sector).getSeverity() != Severity.MAJOR){
                                sectorMap.get(siteId).get(sector).setSeverity( Severity.MAJOR );
                            }
                            else if( cell.getSeverity() == Severity.MINOR && sectorMap.get(siteId).get(sector).getSeverity() == null ){
                                sectorMap.get(siteId).get(sector).setSeverity( Severity.MINOR );
                            }
                        }
                    }                   
                }
                
            }
        });
        
        List<Map<Integer,SectorMarker>> sectorMaps = new ArrayList<>( sectorMap.values() );
        List<SectorMarker> sectors = new ArrayList<>();
        sectorMaps.stream().forEach( m ->{
            sectors.addAll( m.values() );
        } );

        return sectors;        
    }


    
    
}
