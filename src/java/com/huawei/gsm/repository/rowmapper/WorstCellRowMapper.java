/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.repository.rowmapper;

import com.huawei.gsm.entity.WorstCell;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Administrator
 */
public class WorstCellRowMapper implements RowMapper<WorstCell>{

    @Override
    public WorstCell mapRow(ResultSet rs, int i) throws SQLException {
        WorstCell worstCell = new WorstCell();
        worstCell.setCellIndex( rs.getString("CELL_NAME") );
        worstCell.setCellName( rs.getString("CELL_NAME") );
        worstCell.setValue( rs.getDouble("VALUE") );
        return worstCell;
    }
    
}
