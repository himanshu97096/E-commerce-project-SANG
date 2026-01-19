<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${product.name} | SANG</title>

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;600;700&display=swap" rel="stylesheet">

    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">

    <!-- FontAwesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

    <!-- Custom CSS (use same theme as main) -->
    <link rel="stylesheet" type="text/css" href="../css/mainPageCss.css">
    <link rel="stylesheet" type="text/css" href="../css/productDetails.css">
</head>
<body>

<!-- Navbar (same as main page) -->
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
                    <a class="nav-link position-relative" href="/cart">
                        <i class="fa fa-shopping-cart"></i> Cart
                        <c:if test="${cartCount > 0}">
                            <span class="badge badge-danger position-absolute"
                                  style="top:-5px; right:47px; font-size:0.75rem; border-radius:50%;">
                                ${cartCount}
                            </span>
                        </c:if>
                    </a>
                </li>
                <li class="nav-item"><a class="nav-link" href="/profileDisplay">Profile</a></li>
                <li class="nav-item"><a class="nav-link" href="/logout">Logout</a></li>
            </ul>
        </div>
    </div>
</nav>

<!-- Product Details Section -->
<main class="container" style="margin-top: 80px;">
    <div class="row align-items-center">
        <!-- Left: Product Image -->
        <div class="col-md-6 text-center">
            <img src="${product.image}" alt="${product.name}" class="img-fluid rounded shadow"
                 style="max-height: 400px; background: #fff; padding: 15px;">
        </div>

        <!-- Right: Product Info -->
        <div class="col-md-6">
            <h2 class="product-title text-light">${product.name}</h2>
            <div class="product-details mt-3">
                <p><strong>Category:</strong> ${product.category.name}</p>
                <p><strong>Description:</strong> ${product.description}</p>
                <p><strong>Quantity:</strong> ${product.quantity}</p>
                <p><strong>Weight:</strong> ${product.weight} g</p>
                <p class="price h4 text-info">Price: ₹${product.price}</p>
            </div>

            <!-- Add to Cart Button -->
            <div class="mt-4">
                <a href="/cart/add/${product.id}" class="btn btn-custom">
                    <i class="fa fa-cart-plus"></i> Add to Cart
                </a>
            </div>
        </div>
    </div>
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
