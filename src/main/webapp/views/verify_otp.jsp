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
                <h2>Enter OTP</h2>
                <form action="verify_otp" method="post">
                    <div class="input-box">
                        <input type="hidden" name="email" value="${email}">
                        <input name="otp" placeholder="Enter OTP" required>
                        <i class="fa fa-user"></i>
                    </div>
                    <button type="submit" class="btn">Verify</button>
                </form>
                <div class="extra-links">
                    <p><a href="user_login">Want To Sign-In</a></p>
                    <p>New here? <a href="/register">Create Account</a></p>
                    <h3 class="text-center text-danger mt-3">${msg}</h3>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
