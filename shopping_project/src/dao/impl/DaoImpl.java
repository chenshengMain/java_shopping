package dao.impl;

import java.sql.SQLException;
import java.util.List;

import domain.Category;
import domain.Order;
import domain.OrderItem;
import domain.Product;
import domain.User;
import net.sf.json.JSONArray;

public interface DaoImpl {
	
	List<Product> findHotProducts() throws SQLException;

	List<Product> findNewProducts() throws SQLException;

	List<User> findAll() throws SQLException;

	boolean addUser(User u) throws SQLException;

	User findBody(User u) throws SQLException;

	List<Category> findCategory() throws SQLException;

	List<Product> findAllByCategoryId(String cid) throws SQLException;

	List<Product> findPorductByLimit(String cid, int currentP, int i) throws SQLException;

	Product findProductByPid(String pid) throws SQLException;

	void changeCodeState(String code) throws SQLException;

	boolean createOrder(Order od) throws SQLException;

	boolean createOrderItems(List<OrderItem> listItem)throws SQLException;

	List<Order> findOrdersByUid(String uid, int currentPage, int pageSize)throws Exception;

	int findAllOrders(String uid) throws SQLException;

	Order findOrderInfo(String oid) throws  Exception;

	boolean updateOrderState(String r6_Order) throws SQLException;

	boolean addCategory(Category cname) throws SQLException;

	boolean delCategory(String cid)  throws SQLException;

	Category findCategory(String cid) throws SQLException;

	boolean updateCategory(String cname,String cid)throws SQLException;

	List<Product> findAllProducts(String cid)throws SQLException;

	boolean addProduct(Product p)throws SQLException;

	boolean delProduct(String pid) throws SQLException;

	boolean updateProduct(Product p)throws SQLException;

	

}
