<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../commoncloud/header.jsp"%>
<%@ include file="../commoncloud/menu.jsp"%>
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			对象存储 <small>Version 2.0</small>
		</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-dashboard"></i> 存储管理</a></li>
			<li class="active">对象存储</li>
		</ol>
	</section>
	<section class="content">
		<div class="box box-widget">
			<!-- /.box-header -->
			<div class="box-header with-border">
				<h3 class="box-title">桶信息</h3>
			</div>
			<div class="box-body">
				<div id="toolbar" class="toolbar">
					<a href="javascript:;" id="btn-add-bucket" class="btn btn-primary"><i class="fa fa-upload"></i> 新增</a>
					<a href="javascript:;" id="btn-del-bucket" class="btn btn-info"><i class="fa fa-download"></i> 删除</a>
		        </div>
				<table id="table" class="table table-striped table-bordered table-hover"></table>
			</div>
		</div>
	</section>
	
	<!--add bucket modal-->
	<div id="addBucketModal" class="modal fade" tabindex="-1" role="dialog" aira-labelledby="gridSystemModalLabel">
	 <div class="modal-dialog" role="document">
	   <div class="modal-content">
	     <div class="modal-header">
	       <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	       <h4 class="modal-title" id="gridSystemModalLabel">新增桶</h4>
	     </div>
	     <div class="]modal-body">
	         <form id="add-bucket-form" class="form-horizontal" action="" encrypt="multipart/form-data" method="">
	         	<div class="row">
		       		<div class="form-group">
		       			<label for="add-bucket-name" class="col-sm-4 control-label">桶名：</label>
		       			<div class="col-sm-6">
		       				<input type="text" id="add-bucket-name" class="form-control" name="name" value=""></input>
		       			</div>
		       		</div>
		       	</div>
	         </form>
	     </div>
	     <div class="modal-footer">
	       <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	       <button id="addBucket" type="button" class="btn btn-primary">新增</button>
	     </div>
	   </div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	<!-- /.content -->
</div>
<%@ include file="../commoncloud/bottom.jsp"%>
<script>
	$(function() {
		$('#table').bootstrapTable({
			method : 'get',
			url : "bucket",
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
                    {field: 'name', title: '桶名'},
                    {field: 'creationDate', title: '创建时间',formatter:$.table.api.formatter.date},
                    {field: 'owner.displayName', title: '拥有者'},
                    {field: 'owner.id', title: '拥有者ID'},
                    {field: 'operate', title: '操作',events: $.table.api.events.operate,formatter:$.table.api.formatter.operate}
                ],
	         ],
		});
	});
	$("#btn-add-bucket").on("click", function() {
	    $("#addBucketModal").modal({
	        keyboard: true
	    }); 
	});
	/*  */
	$("#addBucket").on("click", function(){
		console.log($("add-bucket-form").serialize());
		$.ajax({
			url: "bucket/add",
			type: "POST",
			data: $("#add-bucket-form").serialize(),
			dataType: "json",
			success: function(data){
				if(data.code!=-1){
					$("#addBucketModal").modal("hide");
					$("#table").bootstrapTable("refresh");
					$('#addBucketModal').on('hide.bs.modal',function() {
					    $("#add-bucket-form").get(0).reset();
					});
				}
				console.log("success!");
			},
			error: function(e){
				console.log(e.message);
			}
		});
	});
	/*  */
	$("#btn-del-bucket").on("click", function(){
		var table = $("#table");
		var selectedItem = table.bootstrapTable("getSelections");
		if(selectedItem.length === 0){
			//modal
			alert("请选择要删除的项！");
		}else{
			var dataArr = [];
			for(var i = 0; i < selectedItem.length; i++){
				dataArr.push(selectedItem[i].name);
			}
			console.log(dataArr);
			$.ajax({
				url: "bucket/del",
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
