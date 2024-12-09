package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import model.bean.Video;
import utils.DBConnection;

public class VideoDAO {
	public void createVideo(Video video) {
	    try {
	        Connection connect = DBConnection.getConnection();
	        String sql = "INSERT INTO Video (idVideo, nameVideo, userID, pathVideo, status) VALUES (?, ?, ?, ?, ?)";
	        PreparedStatement statement = (PreparedStatement) connect.prepareStatement(sql);
	        statement.setInt(1, video.getIdVideo());
	        statement.setString(2, video.getNameVideo());
	        statement.setString(3, video.getUserID());
	        statement.setString(4, video.getPathVideo());
	        statement.setString(5, video.getStatus());
	        statement.executeUpdate();
	        statement.close();
	        connect.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	public List<Video> getVideoByUser(String userID) {
		List<Video> videos=new ArrayList<Video>();
	    try {
	        Connection connect = DBConnection.getConnection();
	        String sql = "Select * from Video where userID=?";
	        PreparedStatement statement = (PreparedStatement) connect.prepareStatement(sql);
	        statement.setString(1, userID);
	        ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				Video video=new Video(rs.getInt("idVideo"), rs.getString("nameVideo"), rs.getString("userID"), rs.getString("pathVideo"), rs.getString("status"));
				videos.add(video);
			}
	        statement.close();
	        connect.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	    return videos;
	}
	public Video getVideoByID(int id) {
		try {
			Connection connect = DBConnection.getConnection();
			String sql = "Select * from Video where idVideo=?";
			PreparedStatement psm = connect.prepareStatement(sql);
			psm.setInt(1, id);
			ResultSet rs = psm.executeQuery();
			rs.next();
			Video video=new Video(rs.getInt("idVideo"), rs.getString("nameVideo"), rs.getString("userID"), rs.getString("pathVideo"), rs.getString("status"));
			return video;
		}
		catch (Exception ex) {
			ex.getStackTrace();
		}
		return null;
	}
	public void updateStatus(int id, String newStatus) {
		try {
			Connection connect = DBConnection.getConnection();
			String sql = "Update Video Set status=? where idVideo=?";
			PreparedStatement psm = connect.prepareStatement(sql);
			psm.setString(1, newStatus);
			psm.setInt(2, id);
			psm.executeUpdate();
		}
		catch (Exception ex){
			ex.getStackTrace();
		}
	}
	public int getMaxVideoID() {
		try {
			Connection connect = DBConnection.getConnection();
			String sql = "Select MAX(idVideo) from video";
			Statement sm = (Statement) connect.createStatement();
			ResultSet rs = sm.executeQuery(sql);
			rs.next();
			Integer res = rs.getInt(1);
			if (res == null) return 0;
			else return res;
		}
		catch (Exception ex){
			ex.getStackTrace();
		}
		return 0;
	}
}
