import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;



@Configuration
public class DaoFactory{
	@Bean
	public UserDao userDao() {
		return new UserDao(connectionMaker());
	}
	
	
	@Bean
	public ConnectionMaker connectionMaker() {
		return new NConnectionMaker();
	}
}
