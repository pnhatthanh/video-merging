<%@page import="java.util.Comparator"%>
<%@page import="model.bean.Video"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="Resources/css/home.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.1/css/all.min.css" integrity="sha512-5Hs3dF2AEPkpNAR7UiOHba+lRSJNeM2ECkwxUIxC1Q/FLycGTbNapWXB4tP889k5T5Ju8fs4b1P5z/iB4nMfSQ==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <title>Home</title>
</head>

<body>
    <section class="header">
        <div class="logo">
            <img src="Resources/img/video.png" alt="icon">
            <h1>Video merging</h1>
        </div>
        <div>
            <h3>Hello,<span> <%=(String) request.getAttribute("fullName") %> </span><a href="logout">(Logout)</a></h3>
        </div>
    </section>
    <section class="body">
        <div class="history">
            <h2>Recent Videos</h2>
            <div>
			<%
	        List<Video> listVideo = (List<Video>)request.getAttribute("listVideo"); 
	       	listVideo.sort(new Comparator<Video>(){
	       		public int compare(Video o1, Video o2){
	       			return o2.getIdVideo()-o1.getIdVideo();
	       		}
	       	});
	        for (Video vi:listVideo){
				if (vi.getStatus().equals("Dang xu ly")){
			%>
				<div id = "<%=vi.getIdVideo() %>"  class="video uncomplete">
                	<h3><%=vi.getNameVideo() %>.mp4</h3>
                	<div id="processInfo">
                    	<label>Merge</label>
                    	<progress id="progressBarMerge" value="0" max="100"></progress>
                    	<label>HD</label>
                    	<progress id="progressBarHD" value="0" max="100"></progress>
                	</div>
                	<div class="button-download"  style="display: none">
                		<form onclick="this.submit()" action="DownloadServlet" method="post">
                			<input name="idVideo" value='<%=vi.getIdVideo()%>' hidden>
                			<i class="fa-solid fa-download button-download" ></i>
                		</form>
                	</div>
            	</div>
			<%
				}
				else{
			%>
				<div class="video complete">
                	<h3><%=vi.getNameVideo()%>.mp4</h3>
                	<div class="button-download" >
                		<form onclick="this.submit()" action="DownloadServlet" method="post">
                			<input name="idVideo" value='<%=vi.getIdVideo()%>' hidden>
                			<i class="fa-solid fa-download button-download" ></i>
                		</form>
                	</div>
            	</div>
			<%
				}
	        }
			%>
				
            </div>
        </div>
        <div class="content">
            <h2>Upload Videos for Merging</h2>
            <form id = "uploadForm" action="UploadServlet" method="POST" enctype="multipart/form-data">
                <div class="context-nameVideo">
                    <input class="name-file" type="text" placeholder="Enter file name here" name="nameVideo" required>
                    <span>.MP4</span>
                </div>
                <div class="video-preview" id="videoPreview-container">
                	<input id="videoInput" type="file" name="videoFiles" accept="video/mp4" multiple required>
                </div>           
                <div class="choice">
                    <button id="button_reset" class="button-reset" type="reset" onclick="removeAllFile()">Cancel</button>
                    <button class="button-submit" type="submit">Submit</button>
                </div>
                <div id="progressInfo" style = "display:none">
                	<progress id = "progressBar"  value = "0" max = "0"></progress>
                	<label id = "progressText">Uploading to server</label>
                </div>
            </form>
        </div>
    </section>
    <script type="text/javascript" src="Resources/js/home.js"></script>
</body>
</html>