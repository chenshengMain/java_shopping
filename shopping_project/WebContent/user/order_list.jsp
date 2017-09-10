<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>

<head>
<base href="${pageContext.request.contextPath}/" />
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>会员登录</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	type="text/css" />
<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"
	type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css" type="text/css" />

<style>
body {
	margin-top: 20px;
	margin: 0 auto;
}

.carousel-inner .item img {
	width: 100%;
	height: 300px;
}
</style>
</head>

<body>

	<%@ include file="/toolUtils/header.jsp"%>


	<div class="container">
		<div class="row">

			<div style="margin: 0 auto; margin-top: 10px; width: 950px;">
				<strong>我的订单</strong>
				<table class="table table-bordered">
					<c:forEach var="order" items="${page.pageList }">
						<tbody>
							<tr class="success">
								<th colspan="5">
								订单编号:${order.oid }
								<c:if test="${order.state == 0 }"><a href="${pageContext.request.contextPath }/orderServlet?method=showOrderInfo&&oid=${order.oid}">未支付</a></c:if>
								<c:if test="${order.state == 1 }"><a href="javascript:void(0)">已支付</a></c:if>
								<c:if test="${order.state == 2 }"><a href="javascript:void(0)">已发货</a></c:if>
								<c:if test="${order.state == 3 }"><a href="javascript:void(0)">已收货支付</a></c:if>
								</th>
							</tr>
							<tr class="warning">
								<th>图片</th>
								<th>商品</th>
								<th>价格</th>
								<th>数量</th>
								<th>小计</th>
							</tr>
							<c:forEach var="orderItem" items="${order.listItem }">

								<tr class="active">
									<td width="60" width="40%"><input type="hidden" name="id"
										value="22"> <img src="${orderItem.p.pimage}"
										width="70" height="60"></td>
									<td width="30%"><a href = "${pageContext.request.contextPath }/ProductServlet?method=showProductInfo&&pid=${orderItem.pid}" target="_blank">${orderItem.p.pname }</a></td>
									<td width="20%">${orderItem.p.shop_price }</td>
									<td width="10%">${orderItem.count}</td>
									<td width="15%"><span class="subtotal">￥${orderItem.count * orderItem.p.shop_price }</span></td>

								</tr>
							</c:forEach>
						</tbody>



					</c:forEach>

				</table>
			</div>
		</div>
		<!-- 分页 
		<div style="text-align: center;">
			<ul class="pagination">
				<li class="disabled"><a href="#" aria-label="Previous"><span
						aria-hidden="true">&laquo;</span></a></li>
				<li class="active"><a href="#">1</a></li>
				<li><a href="#">2</a></li>
				<li><a href="#" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
			</ul>
		</div>
	</div>
	 -->
		<!-- 分页开始 -->
		<div style="width: 380px; margin: 0 auto; margin-top: 50px;">
			<ul class="pagination" style="text-align: center; margin-top: 10px;">
				<c:choose>
					<c:when test="${page.pageIndex == 1 }">
						<li class="disabled"><a href="javascript:void(0)"><span aria-hidden="true">&laquo;</span></a></li>
					</c:when>
					<c:otherwise>
						<li><a
							href="${pageContext.request.contextPath }/orderServlet?method=showMyOrders&&pageIndex=${page.pageIndex -1}"><span>&laquo;</span></a></li>

					</c:otherwise>

				</c:choose>

				<c:forEach var="i" begin="1" end="${page.totalPage }">
					<c:if test="${page.pageIndex ==i }">
						<li class="active"><a href="javascrip:void(0)">${i }</a></li>
					</c:if>
					<c:if test="${page.pageIndex!=i }">
						<li><a
							href="${pageContext.request.contextPath }/orderServlet?method=showMyOrders&&pageIndex=${i}">${i }</a></li>
					</c:if>

				</c:forEach>

				<c:choose>
					<c:when test="${page.pageIndex == page.totalPage }">

						<li class="disabled"><a href="javascript:void(0)" aria-label="Next"> <span
								aria-hidden="false">&raquo;</span>
						</a></li>
					</c:when>
					<c:otherwise>
						<li><a
							href="${pageContext.request.contextPath }/orderServlet?method=showMyOrders&&pageIndex=${page.pageIndex +1}"><span
								aria-hidden="false">&raquo;</span></a></li>

					</c:otherwise>

				</c:choose>

			</ul>
		</div>
		<!-- 分页结束 -->

		<div style="margin-top: 50px;">
			<img src="${pageContext.request.contextPath}/image/footer.jpg"
				width="100%" height="78" alt="我们的优势" title="我们的优势" />
		</div>

		<div style="text-align: center; margin-top: 5px;">
			<ul class="list-inline">
				<li><a>关于我们</a></li>
				<li><a>联系我们</a></li>
				<li><a>招贤纳士</a></li>
				<li><a>法律声明</a></li>
				<li><a>友情链接</a></li>
				<li><a target="_blank">支付方式</a></li>
				<li><a target="_blank">配送方式</a></li>
				<li><a>服务声明</a></li>
				<li><a>广告声明</a></li>
			</ul>
		</div>
		<div style="text-align: center; margin-top: 5px; margin-bottom: 20px;">
			Copyright &copy; 2005-2016 传智商城 版权所有</div>
</body>

</html>