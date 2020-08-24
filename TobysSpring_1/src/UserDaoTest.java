import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


//테스트를 위한 애플리케이션 컨텍스트 관리
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:test-applicationContext.xml")
public class UserDaoTest {
	
	@Autowired
	private ApplicationContext context;
	
	private UserDaoJdbc dao;
	private User user1;
	private User user2;
	private User user3;

	
	@Before
	public void setUp() {
	
		
//		ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
		this.dao = context.getBean("userDao", UserDaoJdbc.class);
		
		this.user1 = new User("gyumee", "sungcheol Park", "springno1");
		this.user2 = new User("leegw700", "gilwon Lee", "springno2");
		this.user3 = new User("bumjin", "bumjin Park", "springno3");
		
//		SingleConnectionDataSource dataSource = new SingleConnectionDataSource("jdbc:mysql://localhost:3306/testdb", "root", "@min753951", true);
//		dao.setDataSource(dataSource);
	}
	
	@Test
	public void addAndGet() throws SQLException, ClassNotFoundException {
//		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
//		
//		UserDao dao = context.getBean("userDao", UserDao.class);
//		
//		User user1 = new User("gyumee", "sungcheol Park", "springno1");
//		User user2 = new User("leegw700", "gilwon Lee", "springno2");
//		
	
		dao.deleteAll();
        assertThat(dao.getCount(), is(0)); //삭제 한 후 테이블 갯수 count 검증 코드
//		
		User user = new User();
//		
        dao.add(user1);                                                                                                                    
        dao.add(user2);
        assertThat(dao.getCount(), is(2));

        User userget1 = dao.get(user1.getId());
        assertThat(userget1.getName(), is(user1.getName()));
        assertThat(userget1.getPassword(), is(user1.getPassword()));
        //첫 번째 User의 id로 get()을 실행하면 첫 번째 User의 값을 가진 오브젝트를 돌려주는지 확인
        
        User userget2 = dao.get(user2.getId());
        assertThat(userget2.getName(), is(user2.getName()));
        assertThat(userget2.getPassword(), is(user2.getPassword()));
        //두 번째 User에 대해서도 같은 방법으로 검증
//        
      
	}
	
	@Test
	public void count() throws SQLException, ClassNotFoundException {
//		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
//		
//		UserDao dao = context.getBean("userDao", UserDao.class);
//		
//		User user1 = new User("gyumee", "sungcheol Park", "springno1");
//		User user2 = new User("leegw7000", "gilwon Lee", "springno2");
//		User user3 = new User("bumjin", "bumjin Park", "springno3");
//		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.add(user1);
		assertThat(dao.getCount(), is(1));
		
		dao.add(user2);
		assertThat(dao.getCount(), is(2));

		dao.add(user3);
		assertThat(dao.getCount(), is(3));
	}
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void getUserFailure() throws SQLException, ClassNotFoundException {
//		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
//		
//		UserDao dao = context.getBean("userDao", UserDao.class);
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.get("unknown_id"); 
		
	}
	
	@Test
	public void getAll() throws SQLException, ClassNotFoundException {
		
		dao.deleteAll();
		
		
		dao.add(user1);
		List<User> users1 = dao.getAll();
		assertThat(users1.size(), is(1));
		checkSameUser(user1, users1.get(0));
		
		dao.add(user2);
		List<User> users2 = dao.getAll();
		assertThat(users2.size(), is(2));
		checkSameUser(user1, users2.get(0));
		checkSameUser(user2, users2.get(1));
		
		dao.add(user3);
		List<User> users3 = dao.getAll();
		assertThat(users3.size(), is(3));
		checkSameUser(user3, users2.get(0));
		checkSameUser(user1, users2.get(1));
		checkSameUser(user2, users2.get(2));  

	}
	
	private void checkSameUser(User user1, User user2) {
		assertThat(user1.getId(), is(user2.getId()));
		assertThat(user1.getName(), is(user2.getName()));
		assertThat(user1.getPassword(), is(user2.getPassword()));
	}

	
}
