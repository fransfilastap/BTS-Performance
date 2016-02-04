/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.service;

import com.huawei.gsm.entity.AppUser;
import java.util.List;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */
public interface UserServiceContract {
    
    public List<AppUser> listUser(String Keyword, int row,int page);
    public int saveUser(AppUser user);
    public AppUser getUserById(int id);
    public AppUser getUserByUsername(String username);
    public AppUser getUserByEmail(String email);
    public int updateUser(AppUser user);
    public int deleteUser(AppUser user);
    public List<AppUser> getListByRequest(String keyword, int page, int row,String columnOrder,String order);
    public int getTotalRecords();
    public int getTotalFilteredRecords(String filter);
}
