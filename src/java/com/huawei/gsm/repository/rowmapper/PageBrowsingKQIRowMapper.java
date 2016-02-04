/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.repository.rowmapper;

import com.huawei.gsm.entity.KQI;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Administrator
 */
public class PageBrowsingKQIRowMapper implements RowMapper<KQI.PageBrowsingKQI>{


    @Override
    public KQI.PageBrowsingKQI mapRow(ResultSet rs, int i) throws SQLException {
        
        return new KQI.PageBrowsingKQI( 
                                        rs.getFloat("PAGE_BROWSING_SUCC_RATE") , 
                                        rs.getFloat("PAGE_BROWSING_DELAY_MS")
                        );

    }
    
}
