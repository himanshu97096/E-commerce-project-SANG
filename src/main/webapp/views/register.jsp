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
            <div class="login-box" style="width:50%">
                <h2>SignUp</h2>
                <form action="newuserregister" method="post">

                    <div class="input-box">
                    	<label class="label-prop" for="email">Email Address</label>
                        <input type="text" placeholder="Enter Your Email" name="email" id="email" pattern=".{6,}" title="Must be at least 6 characters" required>
                        <i class="fa fa-user"></i>
                    </div>
                    <div class="input-box">
                    	<label class="label-prop" for="password">Password</label>
                        <input type="password" name="password" id="password" placeholder="Enter Your Password" required>
                        <i class="fa fa-user"></i>
                    </div>
                    <div class="input-box">
                    	<label class="label-prop" for="Addres">Shipping Address</label>
                        <input name="address" placeholder="Enter Your Address" required>
                        <i class="fa fa-lock"></i>
                    </div>
                    <div class="input-box">
                    	<label class="label-prop" for="Full Name">Full Name</label>
                        <input name="fullname" placeholder="Enter Your Name" required>
                        <i class="fa fa-lock"></i>
                    </div>
                    <button type="submit" value="Register" class="btn">Register</button>
                </form>
                <div class="extra-links">
                    <p><a href="password_reset">Forgot Password?</a></p>
                    <p class="label-prop">Already Have Account: <a href="/userLogin">Sign In</a></p>
                    <h3 class="text-center text-danger mt-3">${msg}</h3>
                </div>
            </div>
        </div>
    </div>

    <!-- FontAwesome -->
    <!-- script src="https://kit.fontawesome.com/6c45e4b75a.js" crossorigin="anonymous"-->
</body>
</html>
