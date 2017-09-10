package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.jasper.tagplugins.jstl.core.ForEach;

import domain.Order;
import domain.OrderItem;
import domain.Product;
import domain.User;
import services.CS_service;
import services.impl.ServiceImpl;
import toolUtils.UUIDUtils;

@SuppressWarnings("all")
public class CartServlet extends BaseServlet {

	public String addProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("访问购物车服务器成功.....");
		Integer num = Integer.parseInt(request.getParameter("num"));
		String pid = request.getParameter("pid");
		HttpSession session = request.getSession();
		ServiceImpl si = new CS_service();
		Product p = si.findProductByPid(pid);
		Map<Product, Integer> map = (Map<Product, Integer>) session.getAttribute("cart");
		if (map == null) {
			map = new HashMap<>();
		}
		if (map.containsKey(p)) {
			int count = map.get(p);
			num += count;
		}

		map.put(p, num);
		session.setAttribute("cart", map);

		return "user/cart.jsp";

	}
    //手动输入数量 needProduct
	public String needProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer num = Integer.parseInt(request.getParameter("num"));
		System.out.println("num*******"+num);
		String pid = request.getParameter("pid");
		HttpSession session = request.getSession();
		ServiceImpl si = new CS_service();
		Product p = si.findProductByPid(pid);
		Map<Product, Integer> map = (Map<Product, Integer>) session.getAttribute("cart");
		if (map == null) {
			map = new HashMap<>();
		}
		map.put(p, num);
		session.setAttribute("cart", map);
		return "user/cart.jsp";
	}
	
	// 删除某个商品 pid
	public String deleteProductByPid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		Map<Product, Integer> map = (Map<Product, Integer>) request.getSession().getAttribute("cart");
		String pid = request.getParameter("pid");
		ServiceImpl si = new CS_service();
		Product p = si.findProductByPid(pid);
		map.remove(p);
		request.getSession().setAttribute("cart", map);
		return "user/cart.jsp";
	}

	// 移除所有商品 removeAll
	public String removeAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().removeAttribute("cart");

		return "user/cart.jsp";
	}
   //生成订单  createOrder
	public String createOrder(HttpServletRequest request, HttpServletResponse response)
		 	throws ServletException, IOException {
//		Enumeration<String> list = request.getSession().getAttributeNames();
//		while (list.hasMoreElements()) {
//			String string = (String) list.nextElement();
//			System.out.println("****************"+string);
//			
//		}
		User user = (User) request.getSession().getAttribute("user");
		//么有登录去登录
	     if(user == null){
	    	 
	    	 return "user/login.jsp";
	    	 
	     }
	     Map<Product, Integer> map = (Map<Product, Integer>) request.getSession().getAttribute("cart");
		Order od = new Order();
		od.setOid(UUIDUtils.getUUID());
		od.setOrdertime(new Date().toString());
		List<OrderItem> list = new ArrayList<>();
		double total = 0;
		for (Entry<Product, Integer> en : map.entrySet()) {
			OrderItem oi = new OrderItem();
			oi.setItemid(UUIDUtils.getUUID());
			oi.setCount(en.getValue());
			double subtotal = en.getValue() * Double.parseDouble(en.getKey().getShop_price()) ;
			oi.setSubtotal(subtotal);
			oi.setOid(od.getOid());
			oi.setP(en.getKey());
			list.add(oi);
			total+= subtotal;
			
		}
		od.setTotal(String.valueOf(total));
		od.setListItem(list);
		od.setUser(user);
		System.out.println("***********"+od.toString());
		ServiceImpl si = new CS_service();
		boolean success = si.createOrder(od);
		request.setAttribute("od", od);
		//处理提交失败的情况
//		if(!success){
//			request.setAttribute("msg",	 "提交订单失败");
//			return "toolUtils/errorMeg.jsp";
//		}
		
		return "user/order_info.jsp";
	}
}
