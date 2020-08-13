import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;

public class UserDao {

	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	private JdbcContext jdbcContext;
	
	public void setJdbcContext(JdbcContext jdbcContext) {
		this.jdbcContext = jdbcContext;
	}
	
	public void add(final User user) throws ClassNotFoundException, SQLException {
        
//		class AddStatement implements StatementStrategy{
//        	public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
//        		PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values (?,?,?)");
//        		 ps.setString(1, user.getId());
//        	     ps.setString(2, user.getName());
//        	     ps.setString(3, user.getPassword());
//        	     return ps;
//        	}
//        }
		
		//익명 클래스
//		StatementStrategy st = new StatementStrategy() {
//			public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
//        		PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values (?,?,?)");
//        		 ps.setString(1, user.getId());
//        	     ps.setString(2, user.getName());
//        	     ps.setString(3, user.getPassword());
//        	     return ps;
//        	}
//		};//따로 생성자를 만들어줄 필요가 없다 로컬클래스이므로
		
		//메소드 파라미터로 이전한 익명 내부 클래스
		this.jdbcContext.workWithStatementStrategy(
				new StatementStrategy() {
					public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
		        		PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values (?,?,?)");
		        		 ps.setString(1, user.getId());
		        	     ps.setString(2, user.getName());
		        	     ps.setString(3, user.getPassword());
		        	     return ps;
		        	}
				}
			);

    }
	
	public User get(String id) throws ClassNotFoundException, SQLException {
	
		Connection c = dataSource.getConnection();
        PreparedStatement ps = c.prepareStatement(
                "select * from users where id = ?");
		ps.setString(1,  id);
		
		ResultSet rs = ps.executeQuery();
		
		User user = null;
		
		if(rs.next()) {
			user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
		}
		
		
		rs.close();
		ps.close();
		c.close();
		
		if(user==null) throw new EmptyResultDataAccessException(1);
        
        return user;
	}
	
	public void deleteAll() throws SQLException {
//		StatementStrategy st = new DeleteAllStatement();
		this.jdbcContext.workWithStatementStrategy(
				new StatementStrategy() {
					public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
						
						PreparedStatement ps = c.prepareStatement("delete from users");
						return ps;
					}
				}
			);
		
	}
	
	public int getCount() throws SQLException {
		Connection c = null;
		
		PreparedStatement ps = null;
		//c.prepareStatement("select count(*) from users");
		
		ResultSet rs = null;
		//ps.executeQuery();
		
		try {
			c= dataSource.getConnection();
			
			ps=c.prepareStatement("select count(*) from users");
			
			rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);
		}catch(SQLException e) {
			throw e;
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				}catch(SQLException e) {
					
				}
			}
			if(ps!=null) {
				try {
					ps.close();
				}catch(SQLException e) {
					
				}
			}
			if(c!=null) {
				try {
					c.close();
				}catch(SQLException e) {
					
				}
			}
		}

	}
	
	//컨텍스트 부분을 분리하기 위한 메소드
//	public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
//		Connection c = null;
//		PreparedStatement ps = null;
//		
//		try {
//			c = dataSource.getConnection();
//			ps =stmt.makePreparedStatement(c);
//			ps.executeUpdate();
//		}catch(SQLException e) {
//			throw e;
//		}finally {
//			if(ps != null) {
//				try {
//					ps.close();
//				}catch(SQLException e) {
//					
//				}
//			}
//			if(c != null) {
//				try {
//					c.close();
//				}catch(SQLException e) {
//					
//				}
//			}
//		}
//	}
		

}
