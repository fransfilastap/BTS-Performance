/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.entity;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */

public class AppUser implements Serializable {
    
    private int id;
    private String fullname;
    private String username;
    private String email;
    private String phone;
    private Timestamp created;
    private Timestamp modified;
    private Timestamp lastLogin;
    private String password;
    private boolean enabled;
    private AppRole role;

    public AppUser(String fullname, String username, String email, String phone, Timestamp created, Timestamp modified, Timestamp lastLogin) {
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.created = created;
        this.modified = modified;
        this.lastLogin = lastLogin;
    }

    public AppUser() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getModified() {
        return modified;
    }

    public void setModified(Timestamp modified) {
        this.modified = modified;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    public AppRole getRole() {
        return role;
    }

    public void setRole(AppRole role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    
    
    
}
