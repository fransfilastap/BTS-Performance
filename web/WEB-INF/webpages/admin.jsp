<%-- 
    Document   : admin
    Created on : Aug 24, 2015, 6:08:14 PM
    Author     : Administrator
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context_path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Admin | Hotspot Monitoring</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="${context_path}/assets/vendor/admin/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${context_path}/assets/css/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="${context_path}/assets/css/ionicons/css/ionicons.min.css">
    <link rel="stylesheet" href="${context_path}/assets/vendor/admin/css/AdminLTE.min.css">
    <link rel="stylesheet" href="${context_path}/assets/vendor/admin/css/skins/skin-black-light.min.css">
    <link rel="shortcut icon" type="image/x-icon" href="${context_path}/assets/img/favicon.ico"/>
    <style type="text/css">
        .loading-wrapper{
            background-color: rgba(52, 152, 219,1.0);
            width:100%;
            height:100%;
            position:fixed;
            top:0;
            left:0;
            z-index:99999;
            display: none;
        }
        
        .loading{
            position: relative;
            top : 50%;
            left : 50%;
            width : 32px;
            height : 32px;
        }
       
    </style>
    <c:if test="${current.equalsIgnoreCase('sites')}">
        <link rel="stylesheet" href="${context_path}/assets/vendor/admin/admin-plugin/datatables/dataTables.bootstrap.css">
    </c:if>

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="${context_path}/assets/js/html5shiv.min.js"></script>
        <script src="${context_path}/assets/js/respond.min.js"></script>
    <![endif]-->
  </head>
  <body class="hold-transition skin-black-light sidebar-mini">
    <div class="wrapper">
      
      <header class="main-header">
        <a href="index2.html" class="logo">
          <span class="logo-mini"><img src="${context_path}/assets/img/huawei-logo-admin.png"/></span>
          <span class="logo-lg"><img src="${context_path}/assets/img/huawei-logo-admin.png"/></span>
        </a>
        <nav class="navbar navbar-static-top" role="navigation">
          <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <span class="sr-only">Toggle navigation</span>
          </a>
          <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
                 <li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown">${fullname} <span class="caret"></span></a>
                  <ul class="dropdown-menu" role="menu">
                    <li><a href="${context_path}/">Monitoring Page</a></li>
                    <li class="divider"></li>
                    <li><a href="${context_path}/logout">Logout</a></li>
                  </ul>
                </li>
            </ul>
          </div>

        </nav>
      </header>
      <aside class="main-sidebar">
        <section class="sidebar">
          <ul class="sidebar-menu">
            <li class="header">ADMIN NAVIGATION</li>
            <c:set var="current_url" value="${pageContext.request.getAttribute('javax.servlet.forward.request_uri') }"/>
            <li class="treeview <c:if test="${current.equalsIgnoreCase('sites')}">active</c:if>">
              <a href="#"<c:if test="${current.equalsIgnoreCase('sites')}">class='active'</c:if>>
                <i class="fa fa-files-o"></i>
                <span>Data</span>
                <i class="fa fa-angle-left pull-right"></i>
              </a>
              <ul class="treeview-menu">
                <li><a href="${context_path}/admin/data/sites.html"><i class="fa fa-angle-double-right"></i> Sites</a></li>
              </ul>
            </li>
            <li class="treeview <c:if test="${current.equalsIgnoreCase('users')}">active</c:if>">
              <a href="#">
                <i class="fa fa-key"></i>
                <span>Permission</span>
                <i class="fa fa-angle-left pull-right"></i>
              </a>
              <ul class="treeview-menu">
                  <li><a href="${context_path}/admin/data/users.html" class="<c:if test="${current.equalsIgnoreCase('users')}">active</c:if>"><i class="fa fa-angle-double-right"></i> Users</a></li>
              </ul>
            </li>
          </ul>
        </section>
      </aside>

      <div class="content-wrapper">
        <section class="content-header">
          <h1>
            Data Management
            <small>Version 1.0</small>
          </h1>
          <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
            <li class="active">Dashboard</li>
          </ol>
        </section>

        <section class="content">
            <jsp:include page="${file}" flush="true" />
        </section>
      </div>

      <footer class="main-footer">
        <div class="pull-right hidden-xs">
          <b>Version</b> 1.0
        </div>
        <strong>Copyright &copy; 2015 <a href="http://huawei.com">Huawei Technologies Co, Ltd 2014-2020.</a>.</strong> All rights reserved.
      </footer>

    </div>

    <script src="${context_path}/assets/vendor/admin/admin-plugin/jQuery/jQuery-2.1.4.min.js"></script>
    <script src="${context_path}/assets/vendor/jquery-form/jquery.form.min.js"></script>
    <script src="${context_path}/assets/vendor/admin/bootstrap/js/bootstrap.min.js"></script>
    <script src="${context_path}/assets/vendor/admin/admin-plugin/fastclick/fastclick.min.js"></script>
    <script src="${context_path}/assets/vendor/admin/js/app.min.js"></script>
    <script src="${context_path}/assets/vendor/admin/admin-plugin/slimScroll/jquery.slimscroll.min.js"></script>
    <script src="${context_path}/assets/js/stacked-modal.js" type="text/javascript"></script>
    <c:if test="${current.equalsIgnoreCase('sites')}">
    <script src="${context_path}/assets/vendor/admin/admin-plugin/datatables/jquery.dataTables.js"></script>
    <script src="${context_path}/assets/vendor/admin/admin-plugin/datatables/dataTables.bootstrap.min.js"></script>
    <script src="${context_path}/assets/js/site.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            $("body").append( "<div class='loading-wrapper'><div class='loading'><img src='${context_path}/assets/img/ring.svg'></div></div>" );
            initDataTable("${context_path}/admin/data/sites/list","#sites"); 
            init();
        });
    </script>        
    </c:if>
    <c:if test="${current.equalsIgnoreCase('users')}">
    <script src="${context_path}/assets/vendor/admin/admin-plugin/datatables/jquery.dataTables.js"></script>
    <script src="${context_path}/assets/vendor/admin/admin-plugin/datatables/dataTables.bootstrap.min.js"></script>
    <script src="${context_path}/assets/js/user.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            $("body").append( "<div class='loading-wrapper'><div class='loading'><img src='${context_path}/assets/img/ring.svg'></div></div>" );
            initDataTable("${context_path}/admin/data/users/list","#users"); 
            init();
        });
    </script>         
    </c:if>

  </body>
</html>
