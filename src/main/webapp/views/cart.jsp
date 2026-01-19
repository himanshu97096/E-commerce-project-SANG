<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Your Cart | SANG</title>

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="../css/mainPageCss.css">
    <link rel="stylesheet" type="text/css" href="../css/cart.css">

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
            <h5>${fullName}</h5>
            <ul class="navbar-nav ml-auto">
                <li class="nav-item"><a class="nav-link" href="/">Home</a></li>
                <li class="nav-item"><a class="nav-link" href="order/myOrders">My Orders</a></li>
                <li class="nav-item"><a class="nav-link" href="profileDisplay">Profile</a></li>
                <li class="nav-item"><a class="nav-link" href="logout">Logout</a></li>
            </ul>
        </div>
    </div>
</nav>

<!-- Cart Section -->
<main class="container" style = "margin-top: 80px">
    <h1>Your Shopping Cart</h1>

    <c:if test="${empty cartProducts}">
        <p class="text-center text-light">Your cart is empty 😔</p>
    </c:if>

    <c:forEach var="cp" items="${cartProducts}">
        <div class="cart-card">
            <img src="${cp.product.image}" alt="${cp.product.name}">
            <div class="cart-details">
                <h5>${cp.product.name}</h5>
                <p>Price: ₹${cp.product.price}</p>
                <p>Quantity: ${cp.quantity}</p>
            </div>
            <div class="cart-actions">
                <form action="/cart/increase/${cp.id}" method="post" style="display:inline;">
        			<button type="submit" class="quantity-btn">+</button>
      			</form>

      			<form action="/cart/decrease/${cp.id}" method="post" style="display:inline;">
        			<button type="submit" class="quantity-btn">-</button>
      			</form>

      			<form action="/cart/delete/${cp.id}" method="post" style="display:inline;">
        			<button type="submit" class="remove-btn"><i class="fa fa-trash"></i></button>
      			</form>
            </div>
        </div>
    </c:forEach>

    <!-- Cart Summary -->
    <c:if test="${not empty cartProducts}">
        <div class="cart-summary mt-4">
            <h4>Total: ₹${total}</h4>
            <a href="/order/checkout" class="btn checkout-btn">Proceed to Checkout</a>
        </div>
    </c:if>
</main>

<!-- Footer -->
<footer>
    <p>&copy; SANG App. All Rights Reserved.</p>
</footer>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>

</body>
</html>
