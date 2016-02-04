/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.repository;

import com.huawei.gsm.entity.AppUser;
import java.util.List;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */
public interface UserRepositoryContract {
    public String getUserFullName(String username);
    public AppUser getUserById(int id);
    public AppUser getUserByUsername(String username);
    public AppUser getUserByEmail(String email);
    public int saveUser(AppUser user);
    public int updateUser(AppUser user);
    public List<AppUser> getUserList();
    public int deleteUser(AppUser user);
    public int getTotalRecords();
    public List<AppUser> getListByRequest(String keyword, int page, int row,String columnOrder,String order);
    public int getFilteredSize(String keyword);    
}
