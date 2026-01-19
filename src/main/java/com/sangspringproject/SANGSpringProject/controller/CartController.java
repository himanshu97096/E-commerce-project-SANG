package com.sangspringproject.SANGSpringProject.controller;

import com.sangspringproject.SANGSpringProject.models.Cart;
import com.sangspringproject.SANGSpringProject.models.CartProduct;
import com.sangspringproject.SANGSpringProject.models.Product;
import com.sangspringproject.SANGSpringProject.models.User;
import com.sangspringproject.SANGSpringProject.services.cartService;
import com.sangspringproject.SANGSpringProject.services.productService;
import com.sangspringproject.SANGSpringProject.services.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private cartService cartService;

    @Autowired
    private productService productService;

    @Autowired
    private userService userService;

    // VIEW current user's cart: GET /cart
    @GetMapping
    public String viewCart(Principal principal, Model model) {
        if (principal == null) return "redirect:/login";

        User user = userService.getUserByEmail(principal.getName());
        Cart cart = cartService.getCartByUser(user); // ensures a cart exists
        List<CartProduct> cartProducts = cart.getCartProducts();

        double total = cartProducts.stream()
                .mapToDouble(cp -> cp.getProduct().getPrice() * cp.getQuantity())
                .sum();
        
        int cartCount = cartProducts.stream().mapToInt(CartProduct::getQuantity).sum();

        model.addAttribute("cartProducts", cartProducts);
        model.addAttribute("cartCount", cartCount);
        model.addAttribute("total", total);
        model.addAttribute("fullName", user.getFullname());
        return "cart"; // cart.jsp
    }

    // ADD product to cart (path variable): GET /cart/add/{productId}
    @GetMapping("/add/{productId}")
    public String addToCart(@PathVariable("productId") int productId, Principal principal) {
        if (principal == null) return "redirect:/login";

        User user = userService.getUserByEmail(principal.getName());
        Product product = productService.getProduct(productId);
        if (product == null) return "redirect:/"; // product not found

        cartService.addToCart(user, product);
        return "redirect:/";
    }

    // INCREASE quantity: POST /cart/increase/{cartProductId}
    @PostMapping("/increase/{cartProductId}")
    public String increaseQty(@PathVariable("cartProductId") int cartProductId, Principal principal) {
        if (principal == null) return "redirect:/login";
        cartService.updateQuantity(cartProductId, +1);
        return "redirect:/cart";
    }

    // DECREASE quantity: POST /cart/decrease/{cartProductId}
    @PostMapping("/decrease/{cartProductId}")
    public String decreaseQty(@PathVariable("cartProductId") int cartProductId, Principal principal) {
        if (principal == null) return "redirect:/login";
        cartService.updateQuantity(cartProductId, -1);
        return "redirect:/cart";
    }

    // DELETE cart-product: POST /cart/delete/{cartProductId}
    @PostMapping("/delete/{cartProductId}")
    public String deleteFromCart(@PathVariable("cartProductId") int cartProductId, Principal principal) {
        if (principal == null) return "redirect:/login";
        cartService.removeFromCart(cartProductId);
        return "redirect:/cart";
    }
}
