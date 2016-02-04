/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.repository.rowmapper;

import com.huawei.gsm.entity.AppRole;
import com.huawei.gsm.entity.AppUser;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */
public class AppUserRowMapper implements RowMapper<AppUser>{

    @Override
    public AppUser mapRow(ResultSet rs, int i) throws SQLException {
        
        AppUser user = new AppUser();
        user.setFullname( rs.getString("fullname") );
        user.setId( rs.getInt("id") );
        user.setUsername( rs.getString("username") );
        user.setEmail( rs.getString("email") );
        user.setPhone( rs.getString("phone") );
        user.setLastLogin( rs.getTimestamp("lastlogin") );
        user.setModified( rs.getTimestamp("modified") );
        AppRole role = new AppRole( rs.getString("role") );
        //role.setId( rs.getInt( "role_id" ) );
        user.setRole( role );
        return user;
    }
    
}
