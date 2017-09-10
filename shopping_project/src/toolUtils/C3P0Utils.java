package toolUtils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0Utils {
 
	public static final DataSource ds = new ComboPooledDataSource();
	
	public static DataSource getDataSource() throws SQLException{
		  Connection conn = ds.getConnection();
		  System.out.println("数据连接成功"+conn);
		  return ds;
		
	}
	
}
