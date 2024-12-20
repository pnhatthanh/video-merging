package model.bean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.FileUtils;

import model.BO.UpVideoToHDManagerBO;

public class MergeVideoHandler implements Runnable {
	private static final String ffmpegPath = "D:\\Download\\ffmpeg-7.1-full_build\\ffmpeg-7.1-full_build\\bin\\ffmpeg.exe";
	private static final String rootFolder = "D:\\SourceVideo";
	private String uploadFolder ,fileListPath, outFilePath;
	private double status;
	private List<String> listPath = new ArrayList<String>();
	private String userID;
	private int pID;
	
	
	public MergeVideoHandler(String userID, int pID) {
		// TODO Auto-generated constructor stub
		this.userID = userID;
		this.pID = pID;
		uploadFolder = rootFolder+File.separator+userID+File.separator+pID+File.separator+"upload";
		fileListPath = uploadFolder+File.separator+"file_list.txt";
		File outFolder = new File(rootFolder+File.separator+userID+File.separator+pID+File.separator+"output");
		if (!outFolder.exists()) {
			outFolder.mkdirs();
		}
		outFilePath = rootFolder+File.separator+userID+File.separator+pID+File.separator+"output"+File.separator+"output.mp4";
		status = 0;
		listPath = extractListPath(fileListPath);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
            // Get video duration
            double totalDuration = getTotalVideoDuration();
            if (totalDuration <= 0) {
                System.err.println("Failed to retrieve video duration.");
                return;
            }
            
            // Start FFmpeg process
            ProcessBuilder processBuilder = new ProcessBuilder(ffmpegPath,
            												   "-f",
            												   "concat",
            												   "-safe",
            												   "0",
            												   "-i",
            												   fileListPath,
            												   "-c",
            												   "copy",
            												   outFilePath);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // Monitor progress
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                Pattern timePattern = Pattern.compile("time=(\\d{2}):(\\d{2}):(\\d{2}\\.\\d{2})");
                while ((line = reader.readLine()) != null) {
                    System.out.println(line); // For debugging
                    Matcher matcher = timePattern.matcher(line);
                    if (matcher.find()) {
                        double currentTime = convertTimeToSeconds(matcher.group(1), matcher.group(2), matcher.group(3));
                        status = ((currentTime / totalDuration) * 100);
                        System.out.printf("------------------Progress: %.2f%%-----------------------\n", status); // Console progress bar
                    }
                }
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
            	status=100;
            	//File upF = new File(uploadFolder);
            	System.out.println("\nFFmpeg process completed successfully.");
                System.out.println("\nPrepare to up to HD video.");
            	
                UpVideoToHDManagerBO.getInstance().postTask(userID, pID);
            } else {
                System.err.println("\nFFmpeg process failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	private double getTotalVideoDuration() throws Exception {
		double totalTime = 0;
        for (String filePath: listPath) {
        	System.out.println(filePath);
			totalTime += getVideoDuration(filePath);
		}
		return totalTime;
    }

	private double getVideoDuration(String filePath) throws Exception {
		ProcessBuilder processBuilder = new ProcessBuilder(ffmpegPath, "-i", filePath);
		processBuilder.redirectErrorStream(true);
		Process process = processBuilder.start();
		
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			String line;
			Pattern durationPattern = Pattern.compile("Duration: (\\d{2}):(\\d{2}):(\\d{2}\\.\\d{2})");
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
				Matcher matcher = durationPattern.matcher(line);
				if (matcher.find()) {
					return convertTimeToSeconds(matcher.group(1), matcher.group(2), matcher.group(3));
				}
			}
		}
		process.waitFor();
		return 0;
    }
	
    private double convertTimeToSeconds(String hours, String minutes, String seconds) {
        return Integer.parseInt(hours) * 3600 + Integer.parseInt(minutes) * 60 + Double.parseDouble(seconds);
    }

    private List<String> extractListPath(String fileListPath){
    	try {
			List<String> list = new ArrayList<String>();
			Scanner scn = new Scanner(new File(fileListPath));
			while (scn.hasNext()) {
				String line = scn.nextLine();
				String path = line.substring(line.indexOf("'")+1,line.length()-1);
				list.add(path);
			}
			scn.close();
			return list;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }

	public double getStatus() {
		return status;
	}
	
//    public static void main(String[] args) {
//		new MergeVideoHandler("user01", 3).start();
//	}
    
}

