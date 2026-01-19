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

</head>
<body>
    <div class="container">
        <div class="right-panel">
            <div class="login-box">
                <h2>Forgot Password</h2>
                <form action="/password_reset" method="post">
                    <div class="input-box">
                    	<label class="label-prop" for="email">Enter Your Registered Email</label>
                        <input type="email" name="user_email" id="password_reset" placeholder="Enter Your registered Emial" required>
                        <i class="fa fa-user"></i>
                    </div>
                    <button type="submit" value="Send-OTP" class="btn">Send OTP</button>
                </form>
                <div class="extra-links">
                    <p><a href="user_login">Already Have Account</a></p>
                    <p>New here? <a href="/register">Create Account</a></p>
                    <h3 class="text-center text-danger mt-3">${msg}</h3>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
