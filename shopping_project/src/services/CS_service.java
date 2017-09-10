package services;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.text.DateFormatter;

import org.apache.commons.dbutils.DbUtils;
import org.junit.Test;

import dao.DaoUtils;
import dao.impl.DaoImpl;
import domain.Category;
import domain.Order;
import domain.PageBean;
import domain.Product;
import domain.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import services.impl.ServiceImpl;
import toolUtils.SendJMail;
import toolUtils.SendMailUtils;

public class CS_service implements ServiceImpl {

	@Override
	public boolean addUser(User u) {
		// TODO Auto-generated method stub
		DaoImpl dm = new DaoUtils();
		try {
			User user = dm.findBody(u);
			if (user == null) {

				// SendMailUtils.sendMail("store.com", "<a
				// href='https://192.168.109.71:8080/user/login.jsp'>用户激活</a>");

				boolean isSuccess = dm.addUser(u);
				String emailMsg = "<a href='http://localhost:8080/shopping_project/UserServlet?method=active&code='>马上激活</a>";
				SendJMail.sendMail("2572157262@qq.com", emailMsg);
				return isSuccess;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public User findBody(User u) {
		DaoImpl dm = new DaoUtils();
		User user = null;
		try {
			user = dm.findBody(u);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public String findCategory() {
		String jsonstr = null;
		try {
			DaoImpl di = new DaoUtils();
			List<Category> allC = di.findCategory();
			jsonstr = JSONArray.fromObject(allC).toString();

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return jsonstr;
	}

	@Override
	public PageBean finProductByPage(int currentP, int pageSize, String cid) {
		PageBean<Product> pb = null;
		try {
			pb = new PageBean<>();
			DaoImpl di = new DaoUtils();
			int count = (int) (long) di.findAllProducts(cid).size();
			pb.setCount(count);
			pb.setPageIndex(currentP);
			int totalPage = (int) Math.ceil(count * 1.0 / pageSize);
			pb.setTotalPage(totalPage);
			List<Product> pageList = di.findPorductByLimit(cid, currentP, 5);
			pb.setPageList(pageList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pb;
	}

	@Override
	public List<Product> findHotProducts() {

		DaoImpl di = new DaoUtils();
		List<Product> list = null;
		try {
			list = di.findHotProducts();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public List<Product> findNewProdicts() {
		DaoImpl di = new DaoUtils();
		List<Product> list = null;
		try {
			list = di.findNewProducts();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

	@Override
	public Product findProductByPid(String pid) {
		DaoImpl di = new DaoUtils();
		Product p = null;
		try {
			p = di.findProductByPid(pid);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return p;
	}

	@Override
	public void changeCodeState(String code) {
		DaoImpl di = new DaoUtils();
		try {
			di.changeCodeState(code);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean createOrder(Order od) {
		DaoImpl di = new DaoUtils();
		try {
			boolean hasOrder = di.createOrder(od);
			boolean hasOrderitem = di.createOrderItems(od.getListItem());
			return hasOrder == true && hasOrderitem == true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public PageBean<Order> findOrdersByUid(String uid, int currentPage, int pageSize) {

		DaoImpl di = new DaoUtils();
		PageBean<Order> page = new PageBean<>();
		int count;
		try {
			count = di.findAllOrders(uid);
			List<Order> list = di.findOrdersByUid(uid, currentPage, pageSize);
			page.setCount(count);
			page.setPageIndex(currentPage);
			int total = (int) Math.ceil(count * 1.0 / pageSize);
			page.setTotalPage(total);
			page.setPageList(list);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return page;
	}

	@Override
	public Order findOrderInfo(String oid) {
		DaoImpl di = new DaoUtils();
		Order od = null;
		try {
			od = di.findOrderInfo(oid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return od;
	}

	@Override
	public boolean updateOrderState(String r6_Order) {
		DaoImpl di = new DaoUtils();
		try {
			return di.updateOrderState(r6_Order);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean addCategory(Category cg) {

		DaoImpl di = new DaoUtils();
		try {
			return di.addCategory(cg);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	@Override
	public boolean delCategory(String cid) {
		DaoImpl di = new DaoUtils();
		try {
			return di.delCategory(cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public String findCategory(String cid) {
		DaoImpl di = new DaoUtils();
		try {
			Category cg = di.findCategory(cid);
			return JSONObject.fromObject(cg).toString();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean updateCategory(String cname, String cid) {

		DaoImpl di = new DaoUtils();
		try {

			return di.updateCategory(cname, cid);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean addProduct(Product p) {

		DaoImpl di = new DaoUtils();
		try {
			return di.addProduct(p);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean delProduct(String pid) {

		DaoImpl di = new DaoUtils();
		try {
			return di.delProduct(pid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean updateProduct(Product p) {
       DaoImpl di = new DaoUtils();
     try {
		return di.updateProduct(p);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
		return false;
	}

}
