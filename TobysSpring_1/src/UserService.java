
import java.sql.Connection;
import java.util.List;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;


import javax.sql.DataSource;
public class UserService {
	UserDao userDao;
	
	DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	UserLevelUpgradePolicy userLevelUpgradePolicy;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	
	public void setUserLevelUpgradePolicy(UserLevelUpgradePolicy userLevelUpgradePolicy) {
		this.userLevelUpgradePolicy = userLevelUpgradePolicy;
	}
	
	public void upgradeLevels(int event) throws Exception {
		
		PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
		
		TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
		
		
//		TransactionSynchronizationManager.initSynchronization();
//		Connection c = DataSourceUtils.getConnection(dataSource);
//		
//		c.setAutoCommit(false);
		
		try{
			List<User> users = userDao.getAll();
			for(User user : users) {
				
				if(event == 0) {
					if(canUpgradeLevel(user)) {
						upgradeLevel(user);
			
					}
				}else {
					if(userLevelUpgradePolicy.canUpgradeLevel(user)) {
						userLevelUpgradePolicy.upgradeLevel(user);
					}
				}
				
				
	//			else {
	//				if(this.userLevelUpgradePolicy.canUpgradeLevel(user)) {
	//					this.userLevelUpgradePolicy.upgradeLevel(user);
	//				}
	//			}
			}
			transactionManager.commit(status);
//			c.commit();
		}catch(Exception e) {
			transactionManager.rollback(status);
//			c.rollback();
			throw e;
		}finally {
//			DataSourceUtils.releaseConnection(c, dataSource);
//			TransactionSynchronizationManager.unbindResource(this.dataSource);
//			TransactionSynchronizationManager.clearSynchronization();
		}
		
//		for(User user : users) {
//			Boolean changed = null;
//			if(user.getLevel() == Level.BASIC && user.getLogin() >= 50) {
//				user.setLevel(Level.SILVER);
//				changed = true;
//			}
//			else if(user.getLevel() == Level.SILVER && user.getRecommend() >= 30) {
//				user.setLevel(Level.GOLD);
//				changed = true;
//			}
//			else if(user.getLevel() == Level.GOLD) { changed = false; }
//			else { changed = false; }
//			
//			if(changed) { userDao.update(user); }
//		}
	}
	
	protected void upgradeLevel(User user) {
		user.upgradeLevel();
		userDao.update(user);
		

	}
	
	public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
	public static final int MIN_RECCOMEND_FOR_GOLD = 30;
	
	private boolean canUpgradeLevel(User user) {
		Level currentLevel = user.getLevel();
		switch(currentLevel) {
		case BASIC : return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
		case SILVER : return (user.getRecommend() >= MIN_RECCOMEND_FOR_GOLD);
		case GOLD : return false;
		default : throw new IllegalArgumentException("Unknown Level : " + currentLevel);
		}
	}
	
	
	public void add(User user) {
		if(user.getLevel() == null) user.setLevel(Level.BASIC);
		userDao.add(user);
	}
}
