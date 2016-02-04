/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.repository;

import com.huawei.gsm.entity.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */
public abstract class AbstractTowerRepository {
    
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    
    private final String INSERT_SQL = "INSERT INTO SITE_CELLS(SITE_NAME,SITE_ID,ADDRESS,TYPE,CELL_INDEX,CELL_NAME,FREQUENCY,LATITUDE,LONGITUDE) VALUE(?,?,?,?,?,?,?,?,?)";
    
    protected int save( Cell cell ){
        return jdbcTemplate.update(INSERT_SQL, new Object[]{
            cell.getSite().getSiteName(),
            cell.getSite().getSiteId(),
            cell.getSite().getAddress(),
            cell.getSite().getGroup(),
            cell.getCellIndex(),
            cell.getCellName(),
            cell.getFrequency(),
            cell.getSite().getLatitude(),
            cell.getSite().getLongitude()
        });
    }
    
}
