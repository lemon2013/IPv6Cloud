<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% 
	double d = Math.random();  
	String flag = Double.toString(d);  
	session.setAttribute("flag",flag);
    String path = request.getContextPath();  
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path; 
    String path1 = request.getContextPath();  
    String basePath1 = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path1;  
%>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <!-- Bootstrap 3.3.7 -->
  <link rel="stylesheet" href="<%=basePath%>/resources/bower_components/bootstrap/dist/css/bootstrap.min.css">
  <title>IPv6云</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="<%=basePath%>/resources/bower_components/font-awesome/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="<%=basePath%>/resources/bower_components/Ionicons/css/ionicons.min.css">
  <link href="<%=basePath%>/resources/bower_components/bootstrap-table/dist/bootstrap-table.min.css" rel="stylesheet">
  <!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
  <link rel="stylesheet" href="<%=basePath%>/resources/css/AdminLTE.min.css">
  <link rel="stylesheet" href="<%=basePath%>/resources/css/custom.css">
  <!-- Google Font -->
  <link rel="stylesheet"
        href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
</head>
<body class="hold-transition login-page">
	<div class="login-box">
  		<div class="login-logo">
    			<a href="#">基于Ceph的IPv6云安全查询系统</a>
  		</div>
 		<!-- /.login-logo -->
  		<div class="login-box-body loginPanel radius">
    			<form id="user-login" class="form-horizontal" action="${pageContext.request.contextPath}/login" method="post">
      			<div class="form-group gap_between">
      				<label for="username" class="control-label"></label>
      				<div class="col-sm-12">
      	  				<input type="text" id="username" class="form-control" placeholder="用户名" name="username"  value="">
      				</div>
      			</div>
      			<div class="form-group gap_between">
      				<label class="control-label" for="email"></label>
      				<div class="col-sm-12">
      	  				<input id="password" type="password" class="form-control" placeholder="密码" name="password" value="">
      				</div>  
      			</div>
      			<div class="form-group gap_between">
    	    				<label class="control-label" for="remember"></label>
    	    				<div	 class="col-sm-12">
    	    					<input id="remember" type="checkbox" name="remember">记住我
    	    					<label class="text-red pull-right">${msg}</label>
    	    				</div>
      			</div>
      			<div class="form-group gap_between">
        				<div class="col-sm-12">
          				<button id="login" class="btn btn-primary btn-block btn-flat">登录</button>
        				</div>
      			</div>
    			</form>
  		</div>
  		<!-- /.login-box-body -->
  	</div>
	<!-- /.login-box -->
</body>
<!-- jQuery 3 -->
<script src="<%=basePath1%>/resources/bower_components/jquery/dist/jquery.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<script src="<%=basePath1%>/resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
<script src="<%=basePath1%>/resources/js/jquery.cookie.js"></script>
<script src="<%=basePath1%>/resources/js/jsencrypt.min.js"></script>
<script src="<%=basePath1%>/resources/js/publicKey.js"></script>
<script src="<%=basePath1%>/resources/js/privateKey.js"></script>
<script src="<%=basePath1%>/resources/js/StringToBytes.js"></script>
<script src="<%=basePath1%>/resources/js/jquery.md5.js"></script>
<script>
</script>
</html>
