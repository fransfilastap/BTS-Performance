<%-- 
    Document   : site.list
    Created on : Sep 15, 2015, 3:27:29 PM
    Author     : Frans Filasta Pratama <franspratama@mail01.huawei.com>
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context_path" value="${pageContext.request.contextPath}"></c:set>
              <div class="box">
                <div class="box-header">
                  <h3 class="box-title">Sites</h3>
                </div><!-- /.box-header -->
                <div class="box-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="pull-left">
                                <a href="#" class="btn btn-primary btn-xs" id="create"><i class="fa fa-plus"></i> Create</a>
                                <a href="#" class="btn btn-primary btn-xs" id="upload"><i class="fa fa-upload"></i> Upload</a>
                                <a href="${context_path}/admin/data/sites/export" class="btn btn-primary btn-xs" id="download"><i class="fa fa-download"></i> Download</a>
                            </div>
                        </div>
                    </div>
                    <p></p>
                  <table id="sites" class="table table-hover table-bordered table-striped">
                    <thead>
                      <tr>
                        <th>SITE ID</th>
                        <th>SITE NAME</th>
                        <th>ADDRESS</th>
                        <th>GROUP</th>
                        <th>LATITUDE</th>
                        <th>LONGITUDE</th>
                        <th>ACTION</th>
                      </tr>
                    </thead>
                    <tbody>

                    </tbody>
                    <tfoot>
                      <tr>
                        <th>SITE ID</th>
                        <th>SITE NAME</th>
                        <th>ADDRESS</th>
                        <th>GROUP</th>
                        <th>LATITUDE</th>
                        <th>LONGITUDE</th>
                        <th>ACTION</th>
                      </tr>
                    </tfoot>
                  </table>
                </div>
              </div>
              
              <div id="create_modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="create_modal1" aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <button type="button" class="close" id="close_create_modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">Add New Site</h4>
                  </div>
                  <div class="modal-body">
                      <form class="form-horizontal" id="site_form" method="post" action="${context_path}/admin/data/sites/save">
                        <div class="form-group">
                          <label for="inputEmail3" class="col-sm-2 control-label">Site ID</label>
                          <div class="col-sm-10">
                            <input type="text" class="form-control" id="siteid" placeholder="Site ID">
                          </div>
                        </div>
                        <div class="form-group">
                          <label for="inputPassword3" class="col-sm-2 control-label">Site Name</label>
                          <div class="col-sm-10">
                            <input type="text" class="form-control" id="sitename" placeholder="Site Name">
                          </div>
                        </div>
                        <div class="form-group">
                          <label for="address" class="col-sm-2 control-label">Address</label>
                          <div class="col-sm-10">
                            <textarea class="form-control" id="address" rows="3" placeholder="Site Address"></textarea>
                          </div>
                        </div>
                        <div class="form-group">
                          <label for="sitegroup" class="col-sm-2 control-label">Group</label>
                          <div class="col-sm-10">
                            <select id="sitegroup" class="form-control">
                                <option value="GOLDEN_SITE">Golden Site</option>
                              <option value="EVENT_SITE">Event Site</option>
                            </select>
                          </div>
                        </div>
                        <div class="form-group">
                          <label for="latitude" class="col-sm-2 control-label">Latitude</label>
                          <div class="col-sm-10">
                            <input type="text" class="form-control" id="latitude" placeholder="latitude">
                          </div>
                        </div>
                        <div class="form-group">
                          <label for="longitude" class="col-sm-2 control-label">Longitude</label>
                          <div class="col-sm-10">
                            <input type="text" class="form-control" id="longitude" placeholder="longitude">
                          </div>
                        </div>
                        <div class="form-group">
                          <label for="add_cell" class="col-sm-2 control-label"></label>
                          <div class="col-sm-10">
                              <a href="#" class="btn btn-info btn-xs added_cell"><span class="total_cell">0</span> Cell(s) </a> <a href="#" class="btn btn-default btn-xs add_cell">Add Cell</a>
                          </div>
                        </div>                        
                  </div>
                  <div class="modal-footer">
                      <button type="button" class="btn btn-default pull-left" id="cancel_site" >Cancel</button>
                    <button type="submit" class="btn btn-primary">Save</button>
                    </form>
                  </div>
                </div>
              </div>
            </div>
                        
            <div id="cell_modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="create_modal1" aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <button type="button" class="close" id="close_cell_modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">Add Cell</h4>
                  </div>
                  <div class="modal-body">
                      <form id="cell_form" class="form-horizontal">
                        <div class="form-group">
                          <label for="inputEmail3" class="col-sm-2 control-label">Cell Index</label>
                          <div class="col-sm-10">
                            <input type="text" class="form-control" id="cell_index" placeholder="Cell Index">
                          </div>
                        </div>
                        <div class="form-group">
                          <label for="inputPassword3" class="col-sm-2 control-label">Cell Name</label>
                          <div class="col-sm-10">
                            <input type="text" class="form-control" id="cell_name" placeholder="Cell Name">
                          </div>
                        </div>
                        <div class="form-group">
                          <label for="sitegroup" class="col-sm-2 control-label">Frequency</label>
                          <div class="col-sm-3">
                            <select id="ne" class="form-control">
                              <option>GSM</option>
                              <option>UMTS</option>
                              <option>LTE</option>
                            </select>
                          </div>
                          <div class="col-sm-2">
                              <input type="text" class="form-control" id="frequency" placeholder="">
                          </div>
                        </div>                      
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary">Add</button>
                    </form>
                  </div>
                </div>
              </div>
            </div>

            <div id="cell_edit_modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="create_modal1" aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <button type="button" class="close" id="close_cell_modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">Edit Cell</h4>
                  </div>
                  <div class="modal-body">
                      <form id="cell_edit_form" class="form-horizontal">
                          <input type="hidden" id="cell_id_edit" />
                        <div class="form-group">
                          <label for="inputEmail3" class="col-sm-2 control-label">Cell Index</label>
                          <div class="col-sm-10">
                            <input type="text" class="form-control" id="cell_index_edit" placeholder="Cell Index">
                          </div>
                        </div>
                        <div class="form-group">
                          <label for="inputPassword3" class="col-sm-2 control-label">Cell Name</label>
                          <div class="col-sm-10">
                            <input type="text" class="form-control" id="cell_name_edit" placeholder="Cell Name">
                          </div>
                        </div>
                        <div class="form-group">
                          <label for="sitegroup" class="col-sm-2 control-label">Frequency</label>
                          <div class="col-sm-3">
                            <select id="ne_edit" class="form-control">
                              <option>GSM</option>
                              <option>UMTS</option>
                              <option>LTE</option>
                            </select>
                          </div>
                          <div class="col-sm-2">
                              <input type="text" class="form-control" id="frequency_edit" placeholder="">
                          </div>
                        </div>                      
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary">Save</button>
                    </form>
                  </div>
                </div>
              </div>
            </div>                        
                        
            <div id="added_cell_modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="create_modal1" aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">Added Cell</h4>
                  </div>
                  <div class="modal-body">
                  <table class="table table-bordered" id="added_cell_table">
                      <thead>
                          <tr>
                            <th style="width: 10px">#</th>
                            <th>Cell Index</th>
                            <th>Cell Name</th>
                            <th style="width: 40px">Frequency</th>
                            <th style="width: 100px">Action</th>
                          </tr>
                      </thead>
                    <tbody>

                    </tbody>
                  </table>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-primary" id="close-added-cell-modal">Close</button>
                  </div>
                </div>
              </div>
            </div>
            </div>
                        
           <div id="upload_modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="create_modal1" aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">Upload Site Data</h4>
                  </div>
                  <div class="modal-body">
                      <form role="form" method="post" id="upload_site" action="${context_path}/admin/data/site/upload" enctype="multipart/form-data">
                          <div class="form-group">
                            <label for="exampleInputFile">Excel File</label>
                            <input type="file" id="excel_file" name="excel_file">
                            <p class="help-block"></p>
                          </div>
                      
                  <div class="modal-footer">
                    <button type="submit" class="btn btn-primary">Submit</button>
                    </form>
                  </div>
                </div>
              </div>
            </div>
            </div>

            <div id="site_cell_modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="create_modal1" aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">Site's Cells</h4>
                  </div>
                  <div class="modal-body">
                  <table class="table table-bordered" id="site_cell_table">
                      <thead>
                          <tr>
                            <th style="width: 10px">#</th>
                            <th>Cell Index</th>
                            <th>Cell Name</th>
                            <th style="width: 40px">Frequency</th>
                          </tr>
                      </thead>
                    <tbody>

                    </tbody>
                  </table>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-primary" id="close-added-cell-modal">Close</button>
                  </div>
                </div>
              </div>
            </div>
            </div>                        
                        
                        
            <div id="detail_modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="create_modal1" aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">Site Detail</h4>
                  </div>
                  <div class="modal-body">
                      <table class="table table-bordered">
                          <tbody>
                              <tr>
                                  <td style="width:120px;font-weight: bold">Site ID</td>
                                  <td id="detail_siteid"></td>
                              </tr>
                              <tr>
                                  <td style="width:120px;font-weight: bold">Site Name</td>
                                  <td id="detail_sitename"></td>
                              </tr>
                              <tr>
                                  <td style="width:120px;font-weight: bold">Address</td>
                                  <td id="detail_address"></td>
                              </tr>
                              <tr>
                                  <td style="width:120px;font-weight: bold">Site Group</td>
                                  <td id="detail_group"></td>
                              </tr>    
                              <tr>
                                  <td style="width:120px;font-weight: bold">Latitude</td>
                                  <td id="detail_latitude"></td>
                              </tr>   
                              <tr>
                                  <td style="width:120px;font-weight: bold">Longitude</td>
                                  <td id="detail_longitude"></td>
                              </tr> 
                              <tr>
                                  <td style="width:120px;font-weight: bold">Longitude</td>
                                  <td id="detail_cells"><a href="#" id="see_cell"><span id="total_cells">0</span> Cells</a></td>
                              </tr> 
                          </tbody>
                      </table>
                  </div>
                  <div class="modal-footer">
                      <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
                  </div>
                </div>
              </div>
            </div>
                        
              <div id="edit_modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="create_modal1" aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <button type="button" class="close" id="close_create_modal" aria-label="Close" data-dismiss="modal" ><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">Edit Site</h4>
                  </div>
                  <div class="modal-body">
                      <form class="form-horizontal" id="site_form" method="post" action="${context_path}/admin/data/sites/update">
                        <div class="form-group">
                          <label for="inputEmail3" class="col-sm-2 control-label">Site ID</label>
                          <div class="col-sm-10">
                            <input type="text" class="form-control" id="siteid_edit" placeholder="Site ID">
                          </div>
                        </div>
                        <div class="form-group">
                          <label for="inputPassword3" class="col-sm-2 control-label">Site Name</label>
                          <div class="col-sm-10">
                            <input type="text" class="form-control" id="sitename_edit" placeholder="Site Name">
                          </div>
                        </div>
                        <div class="form-group">
                          <label for="address" class="col-sm-2 control-label">Address</label>
                          <div class="col-sm-10">
                            <textarea class="form-control" id="address_edit" rows="3" placeholder="Site Address"></textarea>
                          </div>
                        </div>
                        <div class="form-group">
                          <label for="sitegroup" class="col-sm-2 control-label">Group</label>
                          <div class="col-sm-10">
                            <select id="sitegroup_edit" class="form-control">
                                <option value="GOLDEN_SITE">Golden Site</option>
                              <option value="EVENT_SITE">Event Site</option>
                            </select>
                          </div>
                        </div>
                        <div class="form-group">
                          <label for="latitude" class="col-sm-2 control-label">Latitude</label>
                          <div class="col-sm-10">
                            <input type="text" class="form-control" id="latitude_edit" placeholder="latitude">
                          </div>
                        </div>
                        <div class="form-group">
                          <label for="longitude" class="col-sm-2 control-label">Longitude</label>
                          <div class="col-sm-10">
                            <input type="text" class="form-control" id="longitude_edit" placeholder="longitude">
                          </div>
                        </div>
                        <div class="form-group">
                          <label for="add_cell" class="col-sm-2 control-label"></label>
                          <div class="col-sm-10">
                              <a href="#" class="btn btn-info btn-xs added_cell"><span class="total_cell">0</span> Cell(s) </a> <a href="#" class="btn btn-default btn-xs add_cell">Add Cell</a>
                          </div>
                        </div>                        
                  </div>
                  <div class="modal-footer">
                      <button type="button" class="btn btn-default pull-left" data-dismiss="modal" >Cancel</button>
                      <button type="submit" class="btn btn-primary">Save</button>
                    </form>
                  </div>
                </div>
              </div>
            </div>                        
                        
                        