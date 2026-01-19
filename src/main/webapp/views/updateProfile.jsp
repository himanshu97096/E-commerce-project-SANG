<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Update Profile | SANG</title>

<!-- Google Fonts -->
<link
	href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;600;700&display=swap"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

<!-- Bootstrap -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="../css/mainPageCss.css">
<link rel="stylesheet" type="text/css" href="../css/updateProfile.css">

</head>
<body>

	<!-- Navbar -->
	<nav class="navbar navbar-expand-lg navbar-dark fixed-top">
		<div class="container-fluid">
			<a class="navbar-brand" href="/"> <i class="fa fa-store"></i>
				SANG
			</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarNav" aria-controls="navbarNav"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>

			<div class="collapse navbar-collapse" id="navbarNav">
				<h5 class="text-light mr-3">${fullName}</h5>
				<ul class="navbar-nav ml-auto">
					<li class="nav-item"><a class="nav-link position-relative"
						href="cart"> <i class="fa fa-shopping-cart"></i> Cart <c:if
								test="${cartCount > 0}">
								<span class="badge badge-danger position-absolute"
									style="top: -5px; right: 47px; font-size: 0.75rem; border-radius: 50%;">
									${cartCount} </span>
							</c:if>
					</a></li>
					<li class="nav-item"><a class="nav-link" href="order/myOrders">My Orders</a></li>
					<li class="nav-item"><a class="nav-link" href="/">Home</a></li>
					<li class="nav-item"><a class="nav-link" href="logout">Logout</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<!-- Profile Update Form -->
	<div class="container">
		<div class="form-container">
			<h2>Update Profile</h2>
			<form action="updateProfile" method="post">
				<div class="form-group">
					<label for="fullname">Full Name</label> <input type="text"
						class="form-control" name="fullname" id="fullname"
						placeholder="Full Name*" value="${fullname}" required>
				</div>
				<div class="form-group">
					<label for="email">Email Address</label> <input type="email"
						class="form-control" name="email" id="email" placeholder="Email*"
						value="${email}" required>
				</div>
				<div class="form-group">
					<label for="address">Shipping Address</label>
					<textarea class="form-control" name="address" id="address" rows="3"
						required>${address}</textarea>
				</div>
				<button type="submit" class="btn btn-custom btn-block">Update
					Profile</button>
			</form>
		</div>
	</div>

	<!-- Footer -->
	<footer>
		<p>&copy; SANG App. All Rights Reserved.</p>
	</footer>

	<!-- Scripts -->
	<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
</body>
</html>
