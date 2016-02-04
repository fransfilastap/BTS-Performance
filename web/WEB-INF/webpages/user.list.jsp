<%-- 
    Document   : user.list
    Created on : Sep 19, 2015, 5:12:37 PM
    Author     : Frans Filasta Pratama <franspratama@mail01.huawei.com>
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context_path" value="${pageContext.request.contextPath}"></c:set>
              <div class="box">
                <div class="box-header">
                  <h3 class="box-title">Users</h3>
                </div><!-- /.box-header -->
                <div class="box-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="pull-left">
                                <a href="#" class="btn btn-primary btn-xs" id="create"><i class="fa fa-plus"></i> Add</a>
                            </div>
                        </div>
                    </div>
                    <p></p>
                  <table id="users" class="table table-hover table-bordered">
                    <thead>
                      <tr>
                        <th>NAME</th>
                        <th>USERNAME</th>
                        <th>EMAIL</th>
                        <th>PHONE</th>
                        <th>ROLE</th>
                        <th>ACTION</th>
                      </tr>
                    </thead>
                    <tbody></tbody>
                    <tfoot>
                      <tr>
                        <th>NAME</th>
                        <th>USERNAME</th>
                        <th>EMAIL</th>
                        <th>PHONE</th>
                        <th>ROLE</th>
                        <th>ACTION</th>
                      </tr>
                    </tfoot>
                  </table>
                </div>
              </div>

        <div id="user-create-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="create_modal1" aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">Add User</h4>
                  </div>
                  <div class="modal-body">
                    <form id="create_user_form" class="form-horizontal">
                        <div class="form-group">
                          <label for="fullname" class="col-sm-2 control-label">Full Name</label>
                          <div class="col-sm-10">
                            <input type="text" class="form-control" id="fullname" name="fullname" placeholder="Full name">
                          </div>
                        </div>
                        <div class="form-group">
                          <label for="username" class="col-sm-2 control-label">Email</label>
                          <div class="col-sm-10">
                            <input type="email" class="form-control" id="email" name="email" placeholder="Email">
                          </div>
                        </div>                        
                        <div class="form-group">
                          <label for="username" class="col-sm-2 control-label">Username</label>
                          <div class="col-sm-10">
                            <input type="text" class="form-control" id="username" name="password" placeholder="Username">
                          </div>
                        </div> 
                        <div class="form-group">
                          <label for="password" class="col-sm-2 control-label">Password</label>
                          <div class="col-sm-10">
                            <input type="password" class="form-control" id="password" name="password">
                          </div>
                        </div>  
                        <div class="form-group">
                          <label for="role" class="col-sm-2 control-label">Role</label>
                          <div class="col-sm-10">
                            <select id="role" name="role" class="form-control">
                                <option value="ROLE_ADMIN">ADMIN</option>
                                <option value="ROLE_NOC">NOC</option>
                            </select>
                          </div>
                        </div>                                                                                            
                    </form>
                  </div>
                  <div class="modal-footer">
                      <button type="button" class="btn btn-default pull-left" data-dismiss="modal" >Cancel</button>
                    <button type="submit" class="btn btn-primary">Save</button>
                    </form>
                  </div>
                </div>
              </div>
            </div>
              
        <div id="user-edit-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="create_modal1" aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">Add User</h4>
                  </div>
                  <div class="modal-body">
                    <form id="create_user_form" class="form-horizontal">
                        <div class="form-group">
                          <label for="fullname" class="col-sm-2 control-label">Full Name</label>
                          <div class="col-sm-10">
                            <input type="text" class="form-control" id="fullname" name="fullname" placeholder="Full name">
                          </div>
                        </div>
                        <div class="form-group">
                          <label for="username" class="col-sm-2 control-label">Email</label>
                          <div class="col-sm-10">
                            <input type="email" class="form-control" id="email" name="email" placeholder="Email">
                          </div>
                        </div>                        
                        <div class="form-group">
                          <label for="username" class="col-sm-2 control-label">Username</label>
                          <div class="col-sm-10">
                            <input type="text" class="form-control" id="username" name="password" placeholder="Username">
                          </div>
                        </div> 
                        <div class="form-group">
                          <label for="password" class="col-sm-2 control-label">Password</label>
                          <div class="col-sm-10">
                            <input type="password" class="form-control" id="password" name="password">
                          </div>
                        </div>  
                        <div class="form-group">
                          <label for="role" class="col-sm-2 control-label">Role</label>
                          <div class="col-sm-10">
                              <select id="role" name="role" class="form-control">
                                <option value="ROLE_ADMIN">ADMIN</option>
                                <option value="ROLE_NOC">NOC</option>
                            </select>
                          </div>
                        </div>                                                                                            
                    </form>
                  </div>
                  <div class="modal-footer">
                      <button type="button" class="btn btn-default pull-left" data-dismiss="modal" >Cancel</button>
                    <button type="submit" class="btn btn-primary">Save</button>
                    </form>
                  </div>
                </div>
              </div>
            </div>   
              
        <div id="user-detail-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="create_modal1" aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">Detail</h4>
                  </div>
                  <div class="modal-body">
                    <form id="create_user_form" class="form-horizontal">
                        <div class="form-group">
                          <label for="fullname" class="col-sm-2 control-label">Full Name</label>
                          <div class="col-sm-10">
                            <input type="text" class="form-control" id="fullname" name="fullname" placeholder="Full name">
                          </div>
                        </div>
                        <div class="form-group">
                          <label for="username" class="col-sm-2 control-label">Email</label>
                          <div class="col-sm-10">
                            <input type="email" class="form-control" id="email" name="email" placeholder="Email">
                          </div>
                        </div>                        
                        <div class="form-group">
                          <label for="username" class="col-sm-2 control-label">Username</label>
                          <div class="col-sm-10">
                            <input type="text" class="form-control" id="username" name="password" placeholder="Username">
                          </div>
                        </div> 
                        <div class="form-group">
                          <label for="password" class="col-sm-2 control-label">Password</label>
                          <div class="col-sm-10">
                            <input type="password" class="form-control" id="password" name="password">
                          </div>
                        </div>  
                        <div class="form-group">
                          <label for="role" class="col-sm-2 control-label">Role</label>
                          <div class="col-sm-10">
                            <select id="role" name="role" class="form-control">
                                <option value="ROLE_ADMIN">ADMIN</option>
                                <option value="ROLE_NOC">NOC</option>
                            </select>
                          </div>
                        </div>                                                                                            
                    </form>
                  </div>
                  <div class="modal-footer">
                      <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary">Save</button>
                    </form>
                  </div>
                </div>
              </div>
            </div>              
                        
