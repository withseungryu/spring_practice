import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;


public class UserDaoJdbc implements UserDao{

	private DataSource dataSource;
	
//	private JdbcContext jdbcContext;
	
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.dataSource = dataSource;
	}
	

	
//	public void setJdbcContext(JdbcContext jdbcContext) {
//		this.jdbcContext = jdbcContext;
//	}
	
	
	
	public void add(final User user) {
        
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
//		this.jdbcContext.workWithStatementStrategy(
//				new StatementStrategy() {
//					public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
//		        		PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values (?,?,?)");
//		        		 ps.setString(1, user.getId());
//		        	     ps.setString(2, user.getName());
//		        	     ps.setString(3, user.getPassword());
//		        	     return ps;
//		        	}
//				}
//			);
		this.jdbcTemplate.update("insert into users(id, name, password, level, login, recommend) values(?,?,?,?,?,?)", user.getId(), user.getName(), user.getPassword()
				, user.getLevel().intValue(), user.getLogin(), user.getRecommend()
				);
		

    }
	
	public User get(String id) {
	
//		Connection c = dataSource.getConnection();
//        PreparedStatement ps = c.prepareStatement(
//                "select * from users where id = ?");
//		ps.setString(1,  id);
//		
//		ResultSet rs = ps.executeQuery();
//		
//		User user = null;
//		
//		if(rs.next()) {
//			user = new User();
//			user.setId(rs.getString("id"));
//			user.setName(rs.getString("name"));
//			user.setPassword(rs.getString("password"));
//		}
//		
//		
//		rs.close();
//		ps.close();
//		c.close();
//		
//		if(user==null) throw new EmptyResultDataAccessException(1);
//        
//        return user;
		
		return this.jdbcTemplate.queryForObject("select * from users where id =?", 
				new Object[] {id},
				new RowMapper<User>() {
					public User mapRow(ResultSet rs, int rowNum) throws SQLException {
						User user = new User();
						user.setId(rs.getString("id"));
						user.setName(rs.getString("name"));
						user.setPassword(rs.getString("password"));
						user.setLevel(Level.valueOf(rs.getInt("level")));
						user.setLogin(rs.getInt("login"));
						user.setRecommend(rs.getInt("recommend"));
						return user;
					}
			
		});
				
	}	
	
	
	public void deleteAll() {
//		StatementStrategy st = new DeleteAllStatement();

		this.jdbcTemplate.update(
				"delete from users"
				);
	
	}
	

	
	public int getCount() {
//		Connection c = null;
//		
//		PreparedStatement ps = null;
//		//c.prepareStatement("select count(*) from users");
//		
//		ResultSet rs = null;
//		//ps.executeQuery();
//		
//		try {
//			c= dataSource.getConnection();
//			
//			ps=c.prepareStatement("select count(*) from users");
//			
//			rs = ps.executeQuery();
//			rs.next();
//			return rs.getInt(1);
//		}catch(SQLException e) {
//			throw e;
//		}finally {
//			if(rs!=null) {
//				try {
//					rs.close();
//				}catch(SQLException e) {
//					
//				}
//			}
//			if(ps!=null) {
//				try {
//					ps.close();
//				}catch(SQLException e) {
//					
//				}
//			}
//			if(c!=null) {
//				try {
//					c.close();
//				}catch(SQLException e) {
//					
//				}
//			}
//		}
		
//		return this.jdbcTemplate.query(new PreparedStatementCreator() {
//			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//				return con.prepareStatement("select count(*) from users");
//			}
//		}, new ResultSetExtractor<Integer>() {
//			public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
//				rs.next();
//				return rs.getInt(1);
//			}
//		});
		
	
			return this.jdbcTemplate.queryForInt("select count(*) from users");
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
	public List<User> getAll(){
		return this.jdbcTemplate.query("select * from users order by id" ,
				new RowMapper<User>() {
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setId(rs.getString("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setLevel(Level.valueOf(rs.getInt("level")));
				user.setLogin(rs.getInt("login"));
				user.setRecommend(rs.getInt("recommend"));
				return user;
				
			}
		}
				);
	}



	public void update(User user1) {
	
		this.jdbcTemplate.update(
				"update users set name = ?, password = ?, level = ?, login = ?, recommend = ? where id= ?", 
				user1.getName(), user1.getPassword(), user1.getLevel().intValue(), user1.getLogin(), user1.getRecommend(), user1.getId()
				);
				
		System.out.println("gd");
	}

}
