package domain;



/*
 * 'itemid','varchar(32)','NO','PRI',NULL,''
'count','int(11)','YES','',NULL,''
'subtotal','double','YES','',NULL,''
'pid','varchar(32)','YES','MUL',NULL,''
'oid','varchar(32)','YES','MUL',NULL,''

 * 
 * */
public class OrderItem {
    private String  itemid  ;
    private Integer  count  ;
    private double  subtotal  ;
    private Product  p;
	private String pid;
    private String   oid ;
    
    public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	
	public Product getP() {
		return p;
	}
	public void setP(Product p) {
		this.p = p;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
    
}
