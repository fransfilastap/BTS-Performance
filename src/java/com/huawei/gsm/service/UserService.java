/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.service;

import com.huawei.gsm.entity.AppUser;
import com.huawei.gsm.repository.UserRepositoryContract;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */

@Service
@Transactional
public class UserService implements UserServiceContract{

    @Override
    public List<AppUser> listUser(String Keyword, int row, int page) {
        return userRepository.getUserList();
    }

    @Override
    public int saveUser(AppUser user) {
        return userRepository.saveUser(user);
    }

    @Override
    public AppUser getUserById(int id) {
        return userRepository.getUserById(id);
    }

    @Override
    public AppUser getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    @Override
    public AppUser getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public int updateUser(AppUser user) {
        return userRepository.updateUser(user);
    }

    @Override
    public int deleteUser(AppUser user) {
        return userRepository.deleteUser(user);
    }
    
    @Override
    public List<AppUser> getListByRequest(String keyword, int page, int row, String columnOrder, String order) {
        return userRepository.getListByRequest(keyword, page, row, columnOrder, order);
    }

    @Override
    public int getTotalRecords() {
        return userRepository.getTotalRecords();
    }

    @Override
    public int getTotalFilteredRecords(String filter) {
        return userRepository.getFilteredSize(filter);
    }
    
    @Autowired
    private UserRepositoryContract userRepository;

}
