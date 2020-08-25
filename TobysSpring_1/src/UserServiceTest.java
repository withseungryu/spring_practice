import static org.junit.Assert.assertThat;

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
	public void upgradeLevels() {
		userDao.deleteAll();
		
		for(User user : users) {userDao.add(user);
		
		}
		
		userService.upgradeLevels();
		
		checkLevel(users.get(0), Level.BASIC);
		checkLevel(users.get(1), Level.SILVER);
		checkLevel(users.get(2), Level.SILVER);
		checkLevel(users.get(3), Level.GOLD);
		checkLevel(users.get(4), Level.GOLD);
	}
	
	private void checkLevel(User user, Level expectedLevel) {
		User userUpdate = userDao.get(user.getId());
		assertThat(userUpdate.getLevel(), is(expectedLevel));
	}
}
