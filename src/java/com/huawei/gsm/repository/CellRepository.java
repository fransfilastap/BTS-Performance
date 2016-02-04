/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.repository;

import com.huawei.gsm.entity.Cell;
import com.huawei.gsm.entity.Frequency;
import com.huawei.gsm.entity.Site;
import com.huawei.gsm.repository.rowmapper.CellRowMapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */

@Repository
public class CellRepository implements CellRepositoryContract{
   
    
    @Override
    public List<Cell> getAllCell() {
        return jdbcTemplate.query(CELL_LIST, new CellRowMapper());
    }

    @Override
    public List<Cell> getAllCellBySite(Site site) {
        return jdbcTemplate.query( CELL_LIST_BY_SITE , 
                                    new Object[]{ site.getSiteId() }, 
                                    new CellRowMapper());
    }

    @Override
    public List<Cell> getAllCellByFrequency(Frequency frequency) {
        return jdbcTemplate.query( CELL_LIST_BY_FREQUENCY , 
                                    new Object[]{ "%"+frequency.toString()+"%" }, 
                                    new CellRowMapper());
    }

    @Override
    public Cell getCell(String id) {
        return jdbcTemplate.queryForObject( CELL_GET , new Object[]{ id }, new CellRowMapper());
    }

    @Override
    public int updateCell(Cell cell) {
        return jdbcTemplate.update(CELL_UPDATE, new Object[]{
            cell.getSite().getSiteName(),
            cell.getSite().getSiteId(),
            cell.getSite().getAddress(),
            cell.getSite().getGroup(),
            cell.getCellIndex(),
            cell.getCellName(),
            cell.getFrequency(),
            cell.getSite().getLatitude(),
            cell.getSite().getLongitude(),
            cell.getId()            
        });
    }

    @Override
    public int deleteCell(Cell cell) {
        return jdbcTemplate.update(CELL_DELETE, new Object[]{
            cell.getId()
        });
    }

    @Override
    public int saveCell(Cell cell) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update((Connection cnctn) -> {
            String[] columns = new String[]{ "cellid","cellindex","cellname","frequency","siteId" };
            PreparedStatement ps = cnctn.prepareStatement(CELL_INSERT, columns);
            
            ps.setString(2, cell.getCellIndex());
            ps.setString(3, cell.getCellName());
            ps.setString(4, cell.getFrequency());
            ps.setString(5, cell.getSite().getSiteId());
            ps.setString(1, cell.getCellId());
            
            return ps;
        },holder);
        
        return holder.getKey().intValue();
    }
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
   //query
    private final String CELL_LIST              = "SELECT * FROM cells";
    private final String CELL_LIST_BY_SITE      = "SELECT * FROM cells WHERE siteid = ?";
    private final String CELL_LIST_BY_FREQUENCY = "SELECT * FROM cells WHERE FREQUENCY LIKE ?";
    private final String CELL_GET               = "SELECT * FROM cells WHERE cellindex = ?";
    private final String CELL_UPDATE            = "UPDATE cells SET cellindex = ?, cellname = ?, frequency = ?, SET siteId = ? WHERE id = ?";
    private final String CELL_INSERT            = "INSERT INTO cells(cellid,cellindex,cellname,frequency,siteId) value(?,?,?,?,?)";
    private final String CELL_DELETE            = "DELETE FROM cells WHERE ID = ?";
    //end of query

    @Override
    public List<Cell> getTopWorstCell(Site site) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
