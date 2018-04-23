<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<% 
    String path = request.getContextPath();  
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path; 
    String path1 = request.getContextPath();  
    String basePath1 = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path1;  
%>
<style type="text/css">
</style>
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
<body style="background-color:#ECF0F5">
  <!-- Content Wrapper. Contains page content -->
  <div>
    <section class="content">
      <div class="error-page">
        <h2 class="headline text-yellow"> 404</h2>

        <div class="error-content">
          <h3><i class="fa fa-warning text-yellow"></i>糟糕！找不到网页</h3>

          <p>
            抱歉，我们无法找到您正在访问的页面。
            你可以尝试 <a href="${pageContext.request.contextPath}/${role}/">返回主页</a>
          </p>
        </div>
        <!-- /.error-content -->
      </div>
      <!-- /.error-page -->
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
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
  $("#login").on("click",function(e){
    window.history.back();
  }
</script>
</html>
