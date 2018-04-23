<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%  
    String path2 = request.getContextPath();  
    String basePath2 = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path2;  
%>
<!-- Left side column. contains the logo and sidebar -->
  <aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar" style="height: 588px; overflow: hidden; width: auto;">
      <!-- Sidebar user panel -->
      <div class="user-panel">
        <div class="pull-left image">
          <img src="<%=basePath2%>/resources/dist/img/lemon.jpg" class="img-circle" alt="User Image">
        </div>
        <div class="pull-left info">
          <p>${username}</p>
          <a href="#"><i class="fa fa-circle text-success"></i> 在线</a>
        </div>
      </div>
      <!-- search form -->
      <form action="#" method="get" class="sidebar-form">
        <div class="input-group">
          <input type="text" name="q" class="form-control" placeholder="Search...">
          <span class="input-group-btn">
                <button type="submit" name="search" id="search-btn" class="btn btn-flat">
                  <i class="fa fa-search"></i>
                </button>
              </span>
        </div>
      </form>
      <!-- /.search form -->
      <!-- sidebar menu: : style can be found in sidebar.less -->
      <ul class="sidebar-menu" data-widget="tree">
        <li class="header"></li>
        <li class="treeview">
          <a href="#">
            <i class="fa fa-book"></i> <span>存储管理</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li><a href="<%=basePath2%>/cloud/storage/index"><i class="fa fa-circle-o"></i> 对象存储</a></li>
            <li><a href="<%=basePath2%>/cloud/storage/object"><i class="fa fa-circle-o"></i> 内容管理</a></li>
          </ul>
        </li>
        <li class="treeview">
          <a href="#">
            <i class="fa fa-share-alt-square"></i> <span>共享文件</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li><a href="<%=basePath2%>/cloud/share/index"><i class="fa fa-circle-o"></i> 安全检索</a></li>
            <li><a href="<%=basePath2%>/cloud/share/sstorage"><i class="fa fa-circle-o"></i>安全存储</a></li>
          </ul>
        </li>
        </ul>
    </section>
    <!-- /.sidebar -->
  </aside>