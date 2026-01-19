package com.sangspringproject.SANGSpringProject.controller;

import com.sangspringproject.SANGSpringProject.dao.orderDao;
import com.sangspringproject.SANGSpringProject.models.OrderItem;
import com.sangspringproject.SANGSpringProject.models.Cart;
import com.sangspringproject.SANGSpringProject.models.CartProduct;
import com.sangspringproject.SANGSpringProject.models.Order;
import com.sangspringproject.SANGSpringProject.models.User;
import com.sangspringproject.SANGSpringProject.services.cartService;
import com.sangspringproject.SANGSpringProject.services.userService;
import com.sangspringproject.SANGSpringProject.services.orderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private orderDao orderDao;
    
    @Autowired
    private orderService orderService;

    @Autowired
    private userService userService;

    @Autowired
    private cartService cartService;
    
    @PersistenceContext
    private EntityManager entityManager;

    // ✅ Checkout Page
    @GetMapping("/checkout")
    public String checkoutPage(Model model, Principal principal) {
        if (principal == null) return "redirect:/login";

        User user = userService.getUserByEmail(principal.getName());
        Cart cart = cartService.getCartByUser(user);

        model.addAttribute("user", user);
        model.addAttribute("cart", cart);
        model.addAttribute("total", cart.getCartProducts().stream()
                .mapToDouble(cp -> cp.getProduct().getPrice() * cp.getQuantity())
                .sum());

        return "checkout"; // checkout.jsp
    }

    // ✅ Place Order
    @PostMapping("/success")
    public String placeOrder(@RequestParam("fullname") String fullname,
            				 @RequestParam("address") String address,
            				 @RequestParam("paymentMethod") String paymentMethod,
            				 @RequestParam("razorpay_payment_id") String paymentId,
            				 Principal principal) {
        if (principal == null) return "redirect:/login";

        User user = userService.getUserByEmail(principal.getName());
        Cart cart = cartService.getCartByUser(user);
        List<CartProduct> cartProducts = cart.getCartProducts();
        double total = cartProducts.stream()
                .mapToDouble(cp -> cp.getProduct().getPrice() * cp.getQuantity())
                .sum();

        Order order = new Order();
        order.setFullname(fullname);
        order.setAddress(address);
        order.setPaymentMethod(paymentMethod);
        order.setPaymentId(paymentId); // ✅ store transaction id
        order.setStatus("Preparing for shipping");
        order.setUser(user);
        order.setCart(cart);
        order.setTotalAmount(total);
        
        // ✅ Generate unique order number (start from 100000)
        long orderCount = orderDao.countAllOrders(); // create a method in DAO
        order.setOrderNumber(String.valueOf(100000 + orderCount + 1));
        
        orderDao.saveOrder(order);
        
        for (CartProduct cp : cartProducts) {
            OrderItem item = new OrderItem(
                    order,
                    cp.getProduct(),
                    cp.getQuantity(),
                    cp.getProduct().getPrice()
            );
            order.getOrderItems().add(item);
        }

        orderDao.saveOrder(order);
        

        cartService.clearCart(user);// you already have saveCart in cartDao

        return "redirect:/order/success"; // orderSuccess.jsp
    }
    
    @GetMapping("/success")
    public String orderSuccessPage() {
        return "orderSuccess"; // JSP to show order success
    }
    
    @GetMapping("/myOrders")
    public String myOrders(Model model, Principal principal) {
        if (principal == null) return "redirect:/login";

        User user = userService.getUserByEmail(principal.getName());
        int userId = user.getId();
        List<Order> orders = orderDao.getOrdersByUser(userId);
        
        Cart cart = cartService.getCartByUser(user); // ensures a cart exists
		List<CartProduct> cartProducts = cart.getCartProducts();
		int cartCount = cartProducts.stream().mapToInt(CartProduct::getQuantity).sum();
		model.addAttribute("cartCount", cartCount);

        model.addAttribute("orders", orders);
        return "myOrders";  // JSP page
    }
    
    @GetMapping("/details/{id}")
    public String orderDetails(@PathVariable("id") int id, Model model, Principal principal) {
        if (principal == null) return "redirect:/login";

        Order order = orderService.getOrderWithItems(id);
        if (order == null) return "redirect:/order/myOrders";
        
        User user = userService.getUserByEmail(principal.getName());
        Cart cart = cartService.getCartByUser(user); // ensures a cart exists
		List<CartProduct> cartProducts = cart.getCartProducts();
		int cartCount = cartProducts.stream().mapToInt(CartProduct::getQuantity).sum();
		model.addAttribute("cartCount", cartCount);

        model.addAttribute("order", order);
        return "orderDetails"; // JSP
    }

}
