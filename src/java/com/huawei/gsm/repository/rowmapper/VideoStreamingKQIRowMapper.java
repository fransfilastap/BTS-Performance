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
public class VideoStreamingKQIRowMapper implements RowMapper<KQI.VideoStreamingKQI>{

    @Override
    public KQI.VideoStreamingKQI mapRow(ResultSet rs, int i) throws SQLException {
        return new KQI.VideoStreamingKQI(
                                            rs.getFloat("STREAMING_START_SUCC_RATE") , 
                                            rs.getFloat("STREAMING_START_DEALY_MS")
                                        );
    }
    
}
