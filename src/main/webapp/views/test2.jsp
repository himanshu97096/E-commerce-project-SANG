<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${product.name} | MyApp</title>

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="../css/style.css">

    <!-- FontAwesome for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    
    <style>
        .container {
            display: flex;
            width: 900px;
            height: 550px;
            border-radius: 25px;
            overflow: hidden;
            box-shadow: 0 20px 60px rgba(0,0,0,0.5);
            animation: fadeIn 1.2s ease-in-out;
            background: rgba(255,255,255,0.08);
            backdrop-filter: blur(15px);
        }

        /* Left panel for product image */
        .left-panel {
            flex: 1;
            background-size: cover;
            background-position: center;
            display: flex;
            justify-content: center;
            align-items: center;
            overflow: hidden;
        }

        .left-panel img {
            max-width: 100%;
            max-height: 100%;
            border-radius: 15px;
        }

        /* Right panel for product details */
        .right-panel {
            flex: 1;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            padding: 25px;
            color: #fff;
            overflow-y: auto;
        }

        .product-title {
            font-size: 24px;
            font-weight: 700;
            margin-bottom: 15px;
        }

        .product-details p {
            margin: 8px 0;
            font-size: 15px;
            line-height: 1.4;
        }

        .price {
            font-size: 20px;
            font-weight: bold;
            color: #00f2fe;
            margin: 15px 0;
        }

        .btn {
            width: 100%;
            padding: 14px;
            border: none;
            border-radius: 12px;
            background: linear-gradient(45deg, #00f2fe, #4facfe);
            color: #fff;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: 0.3s;
        }

        .btn:hover {
            background: linear-gradient(45deg, #4facfe, #00f2fe);
            transform: scale(1.05);
            box-shadow: 0 0 15px #00f2fe, 0 0 30px #4facfe;
        }

        /* Scrollable right panel */
        .right-panel::-webkit-scrollbar {
            width: 8px;
        }

        .right-panel::-webkit-scrollbar-thumb {
            background: rgba(255,255,255,0.3);
            border-radius: 10px;
        }
    </style>
</head>


<body>
    <div class="container">
        <!-- Left: Product Image -->
        <div class="left-panel">
            <img src="${product.image}" alt="${product.name}">
        </div>

        <!-- Right: Product Details -->
        <div class="right-panel">
            <div>
                <h2 class="product-title">${product.name}</h2>
                <div class="product-details">
                    <p><strong>Category:</strong> ${product.category.categoryName}</p>
                    <p><strong>Description:</strong> ${product.description}</p>
                    <p><strong>Quantity:</strong> ${product.quantity}</p>
                    <p><strong>Weight:</strong> ${product.weight} g</p>
                    <p class="price">Price: ₹${product.price}</p>
                </div>
            </div>

            <!-- Add to Cart Button -->
            <div>
                <a href="/cart/add?id=${product.id}">
                    <button class="btn"><i class="fa fa-cart-plus"></i> Add to Cart</button>
                </a>
            </div>
        </div>
    </div>
</body>
</html>
