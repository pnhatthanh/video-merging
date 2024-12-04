<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="Resources/css/home.css">
    <title>Home</title>
</head>

<body>
    <section class="header">
        <div class="logo">
            <img src="Resources/img/video.png" alt="icon">
            <h1>Video merging</h1>
        </div>
        <div>
            <h3>Hello,<span> Pham Nhat Thanh </span><a href="">(Logout)</a></h3>
        </div>
    </section>
    <section class="body">
        <div class="history">
            <h2>Recent Videos</h2>
            <div>
            </div>
        </div>
        <div class="content">
            <h2>Upload Videos for Merging</h2>
            <form action="" method="POST" enctype="multipart/form-data">
                <div class="context-nameVideo">
                    <input class="name-file" type="text" placeholder="Enter file name here" required>
                    <span>.MP4</span>
                </div>
                <div class="video-preview" id="videoPreview-container">
                    
                </div>
                <input id="videoInput" type="file" name="videoFiles" accept="video/mp4" multiple required>
                <div class="choice">
                    <button class="button-reset" type="reset" onclick="removeAllFile()">Cancel</button>
                    <button class="button-submit" type="submit">Submit</button>
                </div>
            </form>
        </div>
    </section>
    <script type="text/javascript" src="Resources/js/home.js"></script>
</body>
</html>