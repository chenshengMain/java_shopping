<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商户管理</title>
<link rel="stylesheet" type="text/css" href="../css/easyui.css">
<link rel="stylesheet" type="text/css" href="../css/icon.css">
<script type="text/javascript" src="../js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="../js/jquery.easyui.min.js"></script>
<style type="text/css">
#category a {
	text-decoration: none;
}
</style>
</head>
<body>
  <script type="text/javascript">
			$(function() {
				$('#tt').tabs('add', {
					title : '首页',
					content : '欢迎来到商城管理....',
					closable : true
				});
				$("#category a").click(function() {
					var tabTitle = $(this).html();
					var flag = $("#tt").tabs("exists", tabTitle);
					var url = this.href;
					
					if (flag) {
						$("#tt").tabs("select", tabTitle);
					} else {

						$('#tt').tabs('add', {
							title : tabTitle,
							content :showAllCategory(url),
							closable : true,
						 
						});

					}
					return false;//阻止跳转页面

				});

			});
			function showAllCategory(url) {
		
				return "<iframe  src='"+url+"' style='width:100%;height:100%' frameborder='0'></iframe>";
				
			}
			
			
		</script>
  <div id="cc" class="easyui-layout" data-options="fit:'true'">
    <!-- header  -->
    <div data-options="region:'north',split:true"
      style="height: 100px; background-image: url('../img/header.jpg')"></div>
    <div data-options="region:'west',split:true" style="width: 200px;">
      <!--侧栏 分类 -->
      <div id="category" class="easyui-accordion" data-options="fit:'true'">
        <div title="分类管理" data-options="selected:true"
          style="overflow: auto; padding: 10px;">
          <a href="category/category.html">所有分类</a>
        </div>
        <div title="商品管理" style="overflow: auto;padding: 10px;">
          <a href="category/product_list.jsp">所有商品</a>
        </div>
        <div title="订单管理">
          <a href="javascript:void(0)">所有订单</a>
        </div>
        <div title="用户管理">
          <a href="javascript:void(0)">所有用户</a>
        </div>
      </div>
    </div>
    <!-- 中心内容 -->
    <div data-options="region:'center'" style="padding: 0px; background: #eee;">
      <div id="tt" class="easyui-tabs" data-options="fit:'true'">
   
      </div>
    </div>
</body>
</html>