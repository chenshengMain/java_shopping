package toolUtils;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import domain.User;
import services.CS_service;
import services.impl.ServiceImpl;

/**
 * Servlet Filter implementation class AutoLoginFilter
 */
public class AutoLoginFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public AutoLoginFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		User user = (User) req.getSession().getAttribute("user");
		if (user == null) {

			Cookie[] cookies = req.getCookies();
			ServiceImpl ser = new CS_service();
			User u = new User();
			String name = "";
			String pwd = "";
			for (Cookie cookie : cookies) {

				if ("autologin".equals(cookie.getName())) {
					name = cookie.getValue().split("-")[0];
					pwd = cookie.getValue().split("-")[1];
					break;
				}
			}
			u.setUsername(name);
			u.setPassword(pwd);
			User findBody = ser.findBody(u);
		    req.getSession().setAttribute("user", findBody);
		    System.out.println("自动过滤器进来了....."+ findBody.toString());
		}

		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
