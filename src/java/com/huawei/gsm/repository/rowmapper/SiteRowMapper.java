/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.repository.rowmapper;

import com.huawei.gsm.entity.Site;
import com.huawei.gsm.entity.SiteGroup;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */
public class SiteRowMapper implements RowMapper<Site>{

    @Override
    public Site mapRow(ResultSet rs, int i) throws SQLException {
        Site site = new Site();
        
        site.setId( rs.getInt("id") );
        site.setSiteId( rs.getString("siteid") );
        site.setSiteName( rs.getString("sitename") );
        site.setAddress( rs.getString("address") );
        site.setLatitude( rs.getDouble( "latitude" ) );
        site.setLongitude( rs.getDouble( "longitude"  ) );
        site.setGroup(  rs.getString("site_group").equalsIgnoreCase("EVENT_SITE") ? SiteGroup.EVENT_SITE : SiteGroup.GOLDEN_SITE  );
        return site;
    }
    
}
