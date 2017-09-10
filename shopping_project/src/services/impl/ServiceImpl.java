package services.impl;

import java.util.List;

import domain.Category;
import domain.Order;
import domain.PageBean;
import domain.Product;
import domain.User;

public interface ServiceImpl {


	String findCategory();

	boolean addUser(User u);

	User findBody(User u);

	PageBean finProductByPage(int currentP,int pagSize, String cid );

	List<Product> findHotProducts();

	List<Product> findNewProdicts();

	Product findProductByPid(String pid);

	void changeCodeState(String code);

	boolean createOrder(Order od);

	PageBean<Order> findOrdersByUid(String uid, int currentPage, int pageSize);

	Order findOrderInfo(String oid);

	boolean updateOrderState(String r6_Order);

	boolean addCategory(Category cg);

	boolean delCategory(String cid);

	String findCategory(String cid);

	boolean updateCategory(String cname,String cid);

	boolean addProduct(Product p);

	boolean delProduct(String pid);

	boolean updateProduct(Product p);
}
