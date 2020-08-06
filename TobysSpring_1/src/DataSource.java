import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.CommonDataSource;

import com.mysql.jdbc.Wrapper;

public interface DataSource extends CommonDataSource, Wrapper{
	Connection getConnection() throws SQLException;
	
	

}
