package domain;


import java.util.Date;
import java.util.List;

public class Order {

	/*
	 *   `oid` varchar(32) NOT NULL,
	  `ordertime` datetime DEFAULT NULL,
	  `total` double DEFAULT NULL,# 总金额
	  `state` int(11) DEFAULT NULL,# 订单状态  默认为0:未支付  1:已支付  2:已发货   3:已完成
	  `address` varchar(30) DEFAULT NULL,
	  `name` varchar(20) DEFAULT NULL,
	  `telephone` varchar(20) DEFAULT NULL,
	  `uid` varchar(32) DEFAULT NULL,# 属于那个用户的
	 * */
	private String oid;
	private String ordertime;
	public String getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}
	private String total;
	private String state;
	private String address;
	private String  name;
	private String  telephone;
	private User user;
	private List<OrderItem> listItem;
	@Override
	public String toString() {
		return "Order [oid=" + oid + ", ordertime=" + ordertime + ", total=" + total + ", state=" + state + ", address="
				+ address + ", name=" + name + ", telephone=" + telephone + ", user=" + user + ", listItem=" + listItem
				+ "]";
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<OrderItem> getListItem() {
		return listItem;
	}
	public void setListItem(List<OrderItem> listItem) {
		this.listItem = listItem;
	}
	
	
	
}
