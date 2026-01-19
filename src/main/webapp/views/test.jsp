<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login | MyApp</title>

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="../css/style.css">

    <!-- FontAwesome for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>
    <div class="container">
        <!-- Left branding -->
        <div class="left-panel" style="background-image: url('images/Full_Logo.png'); width: 300px; height: 200px;">

        </div>

        <!-- Right login form -->
        <div class="right-panel">
            <div class="login-box">
                <h2>Login</h2>
                <form action="/userloginvalidate" method="post">
                    <div class="input-box">
                        <input type="text" name="username" id="username" placeholder="Email or Username" required>
                        <i class="fa fa-user"></i>
                    </div>
                    <div class="input-box">
                        <input type="password" name="password" id="password" placeholder="Password" required>
                        <!-- Eye icon for show/hide password -->
                        <i class="fa fa-eye" id="togglePassword" style="cursor:pointer;"></i>
                    </div>
                    <button type="submit" value="Login" class="btn">Sign In</button>
                </form>
                <div class="extra-links">
                    <p><a href="/password_reset">Forgot Password?</a></p>
                    <p>New here? <a href="/register">Create Account</a></p>
                    <h3 class="text-center text-danger mt-3">${msg}</h3>
                </div>
            </div>
        </div>
    </div>
	<script src="../scripts/toggle_eye.js"></script>
</body>
</html>
