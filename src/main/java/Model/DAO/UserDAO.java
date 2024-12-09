package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

import model.bean.User;
import utils.DBConnection;

public class UserDAO {
	public User getUser(String email, String password) {
		try{
			Connection connect=DBConnection.getConnection();
			String sql = "Select * from User where email=? and password=?";
			PreparedStatement statement = (PreparedStatement) connect.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, password);
			ResultSet rs = statement.executeQuery();
			rs.next();
			User user=new User(rs.getString("id"),rs.getString("fullName"),rs.getString("email"),rs.getString("password"));
			statement.close();
			connect.close();
			return user;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public User getUserByEmail(String email) {
		try{
			Connection connect=DBConnection.getConnection();
			String sql = "Select * from User where email=?";
			PreparedStatement statement = (PreparedStatement) connect.prepareStatement(sql);
			statement.setString(1, email);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				User user=new User(rs.getString("id"),rs.getString("fullName"),rs.getString("email"),rs.getString("password"));
				return user;
			}
			statement.close();
			connect.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public void createUser(User user) {
	    try {
	        Connection connect = DBConnection.getConnection();
	        String sql = "INSERT INTO User (id, fullName, email, password) VALUES (?, ?, ?, ?)";
	        PreparedStatement statement = (PreparedStatement) connect.prepareStatement(sql);
	        statement.setString(1, UUID.randomUUID().toString());
	        statement.setString(2, user.getFullName());
	        statement.setString(3, user.getEmail());
	        statement.setString(4, user.getPassword());
	        statement.executeUpdate();
	        statement.close();
	        connect.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
