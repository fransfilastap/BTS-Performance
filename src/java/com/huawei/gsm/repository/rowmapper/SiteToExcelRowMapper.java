/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.repository.rowmapper;

import com.huawei.gsm.util.response.SiteExcelWrapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */
public class SiteToExcelRowMapper implements RowMapper<SiteExcelWrapper>{

    @Override
    public SiteExcelWrapper mapRow(ResultSet rs, int i) throws SQLException {
        return new SiteExcelWrapper(rs.getString("siteid"), 
                                    rs.getString("sitename"), 
                                    rs.getString("address"), 
                                    rs.getDouble("latitude"), 
                                    rs.getDouble("longitude"), 
                                    rs.getString("site_group"), 
                                    rs.getString("cellindex"), 
                                    rs.getString("cellname"), 
                                    rs.getString("frequency"));                
    }
    
}
