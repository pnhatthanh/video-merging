package Model.BO;

import Model.BEAN.User;
import Model.DAO.UserDAO;

public class UserBO {
	private UserDAO userDAO;
	public UserBO() {
		this.userDAO=new UserDAO();
	}
	public User getUser(String email, String password) {
		return userDAO.getUser(email,password);
	}
	public void createUser(User user) {
		userDAO.createUser(user);
	}
	public User getUserByEmail(String email) {
		return userDAO.getUserByEmail(email);
	}
}