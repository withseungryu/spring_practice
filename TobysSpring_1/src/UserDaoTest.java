import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


import java.sql.SQLException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class UserDaoTest {
	
	@Test
	public void addAndGet() throws SQLException, ClassNotFoundException {
		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
		
		UserDao dao = context.getBean("userDao", UserDao.class);
		
		dao.deleteAll();
        assertThat(dao.getCount(), is(0)); //���� �� �� ���̺� ���� count ���� �ڵ�
		
		User user = new User();
		
        user.setId("gyumee");
        user.setName("minseung Lee");
        user.setPassword("springno1");

        dao.add(user);
        assertThat(dao.getCount(), is(1)); //�߰� �� ���̺� ���� count ���� �ڵ� 
        
        User user2 = dao.get(user.getId());
        
        
        assertThat(user2.getName(), is(user.getName()));
        assertThat(user2.getPassword(), is(user.getPassword()));
        
	}
	
	@Test
	public void count() throws SQLException, ClassNotFoundException {
		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
		
		UserDao dao = context.getBean("userDao", UserDao.class);
		
		User user1 = new User("gyumee", "sungcheol Park", "springno1");
		User user2 = new User("leegw7000", "gilwon Lee", "springno2");
		User user3 = new User("bumjin", "bumjin Park", "springno3");
		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.add(user1);
		assertThat(dao.getCount(), is(1));
		
		dao.add(user2);
		assertThat(dao.getCount(), is(2));

		dao.add(user3);
		assertThat(dao.getCount(), is(3));
	}
}
