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
    
    <script>
        function validatePasswords() {
            let newPassword = document.getElementsByName("newPassword")[0].value;
            let rePassword = document.getElementsByName("re_newPassword")[0].value;

            if (newPassword !== rePassword) {
                alert("Passwords do not match!");
                return false; // prevent form submission
            }
            return true; // allow submission
        }
    </script>

</head>
<body>
    <div class="container">
        <div class="right-panel">
            <div class="login-box">
                <h2>Reset Password</h2>
                <form action="update_password" method="post" onsubmit="return validatePasswords()">
                    <div class="input-box">
                    	<input type="hidden" name="email" value="${email}">
                    	<label class="label-prop" for="email">Enter New Password</label>
                        <input type="password" name="newPassword" placeholder="Enter New Password" minlength="6" required>
                        <label class="label-prop" for="email">Re-Enter New Password</label>
                        <input type="password" name="re_newPassword" placeholder="Re-Enter New Password" minlength="6" required>
                        <i class="fa fa-user"></i>
                    </div>
                    <button type="submit" class="btn">Update Password</button>
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
