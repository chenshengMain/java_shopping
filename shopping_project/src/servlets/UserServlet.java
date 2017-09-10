package servlets;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.DateConverter;

import cn.dsna.util.images.ValidateCode;
import domain.User;
import jdk.nashorn.internal.ir.RuntimeNode.Request;
import services.CS_service;
import services.impl.ServiceImpl;
import toolUtils.UUIDUtils;

@SuppressWarnings("all")
public class UserServlet extends BaseServlet {

	// 注册
	public String register(HttpServletRequest reg, HttpServletResponse resp) throws ServletException, IOException {

		System.out.println("Servlet 进来了........");
		User user = new User();
		Map<String, String[]> map = reg.getParameterMap();
		try {
			BeanUtils.populate(user, map);
			user.setUid(UUIDUtils.getUUID());
			user.setCode(UUIDUtils.getUUID());
			// DateConverter dc = new DateConverter();
			// dc.setPatterns(new String[]{"yyyy-MM-dd","yyyy/MM/dd"});

			System.out.println("用户信息" + user);
			ServiceImpl simpl = new CS_service();
			boolean isSucess = simpl.addUser(user);
			if (!isSucess) {
				reg.setAttribute("msg", "该用户已注册!");
				return "/user/register.jsp";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "/user/login.jsp";
	}

	// 验证码 imgCode
	public void imgCode(HttpServletRequest reg, HttpServletResponse resp) throws ServletException, IOException {

		ValidateCode vcode = new ValidateCode(135, 38, 4, 15);
		String code = vcode.getCode();
		reg.getSession().setAttribute("imgcode", code);
		vcode.write(resp.getOutputStream());
	}

	// 登录
	public String login(HttpServletRequest reg, HttpServletResponse resp) throws ServletException, IOException {
		// 获取验证码
		String regImgcode = reg.getParameter("imgcode");
		String imcode = (String) reg.getSession().getAttribute("imgcode");
		if (regImgcode == null | !regImgcode.equalsIgnoreCase(imcode)) {

			reg.setAttribute("msg", "验证码错误");

			return "user/login.jsp";

		}
		User u = new User();
		Map<String, String[]> map = reg.getParameterMap();
		try {
			BeanUtils.populate(u, map);
			ServiceImpl ser = new CS_service();
			User findBody = ser.findBody(u);
			if (findBody == null) {

				reg.setAttribute("msg", "用户名或密码错误");

				return "user/login.jsp";
			}

			// 未激活激活码

			if (findBody.getState() == 0) {
				reg.setAttribute("msg", "激活码未激活");

				return "user/login.jsp";
			}
			
			String autoLogin = reg.getParameter("autoLogin");
			Cookie cok  = new Cookie("autologin", findBody.getName() +"-"+findBody.getPassword());
			if(autoLogin == null){
				cok.setMaxAge(0);
			}else{
				cok.setMaxAge(Integer.MAX_VALUE);
			}
			cok.setPath(reg.getContextPath());
			resp.addCookie(cok);
			reg.getSession().setAttribute("user", findBody);
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/index.jsp";

	}
	
	
	//激活码
	public String active(HttpServletRequest reg, HttpServletResponse resp) throws ServletException, IOException {
		String code = reg.getParameter("code");
		ServiceImpl si = new CS_service();
		si.changeCodeState(code);
		System.out.println("激活码:**********"+code);
	
	  return "/user/login.jsp";
	}
	//注销
	public String logout(HttpServletRequest reg, HttpServletResponse resp) throws ServletException, IOException {
		
		reg.getSession().removeAttribute("user");
		Cookie cookie = new Cookie("autoLogin", "");
		cookie.setPath(reg.getContextPath()); //  /products
		cookie.setMaxAge(0);
		resp.addCookie(cookie);
		
		return "/user/login.jsp";
	}
	

}
