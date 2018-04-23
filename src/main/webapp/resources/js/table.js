$.extend({
	table : {
		config: {
            firsttd: 'tbody tr td:first-child:not(:has(div.card-views))',
            toolbar: '.toolbar',
            refreshbtn: '.btn-refresh',
            addbtn: '.btn-add',
            editbtn: '.btn-edit',
            importbtn: '.btn-import',
            delbtn: '.btn-del',
            multibtn: '.btn-multi',
            disabledbtn: '.btn-disabled',
            editonebtn: '.btn-editone',
            dragsortfield: 'weigh',
        },
		api : {
			formatter : {
				date : function(value, row, index) {
					var date2 = new Date(value);
					return date2.toLocaleString();
				},
				status1 : function(value, row, index) {
					var color='grey';
					var label = '否';
					if(value){
						color='success';
						label = '否';
					}
                    var html = '<span class="text-' + color + '"><i class="fa fa-circle"></i> ' + label + '</span>';
                    return html;
				},
				maxobject : function(value, row, index) {
					if(value<0){
						return '不限制';
					}
				},
				maxsize : function(value, row, index) {
					if(value<0){
						return '不限制';
					}
			        if(value<1024){
			            return size+'KB';
			        }else if((value>=1024)&&(value<1024*1024)){
			            return (value/1024).toFixed(2)+'MB';
			        }else{
			            return (value/(1024*1024)).toFixed(2)+'G';
			        }
				},
				filesize : function(value, row, index) {
					var size = value;
			        if(size<1024){
			            return size+'Byte';
			        }else if((size>=1024)&&(size<1024*1024)){
			            return (size/1024).toFixed(2)+'KB';
			        }else if((size>=1024*1024)&&(size<1024*1024*1024)){
			            return (size/(1024*1024)).toFixed(2)+'MB';
			        }else{
			            return (size/(1024*1024*1024)).toFixed(2)+'G';
			        }
				},
				filename : function(value, row, index) {
					var foler=new RegExp("\/$");
			        var film=new RegExp("\.mp4$");
			        var music=new RegExp("\.mp3$");
			        var pdf = new RegExp("\.pdf$");
			        var doc = new RegExp("(\.doc$|\.docx$)");
			        var txt = new RegExp("\.txt$");
			        var zip = new RegExp("(\.zip$|\.rar$)");
			        var excel = new RegExp("(\.xls$|\..xlsx)");
			        var photo = new RegExp("(\.png$|\.jpg$)");
			        var ppt = new RegExp("(\.ppt$|\.pptx$)");
			        var name = value;
			        if(foler.test(name)){
			            return '<span style="color:#FACB54;"><i class="fa fa-folder fa-lg"></i></span> ' + value;
			        }else if(film.test(name)){
			            return '<span style="color:#6D8AAB;"><i class="fa fa-file-video-o fa-lg"></i></span> ' + value;
			        }else if(music.test(name)){
			            return '<span style="color:#2581E3;"><i class="fa fa-file-sound-o fa-lg"></i></span> ' + value;
			        }else if(pdf.test(name)){
			             return '<span style="color:#CF2C34;"><i class="fa fa-file-pdf-o fa-lg"></i></span> ' + value;
			        }else if(doc.test(name)){
			             return '<span style="color:#3E9AE8;"><i class="fa fa-file-word-o fa-lg"></i></span> ' + value;
			        }else if(txt.test(name)){
			             return '<span style="color:#3366CC;"><i class="fa fa-file-text-o fa-lg"></i></span> ' + value;
			        }else if(zip.test(name)){
			             return '<span style="color:#7DCA3D;"><i class="fa fa-file-zip-o fa-lg"></i></span> ' + value;
			        }else if(excel.test(name)){
			             return '<span style="color:#2FB266;"><i class="fa fa-file-excel-o fa-lg"></i></span> ' + value;
			        }else if(photo.test(name)){
			             return '<span style="color:#E15555;"><i class="fa fa-file-photo-o fa-lg"></i></span> ' + value;
			        }else if(ppt.test(name)){
			             return '<span style="color:#EB8B18;"><i class="fa fa-file-powerpoint-o fa-lg"></i></span> ' + value;
			        }
			        else{
			            return '<span style=""><i class="fa fa-file-o fa-lg"></i></span> ' + value;
			        }
				},
				operate : function(value, row, index) {
					var html = [];
					html.push('<a href="javascript:;" class="btn btn-success btn-downloadone btn-xs"><i class="fa fa-cloud-download"></i></a>');
					html.push('<a href="javascript:;" class="btn btn-danger btn-delone btn-xs"><i class="fa fa-trash"></i></a>');
					return html.join(' ');
				},
				deleteItem : function ($table, requestUrl, selectIndex, reload){
					var selRow = $table.bootstrapTable("getSelections");
					var idField = $table.bootstrapTable("getOptions").uniqueId;
					
					var datas = "";
					if(selRow != null){
						alert("确定要删除" + selectIndex + "行吗？");
						$ajax({
							type: "POST",
							cache: false,
							async: true,
							dataType: "json",
							url: requestUrl,
							success: function(){
								alert(data.mess);
								if(data.state == 200){
									$table.bootstrapTable("hideRow", {index: selectIndex});
								}
								if(reload){
									$table.bootstrapTable("refresh");
								}
							}
						})
					}
				}
			},
			events:{
				operate: {
                  'click .btn-downloadone': function (e, value, row, index) {
                      e.stopPropagation();
                      var bucketName = row.bucketName;
                      var key = row.key;
                      var objStr = "bucketname=" + bucketName + "&" + "objname=" + key;
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
                      
                  },
                  'click .btn-delone': function (e, value, row, index) {
                	  console.log(row);
                	  e.stopPropagation();
                	  var dataArr = [];
          			  dataArr.push(row.bucketName + "&" + row.key);
          			  $.ajax({
          			      url: "index/delete",
          				  type: "POST",
          				  data: {dataArr: dataArr},
          				  dataType: "json",
          				  cache: false,
          				  success: function(data){
          				  if(data.code != -1){
          				      console.log("success!");
          					  $("#table").bootstrapTable("refresh");
          				    }
          				  },
          				  error: function(e){
          					  console.log(e.message);
          				  }
          			  });
                  }
              }
			}
		}
	}
});
