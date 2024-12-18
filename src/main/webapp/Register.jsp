<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="Resources/css/register.css">
    <title>Login</title>
</head>

<body>
    <section class="header">
        <div class="logo">
            <img src="Resources/img/video.png" alt="icon">
            <h1>Video merging</h1>
        </div>
    </section>
    <section  class="body">
        <div class="container">
        	<%
        		String error="";
        		if(request.getAttribute("errorMsg")!=null){
        			error=(String)request.getAttribute("errorMsg");
        		}
        	%>
            <form method="POST" action="register" class="form">
                <h2>Sign up</h2>
        		<span style="color: red"><%=error%></span>
                <div class="input-group">
                    <label for="fullName">Full name:</label>
                    <input type="text" id="fullName" name="fullName" placeholder="Enter your fullname" required>
                </div>
                <div class="input-group">
                    <label for="username">Email address:</label>
                    <input type="email" id="username" name="email" placeholder="Enter your email" required>
                </div>
                <div class="input-group">
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" placeholder="Enter your password" required>
                </div>
                <div class="input-group">
                    <label for="retypePassword">Retype password:</label>
                    <input type="password" id="retypePassword" name="retypePassword" placeholder="Enter your password" required>
                </div>
                <button type="submit" class="btn">Sign up</button>
                <div class="forgot-password">
                    <span>You have an account.<a href="login"> Sign in</a></span>
                </div>
            </form>
        </div>
    </section>
    <script>
    </script>
</body>
</html>