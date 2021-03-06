<%-- 
    Document   : index
    Created on : Aug 20, 2015, 6:08:21 PM
    Author     : franspratama@mail01.huawei.com
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context_path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
    <title>Golden Site Monitoring</title>
    <meta name="description" content="">
    <meta name="author" content="huawei">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="assets/vendor/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="${context_path}/assets/css/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="${context_path}/assets/css/ionicons/css/ionicons.min.css">
    <link rel="stylesheet" type="text/css" href="assets/vendor/perfect-scrollbar/css/perfect-scrollbar.min.css">
    <link rel="stylesheet" type="text/css" href="assets/vendor/jquery-ui/jquery-ui.min.css"/>
    <link rel="stylesheet" type="text/css" href="assets/css/app.css">
</head>
<body>
    
    <div class="container-fluid">
            <section id="header">
                    <div class="row">
                            <div class="col-lg-12">
                                    <a href="#" class="app-title"><img src="assets/img/logo-color-sm.png"></a>
                                    <div class="pull-right">
                                            <ul class="inline text-white">
                                                    <li><span class="circle circle-normal"></span>Normal</li>
                                                    <li><span class="circle circle-minor"></span>Minor</li>
                                                    <li><span class="circle circle-major"></span>Major</li>
                                                    <li><span class="circle circle-critical"></span>Critical</li>
                                                    <li>
                                                        <div class="btn-group">
                                                            <a href="" class="btn btn-xs btn-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                              ${username} <span class="caret"></span>
                                                            </a>
                                                            <ul class="dropdown-menu">
                                                            <sec:authorize access="hasAnyRole('ADMIN')">
                                                              <li><a href="${context_path}/admin/index.html">Admin Page</a></li>
                                                            </sec:authorize>
                                                              <li role="separator" class="divider"></li>
                                                              <li><a href="${context_path}/logout">Logout</a></li>
                                                            </ul>
                                                        </div>
                                                    </li>
                                            </ul>
                                    </div>
                            </div>
                    </div>
            </section>
    </div>
    <div class="container-fluid">
        <section id="content-wrapper">
            <div id="map"></div>
		<div class="network-selection">
                    <ul class="inline">
                            <li><a href="#" id="gsm" data-page="GSM" class="mbtn">GSM</a></li>
                            <li><a href="#" id="umts" data-page="UMTS" class="mbtn">UMTS</a></li>
                            <li><a href="#" id="lte" data-page="LTE" class="mbtn">LTE</a></li>
                    </ul>
		</div>
		<div class="sitetype-selection">
                    <ul class="inline">
                        <li><a href="#" class="mbtn" id="golden" data-page="GOLDEN">Golden Site</a></li>
                            <li><a href="#" class="mbtn" id="event" data-page="EVENT">Event Sites</a></li>
                    </ul>
		</div>
		<div class="app-frame app-frame-shawow">
                    <a href="#" class="toggle app-frame-shadow"></a>
			<div class="app-frame-content">
                            <div id="news-frame">
				<div class="row-fluid">
                                    <div id="critical" class="col-lg-4 col-md-4 col-sm-4">
                                        <div class="panel panel-primary mpanel medium-height">
                                           <div class="panel-heading">
                                              <h3 class="panel-title text-center" style="color:#00ff00;">Alarm</h3>
                                              <h3 class="panel-title pull-right" id="total_critical_alarm" style="color:#00ff00;position: relative;top:-17px;">0</h3>
                                           </div>
                                           <div class="panel-body">
                                                <div class="table-responsive scrollable">
                                                    <table class="table regular">
                                                        <thead>
                                                            <tr>
                                                                <th style="width:10%">No.</th>
                                                                <th style="width:30%">Site Name</th>
                                                                <th>Node Name</th>
                                                                <th>Alarm Name</th>
                                                                <th>Ticket Status</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody id="zzz">
                                                        </tbody>
                                                    </table>
                                                </div>
                                           </div>
                                        </div>
                                    </div>
                                    <div id="kpi" class="col-lg-4 col-md-4 col-sm-4">
                                        <div class="panel panel-primary mpanel medium-height">
                                           <div class="panel-heading">
                                              <h3 class="panel-title text-center" style="color:#ff0000">KPI</h3>
                                           </div>
                                           <div class="panel-body">
                                                <div class="table-responsive scrollable">
                                                    <table class="table kpi-table">
                                                        <tbody>
                                                            <tr>
                                                                <td>1</td>
                                                                <td style="width:70%"><a href="#" class="frame-link" data-type="kpi">2G RAN Call Set Up Success Rate, Speech</a></td>
                                                                <td id="2gRanCall">80%</td>
                                                                <td><span class="indicator" id="2gRanCallIndicator"></span></td>
                                                            </tr>
                                                            <tr>
                                                                <td>2</td>
                                                                <td style="width:70%"><a href="#" class="frame-link" data-type="kpi">2G Call Minutes between Drop</a></td>
                                                                <td id="2gCallMinutes">5.2</td>
                                                                <td><span class="indicator" id="2gCallMinutesIndicator"></span></td>
                                                            </tr>
                                                            <tr>
                                                                <td>3</td>
                                                                <td style="width:70%"><a href="#" class="frame-link" data-type="kpi">3G RAN Call Set Up Success Rate, Speech</a></td>
                                                                <td id="3gRanCall">80%</td>
                                                                <td><span class="indicator" id="3gRanCallIndicator"></span></td>
                                                            </tr>
                                                            <tr>
                                                                <td>4</td>
                                                                <td style="width:70%"><a href="#" class="frame-link" data-type="kpi">3G RAN PS Accessibility</a></td>
                                                                <td id="3gRanPS">80%</td>
                                                                <td><span class="indicator" id="3gRanPSIndicator"></span></td>
                                                            </tr>
                                                            <tr>
                                                                <td>5</td>
                                                                <td style="width:70%"><a href="#" class="frame-link" data-type="kpi">3G Call Minutes between Drop</a></td>
                                                                <td id="3gCallMinutes">5.3</td>
                                                                <td><span class="indicator" id="3gCallMinutesIndicator"></span></td>
                                                            </tr>
                                                            <tr>
                                                                <td>6</td>
                                                                <td style="width:70%"><a href="#" class="frame-link" data-type="kpi">3G Minutes between PS Abnormal Releases</a></td>
                                                                <td id="3gMinutesPS">6.2</td>
                                                                <td><span class="indicator" id="3gMinutesPSIndicator"></span></td>
                                                            </tr>
                                                            <tr>
                                                                <td>7</td>
                                                                <td style="width:70%"><a href="#" class="frame-link" data-type="kpi">4G Establishment Success Rate</a></td>
                                                                <td id="4GEstablishment">95%</td>
                                                                <td><span class="indicator" id="4GEstablishmentIndicator"></span></td>
                                                            </tr> 
                                                            <tr>
                                                                <td>8</td>
                                                                <td style="width:70%"><a href="#" class="frame-link" data-type="kpi">4G Minutes between E-RAB abnormal releases</a></td>
                                                                <td id="4gMinutesERAB">6.3</td>
                                                                <td><span class="indicator" id="4gMinutesERABIndicator"></span></td>
                                                            </tr>                                                          
                                                        </tbody>
                                                    </table>
                                                </div>
                                           </div>
                                        </div>
                                        </div>
                                    <div id="kqi" class="col-lg-4 col-md-4 col-sm-4">
                                        <div class="panel panel-primary mpanel medium-height">
                                           <div class="panel-heading">
                                              <h3 class="panel-title text-center" style="color:#00ff00">KQI</h3>
                                           </div>
                                           <div class="panel-body">
                                                <div class="table-responsive scrollable">
                                                    <table class="table regular">
                                                        <tbody>
                                                            <tr>
                                                                <td style="width:5%">1</td>
                                                                <td style="width:60%"><a href="#" class="frame-link" data-type="kqi">Page Browsing Success Rate</a></td>
                                                                <td id="kqi_bsr">80%</td>
                                                                <td style="width:10%"><span class="indicator" id="bsr_indicator"></span></td>
                                                            </tr>
                                                            <tr>
                                                                <td style="width:5%">2</td>
                                                                <td style="width:60%"><a href="#" class="frame-link" data-type="kqi">Page Browsing Delay</a></td>
                                                                <td id="kqi_bd">80%</td>
                                                                <td style="width:10%"><span class="indicator" id="bd_indicator"></span></td>
                                                            </tr>
                                                            <tr>
                                                                <td style="width:5%">3</td>
                                                                <td style="width:60%"><a href="#" class="frame-link" data-type="kqi">Video Streaming Start Success Rate</a></td>
                                                                <td id="kqi_ssr">80%</td>
                                                                <td style="width:10%"><span class="indicator" id="ssr_indicator"></span></td>
                                                            </tr>
                                                            <tr>
                                                                <td style="width:5%">4</td>
                                                                <td style="width:60%"><a href="#" class="frame-link" data-type="kqi">Video Streaming Start Delay</a></td>
                                                                <td id="kqi_ssd">80%</td>
                                                                <td style="width:10%"><span class="indicator" id="ssd_indicator"></span></td>
                                                            </tr>                                                    
                                                        </tbody>
                                                    </table>
                                                </div>
                                           </div>
                                        </div>
                                        </div>
                                    </div>
				</div>
				<div id="alarm-detail" class="animated">
					<div class="row-fluid">
						<div class="col-lg-12">
                                                   <div class="panel panel-primary mpanel">
						   <div class="panel-heading">
								<h3 class="panel-title pull-left" style="color:#fff;text-align: left;">ALARM</h3>
                                                                <button class="btn btn-xs btn-primary pull-right back"><i class="fa fa-chevron-left"></i></button>
	    						<div class="clearfix"></div>
						   </div>
						   <div class="panel-body">
						   	<div class="row">
                                                            <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">
                                                                <div class="panel panel-primary mpanel-child alarm-height">
                                                                    <div class="panel-body">
                                                                        <div id="doughnutChart" class="chart"></div>
                                                                        <ul class="d-legend">
                                                                            <li><span class="white-circle circle-critical"></span>Critical</li>
                                                                            <li><span class="white-circle circle-major"></span>Major</li>
                                                                            <li><span class="white-circle circle-minor"></span>Minor</li>
                                                                        </ul>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
                                                                    <div class="panel panel-primary mpanel-child alarm-height">
                                                                        <div class="panel-heading">
                                                                            <h3 class="panel-title">Alarm List</h3>
                                                                        </div>
                                                                        <div class="panel-body">
                                                                        <div class="table-responsive scrollable">
                                                                            <table id="alarmlisttable" class="table premium">
                                                                                <thead>
                                                                                    <tr>
                                                                                        <th>Site ID</th>
                                                                                        <th>Site Name</th>
                                                                                        <th>Severity</th>
                                                                                        <th>Node Name</th>
                                                                                        <th>Alarm Name</th>
                                                                                        <th>Last Occurrence</th>
                                                                                        <th>Ticket ID</th>
                                                                                        <th>Summary</th>
                                                                                    </tr>
                                                                                </thead>
                                                                                <tbody>
                                                                                </tbody>
                                                                            </table>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
						   	</div>
						   </div>
						</div>
                                            </div>
                                        </div>
                                    </div>	
                                    <div id="kpi-detail">
					<div class="row-fluid">
						<div class="col-lg-12">
						<div class="panel panel-primary mpanel">
						   <div class="panel-heading">
                                                        <h3 class="panel-title pull-left" style="color:#fff;text-align: left;">KPI</h3>
                                                        <button class="btn btn-xs btn-primary pull-right back"><i class="fa fa-chevron-left"></i></button>
	    						<div class="clearfix"></div>
						   </div>
						   <div class="panel-body">
                                                        <div class="row">
                                                            <div class="col-md-3 col-sm-3">
                                                                <div class="panel panel-primary mpanel-child low-height">
                                                                    <div class="panel-body scrollable">
                                                                        <div class="table-responsive">
                                                                            <table class="table kpi-table">
                                                                                <tbody>
                                                                                    <tr>
                                                                                        <td>1</td>
                                                                                        <td><a href="#" class="frame-link" data-type="kpi_group" data-key="rcsur2g">2G RAN Call Set Up Success Rate, Speech</a></td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td>2</td>
                                                                                        <td><a href="#" class="frame-link" data-type="kpi_group" data-key="cmbtd2g">2G Call Minutes between Drop</a></td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td>3</td>
                                                                                        <td><a href="#" class="frame-link" data-type="kpi_group" data-key="rcsur3g">3G RAN Call Set Up Success Rate, Speech</a></td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td>4</td>
                                                                                        <td><a href="#" class="frame-link" data-type="kpi_group" data-key="ranpsacc3g">3G RAN PS Accessibility</a></td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td>5</td>
                                                                                        <td><a href="#" class="frame-link" data-type="kpi_group" data-key="cmbd3g">3G Call Minutes between Drop</a></td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td>6</td>
                                                                                        <td><a href="#" class="frame-link" data-type="kpi_group" data-key="mbps3g">3G Minutes between PS Abnormal Releases</a></td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td>7</td>
                                                                                        <td><a href="#" class="frame-link" data-type="kpi_group" data-key="esr4g">4G Establishment Success Rate</a></td>
                                                                                    </tr> 
                                                                                    <tr>
                                                                                        <td>8</td>
                                                                                        <td><a href="#" class="frame-link" data-type="kpi_group" data-key="4gmbear">4G Minutes between E-RAB abnormal releases</a></td>
                                                                                    </tr>                                                          
                                                                                </tbody>
                                                                            </table>
                                                                        </div>
                                                                    </div>
                                                                </div>                                                                
                                                            </div>    
                                                            <div class="col-md-2 col-sm-2">
                                                                <div class="panel panel-primary mpanel-child low-height">
                                                                    <div class="panel-heading text-center">
                                                                        <h3 class="panel-title pull-left" style="color:#fff;text-align: center;">TOP5</h3>
                                                                        <h3 class="panel-title pull-right" style="color:#fff;text-align: center;">Value</h3>
                                                                        <div class="clearfix"></div>
                                                                   </div>
                                                                    <div class="panel-body">
                                                                        <div class="table-responsive scrollable">
                                                                            <table id="kpi-worst-cells" class="table table-hover regular">
                                                                                <tbody>                                                                               
                                                                                </tbody>
                                                                            </table>
                                                                            <div class='frameloading'>Please wait...</div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>						   		
                                                                <div class="col-md-7 col-sm-7">
                                                                    <div class="panel panel-primary mpanel-child low-height">
                                                                        <div class="panel-heading">
                                                                            <h3 class="panel-title pull-left" style="color:#fff;text-align: center;" id="kpicellname">AALBORGBY</h3>
                                                                            <div class="panel-title pull-right">
                                                                                <ul class="legend">
                                                                                    <li><div class="line line-1"></div></li>
                                                                                    <li id="kpi_group_legend">TEST</li>
                                                                                    <li><div class="line baseline2"></div></li>
                                                                                    <li>TSL</li>
                                                                                    <li><div class="line baseline"></div></li>
                                                                                    <li>MSL</li>
                                                                                </ul>
                                                                            </div>
                                                                            <div class="clearfix"></div>
                                                                        </div>
                                                                        <div class="panel-body">
                                                                            <div id="container" style="min-width: 700px; min-height: 180px; max-height: 300px;margin: 0 auto"></div>
                                                                            <div class='frameloading'>Please wait...</div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                    </div>
                                                </div>
                                            </div>
					</div>
                                    </div>
                                    <div id="kqi-detail">
					<div class="row-fluid">
						<div class="col-lg-12">
						<div class="panel panel-primary mpanel">
						   <div class="panel-heading">
                                                        <h3 class="panel-title pull-left" style="color:#fff;text-align: left;">KQI</h3>
                                                        <button class="btn btn-xs btn-primary pull-right back"><i class="fa fa-chevron-left"></i></button>
	    						<div class="clearfix"></div>
						   </div>
						   <div class="panel-body">
                                                        <div class="row">
                                                            <div class="col-md-3 col-sm-3">
                                                                <div class="panel panel-primary mpanel-child low-height">
                                                                    <div class="panel-body scrollable">
                                                                        <div class="table-responsive scrollable">
                                                <div class="table-responsive scrollable">
                                                        <table class="table regular">
                                                            <tbody>
                                                                <tr>
                                                                    <td style="width:70%"><a href="#" class="frame-link" data-type="kqi_group" data-key="pbsr">1. Page Browsing Success Rate</a></td>
                                                                    <td id="pbsr1">80%</td>
                                                                </tr>
                                                                <tr>
                                                                    <td style="width:70%"><a href="#" class="frame-link" data-type="kqi_group" data-key="pbd">2. Page Browsing Delay</a></td>
                                                                    <td id="pbd1">80%</td>
                                                                </tr>
                                                                <tr>
                                                                    <td style="width:70%"><a href="#" class="frame-link" data-type="kqi_group" data-key="vsssr">3. Video Streaming Start Success Rate</a></td>
                                                                    <td id="vssr1">80%</td>
                                                                </tr>
                                                                <tr>
                                                                    <td style="width:70%"><a href="#" class="frame-link" data-type="kqi_group" data-key="vssd">4. Video Streaming Start Delay</a></td>
                                                                    <td id="vssd1">80%</td>
                                                                </tr>                                                    
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                                        </div>
                                                                    </div>
                                                                </div>                                                                
                                                            </div>    
                                                            <div class="col-md-2 col-sm-2">
                                                                <div class="panel panel-primary mpanel-child low-height">
                                                                    <div class="panel-body">                                                                            
                                                                        <div id="ne-selection-on0" class="table-responsive">
                                                                            <table class="table kqi-ne">
                                                                                <tr>
                                                                                    <td><a href="#" class="ne-link ne-active" data-ne="AccessType_2G">2G</a></td>
                                                                                    <td><a href="#" class="ne-link" data-ne="AccessType_3G">3G</a></td>
                                                                                    <td><a href="#" class="ne-link" data-ne="AccessType_4G">4G</a></td>
                                                                                </tr>
                                                                            </table>
                                                                        </div>
                                                                        <div class="table-responsive">
                                                                            <table id="worst_cell_kqi" class="table table-hover regular">
                                                                                <thead>
                                                                                    <tr>
                                                                                        <th>TOP5</th>
                                                                                        <th>Value</th>
                                                                                    </tr>
                                                                                </thead>
                                                                                <tbody>                                                                             
                                                                                </tbody>
                                                                            </table>
                                                                            <div class='frameloading'>Please wait...</div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>						   		
                                                                <div class="col-md-7 col-sm-7">
                                                                    <div class="panel panel-primary mpanel-child low-height">
                                                                        <div class="panel-heading">
                                                                            <h3 class="panel-title pull-left" style="color:#fff;text-align: center;" id="kqi_group_tendency_chart"></h3>
                                                                            <div class="panel-title pull-right">
                                                                                <ul class="legend">
                                                                                    <li><div class="line line-1"></div></li>
                                                                                    <li id="kqi_group_legend">TEST</li>
                                                                                    <li><div class="line baseline"></div></li>
                                                                                    <li id="kqi_group_legend_baseline">Baseline</li>
                                                                                </ul>
                                                                            </div>
                                                                            <div class="clearfix"></div>
                                                                        </div>
                                                                        <div class="panel-body">
                                                                            <div id="kqichart1" style="min-width: 700px; min-height: 180px; max-height: 300px;margin: 0 auto"></div>
                                                                            <div class='frameloading'>Please wait...</div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                    </div>
                                                </div>
                                            </div>
					</div>
                                    </div>
                            <div id="logical" >
                                        <div class="row-fluid">
                                            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                                <ul class="nav nav-tabs" role="tablist">
                                                    <li role="presentation" class="active"><a href="#alarm" aria-controls="alarm" role="tab" data-toggle="tab">ALARM</a></li>
                                                    <li role="presentation"><a href="#kpix" aria-controls="kpi" role="tab" data-toggle="tab">KPI</a></li>
                                                    <li role="presentation"><a href="#kqix" aria-controls="kqi" role="tab" data-toggle="tab">KQI</a></li>
                                                  </ul>
                                                  <!-- Tab panes -->
                                                  <div class="tab-content mpanel" style="padding-top: 20px; margin-bottom: 6px">
                                                      <div role="tabpanel" class="tab-pane active" id="alarm">
                                                          <div class="col-lg-12">
						   	<div class="row">
                                                            <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">
                                                                <div class="panel panel-primary mpanel-child alarm-height">
                                                                    <div class="panel-body">
                                                                        <div id="doughnutChartSector" class="chart"></div>
                                                                        <ul class="d-legend">
                                                                            <li><span class="white-circle circle-critical"></span>Critical</li>
                                                                            <li><span class="white-circle circle-major"></span>Major</li>
                                                                            <li><span class="white-circle circle-minor"></span>Minor</li>
                                                                        </ul>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
                                                                    <div class="panel panel-primary mpanel-child alarm-height">
                                                                        <div class="panel-heading">
                                                                            <h3 class="panel-title">Alarm List</h3>
                                                                        </div>
                                                                        <div class="panel-body scrollable">
                                                                        <div class="table-responsive">
                                                                            <table id="sectoralarmlisttable" class="table premium">
                                                                                <thead>
                                                                                    <tr>
                                                                                        <th>Site ID</th>
                                                                                        <th>Site Name</th>
                                                                                        <th>Severity</th>
                                                                                        <th>Node Name</th>
                                                                                        <th>Alarm Name</th>
                                                                                        <th>Last Occurrence</th>
                                                                                        <th>Ticket ID</th>
                                                                                        <th>Summary</th>
                                                                                    </tr>
                                                                                </thead>
                                                                                <tbody>                                                                                        
                                                                                </tbody>
                                                                            </table>
                                                                        </div>
                                                                </div>
                                                            </div>
						   	</div>
						   </div>
                                                            </div>
                                                      </div>
                                                      <div role="tabpanel" class="tab-pane" id="kpix">
                                                            <div class="tdccarousel">
                                                              <div class="carousel-content">
                                                                  <div>
                                                                      <a href="#" class="right-scroll"><img src="assets/img/right-arrow.png" /></a>
                                                                  </div>
                                                                <div class="carousel-wrapper">
                                                                    <ul class="carousel-list"> </ul>
                                                                </div>
                                                                <a href="#" class="left-scroll"><img src="assets/img/left-arrow.png" /></a>
                                                              </div>
                                                          </div>
                                                          <div class="kpi-tendencychart-container">
                                                              <div class="row-fluid">
                                                                  <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                                                    <div class="panel panel-primary mpanel-child">
                                                                        <div class="panel-heading">
                                                                            <h3 class="panel-title pull-left real-title" style="color:#fff;text-align: center;"></h3>
                                                                            <div class="panel-title pull-right">
                                                                                <ul class="legend">
                                                                                    <li><div class="line baseline2"></div></li>
                                                                                    <li>TSL</li>
                                                                                    <li><div class="line baseline"></div></li>
                                                                                    <li>MSL</li>
                                                                                    <li><a href="#" style="margin-left: 30px;" class="btn btn-primary btn-xs back-to-gauge">Back</a></li>
                                                                                </ul>
                                                                            </div>
                                                                            <div class="clearfix"></div>
                                                                        </div>
                                                                        <div class="panel-body">
                                                                            <div id="logical-kpi-tendencychart" style="min-width: 700px; min-height: 180px; max-height: 300px;margin: 0 auto"></div>
                                                                            <div class='frameloading'>Please wait...</div>
                                                                        </div>
                                                                    </div>                                     
                                                                  </div>
                                                              </div>
                                                          </div>
                                                      </div>
                                                    <div role="tabpanel" class="tab-pane" id="kqix">
                                                        <div class="row-fluid">
                                                            <div class="col-lg-6">
                                                                    <div class="panel panel-primary mpanel-mini">
                                                                        <div class="panel-heading">
                                                                            <h3 class="panel-title pull-left" style="color:#fff;text-align: center;">Page Browsing Success Rate</h3>
                                                                            <div class="panel-title pull-right">
                                                                                <ul class="legend">
                                                                                    <li><div class="line baseline"></div></li>
                                                                                    <li>Baseline</li>
                                                                                </ul>
                                                                            </div>
                                                                            <div class="clearfix"></div>
                                                                        </div>
                                                                        <div class="panel-body">
                                                                            <div id="kqi1" style="min-width: 558px; min-height: 130px; max-height: 130px;margin: 0 auto"></div>
                                                                            <div class='frameloading'>Please wait...</div>
                                                                        </div>
                                                                    </div>
                                                            </div>
                                                            <div class="col-lg-6">
                                                                <div class="panel panel-primary mpanel-mini">
                                                                    <div class="panel-heading">
                                                                        <h3 class="panel-title pull-left" style="color:#fff;text-align: center;">Page Browsing Delay</h3>
                                                                        <div class="panel-title pull-right">
                                                                            <ul class="legend">
                                                                                <li><div class="line baseline"></div></li>
                                                                                <li>Baseline</li>
                                                                            </ul>
                                                                        </div>
                                                                        <div class="clearfix"></div>
                                                                    </div>
                                                                    <div class="panel-body">
                                                                        <div id="kqi2" style="min-width: 558px; min-height: 130px; max-height: 130px;margin: 0 auto"></div>
                                                                        <div class='frameloading'>Please wait...</div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row-fluid">
                                                            <div class="col-lg-6">
                                                                <div class="panel panel-primary mpanel-mini">
                                                                    <div class="panel-heading">
                                                                        <h3 class="panel-title pull-left" style="color:#fff;text-align: center;">Video Streaming Start Success Rate</h3>
                                                                        <div class="panel-title pull-right">
                                                                            <ul class="legend">
                                                                                <li><div class="line baseline"></div></li>
                                                                                <li>Baseline</li>
                                                                            </ul>
                                                                        </div>
                                                                        <div class="clearfix"></div>
                                                                    </div>
                                                                    <div class="panel-body">
                                                                        <div id="kqi3" style="min-width: 558px; min-height: 130px; max-height: 130px;margin: 0 auto"></div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-6">
                                                                <div class="panel panel-primary mpanel-mini">
                                                                    <div class="panel-heading">
                                                                        <h3 class="panel-title pull-left" style="color:#fff;text-align: center;">Video Streaming Start Delay</h3>
                                                                        <div class="panel-title pull-right">
                                                                            <ul class="legend">
                                                                                <li><div class="line baseline"></div></li>
                                                                                <li>Baseline</li>
                                                                            </ul>
                                                                        </div>
                                                                        <div class="clearfix"></div>
                                                                    </div>
                                                                    <div class="panel-body">
                                                                        <div id="kqi4" style="min-width: 558px; min-height: 130px; max-height: 130px;margin: 0 auto"></div>
                                                                        <div class='frameloading'>Please wait...</div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                  </div>
                                            </div>
                                        </div>
                        </section>
    </div>
    <footer id="footer">
        <div class="container">
            <p style="color:#fff;" align="center"><img src="assets/img/logo-white-sm.png"> Copyright &copy; Huawei Technologies Co, Ltd 2014-2020. All rights reserved</p>
        </div>
    </footer>
<!-- scripts -->
<script type="text/javascript" src="assets/vendor/jquery/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?v=3&sensor=true"></script>
<script type="text/javascript" src="assets/vendor/gmaps/gmaps.js"></script>
<script type="text/javascript" src="assets/vendor/bootstrap-datatable/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="assets/vendor/bootstrap-datatable/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="assets/js/doughchart.js"></script>
<script type="text/javascript" src="assets/vendor/highchart/js/highcharts.js"></script>
<script type="text/javascript" src="assets/vendor/highchart/js/highcharts-more.js"></script>
<script type="text/javascript" src="assets/vendor/perfect-scrollbar/js/perfect-scrollbar.jquery.js"></script>
<script type="text/javascript" src="assets/js/sockjs-0.3.4.min.js"></script>
<script type="text/javascript" src="assets/js/stomp.min.js"/></script>
<script type="text/javascript" src="assets/js/kpicarousel.js"/></script>
<script type="text/javascript" src="assets/js/app.js"></script>
<script type="text/javascript" src="assets/js/news.js"></script>
<script type="text/javascript" src="assets/js/gis.site.js"></script>
</body>

</html>
