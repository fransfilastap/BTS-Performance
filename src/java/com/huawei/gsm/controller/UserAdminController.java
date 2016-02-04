/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.controller;

import com.huawei.gsm.entity.AppUser;
import com.huawei.gsm.repository.rowmapper.AuthenticatedUserService;
import com.huawei.gsm.service.UserServiceContract;
import com.huawei.gsm.string.UILang;
import com.huawei.gsm.util.datatable.DataTableRequestWrapper;
import com.huawei.gsm.util.datatable.UserDataTable;
import com.huawei.gsm.util.requestholder.UserEditJson;
import com.huawei.gsm.util.requestholder.UserJson;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */

@Controller
public class UserAdminController {
    
    @Autowired
    private UserServiceContract userService;
    @Autowired
    private AuthenticatedUserService auService;   
    
    
    @RequestMapping( value = { "/admin/data/users/list" }, method = RequestMethod.POST , consumes = {"application/json"} )
    public @ResponseBody UserDataTable dataTable( @RequestBody DataTableRequestWrapper dataTableRequest, HttpServletRequest rq ){
        //request
        String searchKeyword = dataTableRequest.getSearch().getValue();
        int start = dataTableRequest.getStart();
        int length = dataTableRequest.getLength();
        int draw = dataTableRequest.getDraw();
        String order = dataTableRequest.getOrder().get(0).getDir();
        int column = dataTableRequest.getOrder().get(0).getColumn();
        String columnName = dataTableRequest.getColumns().get( column ).getData();
        //</-- end of request variable -->
        
        //response
        int rDraw = draw;
        int recordsTotal = userService.getTotalRecords();
        int recordsFiltered = userService.getTotalFilteredRecords( searchKeyword );
        //DataTable
        UserDataTable response = new UserDataTable(); 
        //all sites
        List<AppUser> users = userService.getListByRequest(searchKeyword, start, length, columnName, order);
        //siteJson list
        List<UserJson> userObjects = new ArrayList<>();
        
        //baseurl
        String baseUrl = String.format("%s://%s:%d%s",rq.getScheme() , rq.getServerName(), rq.getServerPort(), rq.getContextPath());

        users.stream().map((get) -> {
            UserJson jsonObj;
            jsonObj = new UserJson();
            jsonObj.setFullname( get.getFullname() );
            jsonObj.setEmail( get.getEmail() );
            jsonObj.setRole( get.getRole().getRole() );
            jsonObj.setUsername( get.getUsername() );
            jsonObj.setPhone( get.getPhone() );
            jsonObj.setAction("<div class='btn-group'>\n" +
                                "                      <button type='button' class='btn btn-success btn-xs'>"+UILang.BUTTON_CRUD+"</button>\n" +
                                "                      <button type='button' class='btn btn-success dropdown-toggle btn-xs' data-toggle='dropdown'>\n" +
                                "                        <span class='caret'></span>\n" +
                                "                        <span class='sr-only'>Toggle Dropdown</span>\n" +
                                "                      </button>\n" +
                                "                      <ul class='dropdown-menu' role='menu'>\n" +
                                "                        <li><a href='"+baseUrl+"/admin/data/users/get/"+get.getId()+"' class='detail'>"+UILang.BUTTON_CRUD_DETAIL+"</a></li>\n" +
                                "                        <li><a href='"+baseUrl+"/admin/data/users/get/"+get.getId()+"' class='edit'>"+UILang.BUTTON_CRUD_EDIT+"</a></li>\n" +                    
                                "                        <li class='divider'></li>\n" +
                                "                        <li><a href='"+baseUrl+"/admin/data/users/delete/"+get.getId()+"' class='delete-user'>"+UILang.BUTTON_CRUD_DELETE+"</a></li>\n" +
                                "                      </ul>\n" +
                                "                    </div>");
            
            return jsonObj;
            
        }).forEach((jsonObj) -> {
            userObjects.add( jsonObj );
        });
        
        response.setData(userObjects);
        response.setRecordsTotal(recordsTotal);
        response.setRecordsFiltered( recordsFiltered );
        response.setDraw( rDraw );
        
        return response;    
    }
    
    @RequestMapping( value = { "/admin/data/users", "/admin/data/users.html" }, method =RequestMethod.GET )
    public ModelAndView getIndex(){
        ModelAndView model = new ModelAndView();
        model.addObject("current", "users");
        model.addObject("file", "user.list.jsp");
        model.addObject("fullname", auService.getAuthenticatedUserFullname(userService));
        model.setViewName("admin");
        return model;
    }
    
    @RequestMapping( value = { "/admin/data/users/get/{userId}" }, produces = { "application/json" }, method = RequestMethod.GET )
    public @ResponseBody UserEditJson getUser( @PathVariable int userId ){
        
        AppUser user = userService.getUserById(userId);
        UserEditJson json = new UserEditJson();
                     json.setId( user.getId() ) ;
                     json.setEmail( user.getEmail() );
                     json.setFullname( user.getFullname() );
                     json.setPhone( user.getPhone() );
                     json.setRole( user.getRole().getRole() );
        
        return json;
    }
    
}
