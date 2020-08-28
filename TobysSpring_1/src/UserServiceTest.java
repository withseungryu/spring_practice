import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:test-applicationContext.xml")
public class UserServiceTest {
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	UserService userService;
	
	
	UserDaoJdbc userDao;
	List<User> users;
	
	@Before
	public void setUp() {
		this.userDao = context.getBean("userDao", UserDaoJdbc.class);
		users = Arrays.asList(
				new User("bumjin", "bumjin Park", "p1", Level.BASIC, 49, 0),
				new User("joytouch", "myungsung Kang", "p2", Level.BASIC, 50, 0),
				new User("erwins", "seunghwan Sin", "p3", Level.SILVER, 60, 29),
				new User("madnite1", "sangho Lee", "p4", Level.SILVER, 60, 30),
				new User("green", "mingyu Oh", "p5", Level.GOLD, 100, 100)
				);
				
	}
	
	@Test
	public void bean() {
		assertThat(this.userService, is(notNullValue()));
	}
	
	
	@Test
	public void upgradeLevels() throws Exception {
		int event = 0;
		
		userDao.deleteAll();
		
		for(User user : users) {userDao.add(user);
		
		}
		
		userService.upgradeLevels(1);
		
		checkLevel(users.get(0), false);
		checkLevel(users.get(1), true);
		checkLevel(users.get(2), false);
		checkLevel(users.get(3), true);
		checkLevel(users.get(4), false);
	}
	
	@Test
	public void upgradeAllOrNothing() throws Exception {
		UserService testUserService = new TestUserService(users.get(3).getId());
		testUserService.setUserDao(this.userDao);
		
		userDao.deleteAll();
		for(User user : users) userDao.add(user);
		
		try {
			testUserService.upgradeLevels(0);
			fail("TestUserServiceException expected");
		}
		catch(TestUserServiceException e) {
			
		}
		
		checkLevel(users.get(1), false);
	}
	
	private void checkLevel(User user,boolean upgraded) {
		User userUpdate = userDao.get(user.getId());
		if(upgraded) {
			assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel()));
		}else{
			assertThat(userUpdate.	getLevel(), is(user.getLevel()));
		}
	}
	
	@Test
	public void add() {
		userDao.deleteAll();
		
		User userWithLevel = users.get(4);
		User userWithoutLevel = users.get(0);
		userWithoutLevel.setLevel(null);
		
		userService.add(userWithLevel);
		userService.add(userWithoutLevel);
		
		User userWithLevelRead = userDao.get(userWithLevel.getId());
		User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());
		
		assertThat(userWithLevelRead.getLevel(), is(userWithLevel.getLevel()));
		assertThat(userWithoutLevelRead.getLevel(), is(Level.BASIC));
		
	}
	

}
