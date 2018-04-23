<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="common/header.jsp"%>
<%@ include file="common/menu.jsp"%>
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			控制面板 <small>Version 2.0</small>
		</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
			<li class="active">Dashboard</li>
		</ol>
	</section>

	<!-- Main content -->
	<section class="content">
		<!-- Info boxes -->
		<div class="row">
			<div class="col-md-3 col-sm-6 col-xs-12">
				<div class="info-box">
					<span class="info-box-icon bg-aqua"><i
						class="ion ion-ios-gear-outline"></i></span>

					<div class="info-box-content">
						<span class="info-box-text">CPU Traffic</span> <span
							class="info-box-number">90<small>%</small></span>
					</div>
					<!-- /.info-box-content -->
				</div>
				<!-- /.info-box -->
			</div>
			<!-- /.col -->
			<div class="col-md-3 col-sm-6 col-xs-12">
				<div class="info-box">
					<span class="info-box-icon bg-red"><i
						class="fa fa-google-plus"></i></span>

					<div class="info-box-content">
						<span class="info-box-text">Likes</span> <span
							class="info-box-number">41,410</span>
					</div>
					<!-- /.info-box-content -->
				</div>
				<!-- /.info-box -->
			</div>
			<!-- /.col -->

			<!-- fix for small devices only -->
			<div class="clearfix visible-sm-block"></div>

			<div class="col-md-3 col-sm-6 col-xs-12">
				<div class="info-box">
					<span class="info-box-icon bg-green"><i
						class="ion ion-ios-cart-outline"></i></span>

					<div class="info-box-content">
						<span class="info-box-text">Sales</span> <span
							class="info-box-number">760</span>
					</div>
					<!-- /.info-box-content -->
				</div>
				<!-- /.info-box -->
			</div>
			<!-- /.col -->
			<div class="col-md-3 col-sm-6 col-xs-12">
				<div class="info-box">
					<span class="info-box-icon bg-yellow"><i
						class="ion ion-ios-people-outline"></i></span>

					<div class="info-box-content">
						<span class="info-box-text">New Members</span> <span
							class="info-box-number">2,000</span>
					</div>
					<!-- /.info-box-content -->
				</div>
				<!-- /.info-box -->
			</div>
			<!-- /.col -->
		</div>
		<!-- /.row -->

		<div class="row">
			<div class="col-md-12">
				<div class="box">
					<div class="box-header with-border">
						<h3 class="box-title">Monthly Recap Report</h3>

						<div class="box-tools pull-right">
							<button type="button" class="btn btn-box-tool"
								data-widget="collapse">
								<i class="fa fa-minus"></i>
							</button>
							<div class="btn-group">
								<button type="button" class="btn btn-box-tool dropdown-toggle"
									data-toggle="dropdown">
									<i class="fa fa-wrench"></i>
								</button>
								<ul class="dropdown-menu" role="menu">
									<li><a href="#">Action</a></li>
									<li><a href="#">Another action</a></li>
									<li><a href="#">Something else here</a></li>
									<li class="divider"></li>
									<li><a href="#">Separated link</a></li>
								</ul>
							</div>
							<button type="button" class="btn btn-box-tool"
								data-widget="remove">
								<i class="fa fa-times"></i>
							</button>
						</div>
					</div>
					<!-- /.box-header -->
					<div class="box-body">
						<div class="row">
							<div class="col-md-8">
								<p class="text-center">
									<strong>Sales: 1 Jan, 2014 - 30 Jul, 2014</strong>
								</p>

								<div class="chart">
									<!-- Sales Chart Canvas -->
									<canvas id="salesChart" style="height: 180px;"></canvas>
								</div>
								<!-- /.chart-responsive -->
							</div>
							<!-- /.col -->
							<div class="col-md-4">
								<p class="text-center">
									<strong>Goal Completion</strong>
								</p>

								<div class="progress-group">
									<span class="progress-text">Add Products to Cart</span> <span
										class="progress-number"><b>160</b>/200</span>

									<div class="progress sm">
										<div class="progress-bar progress-bar-aqua" style="width: 80%"></div>
									</div>
								</div>
								<!-- /.progress-group -->
								<div class="progress-group">
									<span class="progress-text">Complete Purchase</span> <span
										class="progress-number"><b>310</b>/400</span>

									<div class="progress sm">
										<div class="progress-bar progress-bar-red" style="width: 80%"></div>
									</div>
								</div>
								<!-- /.progress-group -->
								<div class="progress-group">
									<span class="progress-text">Visit Premium Page</span> <span
										class="progress-number"><b>480</b>/800</span>

									<div class="progress sm">
										<div class="progress-bar progress-bar-green"
											style="width: 80%"></div>
									</div>
								</div>
								<!-- /.progress-group -->
								<div class="progress-group">
									<span class="progress-text">Send Inquiries</span> <span
										class="progress-number"><b>250</b>/500</span>

									<div class="progress sm">
										<div class="progress-bar progress-bar-yellow"
											style="width: 80%"></div>
									</div>
								</div>
								<!-- /.progress-group -->
							</div>
							<!-- /.col -->
						</div>
						<!-- /.row -->
					</div>
					<!-- ./box-body -->
					<div class="box-footer">
						<div class="row">
							<div class="col-sm-3 col-xs-6">
								<div class="description-block border-right">
									<span class="description-percentage text-green"><i
										class="fa fa-caret-up"></i> 17%</span>
									<h5 class="description-header">$35,210.43</h5>
									<span class="description-text">TOTAL REVENUE</span>
								</div>
								<!-- /.description-block -->
							</div>
							<!-- /.col -->
							<div class="col-sm-3 col-xs-6">
								<div class="description-block border-right">
									<span class="description-percentage text-yellow"><i
										class="fa fa-caret-left"></i> 0%</span>
									<h5 class="description-header">$10,390.90</h5>
									<span class="description-text">TOTAL COST</span>
								</div>
								<!-- /.description-block -->
							</div>
							<!-- /.col -->
							<div class="col-sm-3 col-xs-6">
								<div class="description-block border-right">
									<span class="description-percentage text-green"><i
										class="fa fa-caret-up"></i> 20%</span>
									<h5 class="description-header">$24,813.53</h5>
									<span class="description-text">TOTAL PROFIT</span>
								</div>
								<!-- /.description-block -->
							</div>
							<!-- /.col -->
							<div class="col-sm-3 col-xs-6">
								<div class="description-block">
									<span class="description-percentage text-red"><i
										class="fa fa-caret-down"></i> 18%</span>
									<h5 class="description-header">1200</h5>
									<span class="description-text">GOAL COMPLETIONS</span>
								</div>
								<!-- /.description-block -->
							</div>
						</div>
						<!-- /.row -->
					</div>
					<!-- /.box-footer -->
				</div>
				<!-- /.box -->
			</div>
			<!-- /.col -->
		</div>
		<!-- /.row -->



		<!-- TABLE: LATEST ORDERS -->
		<div class="box box-info">
			<div class="box-header with-border">
				<h3 class="box-title">Latest Orders</h3>

				<div class="box-tools pull-right">
					<button type="button" class="btn btn-box-tool"
						data-widget="collapse">
						<i class="fa fa-minus"></i>
					</button>
					<button type="button" class="btn btn-box-tool" data-widget="remove">
						<i class="fa fa-times"></i>
					</button>
				</div>
			</div>
			<!-- /.box-header -->
			<div class="box-body">
				<div class="table-responsive">
					<table class="table no-margin">
						<thead>
							<tr>
								<th>Order ID</th>
								<th>Item</th>
								<th>Status</th>
								<th>Popularity</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><a href="pages/examples/invoice.html">OR9842</a></td>
								<td>Call of Duty IV</td>
								<td><span class="label label-success">Shipped</span></td>
								<td>
									<div class="sparkbar" data-color="#00a65a" data-height="20">90,80,90,-70,61,-83,63</div>
								</td>
							</tr>
							<tr>
								<td><a href="pages/examples/invoice.html">OR1848</a></td>
								<td>Samsung Smart TV</td>
								<td><span class="label label-warning">Pending</span></td>
								<td>
									<div class="sparkbar" data-color="#f39c12" data-height="20">90,80,-90,70,61,-83,68</div>
								</td>
							</tr>
							<tr>
								<td><a href="pages/examples/invoice.html">OR7429</a></td>
								<td>iPhone 6 Plus</td>
								<td><span class="label label-danger">Delivered</span></td>
								<td>
									<div class="sparkbar" data-color="#f56954" data-height="20">90,-80,90,70,-61,83,63</div>
								</td>
							</tr>
							<tr>
								<td><a href="pages/examples/invoice.html">OR7429</a></td>
								<td>Samsung Smart TV</td>
								<td><span class="label label-info">Processing</span></td>
								<td>
									<div class="sparkbar" data-color="#00c0ef" data-height="20">90,80,-90,70,-61,83,63</div>
								</td>
							</tr>
							<tr>
								<td><a href="pages/examples/invoice.html">OR1848</a></td>
								<td>Samsung Smart TV</td>
								<td><span class="label label-warning">Pending</span></td>
								<td>
									<div class="sparkbar" data-color="#f39c12" data-height="20">90,80,-90,70,61,-83,68</div>
								</td>
							</tr>
							<tr>
								<td><a href="pages/examples/invoice.html">OR7429</a></td>
								<td>iPhone 6 Plus</td>
								<td><span class="label label-danger">Delivered</span></td>
								<td>
									<div class="sparkbar" data-color="#f56954" data-height="20">90,-80,90,70,-61,83,63</div>
								</td>
							</tr>
							<tr>
								<td><a href="pages/examples/invoice.html">OR9842</a></td>
								<td>Call of Duty IV</td>
								<td><span class="label label-success">Shipped</span></td>
								<td>
									<div class="sparkbar" data-color="#00a65a" data-height="20">90,80,90,-70,61,-83,63</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<!-- /.table-responsive -->
			</div>
			<!-- /.box-body -->
			<div class="box-footer clearfix">
				<a href="javascript:void(0)"
					class="btn btn-sm btn-info btn-flat pull-left">Place New Order</a>
				<a href="javascript:void(0)"
					class="btn btn-sm btn-default btn-flat pull-right">View All
					Orders</a>
			</div>
			<!-- /.box-footer -->
		</div>
		<!-- /.box -->
</div>
<!-- /.content-wrapper -->
<%@ include file="common/bottom.jsp"%>