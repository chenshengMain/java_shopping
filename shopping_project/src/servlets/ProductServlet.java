package servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.ForEach;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.InterningXmlVisitor;

import domain.PageBean;
import domain.Product;
import services.CS_service;
import services.impl.ServiceImpl;

@SuppressWarnings("all")
public class ProductServlet extends BaseServlet {

	// 分类
	public void category(HttpServletRequest reg, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("访问服务器category......成功......");
		ServiceImpl sp = new CS_service();
		String ClistStr = sp.findCategory();
		resp.getWriter().println(ClistStr);
	}

	// 分类商品 及分页请求
	public String productsList(HttpServletRequest reg, HttpServletResponse resp) throws ServletException, IOException {
	
		String cid = reg.getParameter("cid");
		String pageIndex = reg.getParameter("pageIndex");
		int pageSize = 5;
		int currentP = 1;
		if (pageIndex != null) {
			currentP = Integer.parseInt(pageIndex);
		}
		System.out.println("cid" + cid);
		ServiceImpl sp = new CS_service();
		PageBean<Product> pb = sp.finProductByPage(currentP,pageSize, cid);
		reg.setAttribute("pageBean", pb);
		reg.setAttribute("cid", cid);
		System.out.println("分页*******" + pb.getPageList().size());
		return "/user/product_list.jsp";
	}

	// 热门 新上架 findHotAndNewProducts
	public String findHotAndNewProducts(HttpServletRequest reg, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("访问服务器成功......findHotAndNewProducts");

		ServiceImpl si = new CS_service();
		List<Product> hotList = si.findHotProducts();
		List<Product> newList = si.findNewProdicts();
		reg.setAttribute("hotList", hotList);
		reg.setAttribute("newList", newList);
		System.out.println(hotList + "************" + newList);
		return "/user/index.jsp";
	}

	// 商品详情showProductInfo
	public String showProductInfo(HttpServletRequest reg, HttpServletResponse resp)
			throws ServletException, IOException {
		String pid = reg.getParameter("pid");
		ServiceImpl sp = new CS_service();
		Product p = sp.findProductByPid(pid);
		reg.setAttribute("p", p);
		// deal with the data processing
		String historyId = organizeHistoryData(pid, reg);
		Cookie cookie = new Cookie("historyId", historyId);
		cookie.setPath("/");
		cookie.setMaxAge(Integer.MAX_VALUE);
		resp.addCookie(cookie);
		System.out.println("浏览记录 cook*****"+historyId + "pid"+pid);
		return "/user/product_info.jsp";

	}

	private String organizeHistoryData(String pid, HttpServletRequest reg) {

		Cookie[] cookies = reg.getCookies();
		StringBuffer bufStrId = new StringBuffer();
		if (cookies == null) {

			return pid;
		}

		for (Cookie cookie : cookies) {
		//	System.out.println(cookie.getName()+"---------" + cookie.getValue());

			if ("historyId".equals(cookie.getName())) {

				bufStrId.append(cookie.getValue());
				break;
			}
		}

		if (bufStrId.length() == 0) {

			return pid;
		}
		String[] ids = bufStrId.toString().split("-");
		System.out.println(Arrays.toString(ids));
		LinkedList<String> idList =  new LinkedList<>(Arrays.asList(ids));
		
		if (idList.size() < 3) {

			if (idList.contains(pid)) {

				idList.remove(pid);
			}
		} else {

			if (idList.contains(pid)) {

				idList.remove(pid);

			} else {

				((LinkedList<String>) idList).removeLast();

			}

		}

		idList.addFirst(pid);
       StringBuffer historyId = new StringBuffer();
		for (int i = 0; i < idList.size(); i++) {
		
			if(i > 0){
				historyId.append("-");
			}
			historyId.append(idList.get(i));
		}

		 
		return historyId.toString();
	}
}


/*
 * 
 * */

