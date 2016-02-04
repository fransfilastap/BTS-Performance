/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.controller;

import com.huawei.gsm.repository.rowmapper.AuthenticatedUserService;
import com.huawei.gsm.service.UserServiceContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */
@Controller
public class HomeController {
    
    @RequestMapping(value = {"/","/map.jsp","index.html"},method = RequestMethod.GET)
    public String map(ModelMap model){

        model.addAttribute("username",auService.getAuthenticatedUserFullname(userService));
        
        return "index";
    }
    
    @RequestMapping(value={"/login"},method= RequestMethod.GET )
    public ModelAndView loginPage(ModelAndView model){
        model.setViewName("login");
        
        return model;
    }
    
    @RequestMapping(value={"/logout"},method=GET)
    public ModelAndView logoutPage(){
        return new ModelAndView("redirect:/login");
    }
    
       
    
    @RequestMapping(value={"/admin.jsp"},method = RequestMethod.GET)
    public String adminPage(){
        return "admin";
    }
    
    
    @Autowired
    private UserServiceContract userService;
    
    @Autowired
    private AuthenticatedUserService auService;
}
