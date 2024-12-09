package controller;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BO.DownloadVideoManagerBO;
import model.BO.VideoBO;
import model.bean.Video;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@WebServlet("/DownloadServlet")
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public DownloadServlet() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		int pID = Integer.parseInt(request.getParameter("idVideo"));
		Video video = new VideoBO().getVideoByID(pID);
		//DownloadVideoManagerBO.getInstance().postTask(video.getPathVideo(),video.getNameVideo(), request, response);
		String outFilePath=video.getPathVideo();
		String fileName=video.getNameVideo();
		try {
			File downloadFile = new File(outFilePath);
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(downloadFile));
			ServletContext sctx = request.getServletContext();
			String mimeType = sctx.getMimeType(outFilePath);
			System.out.println(mimeType);
			response.setContentType(mimeType != null? mimeType:"application/octet-stream");
			System.out.println(downloadFile.length());
			response.setContentLength((int) downloadFile.length());
			response.setHeader("Content-Disposition", "attachment; filename=\""+fileName+".mp4\"");
			ServletOutputStream sos = response.getOutputStream();
			byte[] buffer = new byte[65536];
			int b;
			while ((b=bis.read(buffer))!=-1) {
				sos.write(buffer);
			}
			sos.flush();
			sos.close();
			bis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
