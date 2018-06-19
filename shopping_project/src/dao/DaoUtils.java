package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import domain.Category;
import domain.Order;
import domain.OrderItem;
import domain.Product;
import domain.User;
import net.sf.json.JSONObject;
import dao.impl.DaoImpl;
import toolUtils.C3P0Utils;
import toolUtils.TableUtils;

public class DaoUtils implements DaoImpl {
  ///这是在测试哈哈哈哈哈哈哈
	//这是 dev1 修改的哈哈
//	
//	public static void main(String[] args) {
//		
//		Product p = new Product();
//	    Object obj = new java.sql.Date(System.currentTimeMillis());
//	    Date date = (Date)obj;
//	     p.setCid("1");
//	     p.setPdate(date);
//	     p.setIs_hot("2");
//	     p.setMarket_price("22");
//	     p.setPdesc("sss");
//	     p.setPflag("1");
//	     p.setPname("sb");
//	     p.setPimage("5555");
//	     p.setPid("1");
//	     p.setShop_price("2222");
//	    System.out.println(JSONObject.fromObject(p).toString());
//	
//		
//	}
	
	

	@Override
	public boolean addUser(User u) throws SQLException {

		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "insert into " + TableUtils.USER + "values(?,?,?,?,?,?,?,?,0,?)";
		int state = qr.update(sql, u.getUid(), u.getUsername(), u.getPassword(), u.getName(), u.getEmail(), null,
				u.getBirthday(), u.getSex(), u.getCode());
		return state != 0;
	}

	@Override
	public List<User> findAll() throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from " + TableUtils.USER;
		List<User> list = qr.query(sql, new BeanListHandler<User>(User.class));

		return list;
	}

	@Override
	public User findBody(User u) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from " + TableUtils.USER + "where username = ? and  password = ?";
		User result = qr.query(sql, new BeanHandler<User>(User.class), u.getUsername(), u.getPassword());
		System.out.println(result + "********结果");

		return result;
	}

	@Override
	public List<Category> findCategory() throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from " + TableUtils.CATEGORY;
		List<Category> list = qr.query(sql, new BeanListHandler<Category>(Category.class));

		return list;
	}

	@Override
	public List<Product> findAllByCategoryId(String cid) throws SQLException {

		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from " + TableUtils.PRODUCT + "where cid = ?";
		List<Product> list = qr.query(sql, new BeanListHandler<Product>(Product.class), cid);

		return list;
	}

	@Override
	public List<Product> findPorductByLimit(String cid, int currentP, int pageSize) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from  " + TableUtils.PRODUCT + "where 1 = 1 ";
		List<Object> paramerlist = new ArrayList<>();
		if (cid != null) {
			sql += "and cid = ?";
			paramerlist.add(cid);
			
		}
          sql += " limit ?,?";
          
          paramerlist.add((currentP - 1) * pageSize);
          paramerlist.add(pageSize);
		List<Product> list = qr.query(sql, new BeanListHandler<Product>(Product.class), paramerlist.toArray());
		return list;
	}

	@Override
	public List<Product> findHotProducts() throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from " + TableUtils.PRODUCT + "where is_hot=1 and pflag= 0 limit 0,9 ";

		List<Product> list = qr.query(sql, new BeanListHandler<Product>(Product.class));

		return list;
	}

	@Override
	public List<Product> findNewProducts() throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from " + TableUtils.PRODUCT + "where   pflag= 0  limit 0,9 ";
		List<Product> list = qr.query(sql, new BeanListHandler<Product>(Product.class));
		return list;
	}

	@Override
	public Product findProductByPid(String pid) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from" + TableUtils.PRODUCT + "where pid = ?";
		Product product = qr.query(sql, new BeanHandler<Product>(Product.class), pid);

		return product;

	}

	@Override
	public void changeCodeState(String code) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = " update " + TableUtils.USER + " set state = '1' where code = ?";
		qr.update(sql, code);
	}

	@Override
	public boolean createOrder(Order od) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "insert into" + TableUtils.ORDERS + "values(?,?,?,0,null,null,null,?)";
		int num = qr.update(sql, od.getOid(), od.getOrdertime(), od.getTotal(), od.getUser().getUid());
		return num != 0;
	}

	@Override
	public boolean createOrderItems(List<OrderItem> listItem) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "insert into" + TableUtils.ORDERITEM + "values(?,?,?,?,?)";
		int count = 0;
		for (OrderItem item : listItem) {
			System.out.println("daoUtils:" + item.toString());
			int num = qr.update(sql, item.getItemid(), item.getCount(), item.getSubtotal(), item.getP().getPid(),
					item.getOid());
			count += num;
		}

		return count == listItem.size();

	}

	@Override
	public List<Order> findOrdersByUid(String uid, int currentPage, int pageSize) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from " + TableUtils.ORDERS + "where uid = ? limit ?,?";
		List<Order> list = qr.query(sql, new BeanListHandler<Order>(Order.class), uid, (currentPage - 1) * pageSize,
				pageSize);

		for (Order order : list) {
			ArrayList<OrderItem> orderItemList = new ArrayList<>();
			String itesSql = "select * from " + TableUtils.ORDERITEM + "where oid = ?";
			List<OrderItem> otList = qr.query(itesSql, new BeanListHandler<OrderItem>(OrderItem.class), order.getOid());
			for (OrderItem orderItem : otList) {

				Product p = new Product();
				String subsql = "select p.pimage,p.pname,p.shop_price,o.count from" + TableUtils.ORDERITEM + "as o,"
						+ TableUtils.PRODUCT + " as p where  o.oid = ? and p.pid = ?";
				List<Map<String, Object>> ListM = qr.query(subsql, new MapListHandler(), orderItem.getOid(),
						orderItem.getPid());
				for (Map<String, Object> map : ListM) {
					BeanUtils.populate(p, map);
				}
				orderItem.setP(p);

				orderItemList.add(orderItem);

			}

			order.setListItem(orderItemList);
		}
		return list;
	}

	@Override
	public int findAllOrders(String uid) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select count(*) from " + TableUtils.ORDERS + "where uid = ?";
		long count = (long) qr.query(sql, new ScalarHandler(), uid);
		return (int) count;
	}

	@Override
	public Order findOrderInfo(String oid) throws Exception {

		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from" + TableUtils.ORDERS + "where oid = ?";
		Order od = qr.query(sql, new BeanHandler<Order>(Order.class), oid);
		ArrayList<OrderItem> orderItemList = new ArrayList<>();
		String itesSql = "select * from " + TableUtils.ORDERITEM + "where oid = ?";
		List<OrderItem> otList = qr.query(itesSql, new BeanListHandler<OrderItem>(OrderItem.class), oid);
		for (OrderItem orderItem : otList) {

			Product p = new Product();
			String subsql = "select p.pimage,p.pname,p.shop_price,o.count from" + TableUtils.ORDERITEM + "as o,"
					+ TableUtils.PRODUCT + " as p where  o.oid = ? and p.pid = ?";
			List<Map<String, Object>> ListM = qr.query(subsql, new MapListHandler(), orderItem.getOid(),
					orderItem.getPid());
			for (Map<String, Object> map : ListM) {
				BeanUtils.populate(p, map);
			}
			orderItem.setP(p);
			orderItemList.add(orderItem);

		}
		od.setListItem(otList);

		return od;
	}

	@Override
	public boolean updateOrderState(String r6_Order) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "update " + TableUtils.ORDERS + " set state = '1' where oid = ?";
		int num = qr.update(sql, r6_Order);

		return num != 0;
	}

	@Override
	public boolean addCategory(Category cg) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "insert into " + TableUtils.CATEGORY + "values(?,?)";
		int num = qr.update(sql, cg.getCid(), cg.getCname());
		return num != 0;

	}

	@Override
	public boolean delCategory(String cid) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "delete from " + TableUtils.CATEGORY + "where cid = ?";
		int num = qr.update(sql, cid);
		return num != 0;
	}

	@Override
	public Category findCategory(String cid) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from" + TableUtils.CATEGORY + "where cid = ?";
		Category cg = qr.query(sql, new BeanHandler<Category>(Category.class), cid);

		return cg;
	}

	@Override
	public boolean updateCategory(String cname, String cid) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "update" + TableUtils.CATEGORY + "set cname = ? where cid = ?";
		int num = qr.update(sql, cname, cid);

		return num != 0;
	}

	@Override
	public List<Product> findAllProducts(String cid) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from " + TableUtils.PRODUCT + "where 1=1";
		List<Object> lis = new ArrayList<>();
		if(cid != null){
			sql += " and cid = ?";
			lis.add(cid);
		}
		List<Product> list = qr.query(sql, new BeanListHandler<Product>(Product.class),lis.toArray());
		return list;
	}

	@Override
	public boolean addProduct(Product p) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "insert into "+TableUtils.PRODUCT + "values(?,?,?,?,?,?,?,?,0,?)";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		int num = qr.update(sql , p.getPid(),p.getPname(),p.getMarket_price(),p.getShop_price(),p.getPimage(),df.format(p.getPdate()),p.getIs_hot(),p.getPdesc(),p.getCid());
		
		
		return num != 0;
	}

	@Override
	public boolean delProduct(String pid) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());  
		int num = qr.update("delete from" + TableUtils.PRODUCT + "where pid =? " , pid);
		
		return num != 0;
	}

	@Override
	public boolean updateProduct(Product p) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource()); 
		String sql = "update " + TableUtils.PRODUCT + "set pname = ? ,shop_price = ?,pimage=?,pflag = ? where pid = ?";
		int num = qr.update(sql,p.getPname(),p.getShop_price(),p.getPimage(),p.getPflag(),p.getPid());
		
		return num != 0;
	}

}
