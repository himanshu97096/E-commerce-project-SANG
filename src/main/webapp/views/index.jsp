<%@page import="java.sql.*"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Index | SANG</title>

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="../css/mainPageCss.css">
    
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
                <li class="nav-item"><a class="nav-link" href="profileDisplay">Profile</a></li>
                <li class="nav-item"><a class="nav-link" href="order/myOrders">My Orders</a></li>
                <li class="nav-item"><a class="nav-link" href="logout">Logout</a></li>
            </ul>
        </div>
    </div>
</nav>


<!-- Main Content -->
<main class="container" style = "margin-top: 60px">
    <h1>Explore Our Products</h1>
    <div class="row">
        <c:forEach var="product" items="${products}">
            <div class="col-md-3 mb-4">
                <!-- Card links to productDetails page -->
                <div class="card" onclick="window.location.href='/product/${product.id}'">
                    <img class="card-img-top" src="${product.image}" alt="Product">
                    <div class="card-body">
                        <h5 class="card-title">${product.name}</h5>
                        <p>Price: ₹${product.price}</p>
                        <a href="/cart/add/${product.id}" class="btn btn-custom btn-block" onclick="event.stopPropagation()">Add to Cart</a>
                        <c:if test="${not empty successMessage}">
    						<div class="alert alert-success text-center">${successMessage}</div>
						</c:if>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</main>

<!-- Footer -->
<footer>
    <p>&copy; SANG App. All Rights Reserved.</p>
</footer>

<!-- Scripts -->
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
</body>
</html>
