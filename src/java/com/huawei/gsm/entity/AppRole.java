/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Set;


/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */
public class AppRole implements Serializable {

    private int id;
    
    private String role;
    
    private Set<AppUser> userRoles;

    public AppRole() {
        
    }

    public AppRole(String role) {
        this.role = role;
    }
    
    

    public AppRole(String role, Set<AppUser> userRoles) {
        this.role = role;
        this.userRoles = userRoles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    @JsonIgnore
    public Set<AppUser> getUserRoles() {
        return userRoles;
    }
    @JsonIgnore
    public void setUserRoles(Set<AppUser> userRoles) {
        this.userRoles = userRoles;
    }
    
    
}
