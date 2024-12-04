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
            <form class="form">
                <h2>Đăng ký tại đây</h2>
                <div class="input-group">
                    <label for="username">Họ và tên</label>
                    <input type="email" id="username" name="username" placeholder="Enter your username" required>
                </div>
                <div class="input-group">
                    <label for="username">Địa chỉ email</label>
                    <input type="email" id="username" name="username" placeholder="Enter your username" required>
                </div>
                <div class="input-group">
                    <label for="password">Mật khẩu</label>
                    <input type="password" id="password" name="password" placeholder="Enter your password" required>
                </div>
                <div class="input-group">
                    <label for="password">Nhập lại mật khẩu</label>
                    <input type="password" id="password" name="password" placeholder="Enter your password" required>
                </div>
                <button type="submit" class="btn">Đăng ký</button>
                <div class="forgot-password">
                    <span>Bạn đã có tài khoản?<a href="login"> Đăng nhập</a></span>
                </div>
            </form>
        </div>
    </section>
    <script>
    </script>
</body>
</html>