<%@page import="services.CS_service"%>
<%@page import="domain.Product"%>
<%@page import="services.impl.ServiceImpl"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Arrays"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page
  language="java"
  contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib
  uri="http://java.sun.com/jsp/jstl/core"
  prefix="c"%>
<%@ taglib
  uri="http://java.sun.com/jsp/jstl/functions"
  prefix="fn"%>
<!doctype html>
<html>
<head>
<base href="${pageContext.request.contextPath}/" />
<meta charset="utf-8" />
<meta
  name="viewport"
  content="width=device-width, initial-scale=1">
<title>会员登录</title>
<link
  rel="stylesheet"
  href="${pageContext.request.contextPath}/css/bootstrap.min.css"
  type="text/css" />
<script
  src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"
  type="text/javascript"></script>
<script
  src="${pageContext.request.contextPath}/js/bootstrap.min.js"
  type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link
  rel="stylesheet"
  href="${pageContext.request.contextPath}/css/style.css"
  type="text/css" />
<style>
body {
	margin-top: 20px;
	margin: 0 auto;
	width: 100%;
}

.carousel-inner .item img {
	width: 100%;
	height: 300px;
}
</style>
</head>
<body>
  <%@ include file="/toolUtils/header.jsp"%>
  <div
    class="row"
    style="width: 1210px; margin: 0 auto;">
    <div class="col-md-12">
      <ol class="breadcrumb">
        <li><a href="#">首页</a></li>
      </ol>
    </div>
    <c:forEach
      var="p"
      items="${pageBean.pageList }">
      <div class="col-md-2">
        <a
          href="${pageContext.request.contextPath }/ProductServlet?method=showProductInfo&&pid=${p.pid}">
          <img
            src="${p.pimage }"
            width="170"
            height="170"
            style="display: inline-block;">
        </a>
        <p>
          <a
            href="${pageContext.request.contextPath }/ProductServlet?method=showProductInfo&&pid=${p.pid}"
            style='color: green'>${fn:substring(p.pname,0,12) }....</a>
        </p>
        <p>
          <font color="#FF0000">商城价：&yen;${p.market_price }</font>
        </p>
      </div>
    </c:forEach>
  </div>
  <!--分页 -->
  <div style="width: 380px; margin: 0 auto; margin-top: 50px;">
    <ul
      class="pagination"
      style="text-align: center; margin-top: 10px;">
      <c:choose>
        <c:when test="${pageBean.pageIndex == 1 }">
          <li><a>
              <span aria-hidden="true">&laquo;</span>
            </a></li>
        </c:when>
        <c:otherwise>
          <li><a
              href="${pageContext.request.contextPath }/ProductServlet?method=productsList&&cid=${cid}&&pageIndex=${pageBean.pageIndex -1}">
              <span>&laquo;</span>
            </a></li>
        </c:otherwise>
      </c:choose>
      <c:forEach
        var="i"
        begin="1"
        end="${pageBean.totalPage }">
        <li><a
            href="${pageContext.request.contextPath }/ProductServlet?method=productsList&&cid=${cid}&&pageIndex=${i}">${i }</a></li>
      </c:forEach>
      <c:choose>
        <c:when test="${pageBean.pageIndex == pageBean.totalPage }">
          <li><a aria-label="Next">
              <span aria-hidden="false">&raquo;</span>
            </a></li>
        </c:when>
        <c:otherwise>
          <li><a
              href="${pageContext.request.contextPath }/ProductServlet?method=productsList&&cid=${cid}&&pageIndex=${pageBean.pageIndex +1}">
              <span aria-hidden="false">&raquo;</span>
            </a></li>
        </c:otherwise>
      </c:choose>
    </ul>
  </div>
  <!-- 分页结束=======================        -->
  <!--
       		商品浏览记录:
        -->
  <div
    style="width: 1210px; margin: 0 auto; padding: 0 9px; border: 1px solid #ddd; border-top: 2px solid #999; height: 246px;">
    <h4
      style="width: 50%; float: left; font: 14px/30px"微软雅黑 ";">浏览记录</h4>
    <div style="width: 50%; float: right; text-align: right;">
      <a href="">more</a>
    </div>
    <div style="clear: both;"></div>
    <div style="overflow: hidden;">
      <ul style="list-style: none;">
        <%
        	Cookie[] cooks = request.getCookies();

        	if (cooks == null) {
        		out.write("<h2>暂无浏览记录</h2>");
        		return;
        	}
        	String historyId = null;
        	for (Cookie cook : cooks) {
        		if ("historyId".equals(cook.getName())) {

        			historyId = cook.getValue();
        			break;
        		}
        	}

        	if (historyId == null) {

        		out.write("<h2>暂无浏览记录</h2>");
        		return;

        	}
        	List<String> ids = Arrays.asList(historyId.split("-"));
        	for (String id : ids) {

        		ServiceImpl si = new CS_service();
        		Product p = si.findProductByPid(id);
        %>
        <li
          style="width: 150px; height: 216; float: left; margin: 0 8px 0 0; padding: 0 18px 15px; text-align: center;"><img
          src="<%=p.getPimage()%>"
          width="130px"
          height="130px" /></li>
        <%
        	}
        %>
      </ul>
    </div>
  </div>
  <div style="margin-top: 50px;">
    <img
      src="${pageContext.request.contextPath}/image/footer.jpg"
      width="100%"
      height="78"
      alt="我们的优势"
      title="我们的优势" />
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