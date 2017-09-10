package servlets;

import java.io.IOException;

import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("all")
public class BaseServlet extends HttpServlet {

	@Override
	@SuppressWarnings(value = { "all" })
	protected void service(HttpServletRequest reg, HttpServletResponse resp) throws ServletException, IOException {
		// super.service(reg,resp);
		System.out.println("修改日志!..父类控制器......");
		reg.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		String methodstr = reg.getParameter("method");
		Class clas = this.getClass();
		try {
			Method me = clas.getMethod(methodstr, HttpServletRequest.class, HttpServletResponse.class);
			String returnValue = (String) me.invoke(this, reg, resp);
			if (returnValue != null) {

				reg.getRequestDispatcher(returnValue).forward(reg, resp);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
