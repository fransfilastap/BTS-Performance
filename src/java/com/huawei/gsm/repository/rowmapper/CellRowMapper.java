/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.repository.rowmapper;

import com.huawei.gsm.entity.Cell;
import com.huawei.gsm.entity.Site;
import com.huawei.gsm.entity.SiteGroup;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */
public class CellRowMapper implements RowMapper<Cell>{

    @Override
    public Cell mapRow(ResultSet rs, int i) throws SQLException {
        Cell cell = new Cell();
        cell.setId( rs.getLong("id") );
        cell.setCellIndex( rs.getString("CELLINDEX") );
        cell.setCellName( rs.getString("CELLNAME") );
        cell.setFrequency( rs.getString("FREQUENCY") );

        return cell;  
    }
    
}
