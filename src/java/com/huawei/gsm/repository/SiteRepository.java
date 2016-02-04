/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.repository;

import com.huawei.gsm.entity.Site;
import com.huawei.gsm.repository.rowmapper.SiteRowMapper;
import com.huawei.gsm.repository.rowmapper.SiteToExcelRowMapper;
import com.huawei.gsm.util.response.SiteExcelWrapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */
@Repository
public class SiteRepository  implements SiteRepositoryContract{
    private static final Logger LOG = Logger.getLogger(SiteRepository.class.getName());

    @Override
    public Site getSite(int id) {
        Site site = jdbcTemplate.queryForObject(SITE_GET,new Object[]{ id }, new SiteRowMapper());
        return site;
    }

    @Override
    public int deleteSite(Site site) {
        return jdbcTemplate.update(SITE_DELETE, new Object[]{ site.getId() }, new int[]{ Types.INTEGER });
    }

    @Override
    public List<Site> getAllSites() {
        return jdbcTemplate.query(SITE_LIST, new SiteRowMapper());
    }
    
    @Override
    public int saveSite(Site site){
        
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update((Connection cnctn) -> {
            String[] columns = new String[]{ "sitename","siteid","address","latitude","longitude","site_group" };
            PreparedStatement ps = cnctn.prepareStatement(SITE_INSERT, columns );
            
            ps.setString(1, site.getSiteName());
            ps.setString(2, site.getSiteId());
            ps.setString(3, site.getAddress());
            ps.setDouble(4, site.getLatitude());
            ps.setDouble(5, site.getLongitude());
            ps.setString(6, site.getGroup().toString());
            return ps;
        },keyHolder );
        return keyHolder.getKey().intValue();
    }

        @Override
    public int updateSite(Site site) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getTotalRecords() {
        return jdbcTemplate.queryForObject(SITE_SIZE, (ResultSet rs, int i) -> rs.getInt("TOTAL_RECORDS"));
    }

    @Override
    public List<Site> getListByRequest(String keyword, int start, int length,String columnOrder,String order) {
        String siteSearch = SITE_SEARCH;
        String sql = siteSearch.replace("COLUMN_ORDER", columnOrder);
               sql = sql.replace("ORDER_TYPE", order);
        keyword = "%"+keyword+"%";
        return jdbcTemplate.query( sql , new Object[]{ keyword,keyword,keyword,keyword,start,length }, new SiteRowMapper());
    }
    
    @Override
    public int getFilteredSize(String keyword) {
        keyword = "%"+keyword+"%";
        return jdbcTemplate.queryForObject(SITE_SEARCH_SIZE , new Object[]{ keyword,keyword,keyword,keyword }, (ResultSet rs, int i) -> {
            return rs.getInt("TOTAL_FILTERED_RECORDS");
        });
    }

    @Override
    public List<SiteExcelWrapper> exportToExcel() {
        return jdbcTemplate.query( SITE_EXPORT, new SiteToExcelRowMapper() );
    }
    
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    //QUERY
    private final String SITE_GET = "SELECT id,siteid,sitename,address,latitude,longitude,site_group FROM sites WHERE id = ? LIMIT 1";
    private final String SITE_LIST = "SELECT id,siteid,sitename,address,latitude,longitude,site_group FROM sites GROUP BY siteid ORDER BY ID";
    private final String SITE_DELETE = "DELETE FROM sites WHERE id = ?";
    private final String SITE_SIZE = "SELECT COUNT(*) AS TOTAL_RECORDS FROM sites";
    private final String SITE_SEARCH = "SELECT id,sitename,siteid,address,site_group,latitude,longitude FROM sites WHERE siteid LIKE ? OR sitename LIKE ? OR address LIKE ? OR site_group LIKE ? ORDER BY COLUMN_ORDER ORDER_TYPE LIMIT ?,? ";
    private final String SITE_SEARCH_SIZE = "SELECT count(*) AS TOTAL_FILTERED_RECORDS FROM sites WHERE siteid LIKE ? OR sitename LIKE ? OR address LIKE ? OR site_group LIKE ? ";
    private final String SITE_INSERT = "INSERT IGNORE INTO sites(sitename,siteid,address,latitude,longitude,site_group) value(?,?,?,?,?,?)";
    private final String SITE_EXPORT = "SELECT `sites`.`siteid` , \n" +
                                        "	`sites`.`sitename`, " +
                                        "	`sites`.`address`, " +
                                        "	`sites`.`latitude`, " +
                                        "	`sites`.`longitude`, " +
                                        "	`sites`. `site_group`," +
                                        "	`cells`.`cellindex`," +
                                        "	`cells`.`cellname`," +
                                        "	`cells`.`frequency`" +
                                        "FROM `sites`" +
                                        "LEFT JOIN `cells` ON `sites`.`siteid` = `cells`.`siteId`";

    @Override
    public void saveSiteBatch(List<Site> sites) {
        
        jdbcTemplate.batchUpdate( SITE_INSERT , new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                
                Site site = sites.get(i);
                
                ps.setString(1, site.getSiteName());
                ps.setString(2, site.getSiteId());
                ps.setString(3, site.getAddress());
                ps.setDouble(4, site.getLatitude());
                ps.setDouble(5, site.getLongitude());
                ps.setString(6, site.getGroup().toString());                
            }

            @Override
            public int getBatchSize() {
                return sites.size();
            }
        });
        
    }



}
