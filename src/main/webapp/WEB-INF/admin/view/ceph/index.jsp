<style type="text/css">
.infoval {
    font-size: 100px;
}
.bodyheigh{
	height: 280px;
}
.boxheigh{
	height: 350px;
}
</style>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp"%>
<%@ include file="../common/menu.jsp"%>
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			控制面板
		</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-share-alt-square"></i> 集群监控</a></li>
			<li class="active">控制面板</li>
		</ol>
	</section>
	<section class="content">
		<div class="row">
	        <div class="col-md-3 col-sm-3 col-xs-3">
	          <div class="info-box bg-aqua">
	            <span class="info-box-icon"><i class="fa fa-heart"></i></span>

	            <div class="info-box-content">
	              <span class="info-box-text">health</span>
	              <span class="info-box-number">${health}</span>
	              <div class="progress">
	                <div class="progress-bar" style="width: 100%"></div>
	              </div>
	              <span class="progress-description">
	                    ceph version ${version}
	               </span>
	            </div>
	            <!-- /.info-box-content -->
	          </div>
	          <!-- /.info-box -->
	        </div>
	        <!-- /.col -->
	        <div class="col-md-3 col-sm-3 col-xs-3">
	          <div class="info-box bg-green">
	            <span class="info-box-icon"><i class="fa fa-eye"></i></span>

	            <div class="info-box-content">
	              <span class="info-box-text" id="mon">monmap</span>
	              <label class="hidden" id="moninfo"></label>
	              <span class="info-box-number">${monnum}</span>
	              <div class="progress">
	                <div class="progress-bar" style="width: 100%"></div>
	              </div>
	              <span class="progress-description">
	                    ${monname}
	               </span>
	            </div>
	            <!-- /.info-box-content -->
	          </div>
	          <!-- /.info-box -->
	        </div>
	        <!-- /.col -->
	        <div class="col-md-3 col-sm-3 col-xs-3">
	          <div class="info-box bg-yellow">
	            <span class="info-box-icon"><i class="fa fa-hdd-o"></i></span>

	            <div class="info-box-content">
	              <span class="info-box-text" id="osd">osdmap</span>
	              <label class="hidden" id="osdinfo"></label>
	              <span class="info-box-number">${osdnum}</span>

	              <div class="progress">
	                <div class="progress-bar" style="width: 100%"></div>
	              </div>
	                  <span class="progress-description">
	                    ${upnum} up, ${innum} in
	                  </span>
	            </div>
	            <!-- /.info-box-content -->
	          </div>
	          <!-- /.info-box -->
	        </div>
	        <!-- /.col -->
	        <div class="col-md-3 col-sm-3 col-xs-3">
	          <div class="info-box bg-red">
	            <span class="info-box-icon"><i class="fa fa-braille"></i></span>

	            <div class="info-box-content">
	              <span class="info-box-text">pgmap</span>
	              <span class="info-box-number">${pgnum}</span>

	              <div class="progress">
	                <div class="progress-bar" style="width: 100%"></div>
	              </div>
	                  <span class="progress-description">
	                    ${pgavail}
	                  </span>
	            </div>
	            <!-- /.info-box-content -->
	          </div>
	          <!-- /.info-box -->
	        </div>
	        <!-- /.col -->
        </div>
        <div class="row">
        	<div class="col-md-4 col-sm-4 col-xs-4">
            <div class="box box-success">
            <!-- /.box-header -->
            <div class="box-body no-padding bodyheigh">
              <div class="row">
                <div class="col-md-9 col-sm-9 col-xs-9">
                  <div class="pad">
                    <div>
					    <div id="container" style="min-width: 150px; height: 260px; max-width: 600px; margin: 0 auto"></div>
					</div>
                  </div>
                </div>
                <!-- /.col -->
                <div class="col-md-3 col-sm-3 col-xs-3">
                  <div class="pad box-pane-right bg-green" style="min-height: 280px">
                    <div class="description-block margin-bottom">
                      <h5 class="description-header">${objectnum}</h5>
                      <span class="description-text">Objects</span>
                    </div>
                    <!-- /.description-block -->
                    <div class="description-block margin-bottom">
                      <h5 class="description-header">${poolnum}</h5>
                      <span class="description-text">Pools</span>
                    </div>
                    <!-- /.description-block -->
                    <div class="description-block">
                      <h5 class="description-header">${data}</h5>
                      <span class="description-text">DATA</span>
                    </div>
                    <!-- /.description-block -->
                  </div>
                </div>
                <!-- /.col -->
              </div>
              <!-- /.row -->
            </div>
            <!-- /.box-body -->
          </div>
        </div>
        <div class="col-md-4 col-sm-4 col-xs-4">
            <div class="box box-success">
            <!-- /.box-header -->
            <div class="box-body no-padding bodyheigh">
            <div id="container-rw" style="min-width: 100px; height: 260px; margin: 0 auto"></div>
            </div>
            </div>
        </div>
        <div class="col-md-4 col-sm-4 col-xs-4">
            <div class="box box-success">
            <!-- /.box-header -->
            <div class="box-body no-padding bodyheigh">
            <div id="container-obj" style="min-width: 100px; height: 260px; margin: 0 auto"></div>
            </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-7 col-sm-7 col-xs-7">
        	<div class="box box-warning boxheigh">
                    <!-- /.box-header -->
                <div class="box-body no-padding">
                    <div id="container-pools" style="min-width: 600px; height: 350px; max-width: 1200px;margin: 0 auto"></div>
                </div>
            </div>
        </div>
        <div class="col-md-5 col-sm-5 col-xs-5">
            <div class="box box-success boxheigh">
            <!-- /.box-header -->
            <div class="box-body no-padding">
                <div id="container-use" style="min-width: 100px; height: 350px; margin: 0 auto"></div>
            </div>
            </div>
        </div>
    </div>
	</section>
	<!-- /.content -->
</div>
<%@ include file="../common/bottom.jsp"%>
<script type="text/javascript">
    // Build the chart
    Highcharts.chart('container', {
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie'
        },
        title: {
            text: '存储容量分布'
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: false
                },
                showInLegend: true
            }
        },
        series: [{
            name: '百分比',
            colorByPoint: true,
            data: [{
                name: '可用',
                y: 283,
                sliced: true,
                selected: true
            }, {
                name: 'RAW USED',
                y: 1.57
            }, {
                name: '其他',
                y: 0.14
            }]
        }]
    });
    var pool0 = ${Pool0};
    var pool1 = ${Pool1};
    var pool2 = ${Pool2};
    var pool3 = ${Pool3};
    var pool4 = ${Pool4};
    var pool5 = ${Pool5};
    var pool6 = ${Pool6};
    var pool7 = ${Pool7};
    var pool8 = ${Pool8};
    var pool9 = ${Pool9};
    Highcharts.chart('container-pools', {
    chart: {
        type: 'spline'
    },
    title: {
        text: 'Pools信息'
    },
    xAxis: {
        categories: [
            'log_size',
            'up',
            '#object',
            '#obj_dirty',
            '#read',
            '#write',
            '#obj_reco',
            '#scrub_err',
            '#deep_scrub_err',
            '#obj_mis',
            '#obj_mis_on_pri',
            '#obj_unfound'
        ],
        crosshair: true
    },
    yAxis: {
        min: 0,
        title: {
            text: '数目'
        }
    },
    tooltip: {
        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
            '<td style="padding:0"><b>{point.y:.1f}</b></td></tr>',
        footerFormat: '</table>',
        shared: true,
        useHTML: true
    },
    plotOptions: {
        column: {
            pointPadding: 0,
            borderWidth: 0
        }
    },
    series: [{
        name: 'Pool0',
        data: pool0

    }, {
        name: 'Pool1',
        data: pool1

    }, {
        name: 'Pool2',
        data: pool2

    }, {
        name: 'Pools3',
        data: pool3

    }, {
        name: 'Pools4',
        data: pool4

    }, {
        name: 'Pools5',
        data: pool5

    }, {
        name: 'Pools6',
        data: pool6

    }, {
        name: 'Pools7',
        data: pool7

    }, {
        name: 'Pools8',
        data: pool8

    }, {
        name: 'Pools9',
        data: pool9

    }
    ]
});
    Highcharts.setOptions({
        global: {
            useUTC: false
        }
    });

    Highcharts.chart('container-rw', {
        chart: {
            type: 'areaspline',
            animation: Highcharts.svg, // don't animate in old IE
            marginRight: 10,
            events: {
                load: function () {

                    // set up the updating of the chart each second
                    var series = this.series[0];
                    var seriesr = this.series[1];
                    var seriesop = this.series[2];
                    setInterval(function () {
                    	var x;
                    	var y=0;
                    	var y2=0;
                        var y3=0;
                    	$.ajax({
                			url: "index/rwrspeed",
                			type: "GET",
                			dataType: "json",
                			async: false,
                			success: function(ret){
                				console.log("wr="+ret.wr);
                				if(y!=ret.wr){
                					 y = parseInt(ret.wr);
                					 y2=parseInt(ret.rd);
                					 y3=parseInt(ret.op);
                				}
                				x = (new Date()).getTime(); // current time
                			},
                			error: function(e){
                				console.log(e.message);
                			}
                		});
                        series.addPoint([x, y], true, true);
                        seriesr.addPoint([x, y2], true, true);
                        seriesop.addPoint([x, y3], true, true);
                    }, 4000);
                }
            }
        },
        title: {
            text: '读写速度'
        },
        xAxis: {
            type: 'datetime',
            tickPixelInterval: 150
        },
        yAxis: {
            title: {
                text: 'kB/s'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            formatter: function () {
                return '<b>' + this.series.name + '</b><br/>' +
                    Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                    Highcharts.numberFormat(this.y, 0);
            }
        },
        legend: {
            enabled: false
        },
        exporting: {
            enabled: false
        },
        series: [{
            name: '写入',
            data: (function () {
                // generate an array of random data
                var data = [],
                    time = (new Date()).getTime(),
                    i;

                for (i = -19; i <= 0; i += 1) {
                	$.ajax({
            			url: "index/rwrspeed",
            			type: "GET",
            			dataType: "json",
            			async: false,
            			success: function(ret){
            				var ydata = 0;
            				if(ydata!=ret.wr){
            					ydata=parseInt(ret.wr);
            				}
            				data.push({
                                x: time + i * 1000,
                                y: ydata
                            });
            			},
            			error: function(e){
            				console.log(e.message);
            			}
            		});
                    
                }
                return data;
            }())
        },{
            name: '读取',
            data: (function () {
                // generate an array of random data
                var data = [],
                    time = (new Date()).getTime(),
                    i;

                for (i = -19; i <= 0; i += 1) {
                	$.ajax({
            			url: "index/rwrspeed",
            			type: "GET",
            			dataType: "json",
            			async: false,
            			success: function(ret){
            				var ydata = 0;
            				if(ydata!=ret.rd){
            					ydata=parseInt(ret.rd);
            				}
            				data.push({
                                x: time + i * 1000,
                                y: ydata
                            });
            			},
            			error: function(e){
            				console.log(e.message);
            			}
            		});
                    
                }
                return data;
            }())
        },{
            name: 'OP',
            data: (function () {
                // generate an array of random data
                var data = [],
                    time = (new Date()).getTime(),
                    i;

                for (i = -19; i <= 0; i += 1) {
                	$.ajax({
            			url: "index/rwrspeed",
            			type: "GET",
            			dataType: "json",
            			async: false,
            			success: function(ret){
            				var ydata = 0;
            				if(ydata!=ret.rd){
            					ydata=parseInt(ret.op);
            				}
            				data.push({
                                x: time + i * 1000,
                                y: ydata
                            });
            			},
            			error: function(e){
            				console.log(e.message);
            			}
            		});
                    
                }
                return data;
            }())
        }]
    });
     var varline = Highcharts.chart('container-obj', {
        chart: {
            type: 'scatter',
            zoomType: 'x',
            panning: true,
            panKey: 'shift',
            animation: Highcharts.svg, // don't animate in old IE
            marginRight: 10,
            events: {
                load: function () {

                    // set up the updating of the chart each second
                    var series = this.series[0];
                    setInterval(function () {
                        $.ajax({
                            url: "index/objnum",
                            type: "GET",
                            dataType: "json",
                            async: false,
                            success: function(ret){
                                y=ret.obj_num;
                                x = (new Date()).getTime(); // current time
                            },
                            error: function(e){
                                console.log(e.message);
                            }
                        });
                        series.addPoint([x, y], true, true);
                    }, 4000);
                }
            }
        },
        title: {
            text: '对象数'
        },
        xAxis: {
            type: 'datetime',
            tickPixelInterval: 150
        },
        yAxis: {
            title: {
                text: '个数'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            formatter: function () {
                return '<b>' + this.series.name + '</b><br/>' +
                    Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                    Highcharts.numberFormat(this.y, 0);
            }
        },
        legend: {
            enabled: false
        },
        exporting: {
            enabled: false
        },
        series: [{
            name: 'Objects',
            data: (function () {
                // generate an array of random data
                var data = [],
                    time = (new Date()).getTime(),
                    i;

                for (i = -19; i <= 0; i += 1) {
                    $.ajax({
                        url: "index/objnum",
                        type: "GET",
                        dataType: "json",
                        async: false,
                        success: function(ret){
                            var ydata = ret.objnum;
                            data.push({
                                x: time + i * 1000,
                                y: ydata
                            });
                        },
                        error: function(e){
                            console.log(e.message);
                        }
                    });
                }
                return data;
            }())
        }]
    });
      var varline = Highcharts.chart('container-use', {
        chart: {
            type: 'scatter',
            zoomType: 'x',
            panning: true,
            panKey: 'shift',
            animation: Highcharts.svg, // don't animate in old IE
            marginRight: 10,
            events: {
                load: function () {

                    // set up the updating of the chart each second
                    var series = this.series[0];
                    setInterval(function () {
                        $.ajax({
                            url: "index/use",
                            type: "GET",
                            dataType: "json",
                            async: false,
                            success: function(ret){
                                y=ret.use;
                                x = (new Date()).getTime(); // current time
                            },
                            error: function(e){
                                console.log(e.message);
                            }
                        });
                        series.addPoint([x, y], true, true);
                    }, 4000);
                }
            }
        },
        title: {
            text: '已用量'
        },
        xAxis: {
            type: 'datetime',
            tickPixelInterval: 150
        },
        yAxis: {
            title: {
                text: 'MB'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            formatter: function () {
                return '<b>' + this.series.name + '</b><br/>' +
                    Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                    Highcharts.numberFormat(this.y, 0)+'MB';
            }
        },
        legend: {
            enabled: false
        },
        exporting: {
            enabled: false
        },
        series: [{
            name: 'used',
            data: (function () {
                // generate an array of random data
                var data = [],
                    time = (new Date()).getTime(),
                    i;

                for (i = -19; i <= 0; i += 1) {
                    $.ajax({
                        url: "index/use",
                        type: "GET",
                        dataType: "json",
                        async: false,
                        success: function(ret){
                            var ydata = ret.use;
                            data.push({
                                x: time + i * 1000,
                                y: ydata
                            });
                        },
                        error: function(e){
                            console.log(e.message);
                        }
                    });
                }
                return data;
            }())
        }]
    });
    $(document).ready(function(){
    	 $.ajax({
             url: "index/moninfo",
             type: "GET",
             dataType: "json",
             success: function(ret){
                 info = ret.moninfo;
                 $('#moninfo').text(info);
             },
             error: function(e){
                 console.log(e.message);
             }
         });
    	 $.ajax({
             url: "index/osdinfo",
             type: "GET",
             dataType: "json",
             success: function(ret){
                 info = ret.osdinfo;
                 $('#osdinfo').text(info);
             },
             error: function(e){
                 console.log(e.message);
             }
         });
        $("#mon").hover(function() {
            openMsg();
        }, function() {
        	layer.close(tips);
        });
        $("#osd").hover(function() {
            openTips();
        }, function() {
            layer.close(osdtips);
        });
    });

    function openMsg() {
    	var info =$('#moninfo').text();
    	 tips=layer.tips("<span style='color:black'>"+info+"</span>", '#mon', {
             tips: [1, '#F8F8F8'],
             area: ['350px', 'auto']
           });
    }
    function openTips() {
        var info=$('#osdinfo').text();
        osdtips=layer.tips("<span style='color:black'>"+info+"</span>", '#osd', {
            tips: [1, '#F8F8F8'],
            area: ['350px', 'auto']
          });
    }
</script>