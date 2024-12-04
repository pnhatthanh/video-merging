package Model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import Model.BEAN.Video;
import Utils.DBConnection;

public class VideoDAO {
	public void createUser(Video video) {
	    try {
	        Connection connect = DBConnection.getConnection();
	        String sql = "INSERT INTO Video (idVideo, nameVideo, userID, pathVideo, status) VALUES (?, ?, ?, ?, ?)";
	        PreparedStatement statement = (PreparedStatement) connect.prepareStatement(sql);
	        statement.setString(1, UUID.randomUUID().toString());
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
				Video video=new Video(rs.getString("idVideo"), rs.getString("nameVideo"), rs.getString("userID"), rs.getString("pathVideo"), rs.getString("status"));
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
}
