/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.repository.rowmapper;

import com.huawei.gsm.service.UserServiceContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */
@Service
public class AuthenticatedUserService {
    
    public String getAuthenticatedUserFullname( UserServiceContract userService ){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        String fullname = userService.getUserByUsername(username).getFullname();
        
        return fullname;
    }
    
    
    
}
