package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.org.apache.regexp.internal.REUtil;

import domain.Order;
import domain.PageBean;
import domain.User;
import services.CS_service;
import services.impl.ServiceImpl;

/**
 * Servlet implementation class OrderServlet
 */
@SuppressWarnings("all")
public class OrderServlet extends BaseServlet {
	

	// 查询有用户的订单 showMyOrders
	public String showMyOrders(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int currentPage = 1;
		int pageSize = 3;
		String pageIndex =  request.getParameter("pageIndex");
		if(pageIndex != null){
			
			currentPage = Integer.parseInt(pageIndex);
		}
		User u = (User) request.getSession().getAttribute("user");
		ServiceImpl si = new CS_service();
		PageBean<Order> page = si.findOrdersByUid(u.getUid(),currentPage,pageSize);
		
		request.setAttribute("page", page);

		return "/user/order_list.jsp";

	}
	//订单详情 showOrderInfo
	
	public String showOrderInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String oid = request.getParameter("oid");
		ServiceImpl si = new CS_service();
	    Order od=  si.findOrderInfo(oid);
	    request.setAttribute("od", od);
		
		return "/user/order_info.jsp";
		
		
	}
	

}
