
public class UserLevelUpNewYear implements UserLevelUpgradePolicy {

	UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public boolean canUpgradeLevel(User user) {
		Level currentLevel = user.getLevel();
		switch(currentLevel) {
		case BASIC : return (user.getLogin() >= 20);
		case SILVER : return (user.getRecommend() >= 30);
		case GOLD : return false;
		default : throw new IllegalArgumentException("Unknown Level : " + currentLevel);
		}
	}


	public void upgradeLevel(User user) {
		// TODO Auto-generated method stub
		user.upgradeLevel();
		userDao.update(user);
	}

}
