package com.sangspringproject.SANGSpringProject.controller;

import com.sangspringproject.SANGSpringProject.dao.userDao;
import com.sangspringproject.SANGSpringProject.models.Cart;
import com.sangspringproject.SANGSpringProject.models.CartProduct;
import com.sangspringproject.SANGSpringProject.models.Category;
import com.sangspringproject.SANGSpringProject.models.Product;
import com.sangspringproject.SANGSpringProject.models.User;
import com.sangspringproject.SANGSpringProject.services.*;

import java.io.Console;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sangspringproject.SANGSpringProject.services.cartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.sangspringproject.SANGSpringProject.services.userService;
import com.sangspringproject.SANGSpringProject.services.productService;
import com.sangspringproject.SANGSpringProject.services.cartService;
import java.security.Principal;

@Controller
public class UserController {

	private final userService userService;
	private final productService productService;
	private userDao userDao;

	@Autowired
	private cartService cartService;

	@Autowired
	public UserController(userService userService, productService productService) {
		this.userService = userService;
		this.productService = productService;
	}

	@GetMapping("/register")
	public String registerUser() {
		return "register";
	}

	@GetMapping("/password_reset")
	public String password_reset() {
		return "password_reset";
	}

	@GetMapping("/verify_otp")
	public String verify_otp() {
		return "verify_otp";
	}

	@GetMapping("/updateProfile")
	public String updateProfile() {
		return "updateProfile";
	}

	@GetMapping("/update_password")
	public String update_password() {
		return "update_password";
	}

	@GetMapping("/buy")
	public String buy() {
		return "buy";
	}

	@GetMapping("/login")
	public ModelAndView userlogin(@RequestParam(required = false) String error) {
		ModelAndView mv = new ModelAndView("userLogin");
		if ("true".equals(error)) {
			mv.addObject("msg", "Please enter correct email and password");
		}
		return mv;
	}

	@GetMapping("/")
	public ModelAndView indexPage() {
		ModelAndView mView = new ModelAndView("index");
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.getUserByEmail(username);
		mView.addObject("fullName", user.getFullname());
		mView.addObject("username", username);
		List<Product> products = this.productService.getProducts();

		if (products.isEmpty()) {
			mView.addObject("msg", "No products are available");
		} else {
			mView.addObject("products", products);
		}
		Cart cart = cartService.getCartByUser(user); // ensures a cart exists
		List<CartProduct> cartProducts = cart.getCartProducts();

		int cartCount = cartProducts.stream().mapToInt(CartProduct::getQuantity).sum();
		mView.addObject("cartCount", cartCount);

		return mView;
	}

	@GetMapping("/user/products")
	public ModelAndView getproduct() {

		ModelAndView mView = new ModelAndView("uproduct");

		List<Product> products = this.productService.getProducts();

		if (products.isEmpty()) {
			mView.addObject("msg", "No products are available");
		} else {
			mView.addObject("products", products);
		}

		return mView;
	}

	@RequestMapping(value = "newuserregister", method = RequestMethod.POST)
	public ModelAndView newUseRegister(@ModelAttribute User user) {
		// Check if email already exists in database
		boolean existsByEmial = this.userService.checkUserExistsByEmial(user.getEmail());

		if (!existsByEmial) {
			System.out.println(user.getEmail());
			user.setRole("ROLE_NORMAL");
			this.userService.addUser(user);

			System.out.println("New user created: " + user.getFullname());
			ModelAndView mView = new ModelAndView("userLogin");
			return mView;
		} else {
			System.out.println("New user not created - username taken: " + user.getFullname());
			ModelAndView mView = new ModelAndView("register");
			mView.addObject("msg", user.getEmail() + " is already registered. Please Sign In.");
			return mView;
		}
	}

	@GetMapping("/profileDisplay")
	public String profileDisplay(Model model, HttpServletRequest request) {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.getUserByEmail(email);
		
		Cart cart = cartService.getCartByUser(user); // ensures a cart exists
		List<CartProduct> cartProducts = cart.getCartProducts();
		int cartCount = cartProducts.stream().mapToInt(CartProduct::getQuantity).sum();
		model.addAttribute("cartCount", cartCount);

		if (user != null) {
			model.addAttribute("userid", user.getId());
			model.addAttribute("fullname", user.getFullname());
			model.addAttribute("email", user.getEmail());
			model.addAttribute("password", user.getPassword());
			model.addAttribute("address", user.getAddress());
		} else {
			model.addAttribute("msg", "User not found");
		}

		return "updateProfile";
	}

	@GetMapping("product/{id}")
	public String getProductDetails(@PathVariable("id") int id, Model model) {
		Product product = productService.getProduct(id); // fetch product by ID
		if (product != null) {
			model.addAttribute("product", product);
		} else {
			model.addAttribute("msg", "Product not found!");
		}
		return "productDetails"; // JSP page name
	}

	// for Learning purpose of model
	@GetMapping("/test")
	public String Test(Model model) {
		System.out.println("test page");
		model.addAttribute("author", "jay gajera");
		model.addAttribute("id", 40);

		List<String> friends = new ArrayList<String>();
		model.addAttribute("f", friends);
		friends.add("xyz");
		friends.add("abc");

		return "test";
	}

	// for learning purpose of model and view ( how data is pass to view)

	@GetMapping("/test2")
	public ModelAndView Test2() {
		System.out.println("test page");
		// create modelandview object
		ModelAndView mv = new ModelAndView();
		mv.addObject("name", "jay gajera 17");
		mv.addObject("id", 40);
		mv.setViewName("test2");

		List<Integer> list = new ArrayList<Integer>();
		list.add(10);
		list.add(25);
		mv.addObject("marks", list);
		return mv;

	}

	@PostMapping("/updateProfile")
	public String updateProfile(@RequestParam("fullname") String fullName, @RequestParam("email") String email,
			@RequestParam("address") String address, Model model) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.getUserByEmail(username);

		if (user != null) {
			user.setFullname(fullName);
			user.setEmail(email);
			user.setAddress(address);

			userService.updateUser(user); // call update in your service
			model.addAttribute("message", "Profile updated successfully!");
			return "redirect:/"; // redirect to profile page
		} else {
			model.addAttribute("error", "User not found!");
			return "updateProfile";
		}
	}

}