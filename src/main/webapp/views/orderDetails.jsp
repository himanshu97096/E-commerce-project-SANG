<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Details | SANG</title>

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/mainPageCss.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/orderDetails.css">
</head>
<body>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark fixed-top">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">
            <i class="fa fa-store"></i> SANG
        </a>
        <button class="navbar-toggler" type="button" data-toggle="collapse"
                data-target="#navbarNav" aria-controls="navbarNav"
                aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">
            <h5 class="text-light mr-3">${fullName}</h5>
            <ul class="navbar-nav ml-auto">
            	<li class="nav-item">
                    <a class="nav-link position-relative" href="cart">
                        <i class="fa fa-shopping-cart"></i> Cart
                        <c:if test="${cartCount > 0}">
                            <span class="badge badge-danger position-absolute notification">
                                ${cartCount}
                            </span>
                        </c:if>
                    </a>
                </li>
                <li class="nav-item"><a class="nav-link" href="/">Home</a></li>
                <li class="nav-item"><a class="nav-link" href="/order/myOrders">My Orders</a></li>
                <li class="nav-item"><a class="nav-link" href="/profileDisplay">Profile</a></li>
                <li class="nav-item"><a class="nav-link" href="/logout">Logout</a></li>
            </ul>
        </div>
    </div>
</nav>

<!-- Order Details -->
<div class="container">
    <div class="order-details-card">
        <h2>Order #${order.orderNumber}</h2>
        <div class="order-summary">
            <p><strong>Status:</strong> ${order.status}</p>
            <p><strong>Payment ID:</strong> ${order.paymentId}</p>
            <p><strong>Total Amount:</strong> ₹${order.totalAmount}</p>
            <p><strong>Placed On:</strong> ${order.createdAt}</p>
        </div>

        <h4 class="mt-4 text-light">Products:</h4>
		<c:forEach var="item" items="${order.orderItems}">
    <div class="product-card">
        <img src="${item.product_image}" alt="${item.product_name}">
        <div class="product-info">
            <h5>${item.product_name}</h5>
            <p>Quantity: ${item.quantity}</p>
            <p>Price: ₹${item.total_price}</p>
        </div>
    </div>
</c:forEach>

        <a href="javascript:history.back()" class="btn back-btn"><i class="fa fa-arrow-left"></i> Back to My Orders</a>
    </div>
</div>

<!-- Footer -->
<footer>
    <p>&copy; 2025 SANG. All Rights Reserved.</p>
</footer>

<!-- Scripts -->
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
</body>
</html>
