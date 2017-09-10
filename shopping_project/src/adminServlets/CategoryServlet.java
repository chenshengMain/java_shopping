package adminServlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Category;
import domain.Message;
import net.sf.json.JSONObject;
import services.CS_service;
import services.impl.ServiceImpl;
import servlets.BaseServlet;
import toolUtils.UUIDUtils;

@SuppressWarnings("all")
public class CategoryServlet extends BaseServlet {

	public void addCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("访问服务器成功..addCategory...");
		String cname = request.getParameter("newCname");
		Category cg = new Category();
		cg.setCid(UUIDUtils.getUUID());
		cg.setCname(cname);
		ServiceImpl si = new CS_service();
		boolean issuccess = si.addCategory(cg);
		Message msg = new Message();
		if (issuccess) {
			msg.setMsg("添加成功");

		} else {
			msg.setMsg("添加失败");

		}

		String str = JSONObject.fromObject(msg).toString();
		System.out.println("*****addCategory****回调数据" + str);
		response.getWriter().println(str);

	}

	public void delCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("访问服务器成功..delCategory...");
		String cid = request.getParameter("cid");

		ServiceImpl si = new CS_service();
		boolean issuccess = si.delCategory(cid);
		Message msg = new Message();
		if (issuccess) {
			msg.setMsg("删除成功");

		} else {
			msg.setMsg("删除失败");

		}

		String str = JSONObject.fromObject(msg).toString();

		response.getWriter().println(str);

	}

	public void findCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("访问服务器成功..findCategory...");
		String cid = request.getParameter("cid");

		ServiceImpl si = new CS_service();
		String resultstr = si.findCategory(cid);

		response.getWriter().println(resultstr);

	}

	public void updateCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("访问服务器成功..updateCategory...");

		String cid = request.getParameter("cid");
		String cname = request.getParameter("cname");

		ServiceImpl si = new CS_service();
		boolean issuccess = si.updateCategory(cname,cid);

		Message msg = new Message();
		if (issuccess) {
			msg.setMsg("修改成功");

		} else {
			msg.setMsg("修改失败");

		}
		String str = JSONObject.fromObject(msg).toString();

		response.getWriter().println(str);

	}

}
