<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Checkout | SANG</title>

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="../css/mainPageCss.css">
	<link rel="stylesheet" type="text/css" href="../css/checkout.css">
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
            <h5 class="text-light mr-3">${user.fullname}</h5>
            <ul class="navbar-nav ml-auto">
                <li class="nav-item"><a class="nav-link" href="/">Home</a></li>
                <li class="nav-item"><a class="nav-link" href="/cart">Cart</a></li>
                <li class="nav-item"><a class="nav-link" href="profileDisplay">Profile</a></li>
                <li class="nav-item"><a class="nav-link" href="logout">Logout</a></li>
            </ul>
        </div>
    </div>
</nav>

<!-- Checkout Form -->
<div class="container d-flex justify-content-center">
    <div class="checkout-container">
        <h2>Checkout</h2>
        <form action="/order/place" method="post">
            <div class="form-group">
                <label for="fullname">Full Name</label>
                <input type="text" class="form-control" name="fullname" id="fullname" 
                       value="${user.fullname}" required>
            </div>
            <div class="form-group">
                <label for="address">Shipping Address</label>
                <textarea class="form-control" name="address" id="address" rows="3" required>${user.address}</textarea>
            </div>

            <div class="form-group">
                <label>Payment Method</label><br>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="paymentMethod" value="ONLINE">
                    <label class="form-check-label">Online Payment</label>
                </div>
            </div>

            <!-- Order Summary -->
            <div class="summary-box">
                <h5>Order Summary</h5>
                <c:forEach var="cp" items="${cart.cartProducts}">
                    <p>${cp.product.name} × ${cp.quantity} = ₹${cp.product.price * cp.quantity}</p>
                </c:forEach>
                <hr>
                <h4>Total: ₹${total}</h4>
            </div>

            <button id="payBtn" type="button" class="btn-checkout">Pay Now</button>
        </form>
    </div>
</div>


<!-- Footer -->
<footer>
    <p>&copy; 2025 SANG. All Rights Reserved.</p>
</footer>

<!-- Scripts -->
<script>
document.getElementById("payBtn").onclick = function(e){
    e.preventDefault();

    // Convert total to integer (Razorpay needs amount in INR, backend converts to paise)
    let amount = ${total};

    fetch("/payment/createOrder?amount=" + amount, { method: "POST" })
        .then(response => response.json())
        .then(order => {
            if(order.error){
                alert("Error: " + order.error);
                return;
            }
            var options = {
                "key": "rzp_test_RIlXdQyidkY1TJ",  // ✅ Replace with your test key
                "amount": order.amount,
                "currency": "INR",
                "name": "SANG Store",
                "description": "Order Payment",
                "order_id": order.id,
                "handler": function (response){
                    // Send payment success info to backend
                    fetch("/order/success", {
                        method: "POST",
                        headers: { "Content-Type": "application/x-www-form-urlencoded" },
                        body: "razorpay_payment_id=" + response.razorpay_payment_id 
                              + "&fullname=" + encodeURIComponent(document.getElementById("fullname").value)
                              + "&address=" + encodeURIComponent(document.getElementById("address").value)
                              + "&paymentMethod=ONLINE"
                    }).then(() => {
                        window.location.href = "/order/success";
                    });
                },
                "theme": {
                    "color": "#00f2fe"
                }
            };
            var rzp1 = new Razorpay(options);
            rzp1.open();
        });
}
</script>
<script src="https://checkout.razorpay.com/v1/checkout.js"></script>
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
</body>
</html>
