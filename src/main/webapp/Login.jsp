<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="Resources/css/login.css">
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
        <div class="login-container">
        	<%
        		String error="";
        		if(request.getAttribute("errorMsg")!=null){
        			error=(String)request.getAttribute("errorMsg");
        		}
        	%>
            <form action="login" method="POST" class="login-form">
                <h2>Welcome Back</h2>
                <span style="color: red"><%=error%></span>
                <div class="input-group">
                    <label for="username">Email address:</label>
                    <input type="email" id="username" name="email" placeholder="Enter your username" required>
                </div>
                <div class="input-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" placeholder="Enter your password" required>
                </div>
                <button type="submit" class="btn">Login</button>
                <div class="forgot-password">
                    <span>You don't have an account<a href="register">Sign up</a></span>
                </div>
            </form>
        </div>
    </section>
    <script>
    </script>
</body>
</html>