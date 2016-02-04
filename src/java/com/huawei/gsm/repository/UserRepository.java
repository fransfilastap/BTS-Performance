/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.repository;

import com.huawei.gsm.entity.AppUser;
import com.huawei.gsm.repository.rowmapper.AppUserRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */
@Repository
public class UserRepository implements UserRepositoryContract{
    
    @Override
    public String getUserFullName(String username) {
        return jdbcTempalte.queryForObject( USER_BY_USERNAME, 
                                            new Object[]{ username }, 
                                            new AppUserRowMapper())
                                                .getFullname();
    }

    @Override
    public AppUser getUserById(int id) {
        return jdbcTempalte.queryForObject( USER_BY_ID , new Object[]{ id }, new AppUserRowMapper());
    }

    @Override
    public AppUser getUserByUsername(String username) {
        return jdbcTempalte.queryForObject( USER_BY_USERNAME , new Object[]{ username }, new AppUserRowMapper());
    }

    @Override
    public AppUser getUserByEmail(String email) {
        return jdbcTempalte.queryForObject( USER_BY_EMAIL , new Object[]{ email }, new AppUserRowMapper());
    }

    @Override
    public int saveUser(AppUser user) {
        return jdbcTempalte.update(USER_INSERT, new Object[]{
            user.getFullname(),
            user.getUsername(),
            user.getEmail(),
            user.getPhone(),
            passwordEncoder.encode(user.getPassword())
        });
    }

    @Override
    public int updateUser(AppUser user) {
        return jdbcTempalte.update(USER_INSERT, new Object[]{
            user.getFullname(),
            user.getUsername(),
            user.getEmail(),
            user.getPhone(),
            passwordEncoder.encode(user.getPassword())
        });
    }

    @Override
    public List<AppUser> getUserList() {
        return jdbcTempalte.query(USER_LIST, new AppUserRowMapper());
    }

    @Override
    public int deleteUser(AppUser user) {
        return jdbcTempalte.update(USER_DELETE, new Object[]{ user.getId() });
    }

    @Override
    public int getTotalRecords() {
        return jdbcTempalte.queryForObject(USER_TOTALRECORDS, new RowMapper<Integer>() {

            @Override
            public Integer mapRow(ResultSet rs, int i) throws SQLException {
                return rs.getInt( "totalrecords" );
            }
        });
    }

    @Override
    public List<AppUser> getListByRequest(String keyword, int page, int row, String columnOrder, String order) {
        String userSearch = USER_SEARCH;
        String sql = userSearch.replace("COLUMN_ORDER", columnOrder);
               sql = sql.replace("ORDER_TYPE", order);        
        keyword = "%"+keyword+"%";
        return jdbcTempalte.query( sql , new Object[] { keyword,keyword,keyword,keyword,keyword,page,row }, new AppUserRowMapper() );
    }

    @Override
    public int getFilteredSize(String keyword) {
        
        return jdbcTempalte.queryForObject( USER_SEARCH_SIZE , new Object[]{ keyword,keyword,keyword,keyword,keyword }, new RowMapper<Integer>() {

            @Override
            public Integer mapRow(ResultSet rs, int i) throws SQLException {
                return rs.getInt( "TOTAL_FILTERED_RECORDS" );
            }
        } );
    }
    
    @Autowired 
    private JdbcTemplate jdbcTempalte;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    
    private final String USER_LIST = "SELECT users.id, fullname, username, enabled, email , phone, created, modified, lastlogin FROM users LEFT JOIN user_roles ON user_roles.user_id = users.id LEFT JOIN roles ON user_roles.role_id = roles.id order by users.id asc";
    private final String USER_INSERT = "INSERT INTO users(fullname,username,email,phone,password) VALUE(?,?,?,?,?)";
    private final String USER_BY_ID = "SELECT users.id, fullname, username, enabled, email , phone, created, modified, lastlogin,role FROM users LEFT JOIN user_roles ON user_roles.user_id = users.id LEFT JOIN roles ON user_roles.role_id = roles.id WHERE users.id = ? LIMIT 1";
    private final String USER_BY_USERNAME = "SELECT users.id, fullname, username, enabled, email , phone, created, modified, lastlogin,role FROM users LEFT JOIN user_roles ON user_roles.user_id = users.id LEFT JOIN roles ON user_roles.role_id = roles.id WHERE username = ? LIMIT 1";
    private final String USER_BY_EMAIL = "SELECT users.id, fullname, username, enabled, email , phone, created, modified, lastlogin,role FROM users LEFT JOIN user_roles ON user_roles.user_id = users.id LEFT JOIN roles ON user_roles.role_id = roles.id WHERE email = ? LIMIT 1";
    private final String USER_DELETE = "DELETE users WHERE id = ?";
    private final String USER_USERNAME = "SELECT fullname FROM users LEFT JOIN user_roles ON user_roles.user_id = users.id LEFT JOIN roles ON user_roles.role_id = roles.id  WHERE username = ?";
    private final String USER_TOTALRECORDS = "SELECT count(*) AS totalrecords FROM users";
    private final String USER_SEARCH = "SELECT * FROM users LEFT JOIN user_roles ON user_roles.user_id = users.id LEFT JOIN roles ON user_roles.role_id = roles.id WHERE fullname LIKE ? OR username LIKE ?  OR enabled LIKE ? OR phone LIKE ? OR role LIKE ? ORDER BY COLUMN_ORDER ORDER_TYPE LIMIT ?,? ";
    private final String USER_SEARCH_SIZE = "SELECT count(*) AS TOTAL_FILTERED_RECORDS FROM users LEFT JOIN user_roles ON user_roles.user_id = users.id LEFT JOIN roles ON user_roles.role_id = roles.id WHERE fullname LIKE ? OR username LIKE ?  OR enabled LIKE ? OR phone LIKE ? OR role LIKE ?";

}
