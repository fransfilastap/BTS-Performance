/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.util.requestholder;

import org.springframework.stereotype.Component;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */

@Component
public class RequestStatus {
    
    public boolean status = false;
    public String message;

    public RequestStatus() {
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
    
}
