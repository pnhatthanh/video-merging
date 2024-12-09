package model.BO;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadVideoManagerBO {
	private static DownloadVideoManagerBO instance = null;
	private ExecutorService executorService=Executors.newFixedThreadPool(10);
    private static Object key_output = new Object();
    private DownloadVideoManagerBO() {
		// TODO Auto-generated constructor stub
    	
    }
    
    public static DownloadVideoManagerBO getInstance() {
    	if (instance == null) {
    		instance = new DownloadVideoManagerBO();
    	}
    	return instance;
    }
	
	
    
    
   
	public void postTask(String outFilePath, String fileName, HttpServletRequest request, HttpServletResponse response) {
		executorService.submit(()->{
			processFileDownload(outFilePath, fileName, request, response);
		});
	}
    
	public void destroy() {
		if (executorService != null && !executorService.isShutdown()) {
			System.out.println("on destroy");
            executorService.shutdown();
        }
	}
	
	private void processFileDownload(String outFilePath, String fileName,  HttpServletRequest request, HttpServletResponse response) {
		try {
			File downloadFile = new File(outFilePath);
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(downloadFile));
			ServletContext sctx = request.getServletContext();
			String mimeType = sctx.getMimeType(outFilePath);
			System.out.println(mimeType);
			response.setContentType(mimeType != null? mimeType:"application/octet-stream");
			System.out.println(downloadFile.length());
			response.setContentLength((int) downloadFile.length());
			response.setHeader("Content-Disposition", "attachment; filename=\""+fileName+"\"");
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
}
