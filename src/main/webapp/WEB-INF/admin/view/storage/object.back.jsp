<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp"%>
<%@ include file="../common/menu.jsp"%>
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			内容管理 <small>Version 2.0</small>
		</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-dashboard"></i> 存储管理</a></li>
			<li class="active">内容管理</li>
		</ol>
	</section>
	<section class="content">
		<div class="box box-widget">
			<!-- /.box-header -->
			<div class="box-header with-border">
				<h3 class="box-title">文件信息</h3>
			</div>
			<div class="box-body">
				<div id="toolbar" class="toolbar">
            			<a href="javascript:;" id="btn-upload" class="btn btn-primary"><i class="fa fa-upload"></i> 上传</a>
					<a href="javascript:;" id="btn-download" class="btn btn-info"><i class="fa fa-download"></i> 下载</a>
					<a href="javascript:;" id="btn-del" class="btn btn-danger"><i class="fa fa-trash"></i> 删除</a>
          		</div>
				<table id="table" class="table table-striped table-bordered table-hover"></table>
			</div>
		</div>
	</section>
	
	<!--upload modal-->
	<div id="uploadModal" class="modal fade" tabindex="-1" role="dialog" aira-labelledby="gridSystemModalLabel">
	 <div class="modal-dialog" role="document">
	   <div class="modal-content">
	     <div class="modal-header">
	       <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	       <h4 class="modal-title" id="gridSystemModalLabel">上传文件</h4>
	     </div>
	     <div class="modal-body">
	         <form id="upload-form" class="form-horizontal" action="" encrypt="multipart/form-data" method="post">
	         	<div	 class="row">
					<div class="form-group">
						<label for="file-upload" class="col-sm-2 control-label"></label>
						<div class="col-sm-8">
							<input type="file" id="file-upload"></input>
						</div>
					</div>
	         	</div>
	         	<div class="row">
		       		<div class="form-group">
		       			<label for="file-name" class="col-sm-2 col-sm-offerset-2 control-label">文件名：</label>
		       			<div class="col-sm-8">
		       				<input type="text" id="file-name" class="form-control" name="fileName" value=""></input>
		       			</div>
		       		</div>
		       		<div class="form-group">
		       			<label for="file-type" class="col-sm-2 control-label">文件类型：</label>
		       			<div class="col-sm-8">
		       				<input type="text" id="file-type" class="form-control" name="fileType" value=""></input>
		       			</div>
		       		</div>
		       		<div class="form-group">
		       			<label for="file-size" class="col-sm-2 control-label">文件大小：</label>
		       			<div class="col-sm-8">
		       				<input type="text" id="file-size" class="form-control" name="fileSize" value=""></input>
		       			</div>
					</div>
		       	</div>
	         </form>
	         <!-- 进度条 -->
        		<div id="progress_bar">
       			<div class="percent"></div>
       		</div>
       		<!--  -->
	     </div>
	     <div class="modal-footer">
	       <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	       <button id="upload" type="button" class="btn btn-primary">上传</button>
	     </div>
	   </div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->	
	
	<!--download modal-->
	<div id="downloadModal" class="modal fade" tabindex="-1" role="dialog">
	 <div class="modal-dialog" role="document">
	   <div class="modal-content">
	     <div class="modal-header">
	       <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	       <h4 class="modal-title">文件下载</h4>
	     </div>
	     <div class="modal-body">
	       <p>One fine body&hellip;</p>
	     </div>
	     <div class="modal-footer">
	       <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	       <button type="button" class="btn btn-primary">保存</button>
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
			url : "objectlist",
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
                    {field: 'bucketName', title: '桶名'},
                    {field: 'key', title: '文件名',formatter:$.table.api.formatter.filename},
                    {field: 'size', title: '文件大小',formatter:$.table.api.formatter.filesize},
                    {field: 'lastModified', title: '更新时间',formatter:$.table.api.formatter.date},
                    {field: 'storageClass', title: '存储类型'},
                    {field: 'owner.displayName', title: '拥有者'},
                    {field: 'owner.id', title: '拥有者ID'},
                    {field: 'operate', title: '操作',events: $.table.api.events.operate,formatter:$.table.api.formatter.operate}
                ],
	         ],
		});
	});
	
	/* 文件上传代码 */
	$("#btn-upload").on("click", function() {
	    $("#uploadModal").modal({
	        keyboard: true
	    }); 
	});
	
	$("#file-upload").change(function(){
		var file = $("#file-upload").get(0).files[0];
		$("#file-name").val(file.name);
		$("#file-size").val(function(){
			if(file){
				if(file.size > 1024 * 1024){
					return (Math.round(file.size * 100 / (1024 * 1024)) / 100).toString() + "MB";
				}else{
					return (Math.round(file.size * 100 / 1024) / 100).toString() + "KB";
				}
			}
		});
		$("#file-type").val(file.type);
	});
	$("#upload").on("click", function(){
		var reader = new FileReader();
		var progress = $(".percent");
		var file = $("#file-upload").get(0).files[0];
		
		progress.css("width", "0%");
		progress.text("0%");
		
		reader.onloadstart = function(e){
			$("#progress_bar").attr("class", "loading");
		}
		reader.onprogress = function(e){
			if(e.lengthComputable){
	            var percentLoaded = Math.round((e.loaded / e.total) * 100);
	            if(percentLoaded < 100){
	                progress.css("width", percentLoaded + "%");
	                progress.text(percentLoaded + "%");
	            }
	        }
		};
		reader.onload = function(e){
			progress.css("width", "100%");
			progress.text("100%");
			setTimeout("$('progress_bar').attr('class', '');", 2000);
		}
		reader.readAsBinaryString(file);
		alert($("#upload-form").serialize());
		$.ajax({
			url: "index/upload",
			type: "POST",
			data: $("#upload-form").serialize(),
			dataType: "json",
			success: function(data){
				console.log("success!");
			},
			error: function(e){
				console.log(e.message);
			}
		})
	});
	/*  */
	
	/* 文件下载代码*/
 	$("#btn-download").on("click", function() {
		//var selects = $("table").bootstrapTable("getSelections");
		var selects = [{bucketName: '123', fileName: '456'}, {bucketName: 'qwe', fileName: 'rty'}, {bucketName: 'asd', fileName: 'fgh'}];
		if(selects.length == 0){
			alert("请选择文件！");
		}else{
			var dataArr = [];
			for(var i = 0; i < selects.length; i++){
				dataArr.push({
					bucketName: selects[i].bucketName,
					key: selects[i].key
				});
			}
			
			var filePath = "";
			
			$.ajax({
				url: "/storage/object/download",
				data: {data: dataArr, filePath: filePath},
				dataType: "json",
				cache: false,
				success: function(){
					console.log("success!");
				},
				error: function(){
					console.log(e.message);
				}
			})
		}
	});
	/*  */
	
	/*文件删除代码*/
		$("#btn-del").on("click", function(){
			var selects = $("table").bootstrapTable("getSelections");
			if(selects.length == 0){
				alert("请选择要删除的文件！");
			}else{
				var urlData = [];
				$.table.api.deleteItem($("table"), urls, true)
			}
		})
	/*  */
</script>
