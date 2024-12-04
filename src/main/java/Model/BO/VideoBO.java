package Model.BO;

import java.util.List;

import Model.BEAN.Video;
import Model.DAO.VideoDAO;

public class VideoBO {
	private VideoDAO videoDAO;
	public VideoBO() {
		this.videoDAO=new VideoDAO();
	}
	public void createUser(Video video) {
		videoDAO.createUser(video);
	}
	public List<Video> getVideoByUser(String userID) {
		return videoDAO.getVideoByUser(userID);
	}
}
