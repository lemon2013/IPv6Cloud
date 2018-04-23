<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../commoncloud/header.jsp"%>
<%@ include file="../commoncloud/menu.jsp"%>
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			安全检索 
		</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-share-alt-square"></i> 共享文件</a></li>
			<li class="active">安全检索</li>
		</ol>
	</section>
	<section class="content">
		<div class="col-md-7">
			<form action="" id="searchform">
				<div class="text-center">
					<div class="input-group">
						<input type="text" id="input-keywords" class="form-control"> <span
							class="input-group-btn">
							<button class="btn btn-success" type="button" id="btn-search">搜索</button>
						</span>
					</div>
					<!-- /input-group -->
            	</div>
			</form>
			<div>
				<span id="keywords" style="color: #FF0000"></span>
			</div>
			<div id="resulttable">
				<div id="toolbar" class="toolbar">
					<a href="javascript:;" id="btn-share-download" class="btn btn-info"><i class="fa fa-cloud-download"></i> 下载</a>
		        </div>
				<table id="table"
					class="table table-striped table-bordered table-hover"></table>
			</div>
			
		</div>
		<div class="col-md-5 col-xs-5">
			<div class="row">
				<div style="float:left;">
	             	<select id="slpk" class="selectpicker" data-live-search="true"></select>
	             </div>
				<img alt="" src="<%=basePath%>/resources/img/IPv6171121.png" width="100%" height="100%">
			</div>
			<div class="row">
			</div>
		</div>
	</section>
	<!-- /.content -->
</div>
<%@ include file="../commoncloud/bottom.jsp"%>

<script>
$(document).on('click', "#btn-search", function () {
	var form = $("#searchform");
	var val = $("#input-keywords").val();
	var num = $('#slpk').find("option:selected").val();
	var data = '{'+'"'+"keys"+'"'+":"+val+","+'"'+"num"+'"'+":"+num+'}';
	var map = {"info":data};
	$.ajax({
	    type: 'get',
	    url: 'search',
	    data: map,
	    dataType: 'json',
	    success: function (data) {
	    	var a = document.getElementById("keywords");
			a.innerHTML = data.keywords;
			$('#table').bootstrapTable('load',data.tabledata);
			$('#table').bootstrapTable({
			    data: data.tabledata,
			    pagination : true,
				toolbar : "#toolbar",// 指定工具栏
				search : true,//是否显示表格搜索，此搜索是客户端搜索，不会进服务端
				pageList : [ 10, 50, 100 ],
				pageSize : 8,
				sortable : true,
				pageNumber : 1,
				uniqueId : "id", // 每一行的唯一标识  
				sidePagination : 'client',
				contentType : "application/x-www-form-urlencoded",
				pk:'name',
				locale: 'zh-CN',
				paginationPreText : '上一页',
				paginationNextText : '下一页',
			    columns: 
			    	[
			    		{checkbox: true},
			    		{field: 'FID',title: '文件名'}, 
			    		{field: 'score',title: '相关性'},
			    		{field: 'owner',title: '拥有者'},
			    		{field: 'operate', title: '操作', formatter:$.table.api.formatter.operate}
			    	]
			});
	    }
	 });
});
/* 文件下载代码*/
	$("#btn-share-download").on("click", function() {
	var select = $("table").bootstrapTable("getSelections");
	if(select.length == 0){
		alert("请选择文件！");
	}else{
		var obj = {};
		var objStr = "";
		console.log(select);
		for(var i = 0; i < select.length; i++){
			objStr = "bucketname=share" + "&" + "objname=" + select[0].FID;
		}
		console.log(objStr);
		$.ajax({
			url: "file/downloadurl",
			type: "POST",
			data: objStr,
			dataType: "json",
			success: function(data){
				if(data.code != -1){
					$('<a id="fileDownload" href="' + data.url + '" download="' + data.filename + '">123</a>')[0].click();
				}
			},
			error: function(){
				console.log(e.message);
			}
		})
	}
});
$(document).ready(function()
{
	var select = $("#slpk"); 
	select.append("<option value='"+10+"'>" + "10个文件" + "</option>");
	select.append("<option value='"+20+"'>" + "20个文件" + "</option>");
    select.append("<option value='"+50+"'>" + "50个文件" + "</option>");
    select.append("<option value='"+100+"'>" + "100个文件" + "</option>");
    select.append("<option value='"+200+"'>" + "200个文件" + "</option>");
    select.append("<option value='"+500+"'>" + "500个文件" + "</option>");
    $('.selectpicker').selectpicker('val', '');  
    $('.selectpicker').selectpicker('refresh');
});
/*  */
</script>
