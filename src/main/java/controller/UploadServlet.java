package controller;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model.BO.MergeVideoMangerBO;
import model.BO.UploadVideoManagerBO;
import model.BO.VideoBO;
import model.bean.Video;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/UploadServlet/*")
@MultipartConfig(location = "D:\\SourceVideo\\temp",
				 fileSizeThreshold=1024*1024*1000, 	// 500 MB dung luong buffer cho phep truoc khi luu
				 maxFileSize=1024*1024*1024)         // 1000MB dung luong 1 file         
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String rootFolder = "D:\\SourceVideo";
    
    public UploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String userID = (String)request.getSession().getAttribute("userID");
		int pID = (request.getSession().getAttribute("pID")==null)?-1:(int)request.getSession().getAttribute("pID");
		System.out.println(userID+"in get");
		String url = request.getRequestURL().toString();
		if (url.contains("/Finish")) {
			UploadVideoManagerBO.getInstance().progressMap.remove(userID);
			return;
		}
		response.setContentType("application/json");
		Integer[] curProgress = UploadVideoManagerBO.getInstance().getStatus(userID);
//		System.out.println(curProgress[0]+" "+curProgress[1]);
		if (curProgress!=null) {
			response.getWriter().write(String.format("{\"numFileUploaded\": %d, \"totalFileUploaded\": %d}", curProgress[0],curProgress[1]));
		}
		else {
			response.getWriter().write("{\"numFileUploaded\": 0, \"totalFileUploaded\": 0}");
		}
		response.flushBuffer();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userID = (String)request.getSession().getAttribute("userID");
		
		System.out.println(userID+"in post");//DEBUG
		
		int totalFileUpload = request.getParts().size() - 1;
		UploadVideoManagerBO.getInstance().putStatus(userID, new Integer[] {0,totalFileUpload});
		
		System.out.println(totalFileUpload);//DEBUG
		
		int pID = MergeVideoMangerBO.getInstance().getNextID();
		String nameVideo = request.getParameter("nameVideo");
		String outputFilePath = rootFolder+File.separator+userID+File.separator+pID+File.separator+"output"+File.separator+nameVideo+".mp4";
		new VideoBO().createVideo(new Video(pID, nameVideo, userID, outputFilePath, "Dang xu ly"));
		
		request.getSession().setAttribute("pID", pID);
		String upFolder = rootFolder+File.separator+userID+File.separator+pID+File.separator+"upload";
		File upF = new File(upFolder);
		if (!upF.exists()) {
			upF.mkdirs();
		}
		PrintWriter fileListPrinter = new PrintWriter(upFolder+File.separator+"file_list.txt");
		
		for (Part part: request.getParts()) {
			if (!part.getName().equals("videoFiles")) continue;
			String filePath = upFolder+File.separator+getFileName(part);
			fileListPrinter.println(String.format("file '%s'", filePath));
			UploadVideoManagerBO.getInstance().postTask(userID, part, filePath);
		}
		fileListPrinter.close();
	}
	
	private static String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        for (String content : contentDisposition.split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
            }
        }
        return null;
    }
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		UploadVideoManagerBO.getInstance().destroy();
	}
}
