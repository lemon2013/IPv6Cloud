<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp"%>
<%@ include file="../common/menu.jsp"%>
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			用户管理 <small>Version 2.0</small>
		</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-dashboard"></i> 配置管理</a></li>
			<li class="active">用户管理</li>
		</ol>
	</section>
	<section class="content">
		<div class="box box-widget">
			<!-- /.box-header -->
			<div class="box-header with-border">
				<h3 class="box-title">用户信息</h3>
			</div>
			<div class="box-body">
				<div id="toolbar" class="toolbar">
            <a href="javascript:;" id="btn-add-user" class="btn btn-success btn-add"><i class="fa fa-plus"></i> 新增</a>
            <a href="javascript:;" id="btn-edit-user" class="btn btn-warning btn-edit"><i class="fa fa-pencil"></i> 编辑</a>
            <a href="javascript:;" id="btn-del-user" class="btn btn-danger btn-del"><i class="fa fa-trash"></i> 删除</a>
            
          </div>
				<table id="table"
					class="table table-striped table-bordered table-hover"></table>
			</div>
		</div>
	</section>
	
	<!--upload modal-->
	<div id="addUserModal" class="modal fade" tabindex="-1" role="dialog" aira-labelledby="gridSystemModalLabel">
	 <div class="modal-dialog" role="document">
	   <div class="modal-content">
	     <div class="modal-header">
	       <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	       <h4 class="modal-title" id="gridSystemModalLabel">用户信息</h4>
	     </div>
	     <div class="modal-body">
	         <form id="add-user-form" class="form-horizontal" action="" encrypt="multipart/form-data" method="">
         		<div class="row">
	         		<div class="form-group">
		       			<label for="add-userId" class="col-sm-4 control-label">用户名ID：</label>
		       			<div class="col-sm-6">
		       				<input type="text" id="add-userId" class="form-control" name="userId" value="" />
		       			</div>
		       		</div>
         		</div>
	       		<div class="row">
					<div class="form-group">
		       			<label for="add-displayName" class="col-sm-4 control-label">用户名：</label>
		       			<div class="col-sm-6">
		       				<input type="text" id="add-displayName" class="form-control" name="displayName" value="" />
		       			</div>
		       		</div>
	       		</div>
	       		<div class="row">
		       		<div class="form-group">
		       			<label for="add-email" class="col-sm-4 control-label">邮箱：</label>
		       			<div class="col-sm-6">
		       				<input type="text" id="add-email" class="form-control" name="email" value="" />
		       			</div>
		       		</div>
	       		</div>
	       		<div class="row">
		       		<div class="form-group">
			       		<label class="col-sm-4 control-label" style="font-weight: bold;">是否停用：</label>
			       		<div class="col-sm-6">
				       		<label class="radio-inline ">
								<input type="radio" name="suspended" id="add-suspended-true" value="true" />是
							</label>
							<label class="radio-inline">
								<input type="radio" name="suspended" id="add-suspended-false" value="false" checked/>否
							</label>
			       		</div>
		       		</div>
	       		</div>
	       		<div class="row">
		       		<div class="form-group">
			       		<label class="col-sm-4 control-label" style="font-weight: bold">是否配额：</label>
			       		<div class="col-sm-6">
				       		<label class="radio-inline ">
								<input type="radio" name="enabled" id="add-enable-true" value="true" />是
							</label>
							<label class="radio-inline">
								<input type="radio" name="enabled" id="add-enable-false" value="false" checked />否
							</label>
			       		</div>
		       		</div>
	       		</div>
	       		<div class="row hidden">
		       		<div class="form-group">
		       			<label for="add-maxBuckets" class="col-sm-4 control-label">配额：</label>
		       			<div class="col-sm-6">
		       				<input type="text" id="add-maxBuckets" class="form-control" name="max-buckets" value="" />
		       			</div>
		       		</div>
	       		</div>
	         </form>
	     </div>
	     <div class="modal-footer">
	       <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	       <button id="add" type="button" class="btn btn-primary">新增</button>
	     </div>
	   </div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	<!-- /.content -->
	<!--upload modal-->
	<div id="editUserModal" class="modal fade" tabindex="-1" role="dialog" aira-labelledby="gridSystemModalLabel">
	 <div class="modal-dialog" role="document">
	   <div class="modal-content">
	     <div class="modal-header">
	       <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	       <h4 class="modal-title" id="gridSystemModalLabel">用户信息</h4>
	     </div>
	     <div class="modal-body">
	         <form id="edit-user-form" class="form-horizontal" action="" encrypt="multipart/form-data" method="">
         		<div class="row">
	         		<div class="form-group">
		       			<label for="edit-userId" class="col-sm-4 control-label">用户名ID：</label>
		       			<div class="col-sm-6">
		       				<input type="text" id="edit-userId" class="form-control" name="userId" value="" />
		       			</div>
		       		</div>
         		</div>
	       		<div class="row">
					<div class="form-group">
		       			<label for="edit-displayName" class="col-sm-4 control-label">用户名：</label>
		       			<div class="col-sm-6">
		       				<input type="text" id="edit-displayName" class="form-control" name="displayName" value="" />
		       			</div>
		       		</div>
	       		</div>
	       		<div class="row">
		       		<div class="form-group">
		       			<label for="edit-email" class="col-sm-4 control-label">邮箱：</label>
		       			<div class="col-sm-6">
		       				<input type="text" id="edit-email" class="form-control" name="email" value="" />
		       			</div>
		       		</div>
	       		</div>
	       		<div class="row">
		       		<div class="form-group">
			       		<label class="col-sm-4 control-label" style="font-weight: bold">是否停用：</label>
			       		<div class="col-sm-6">
				       		<label class="radio-inline ">
								<input type="radio" name="suspended" id="edit-suspended-true" value="true" />是
							</label>
							<label class="radio-inline">
								<input type="radio" name="suspended" id="edit-suspended-false" value="false" checked/>否
							</label>
			       		</div>
		       		</div>
	       		</div>
	       		<div class="row">
		       		<div class="form-group">
			       		<label class="col-sm-4 control-label" style="font-weight: bold">是否配额：</label>
			       		<div class="col-sm-6">
				       		<label class="radio-inline ">
								<input type="radio" name="enabled" id="edit-enable-true" value="true" />是
							</label>
							<label class="radio-inline">
								<input type="radio" name="enabled" id="edit-enable-false" value="false" checked />否
							</label>
			       		</div>
		       		</div>
	       		</div>
	       		<div class="row hidden">
		       		<div class="form-group">
		       			<label for="edit-maxBuckets" class="col-sm-4 control-label">配额：</label>
		       			<div class="col-sm-6">
		       				<input type="text" id="edit-maxBuckets" class="form-control" name="max-buckets" value="" />
		       			</div>
		       		</div>
	       		</div>
	         </form>
	     </div>
	     <div class="modal-footer">
	       <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	       <button id="edit" type="button" class="btn btn-primary">修改</button>
	     </div>
	   </div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	<!-- /.content -->
</div>
<%@ include file="../common/bottom.jsp"%>
<script>
	$(function() {
		$('#table').bootstrapTable({
			method : 'get',
			url : "userlist",
			cache : false,
			striped : true,
			pagination : true,
			toolbar : "#toolbar",// 指定工具栏
			search : true,//是否显示表格搜索，此搜索是客户端搜索，不会进服务端
			pageList : [ 10, 50, 100 ],
			pageSize : 10,
			sortable : true,
			pageNumber : 1,
			showExport : true, //是否显示导出
			exportDataType : "basic", //basic', 'all', 'selected'.
			uniqueId : "name", // 每一行的唯一标识  
			sidePagination : 'client',
			contentType : "application/x-www-form-urlencoded",
			showColumns : true,
			showRefresh : true,
			smartDisplay : true,
			showToggle : true,
			pk:'name',
			locale: 'zh-CN',
			paginationPreText : '上一页',
			paginationNextText : '下一页',
			columns: [
                [   
                    {checkbox: true},
                    {field: 'userId', title: '用户ID'},
                    {field: 'displayName', title: '用户名'},
                    {field: 'email', title: '邮箱'},
                    {field: 'maxBuckets', title: '最大桶数量'},
                    {field: 'createdbuckets', title: '已建桶数量'},
                    {field: 'size', title: '已用量',formatter:$.table.api.formatter.filesize},
                    {field: 'objectssize', title: '文件数'},
                    {field: 'suspended', title: '是否停用'},
                    {field: 'enabled', title: '是否启用配额',formatter:$.table.api.formatter.status1},
                    {field: 'maxObjects', title: '最大文件数',formatter:$.table.api.formatter.maxobject},
                    {field: 'maxSizeKb', title: '最大存储容量',formatter:$.table.api.formatter.maxsize},
                    {field: 'operate', title: '操作',events: $.table.api.events.operate,formatter:$.table.api.formatter.operate}
                ],
	         ],
		});
	});
	
	$("#btn-add-user").on("click", function(){
		$("#addUserModal").modal({
			keybroad: true,
		})
	});
	$("#add-enable-true").on("click", function(){
		$('#addUserModal div.hidden').removeClass("hidden").addClass("show");
		
	});
	$("#add-enable-false").on("click", function(){
		$('#addUserModal div.show').removeClass("show").addClass("hidden");
	})
	$("#btn-edit-user").on("click", function(){
		$("#editUserModal").modal({
			keybroad: true,
		});
		var table = $("#table");
		var selectedItem = table.bootstrapTable("getSelections");
		if(selectedItem.length > 1){
			alert("选择单个用户进行信息修改");
			$('#editUserModal').modal('hide');
		}
		$("#edit-userId").val(selectedItem[0].userId);
		$("#edit-displayName").val(selectedItem[0].displayName);
		$("#edit-email").val(selectedItem[0].email);
		if(selectedItem[0].enable === "true"){
			$("#edit-suspended-true").attr("checked", true);
		}else{
			$("#edit-suspended-false").attr("checked", true);
		}
		if(selectedItem[0].enable === "true"){
			$("#edit-enable-true").attr("checked", true);
			$('#editUserModal div.hidden').removeClass("hidden").addClass("show");
			$("#edit-maxBuckets").val(selectedItem[0].maxBuckets);
		}else{
			$("#edit-enable-false").attr("checked", true);
		}
	});
	
	$("#add").on("click", function(){
		console.log($("#add-user-form").serialize());
		$.ajax({
			url: "user/add",
			type: "POST",
			data: $("#add-user-form").serialize(),
			dataType: "json",
			success: function(data){
				if(data.code!=-1){
					$('#addUserModal').modal('hide');
					$("table").bootstrapTable("refresh");
					$('#addUserModal').on('hide.bs.modal',function() {
						$("#add-user-form").get(0).reset();
					});
				}
				console.log("success!");
			},
			error: function(e){
				console.log(e.message);
			}
		});
	});
	$("#edit").on("click", function(){
		console.log($("#edit-user-form").serialize());
		$.ajax({
			url: "user/modify",
			type: "POST",
			data: $("#edit-user-form").serialize(),
			dataType: "json",
			success: function(data){
				if(data.code!=-1){
					$('#editUserModal').modal('hide');
					$("table").bootstrapTable("refresh");
					$('#editUserModal div.show').removeClass("show").addClass("hidden");
					$('#editUserModal').on('hide.bs.modal',function() {
				       $("#edit-user-form").get(0).reset();
				    });
				}
				console.log("success!");
			},
			error: function(e){
				console.log(e.message);
			}
		});
	});
	$("#btn-del-user").on("click", function(){
		var table = $("#table");
		var selectedItem = table.bootstrapTable("getSelections");
		var selectionLen = selectedItem.length;
		var dataArr = [];
		if(selectionLen === 0){
			//modal
			alert("请选择要删除的项！");
		}else{
			for(var i = 0; i < selectionLen; i++){
				dataArr[i] = selectedItem[i].userId;
			}
			$.ajax({
				url: "user/del",
				type: "POST",
				data: {dataArr: dataArr},
				dataType: "json",
				success: function(data){
					if(data.code!=-1){
						$("table").bootstrapTable("refresh");
					}
					console.log("success!");
				},
				error: function(e){
					console.log(e.message);
				}
			});
		}
	})
</script>
