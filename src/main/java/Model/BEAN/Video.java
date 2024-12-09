package model.bean;

public class Video {
	private int idVideo;
	private String nameVideo;
	private String userID;
	private String pathVideo;
	private String status;
	
	
	public Video(int idVideo, String nameVideo, String userID, String pathVideo, String status) {
		super();
		this.idVideo = idVideo;
		this.nameVideo = nameVideo;
		this.userID = userID;
		this.pathVideo = pathVideo;
		this.status = status;
	}
	public int getIdVideo() {
		return idVideo;
	}
	public String getNameVideo() {
		return nameVideo;
	}
	public void setNameVideo(String nameVideo) {
		this.nameVideo = nameVideo;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getPathVideo() {
		return pathVideo;
	}
	public void setPathVideo(String pathVideo) {
		this.pathVideo = pathVideo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
