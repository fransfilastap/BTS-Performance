<%-- 
    Document   : login
    Created on : Aug 28, 2015, 10:48:39 AM
    Author     : Frans Filasta Pratama <franspratama@mail01.huawei.com>
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>Login | Hotspot Monitoring</title>
    <meta name="description" content="">
    <meta name="author" content="huawei">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="assets/vendor/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="assets/css/login.css">
    <style type="text/css">
    	#alarm-detail,#kpi-detail,#kqi-detail{
    		display: none;
    	}
    </style>
</head>
<body>

    <div class="container">
        <div id="card-blur"></div>
        <div class="card card-container">
            <img id="profile-img" class="profile-img-card" src="assets/img/logo.png" />
            <p id="profile-name" class="profile-name-card"></p>
            <form class="form-signin" action="<c:url value='/login' />" method="POST">
                <span id="reauth-email" class="reauth-email"></span>
                <input type="text" name="username" id="inputEmail" class="form-control" placeholder="Username" required autofocus>
                <input type="password" name="password" id="inputPassword" class="form-control" placeholder="Password" required>
                <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">Sign in</button>
            </form><!-- /form -->
        </div><!-- /card-container -->
    </div><!-- /container -->
<!-- scripts -->
<script type="text/javascript" src="assets/vendor/jquery/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="assets/js/login.js"></script>
<script type="text/javascript">
    $(window).resize(function(){
        var newheight = $(window).height()*0.217;
        $(".card").css({"margin-top":newheight});        
    });
    
</script>
</body>

</html>
