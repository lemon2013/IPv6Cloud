<style type="text/css">
.rowstyle {
    margin-bottom: 10px;
}
</style>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../commoncloud/header.jsp"%>
<%@ include file="../commoncloud/menu.jsp"%>
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			安全存储 
		</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-share-alt-square"></i> 共享文件</a></li>
			<li class="active">安全存储</li>
		</ol>
	</section>
	<section class="content">
		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="box box-widget boxheigh">
	                <div class="box-header with-border">
	                	<h3 class="box-title">关键词提取</h3>
	                </div>
	                <div class="box-body">
	                	<div>
	                		<div class="row rowstyle">
	                			<div class="col-md-6 col-sm-6 col-xs-6">
	                				<div style="float:right;">
	                					<select id="slpk" class="selectpicker" data-live-search="true"></select>
	                				</div>
	                			</div>
	                			<div class="col-md-6 col-sm-6 col-xs-6">
	                				<button type="button" id ="btn-extract" class="btn btn-success">提取</button>
	                			</div>
	                     	</div>
	                     	<div class="row">
	                			<div class="col-md-6 col-sm-6 col-xs-6">
	                				<textarea id="fields" class="form-control" rows="5" name="" cols="50" placeholder="输入待提取关键词文本" style="resize: none;"></textarea>
	                				<h6>可以输入<span>5000</span>个字，现在剩余<span id="word" style="color:red">5000</span>个</h6>
	                			</div>
	                			<div class="col-md-6 col-sm-6 col-xs-6">
	                				<div style="background-color:#F5F5F5;min-height:115px">
	                					<label id="keywords"></label>
	                				</div>
	                				<div id="dtime" class="">
	                					<h6 style="float:right;">耗时<span id="time" style="color:red"></span>毫秒</h6>
	                				</div>
	                			</div>
	                     	</div>
	                 	</div>
	                </div>
	             </div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="box box-widget boxheigh">
	                <div class="box-header with-border">
	                	<h3 class="box-title">关键词加密</h3>
	                </div>
	                <div class="box-body">
	                	<div>
	                		<div class="row rowstyle">
	                			<div class="col-md-6 col-sm-6 col-xs-6">
	                				<div class="row">
	              						<div class="col-md-8 col-sm-8 col-xs-8">
					                		 <div class="input-group input-group-sm">
				                				<input type="text" id="keyword1" class="form-control">
							                    <span class="input-group-btn">
							                      <button type="button" id="btn-user1" class="btn btn-info btn-flat">用户1 加密</button>
							                    </span>
				              				</div>
				              			</div>
				              			<div class="col-md-2 col-sm-2 col-xs-2">
				              			</div>
				              			<div class="col-md-2 col-sm-2 col-xs-2">
				              				<div id="equ">
				              					
				              				</div>
				              			</div>
				              		</div>
	              				</div>
	              				<div class="col-md-6 col-sm-6 col-xs-6">
	              					<div class="row">
	              						<div class="col-md-8 col-sm-8 col-xs-8">
					                		 <div class="input-group input-group-sm">
				                				<input type="text" id="keyword2" class="form-control">
							                    <span class="input-group-btn">
							                      <button type="button" id="btn-user2" class="btn btn-danger btn-flat">用户2 加密</button>
							                    </span>
				              				</div>
				              			</div>
				              			<div class="col-md-4 col-sm-4 col-xs-4">
				              				<button type="button" id="btn-equ" class="btn btn-success btn-sm">判断是否相等</button>
				              			</div>
		              				</div>
	              				</div>
              				</div>
              				<div class="row">
	                			<div class="col-md-6 col-sm-6 col-xs-6">
			                		<textarea id="owner1" class="form-control" rows="5" name="" cols="50" placeholder="加密结果" style="resize: none;"></textarea>
	              				</div>
	              				<div class="col-md-6 col-sm-6 col-xs-6">
			                		<textarea id="owner2" class="form-control" rows="5" name="" cols="50" placeholder="加密结果" style="resize: none;"></textarea>
	              				</div>
              				</div>
	                 	</div>
	                </div>
	             </div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="box box-widget boxheigh">
	                <div class="box-header with-border">
	                	<h3 class="box-title">文件加解密</h3>
	                </div>
	                <div class="box-body">
	                	<div>
	                		<div class="row rowstyle">
	                			<div class="col-md-6 col-sm-6 col-xs-6">
	                				<div class="row">
	              						<div class="col-md-8 col-sm-8 col-xs-8">
					                		 <div class="input-group input-group-sm">
				                				<input type="text" id="encs" class="form-control">
							                    <span class="input-group-btn">
							                      <button type="button" id="btn-enc" class="btn btn-info btn-flat">加密</button>
							                    </span>
				              				</div>
				              			</div>
				              		</div>
	              				</div>
	              				<div class="col-md-6 col-sm-6 col-xs-6">
	              					<div class="row">
	              						<div class="col-md-8 col-sm-8 col-xs-8">
					                		 <div class="input-group input-group-sm">
				                				<input type="text" id="decs" class="form-control">
							                    <span class="input-group-btn">
							                      <button type="button" id="btn-dec" class="btn btn-danger btn-flat">解密</button>
							                    </span>
				              				</div>
				              			</div>
		              				</div>
	              				</div>
              				</div>
              				<div class="row rowstyle">
              					<div class="col-md-4 col-sm-4 col-xs-4">
              						<form id="upload-form" class="form-horizontal" action="" encrypt="multipart/form-data" method="post">
		              				<input id="encfile" type="file" name="file" id="exampleInputFile">
		              				</form>
		              			</div>
		              			<div class="col-md-1 col-sm-1 col-xs-1">
		              				<button type="button" id="btn-encfile" class="btn btn-warning btn-sm">文件加密</button>
		              			</div>
		              			<div class="col-md-1 col-sm-1 col-xs-1">
		              				<!-- <a href="download?filename=E1521629067190图论DFS.docx">下载文件</a> -->
		              				<input type="text" id="encfilename" class="hidden form-control">
		              				<button type="button" id="btn-downloade" class="btn btn-warning btn-sm">下载</button>
		              			</div>
			              		<div class="col-md-4 col-sm-4 col-xs-4">
			              				<form id="dec-form" class="form-horizontal" action="" encrypt="multipart/form-data" method="post">
			              					<input id="decfile" type="file" name="file" id="exampleInputFile">
			              				</form>
			              		</div>
		              			<div class="col-md-1 col-sm-1 col-xs-1">
		              				<button type="button" id="btn-decfile" class="btn btn-success btn-sm">文件解密</button>
		              			</div>
		              			<div class="col-md-1 col-sm-1 col-xs-1">
			              				<!-- <a href="download?filename=E1521629067190图论DFS.docx">下载文件</a> -->
		              				<input type="text" id="decfilename" class="hidden form-control">
		              				<button type="button" id="btn-downloadd" class="btn btn-success btn-sm">下载</button>
			              		</div>
              				</div>
              				<div class="row">
	                			<div class="col-md-6 col-sm-6 col-xs-6">
			                		<textarea id="enc-str" class="form-control" rows="5" name="" cols="50" placeholder="加密结果" style="resize: none;"></textarea>
	              				</div>
	              				<div class="col-md-6 col-sm-6 col-xs-6">
			                		<textarea id="dec-str" class="form-control" rows="5" name="" cols="50" placeholder="解密结果" style="resize: none;"></textarea>
	              				</div>
              				</div>
	                 	</div>
	                </div>
	             </div>
			</div>
		</div>
	</section>
	<!-- /.content -->
</div>
<%@ include file="../commoncloud/bottom.jsp"%>

<script>
 $(".selectpicker").selectpicker({  
        noneSelectedText : '请选择关键词个数'  
    });
  $(document).ready(function()
    {
  		var select = $("#slpk"); 
  		select.append("<option value='"+5+"'>"  
                        + "5个关键词" + "</option>");
  		select.append("<option value='"+10+"'>"  
                        + "10个关键词" + "</option>");
        select.append("<option value='"+20+"'>"  
        + "20个关键词" + "</option>");
        select.append("<option value='"+50+"'>"  
        + "50个关键词" + "</option>");
        select.append("<option value='"+100+"'>"  
        + "100个关键词" + "</option>");
        select.append("<option value='"+200+"'>"  
        + "200个关键词" + "</option>");
        $('.selectpicker').selectpicker('val', '');  
        $('.selectpicker').selectpicker('refresh');
        $('#dtime').hide();
        $("#btn-downloade").hide();
        $("#btn-downloadd").hide();
   });
   //关键词切分 
   $("#btn-extract").click(function(){
	   var num = $('#slpk').find("option:selected").val(); 
	   var fields = $('#fields').val();
	   var str=fields.replace(/\r\n/g,"")    
        str=str.replace(/\n/g,"");
        str=str.replace(/\//g,"");
        str=str.replace(/\\/g,"");  
	   var data='{'+'"'+"num"+'"'+":"+num+","+'"'+"fields"+'"'+":"+'"'+str+'"'+'}';
	   var map={"info":data};
	    $.ajax({  
	        type : 'post',  
	        url : "getkeywords",  
	        dataType : 'json',
	        data:map,  
	        success : function(ret) {//返回list数据并循环获取  
	             $('#keywords').text(ret.keywords);
	             $('#time').text(ret.time);
	             $('#dtime').show();
	        }  
	    });
    });
   //用户1加密
    $("#btn-user1").click(function(){
	   var fields = $('#keyword1').val();
	   var str=fields.replace(/\r\n/g,"")    
       str=str.replace(/\n/g,"");
       str=str.replace(/\//g,"");
       str=str.replace(/\\/g,""); 
	   var map={"keyword":str};
	    $.ajax({  
	        type : 'post',  
	        url : "encku1",  
	        dataType : 'json',
	        data:map,  
	        success : function(ret) {//返回list数据并循环获取  
	             $('#owner1').val(ret.enck);
	        }  
	    });
    });
    //用户2加密
    $("#btn-user2").click(function(){
	   var fields = $('#keyword2').val();
	   var str=fields.replace(/\r\n/g,"")    
       str=str.replace(/\n/g,"");
       str=str.replace(/\//g,"");
       str=str.replace(/\\/g,"");
	   var map={"keyword":str};
	    $.ajax({  
	        type : 'post',  
	        url : "encku2",  
	        dataType : 'json',
	        data:map,  
	        success : function(ret) {//返回list数据并循环获取  
	             $('#owner2').val(ret.enck);
	        }  
	    });
    });
    //判断是否相等
    $("#btn-equ").click(function(){
       layer.load();
	   var fields = $('#keyword2').val();
	   var str=fields.replace(/\r\n/g,"")    
       str=str.replace(/\n/g,"");
       str=str.replace(/\//g,"");
       str=str.replace(/\\/g,"");
       var fields1 = $('#keyword1').val();
	   var str1=fields1.replace(/\r\n/g,"")    
       str1=str1.replace(/\n/g,"");
       str1=str1.replace(/\//g,"");
       str1=str1.replace(/\\/g,"");
       var data='{'+'"'+"k1"+'"'+":"+'"'+str1+'"'+","+'"'+"k2"+'"'+":"+'"'+str+'"'+'}';
	   var map={"keyword":data};
	    $.ajax({  
	        type : 'post',  
	        url : "equal",  
	        dataType : 'json',
	        data:map,  
	        success : function(ret) {//返回list数据并循环获取
	        	layer.closeAll('loading');
	        	var html ='';
	             if(ret.eq=='true'){
	             	html = '<span class="badge bg-green" style="font-size: 20px">相&nbsp;&nbsp;等</span>';
	             }else{
	             	html = '<span class="badge bg-yellow" style="font-size: 20px">不相等</span>';
	             }
	             document.getElementById('equ').innerHTML=html;
	        }  
	    });
    });
    $("#fields").on("input propertychange", function() {
        var $this = $(this),  
            _val = $this.val(),  
            count = "";  
        if (_val.length > 100000) {  
            $this.val(_val.substring(0, 100000));  
        }  
        count = 100000 - $this.val().length;  
        $("#word").text(count);  
    }); 
    //字符串加密
    $("#btn-enc").click(function(){
	   var fields = $('#encs').val();
	   var str=fields.replace(/\r\n/g,"");  
       str=str.replace(/\n/g,"");
       str=str.replace(/\//g,"");
       str=str.replace(/\\/g,""); 
	   var map={"data":str};
	    $.ajax({  
	        type : 'post',  
	        url : "encstr",  
	        dataType : 'json',
	        data:map,  
	        success : function(ret) {//返回list数据并循环获取 
	             $('#enc-str').val(ret.encV);
	        }  
	    });
    });
    //字符串解密
    $("#btn-dec").click(function(){
	   var fields = $('#decs').val();
	   var str=fields.replace(/\r\n/g,"");
       str=str.replace(/\n/g,"");
       str=str.replace(/\//g,"");
       str=str.replace(/\\/g,"");
	   var map={"data":str};
	    $.ajax({  
	        type : 'post',  
	        url : "decstr",  
	        dataType : 'json',
	        data:map,  
	        success : function(ret) {//返回list数据并循环获取  
	            $('#dec-str').val(ret.decV);
	        }  
	    });
    });
     //文件加密
    $("#btn-encfile").click(function(){
    	layer.load();
    	var formData = new FormData($("#upload-form")[0]);
		$.ajax({
			url: "file/enc",
			type: "POST",
			data: formData,
			contentType:false,
			processData:false,
			success: function(ret){
				layer.closeAll('loading');
				var obj = JSON.parse(ret);
				$('#encfilename').val(obj.url);
				$("#btn-downloade").show();
			},
			error: function(e){
				console.log(e.message);
			}
		});
    });
     //加密文件下载
    $("#btn-downloade").click(function(){
    	var filename = $('#encfilename').val();
    	$('<a id="fileDownload" href="download?filename='+filename+'"'+'></a>')[0].click();
    	$("#btn-downloade").hide();
    });
    //文件解密
    $("#btn-decfile").click(function(){
    	layer.load();
    	var formData = new FormData($("#dec-form")[0]);
		$.ajax({
			url: "file/dec",
			type: "POST",
			data: formData,
			contentType:false,
			processData:false,
			success: function(ret){
				layer.closeAll('loading');
				var obj = JSON.parse(ret);
				$('#decfilename').val(obj.url);
				$("#btn-downloadd").show();
			},
			error: function(e){
				console.log(e.message);
			}
		});
    });
     //加密文件下载
    $("#btn-downloadd").click(function(){
    	var filename = $('#decfilename').val();
    	$('<a id="fileDownload" href="download?filename='+filename+'"'+'></a>')[0].click();
    	$("#btn-downloadd").hide();
    });
    $("#keyword2").on("input propertychange", function() {
         document.getElementById('equ').innerHTML='';
    }); 
     $("#keyword1").on("input propertychange", function() {
         document.getElementById('equ').innerHTML='';
    }); 
</script>
