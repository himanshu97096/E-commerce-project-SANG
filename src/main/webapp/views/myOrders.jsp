<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Orders | SANG</title>

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="../css/mainPageCss.css">
    <link rel="stylesheet" type="text/css" href="../css/orders.css">

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
                    <a class="nav-link position-relative" href="\cart">
                        <i class="fa fa-shopping-cart"></i> Cart
                        <c:if test="${cartCount > 0}">
                            <span class="badge badge-danger position-absolute notification">
                                ${cartCount}
                            </span>
                        </c:if>
                    </a>
                </li>
                <li class="nav-item"><a class="nav-link" href="/">Home</a></li>
                <li class="nav-item"><a class="nav-link" href="/profileDisplay">Profile</a></li>
                <li class="nav-item"><a class="nav-link" href="/logout">Logout</a></li>
            </ul>
        </div>
    </div>
</nav>

<!-- My Orders Section -->
<main class="container" style="margin-top: 90px;">
    <h2 class="text-center text-light mb-4">My Orders</h2>

    <c:if test="${empty orders}">
        <p class="text-center text-light">You have not placed any orders yet 😔</p>
    </c:if>

    <c:forEach var="order" items="${orders}">
        <div class="order-card">
            <div class="order-header">
                <h5>Order #${order.orderNumber}</h5>
                <span class="status-badge 
                    <c:choose>
                        <c:when test="${order.status eq 'Preparing for shipping'}">status-preparing</c:when>
                        <c:when test="${order.status eq 'In Transit'}">status-transit</c:when>
                        <c:when test="${order.status eq 'Delivered'}">status-delivered</c:when>
                    </c:choose>">
                    ${order.status}
                </span>
            </div>
            <div class="order-details">
                <p><strong>Date:</strong> ${order.createdAt}</p>
                <p><strong>Total:</strong> ₹${order.totalAmount}</p>
                <p><strong>Payment ID:</strong> ${order.paymentId}</p>
            </div>
            <div class="text-right">
                <a href="/order/details/${order.id}" class="view-btn">View Details</a>
            </div>
        </div>
    </c:forEach>
</main>

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
