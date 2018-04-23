<style type="text/css">
.infoval {
    font-size: 100px;
}
.bodyheigh{
    height: 260px;
}
.boxheigh{
    height: 280px;
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
            OSD面板<small>Version 2.0</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-dashboard"></i> 集群监控</a></li>
            <li class="active">OSD面板</li>
        </ol>
    </section>
    <section class="content">
        <div class="row">
            <div class="col-md-4 col-sm-4 col-xs-4">
                <div class="box box-widget boxheigh">
                    <div class="box-header with-border">
                        <h3 class="box-title">OSD列表&nbsp;&nbsp;</h3>
                        <select id="slpk" class="selectpicker" data-live-search="true"></select>
                    </div>
                    <div class="box-body">
                         <div id="container-speed" style="width: 300px; height: 200px; float: left"></div>
                    </div>
                </div>
            </div>
            <div class="col-md-8 col-sm-8 col-xs-8">
                <div class="box box-widget boxheigh">
                <!-- /.box-header -->
                <div class="box-header with-border">
                        <h3 class="box-title">OSD：&nbsp;&nbsp;osd.</h3>
                        <label id="osd_id">0</label>
                        <label style="font-weight: normal">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;IP地址：&nbsp;&nbsp;</label>
                        <label style="font-weight: normal" id="ip" ></label>
                        <label style="font-weight: normal">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;主机名：&nbsp;&nbsp;</label>
                        <label style="font-weight: normal" id="host" ></label>
                    </div>
                    <div class="box-body">
                        <div id="container" style="min-width: 300px; height: 200px;"></div>
                    </div>
                </div>
            </div>
        </div>
       <div class="row">
            <div class="col-md-4 col-sm-4 col-xs-4">
                <div class="box box-widget">
                   <div class="box-header with-border">
                        <h3 class="box-title">OSD：&nbsp;&nbsp;osd.</h3>
                        <label id="osd_id_var">0</label>
                    </div>
                    <div class="box-body">
                        <div id="container-var" style="min-width: 200px; height: 200px;"></div>
                    </div>
                </div>
            </div>
            <div class="col-md-4 col-sm-4 col-xs-4">
                <div class="box box-widget">
                   <div class="box-header with-border">
                        <h3 class="box-title">OSD：&nbsp;&nbsp;osd.</h3>
                        <label id="osd_id_use">0</label>
                    </div>
                    <div class="box-body">
                        <div id="container-use" style="min-width: 200px; height: 200px;"></div>
                    </div>
                </div>
            </div>
            <div class="col-md-4 col-sm-4 col-xs-4">
                <div class="box box-widget">
                   <div class="box-header with-border">
                        <h3 class="box-title">OSD：&nbsp;&nbsp;osd.</h3>
                        <label id="osd_id_la">0</label>
                    </div>
                    <div class="box-body">
                        <div id="container-la" style="min-width: 200px; height: 200px;"></div>
                    </div>
                </div>
            </div>
       </div>
       <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12">
                <div class=" box box-widget">
                    <!-- /.box-header -->
                    <div class="box-header with-border">
                        <h3 class="box-title">OSD信息</h3>
                    </div>
                    <div class="box-body">
                        <div id="toolbar" class="toolbar">
                        </div>
                        <table id="table" class="table table-striped table-bordered table-hover"></table>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- /.content -->
</div>
<%@ include file="../common/bottom.jsp"%>
<script>
    $(function() {
        $('#table').bootstrapTable({
            method : 'get',
            url : "osdlst",
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
            showColumns : false,
            showRefresh : false,
            smartDisplay : false,
            showToggle : false,
            pk:'name',
            locale: 'zh-CN',
            paginationPreText : '上一页',
            paginationNextText : '下一页',
            columns: [
                [   
                    {checkbox: true},
                    {field: 'id', title: 'ID'},
                    {field: 'weight', title: 'WEIGHT'},
                    {field: 'reweight', title: 'REWEIGHT'},
                    {field: 'size', title: 'SIZE'},
                    {field: 'use', title: 'USE'},
                    {field: 'avail', title: 'AVAIL'},
                    {field: 'useper', title: '%USE'},
                    {field: 'var', title: 'VAR'}
                ],
             ],
        });
    });
    $(".selectpicker").selectpicker({  
        noneSelectedText : '请选择'  
    });
    //下拉数据加载  
    $.ajax({  
        type : 'get',  
        url : "osds",  
        dataType : 'json',  
        success : function(ret) {//返回list数据并循环获取  
            var datas = ret.osds;
            var select = $("#slpk");  
            for (var i = 0; i < datas.length; i++) {  
                select.append("<option value='"+datas[i]+"'>"  
                        + "osd."+datas[i] + "</option>");  
            }  
            $('.selectpicker').selectpicker('val', '');  
            $('.selectpicker').selectpicker('refresh');  
        }  
    });
    // 速度仪表
    var initdata =0;
    var v = $('#osd_id').val();
    var data={"id":v};
    $.ajax({
        url: "osd/osduti",
        type: "GET",
        data: data,
        dataType: "json",
        async: false,
        success: function(ret){
            initdata = ret.per;
        },
        error: function(e){
            console.log(e.message);
        }
    });
    var chart1 = Highcharts.chart('container-speed', {
        chart: {
            type: 'solidgauge'
        },
        title: null,
        pane: {
            center: ['50%', '85%'],
            size: '140%',
            startAngle: -90,
            endAngle: 90,
            background: {
                backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || '#EEE',
                innerRadius: '60%',
                outerRadius: '100%',
                shape: 'arc'
            }
        },
        tooltip: {
            enabled: false
        },
        yAxis: {
            stops: [
                [0.1, '#55BF3B'], // green
                [0.5, '#DDDF0D'], // yellow
                [0.9, '#DF5353'] // red
            ],
            lineWidth: 0,
            minorTickInterval: null,
            tickPixelInterval: 400,
            tickWidth: 0,
            title: {
                y: -70
            },
            labels: {
                y: 16
            }
        },
        plotOptions: {
            solidgauge: {
                dataLabels: {
                    y: 5,
                    borderWidth: 0,
                    useHTML: true
                }
            }
        },
        yAxis: {
            min: 0,
            max: 100,
            title: {
                text: '使用量'
            }
        },
        credits: {
            enabled: false
        },
        series: [{
            name: '使用量',
            data: [initdata],
            dataLabels: {
                format: '<div style="text-align:center"><span style="font-size:25px;color:' +
                ((Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black') + '">{y}</span><br/>' +
                '<span style="font-size:12px;color:silver">百分比</span></div>'
            },
            tooltip: {
                valueSuffix: ' 百分比'
            }
        }]
    });
 // 定时刷新数据
    setInterval(function () {
        var point,
            newVal,
            inc;
        if (chart1) {
            point = chart1.series[0].points[0];
            var val = $('#osd_id').text();
            var data={"id":val};
            $.ajax({
                url: "osd/osduti",
                type: "GET",
                data: data,
                dataType: "json",
                async: false,
                success: function(ret){
                    newVal = ret.per;
                    console.log(ret.per);
                },
                error: function(e){
                    console.log(e.message);
                }
            });
            point.update(newVal);
        }
    }, 4000);
    $(document).on("change", "#slpk", function () {
        var name = $('#slpk').find("option:selected").val();
        $('#osd_id').text(name);
        $('#osd_id_use').text(name);
        $('#osd_id_var').text(name);
        $('#osd_id_la').text(name);
        point = chart1.series[0].points[0];
        var data={"id":name};
        $.ajax({
            url: "osd/osduti",
            type: "GET",
            data: data,
            dataType: "json",
            async: false,
            success: function(ret){
                point.update(ret.per);
            },
            error: function(e){
                console.log(e.message);
            }
        });
        var map={"id":name};
        $.ajax({
            url: "osd/loca",
            type: "GET",
            data:map,
            dataType: "json",
            async: false,
            success: function(ret){
                $('#host').text(ret.host);
                $('#ip').text(ret.ip);
                $('#root').text(ret.root);
            },
            error: function(e){
                console.log(e.message);
            }
        });
    });
    Highcharts.setOptions({
        global: {
            useUTC: false
        }
    }); 
    var pgline = Highcharts.chart('container', {
        chart: {
            type: 'areaspline',
            animation: Highcharts.svg, // don't animate in old IE
            marginRight: 10,
            events: {
                load: function () {

                    // set up the updating of the chart each second
                    var series = this.series[0];
                    setInterval(function () {
                        var val = $('#osd_id').text();
                        var map={"id":val};
                        $.ajax({
                            url: "osd/pgosd",
                            type: "GET",
                            data:map,
                            dataType: "json",
                            async: false,
                            success: function(ret){
                                y=ret.num_pg;
                                x = (new Date()).getTime(); // current time
                            },
                            error: function(e){
                                console.log(e.message);
                            }
                        });
                        series.addPoint([x, y], true, true);
                    }, 1000);
                }
            }
        },
        title: {
            text: 'PGs'
        },
        xAxis: {
            type: 'datetime',
            tickPixelInterval: 150
        },
        yAxis: {
            title: {
                text: ''
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
                    Highcharts.numberFormat(this.y, 0)+'个';
            }
        },
        legend: {
            enabled: false
        },
        exporting: {
            enabled: false
        },
        series: [{
            name: 'PG数目',
            data: (function () {
                // generate an array of random data
                var data = [],
                    time = (new Date()).getTime(),
                    i;

                for (i = -19; i <= 0; i += 1) {
                    var val = $('#osd_id').text();
                    var map={"id":val};
                    $.ajax({
                        url: "osd/pgosd",
                        type: "GET",
                        data:map,
                        dataType: "json",
                        async: false,
                        success: function(ret){
                            var ydata = ret.num_pg;
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
     var varline = Highcharts.chart('container-var', {
        chart: {
            type: 'scatter',
            animation: Highcharts.svg, // don't animate in old IE
            marginRight: 10,
            events: {
                load: function () {

                    // set up the updating of the chart each second
                    var series = this.series[0];
                    setInterval(function () {
                        var val = $('#osd_id').text();
                        var map={"id":val};
                        $.ajax({
                            url: "osd/var",
                            type: "GET",
                            data:map,
                            dataType: "json",
                            async: false,
                            success: function(ret){
                                y=ret.var;
                                x = (new Date()).getTime(); // current time
                            },
                            error: function(e){
                                console.log(e.message);
                            }
                        });
                        series.addPoint([x, y], true, true);
                    }, 2000);
                }
            }
        },
        title: {
            text: 'Variance'
        },
        xAxis: {
            type: 'datetime',
            tickPixelInterval: 150
        },
        yAxis: {
            title: {
                text: ''
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
                    Highcharts.numberFormat(this.y, 2);
            }
        },
        legend: {
            enabled: false
        },
        exporting: {
            enabled: false
        },
        series: [{
            name: 'VAR',
            data: (function () {
                // generate an array of random data
                var data = [],
                    time = (new Date()).getTime(),
                    i;

                for (i = -19; i <= 0; i += 1) {
                    var val = $('#osd_id').text();
                    var map={"id":val};
                    $.ajax({
                        url: "osd/var",
                        type: "GET",
                        data:map,
                        dataType: "json",
                        async: false,
                        success: function(ret){
                            var ydata = ret.var;
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
     var useline = Highcharts.chart('container-use', {
        chart: {
            type: 'area',
            animation: Highcharts.svg, // don't animate in old IE
            marginRight: 10,
            events: {
                load: function () {

                    // set up the updating of the chart each second
                    var series = this.series[0];
                    var series2 = this.series[1];
                    setInterval(function () {
                        var val = $('#osd_id').text();
                        var map={"id":val};
                        $.ajax({
                            url: "osd/use",
                            type: "GET",
                            data:map,
                            dataType: "json",
                            async: false,
                            success: function(ret){
                                y=ret.use;
                                y2= ret.ava;
                                x = (new Date()).getTime(); // current time
                            },
                            error: function(e){
                                console.log(e.message);
                            }
                        });
                        series.addPoint([x, y], true, true);
                        series2.addPoint([x, y2], true, true);
                    }, 2000);
                }
            }
        },
        title: {
            text: 'OSD Storage'
        },
        xAxis: {
            type: 'datetime',
            tickPixelInterval: 150
        },
        yAxis: {
            title: {
                text: 'M'
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
                    Highcharts.numberFormat(this.y, 2)+'M';
            }
        },
        legend: {
            enabled: false
        },
        exporting: {
            enabled: false
        },
        series: [{
            name: 'use',
            data: (function () {
                // generate an array of random data
                var data = [],
                    time = (new Date()).getTime(),
                    i;

                for (i = -19; i <= 0; i += 1) {
                    var val = $('#osd_id').text();
                    var map={"id":val};
                    $.ajax({
                        url: "osd/use",
                        type: "GET",
                        data:map,
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
        },{
            name: 'avail',
            data: (function () {
                // generate an array of random data
                var data = [],
                    time = (new Date()).getTime(),
                    i;

                for (i = -19; i <= 0; i += 1) {
                    var val = $('#osd_id').text();
                    var map={"id":val};
                    $.ajax({
                        url: "osd/use",
                        type: "GET",
                        data:map,
                        dataType: "json",
                        async: false,
                        success: function(ret){
                            var ydata = ret.ava;
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
var laline = Highcharts.chart('container-la', {
        chart: {
            type: 'column',
            animation: Highcharts.svg, // don't animate in old IE
            marginRight: 10,
            events: {
                load: function () {

                    // set up the updating of the chart each second
                    var series = this.series[0];
                    var series2 = this.series[1];
                    setInterval(function () {
                        var val = $('#osd_id').text();
                        var map={"id":val};
                        $.ajax({
                            url: "osd/perf",
                            type: "GET",
                            data:map,
                            dataType: "json",
                            async: false,
                            success: function(ret){
                                y=ret.apply;
                                y2= ret.commit;
                                x = (new Date()).getTime(); // current time
                            },
                            error: function(e){
                                console.log(e.message);
                            }
                        });
                        series.addPoint([x, y], true, true);
                        series2.addPoint([x, y2], true, true);
                    }, 2000);
                }
            }
        },
        title: {
            text: 'Latency'
        },
        xAxis: {
            type: 'datetime',
            tickPixelInterval: 150
        },
        yAxis: {
            title: {
                text: ''
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
                    Highcharts.numberFormat(this.y, 2)+'ms';
            }
        },
        legend: {
            enabled: false
        },
        exporting: {
            enabled: false
        },
        series: [{
            name: 'fs_apply_latency',
            data: (function () {
                // generate an array of random data
                var data = [],
                    time = (new Date()).getTime(),
                    i;

                for (i = -19; i <= 0; i += 1) {
                    var val = $('#osd_id').text();
                    var map={"id":val};
                    $.ajax({
                        url: "osd/perf",
                        type: "GET",
                        data:map,
                        dataType: "json",
                        async: false,
                        success: function(ret){
                            var ydata = ret.apply;
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
            name: 'fs_commit_latency',
            data: (function () {
                // generate an array of random data
                var data = [],
                    time = (new Date()).getTime(),
                    i;

                for (i = -19; i <= 0; i += 1) {
                    var val = $('#osd_id').text();
                    var map={"id":val};
                    $.ajax({
                        url: "osd/perf",
                        type: "GET",
                        data:map,
                        dataType: "json",
                        async: false,
                        success: function(ret){
                            var ydata = ret.commit;
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
    $(document).ready(function()
    {
        var name = $('#slpk').find("option:selected").val();
        $('#osd_id').text(name);
        $('#osd_id_use').text(name);
        $('#osd_id_var').text(name);
        $('#osd_id_la').text(name);
        var map={"id":name};
        $.ajax({
            url: "osd/loca",
            type: "GET",
            data:map,
            dataType: "json",
            async: false,
            success: function(ret){
                $('#host').text(ret.host);
                $('#ip').text(ret.ip);
                $('#root').text(ret.root);
            },
            error: function(e){
                console.log(e.message);
            }
        });
    });
</script>