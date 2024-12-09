package model.BO;

import java.util.List;

import model.bean.Video;
import model.DAO.VideoDAO;

public class VideoBO {
	private VideoDAO videoDAO;
	public VideoBO() {
		this.videoDAO=new VideoDAO();
	}
	public void createVideo(Video video) {
		videoDAO.createVideo(video);
	}
	public List<Video> getVideoByUser(String userID) {
		return videoDAO.getVideoByUser(userID);
	}
	public Video getVideoByID(int id) {
		return videoDAO.getVideoByID(id);
	}
	public void updateStatus(int id, String newStatus) {
		videoDAO.updateStatus(id, newStatus);
	}
	public int getMaxVideoID() {
		return videoDAO.getMaxVideoID();
	}
}
