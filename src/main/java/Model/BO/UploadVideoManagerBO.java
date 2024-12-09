package model.BO;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.Part;
import javax.swing.plaf.ProgressBarUI;


public class UploadVideoManagerBO {
	private static UploadVideoManagerBO instance = null;
	private ExecutorService executorService=Executors.newFixedThreadPool(10);
    public Map<String, Integer[]> progressMap = new ConcurrentHashMap<String, Integer[]>();
    private static Object key = new Object();
    private static Object key_input = new Object();
    private UploadVideoManagerBO() {
		// TODO Auto-generated constructor stub
    	
    }
    
    public static UploadVideoManagerBO getInstance() {
    	if (instance == null) {
    		instance = new UploadVideoManagerBO();
    	}
    	return instance;
    }
	
	
    public void putStatus(String userID, Integer[] status) {
    	progressMap.put(userID, status);
    }
    
    public Integer[] getStatus(String userID) {
		return progressMap.get(userID);
	}
	public void postTask(String userID, Part part, String filePath) {
		executorService.submit(()->{
			processFileUpload(userID, part, filePath);
		});
	}
    
	public void destroy() {
		if (executorService != null && !executorService.isShutdown()) {
			System.out.println("on destroy");
            executorService.shutdown();
        }
	}
	
	private void processFileUpload(String userID, Part part, String filePath) {
        try {
        	System.out.println("File " + filePath + " is going to be uploaded.");
        	InputStream bis = part.getInputStream();
        	FileOutputStream bos = new FileOutputStream(filePath);
        	byte[] buffer = new byte[1024];
        	while(true) {
        		int b;
        		synchronized (key_input) {
        			b = bis.read(buffer);
				}
        		if (b==-1) break;
        		bos.write(buffer,0,b);
        	}
            System.out.println("File " + filePath + " uploaded successfully.");
            Integer[] curProgress;
            synchronized (key) {
            	curProgress = progressMap.get(userID);
            	curProgress[0]++;
            	System.out.println(curProgress[0]+"/"+curProgress[1]);
                progressMap.put(userID, curProgress); 
			}
        	
			
        } catch (Exception e) {
            System.err.println("Error processing file: " + e.getMessage());
        }
    }
}
