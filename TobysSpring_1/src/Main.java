import java.sql.SQLException;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		UserDao dao = new DaoFactory().userDao();

        User user = new User();
        user.setId("whiteship");
        user.setName("minseung Park");
        user.setPassword("married");

        dao.add(user);
        
        System.out.println(user.getId() + "��� ����");
        
        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        
        System.out.println(user2.getPassword());
        
        System.out.println(user2.getId() + "��ȸ ����");
        System.out.println("git check");
	}

}
