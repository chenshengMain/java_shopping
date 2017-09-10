<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="../../" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="css/easyui.css">
<link rel="stylesheet" type="text/css" href="css/icon.css">
<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
<script type="text/javascript">
	$(function() {

		$('#dg')
				.datagrid(
						{
							url : 'http://localhost:8080/shopping_project/productManagerServlet?method=findAllProductsByPage',
							columns : [ [
									{
										field : 'pid',
										title : '编号',
										width : '15%',
										align : 'center'
									},
									{
										field : 'pname',
										title : '商品名称',
										width : '20%',
										align : 'center'
									},
									{
										field : 'shop_price',
										title : '商品价格',
										width : '15%',
										align : 'center'
									},
									{
										field : 'pimage',
										title : '商品图片',
										width : '20%',
										align : 'center',
										formatter : function(value, row, index) {
											return " <img src='" + value
													+ "' width=50%  />";
										}
									},
									{
										field : 'pflag',
										title : '是否下架',
										width : '15%',
										align : 'center',
										formatter : function(value, row, index) {
											return value == 1 ? "是" : "否";
										}
									},
									{
										field : 'xx',
										title : '操作',
										width : '15%',
										align : 'center',
										formatter : function(value, row, index) {
											return "<a href=javascript:editProduct('"
													+ row.pid
													+ "')>编辑</a> | <a href=javascript:delProduct('"
													+ row.pid + "')>删除</a>";
										}
									} ] ],
							toolbar : [ {
								iconCls : 'icon-add',
								handler : function() {

									$('#win').window('open'); // open a window   

								}
							} ],
							pagination : true,
							pageNumber : '1',
							pageList : [ 5, 10, 15, 20 ]
						});

		//获取所有分类
		var url = "http://localhost:8080/shopping_project/ProductServlet";
		var parame = {
			"method" : "category"
		};
		$.post(url, parame, function(data) {
			var cg = $("#category");
			$(data).each(
					function(index, en) {

						cg.append("<option value="+en.cid+">" + en.cname
								+ "</option>");
					});

		}, "json");

	});
	function addProduct() {

		//表单提交
		$('#saveProduct')
				.form(
						'submit',
						{
							url : "http://localhost:8080/shopping_project/productManagerServlet?method=addProduct",
							success : function(data) {
								var data = eval('(' + data + ')'); // change the JSON string to javascript object   
								$.messager.show({
									title : '消息',
									msg : data.msg + "消息将在3秒后关闭。",
									timeout : 3000,
									showType : 'slide'
								});
								$('#win').window('close');
							}
						});

	}
	//编辑
	function editProduct(pid) {

	
	$('#editWin').window('open');
		//获取所有分类
		var url = "http://localhost:8080/shopping_project/ProductServlet";
		var parame = {
			"method" : "category"
		};
		$.post(url, parame, function(data) {
			var cg = $("#Editcategory");
			$(data).each(
					function(index, en) {

						cg.append("<option value="+en.cid+">" + en.cname
								+ "</option>");
					});

		}, "json");

		$('#editSaveProduct')
				.form(
						'load',
						'http://localhost:8080/shopping_project/productManagerServlet?method=finPWithPid&&pid='
								+ pid);

	}

	function updateProduct() {

		$('#editSaveProduct')
				.form(
						'submit',
						{
							url : "http://localhost:8080/shopping_project/productManagerServlet?method=updateProduct",
							success : function(data) {
								var data = eval('(' + data + ')'); // change the JSON string to javascript object   
								$.messager.show({
									title : '消息',
									msg : data.msg + "消息将在3秒后关闭。",
									timeout : 3000,
									showType : 'slide'
								});
								$('#editWin').window('close');
								$('#dg').datagrid('reload'); 
							}
						});
	}
	//删除
	function delProduct(pid) {
		if (confirm("确定要删除该商品吗?")) {

			var url = "http://localhost:8080/shopping_project/productManagerServlet";
			var parame = {
				"method" : "delProduct",
				"pid" : pid
			};
			$.post(url, parame, function(data) {
				$.messager.show({
					title : '我的消息',
					msg : data.msg + '消息将在3秒后关闭。',
					timeout : 3000,
					showType : 'slide'
				});

				$('#dg').datagrid('reload');

			}, "json");

		}
	}
</script>
</head>
<body>
  <table id="dg"></table>
  <div id="win" class="easyui-window" title="添加商品" style="width: 600px; height: 280px"
    data-options="iconCls:'icon-save',closed:true, modal:true">
    <form id="saveProduct" method="post" enctype="multipart/form-data">
      <table cellSpacing="1" cellPadding="5" width="100%" align="center"
        bgColor="#eeeeee" style="border: 1px solid #8ba7e3" border="0">
        <tr>
          <td width="18%" align="center" bgColor="#f5fafe" class="ta_01">商品名称：</td>
          <td class="ta_01" bgColor="#ffffff">
            <input type="text" name="pname" value="" id="userAction_save_do_logonName"
              class="bg" />
          </td>
          <td width="18%" align="center" bgColor="#f5fafe" class="ta_01">是否热门：</td>
          <td class="ta_01" bgColor="#ffffff">
            <select name="is_hot">
              <option value="1">是</option>
              <option value="0">否</option>
            </select>
          </td>
        </tr>
        <tr>
          <td width="18%" align="center" bgColor="#f5fafe" class="ta_01">市场价格：</td>
          <td class="ta_01" bgColor="#ffffff">
            <input type="text" name="market_price" value=""
              id="userAction_save_do_logonName" class="bg" />
          </td>
          <td width="18%" align="center" bgColor="#f5fafe" class="ta_01">商城价格：</td>
          <td class="ta_01" bgColor="#ffffff">
            <input type="text" name="shop_price" value=""
              id="userAction_save_do_logonName" class="bg" />
          </td>
        </tr>
        <tr>
          <td width="18%" align="center" bgColor="#f5fafe" class="ta_01">商品图片：</td>
          <td class="ta_01" bgColor="#ffffff" colspan="3">
            <input type="file" name="pimage" />
          </td>
        </tr>
        <tr>
          <td width="18%" align="center" bgColor="#f5fafe" class="ta_01">所属的分类：</td>
          <td class="ta_01" bgColor="#ffffff" colspan="3">
            <select id="category" name="cid">
              <option value='-1'>--请选择--</option>
            </select>
          </td>
        </tr>
        <tr>
          <td width="18%" align="center" bgColor="#f5fafe" class="ta_01">商品描述：</td>
          <td class="ta_01" bgColor="#ffffff" colspan="3">
            <textarea name="pdesc" rows="5" cols="30"></textarea>
          </td>
        </tr>
        <tr>
          <td class="ta_01" style="WIDTH: 100%" align="center" bgColor="#f5fafe"
            colSpan="4">
            <button type="button" value="确定" onclick="addProduct()" class="button_ok">
              &#30830;&#23450;</button>
            <FONT face="宋体">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</FONT>
            <button type="reset" value="重置" class="button_cancel">&#37325;&#32622;</button>
          </td>
        </tr>
      </table>
    </form>
  </div>
  
  
  <div id="editWin" class="easyui-window" title="编辑商品"
    style="width: 600px; height: 280px"
    data-options="iconCls:'icon-save',closed:true, modal:true">
    <form id="editSaveProduct" method="post" enctype="multipart/form-data">
      <input type="hidden" name="pid" value="" />
      <input type="hidden" name="pdate" value="" />
      <input type="hidden" name="is_hot" value="" />
      <input type="hidden" name="cid" value="" />
      <table cellSpacing="1" cellPadding="5" width="100%" align="center"
        bgColor="#eeeeee" style="border: 1px solid #8ba7e3" border="0">
        <tr>
          <td width="18%" align="center" bgColor="#f5fafe" class="ta_01">商品名称：</td>
          <td class="ta_01" bgColor="#ffffff">
            <input type="text" name="pname" value="" 
              class="bg" />
          </td>
          <td width="18%" align="center" bgColor="#f5fafe" class="ta_01">是否热门：</td>
          <td class="ta_01" bgColor="#ffffff">
            <select name="is_hot">
              <option value="1">是</option>
              <option value="0">否</option>
            </select>
          </td>
        </tr>
        <tr>
          <td width="18%" align="center" bgColor="#f5fafe" class="ta_01">市场价格：</td>
          <td class="ta_01" bgColor="#ffffff">
            <input type="text" name="market_price" value=""
               class="bg" />
          </td>
          <td width="18%" align="center" bgColor="#f5fafe" class="ta_01">商城价格：</td>
          <td class="ta_01" bgColor="#ffffff">
            <input type="text" name="shop_price" value=""
               class="bg" />
          </td>
        </tr>
        <tr>
          <td width="18%" align="center" bgColor="#f5fafe" class="ta_01">商品图片：</td>
          <td class="ta_01" bgColor="#ffffff" colspan="3">
            <input type="file" name="pimage" />
          </td>
        </tr>
        <tr>
          <td width="18%" align="center" bgColor="#f5fafe" class="ta_01">所属的分类：</td>
          <td class="ta_01" bgColor="#ffffff" colspan="3">
            <select id="Editcategory" name="cid">
              <option value='-1'>--请选择--</option>
            </select>
          </td>
        </tr>
        <tr>
          <td width="18%" align="center" bgColor="#f5fafe" class="ta_01">商品描述：</td>
          <td class="ta_01" bgColor="#ffffff" colspan="3">
            <textarea name="pdesc" rows="5" cols="30"></textarea>
          </td>
        </tr>
        <tr>
          <td class="ta_01" style="WIDTH: 100%" align="center" bgColor="#f5fafe"
            colSpan="4">
            <button type="button" value="确定" onclick="updateProduct()" class="button_ok">
              &#30830;&#23450;</button>
            <FONT face="宋体">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</FONT>
            <button type="reset" value="重置" class="button_cancel">&#37325;&#32622;</button>
          </td>
        </tr>
      </table>
    </form>
  </div>
</body>
</html>