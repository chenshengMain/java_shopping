<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>


<script type="text/javascript">
	//用 ajax 获取数据
	$(function(){
		

		var url = "${pageContext.request.contextPath}/ProductServlet";
		var parame = {
			"method" : "category"
		};
		$.post(url, parame, function(data) {
		    $(data).each(function(index,e){
		    	
	    	//	$("#ulId").append("<li class='active'><a href='${pageContext.request.contextPath}/ProductServlet?method=productsList&&cid='"+e.cid+">"+e.cname+"<span class='sr-only'>(current)</span></a></li>");

		   	$("#ulId").append("<li><a href='${pageContext.request.contextPath}/ProductServlet?method=productsList&&cid="+e.cid+"''>"+e.cname+"</a></li>");
		    
		    	
		    });
			
		}, "json");
		
	});
	
</script>


<!--
            	时间：2015-12-30
            	描述：菜单栏
            -->
<div class="container-fluid">
	<div class="col-md-4">
		<img src="img/logo2.png" />
	</div>
	<div class="col-md-5">
		<img src="img/header.png" />
	</div>
	<div class="col-md-3" style="padding-top: 20px">
		<ol class="list-inline">
			<c:choose>
				<c:when test="${not empty user}">
					<li>用户:${user.name }</li>
					<li><a
						href="${pageContext.request.contextPath }/UserServlet?method=logout">注销</a></li>
						<li><a href="${pageContext.request.contextPath }/orderServlet?method=showMyOrders">我的订单</a></li>
					<li><a href="user/cart.jsp">购物车</a></li>
					
				</c:when>

				<c:otherwise>
					<li><a href="user/login.jsp">登录</a></li>
					<li><a href="user/register.jsp">注册</a></li>
					<li><a href="user/cart.jsp">购物车</a></li>

				</c:otherwise>

			</c:choose>

		</ol>
	</div>
</div>


<!--
            	时间：2015-12-30
            	描述：导航条
            -->

<div class="container-fluid">
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
					aria-expanded="false">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">首页</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul id = "ulId" class="nav navbar-nav">
					
					
				</ul>
				<form class="navbar-form navbar-right" role="search">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Search">
					</div>
					<button type="submit" class="btn btn-default">Submit</button>
				</form>

			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>
</div>
