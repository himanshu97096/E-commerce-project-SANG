package com.sangspringproject.SANGSpringProject.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sangspringproject.SANGSpringProject.models.Category;
import com.sangspringproject.SANGSpringProject.models.Product;
import com.sangspringproject.SANGSpringProject.models.User;
import com.sangspringproject.SANGSpringProject.services.categoryService;
import com.sangspringproject.SANGSpringProject.services.productService;
import com.sangspringproject.SANGSpringProject.services.userService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private final userService userService;
	private final categoryService categoryService;
	private final productService productService;

	@Autowired
	public AdminController(userService userService, categoryService categoryService, productService productService) {
		this.userService = userService;
		this.categoryService = categoryService;
		this.productService = productService;
	}
	
	// inside AdminController class
	@Autowired
	private Cloudinary cloudinary;  // Cloudinary bean from config
	
	@GetMapping("/index")
	public String index(Model model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("username", username);
		return "index";			
	}
	
	@GetMapping("login")
	public ModelAndView adminlogin(@RequestParam(required = false) String error) {
	    ModelAndView mv = new ModelAndView("adminlogin");
	    if ("true".equals(error)) {
	        mv.addObject("msg", "Invalid username or password. Please try again.");
	    }
	    return mv;
	}
	
	@GetMapping( value={"/","Dashboard"})
	public ModelAndView adminHome(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    ModelAndView mv = new ModelAndView("adminHome");
	    mv.addObject("admin", authentication.getName());
	    return mv;
	}
	
	@GetMapping("categories")
	public ModelAndView getcategory() {
		ModelAndView mView = new ModelAndView("categories");
		List<Category> categories = this.categoryService.getCategories();
		mView.addObject("categories", categories);
		return mView;
	}
	
	@PostMapping("/categories")
	public String addCategory(@RequestParam("categoryname") String category_name)
	{
		System.out.println(category_name);
		
		Category category =  this.categoryService.addCategory(category_name);
		if(category.getName().equals(category_name)) {
			return "redirect:categories";
		}else {
			return "redirect:categories";
		}
	}
	
	@GetMapping("categories/delete")
	public String removeCategoryDb(@RequestParam("id") int id)
	{	
			this.categoryService.deleteCategory(id);
			return "redirect:/admin/categories";
	}
	
	@GetMapping("categories/update")
	public String updateCategory(@RequestParam("categoryid") int id, @RequestParam("categoryname") String categoryname)
	{
		Category category = this.categoryService.updateCategory(id, categoryname);
		return "redirect:/admin/categories";
	}

	
//	 --------------------------Remaining --------------------
	@GetMapping("products")
	public ModelAndView getproduct() {
		ModelAndView mView = new ModelAndView("products");

		List<Product> products = this.productService.getProducts();
		
		if (products.isEmpty()) {
			mView.addObject("msg", "No products are available");
		} else {
			mView.addObject("products", products);
		}
		return mView;
	}
	
	@GetMapping("products/add")
	public ModelAndView addProduct() {
		ModelAndView mView = new ModelAndView("productsAdd");
		List<Category> categories = this.categoryService.getCategories();
		mView.addObject("categories",categories);
		return mView;
	}
	
	@RequestMapping(value = "products/add",method=RequestMethod.POST)
	public String addProduct(
	        @RequestParam("name") String name,
	        @RequestParam("categoryid") int categoryId,
	        @RequestParam("price") int price,
	        @RequestParam("weight") int weight,
	        @RequestParam("quantity") int quantity,
	        @RequestParam("description") String description,
	        @RequestParam("file") MultipartFile file) {

	    try {
	        // 1) Upload to Cloudinary (folder = products)
	        Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
	                ObjectUtils.asMap("folder", "products"));

	        // 2) Get secure URL from result
	        String imageUrl = uploadResult.get("secure_url").toString(); // <-- exact line you asked for

	        // 3) Build & save product
	        Category category = this.categoryService.getCategory(categoryId); // use existing service
	        Product product = new Product();
	        product.setName(name);
	        product.setCategory(category);
	        product.setPrice(price);
	        product.setWeight(weight);
	        product.setQuantity(quantity);
	        product.setDescription(description);
	        product.setImage(imageUrl); // save Cloudinary URL

	        this.productService.addProduct(product);

	    } catch (Exception e) {
	        e.printStackTrace();
	        // You may want to add error message to model & return view
	        return "redirect:/admin/products?error";
	    }

	    return "redirect:/admin/products";
	}
	
	@GetMapping("products/update/{id}")
	public ModelAndView updateproduct(@PathVariable("id") int id) {
		
		ModelAndView mView = new ModelAndView("productsUpdate");
		Product product = this.productService.getProduct(id);
		List<Category> categories = this.categoryService.getCategories();

		mView.addObject("categories",categories);
		mView.addObject("product", product);
		return mView;
	}

	@RequestMapping(value = "products/update/{id}",method=RequestMethod.POST)
	public String updateProduct(
	        @PathVariable("id") int id,
	        @RequestParam("name") String name,
	        @RequestParam("categoryid") int categoryId,
	        @RequestParam("price") int price,
	        @RequestParam("weight") int weight,
	        @RequestParam("quantity") int quantity,
	        @RequestParam("description") String description,
	        @RequestParam(value = "productImage", required = false) MultipartFile file,
	        @RequestParam(value = "existingImage", required = false) String existingImage) {

	    try {
	        Product product = this.productService.getProduct(id);
	        if (product == null) {
	            return "redirect:/admin/products?notfound";
	        }

	        product.setName(name);
	        Category category = this.categoryService.getCategory(categoryId);
	        product.setCategory(category);
	        product.setPrice(price);
	        product.setWeight(weight);
	        product.setQuantity(quantity);
	        product.setDescription(description);

	        if (file != null && !file.isEmpty()) {
	            // 1. Delete old image from Cloudinary
	            String oldImageUrl = product.getImage();
	            if (oldImageUrl != null && !oldImageUrl.isEmpty()) {
	                try {
	                    // Extract public_id from the old URL
	                    String publicId = oldImageUrl.substring(
	                            oldImageUrl.lastIndexOf("/") + 1,
	                            oldImageUrl.lastIndexOf(".")
	                    );
	                    cloudinary.uploader().destroy("products/" + publicId, ObjectUtils.emptyMap());
	                } catch (Exception ex) {
	                    System.out.println("Failed to delete old image: " + ex.getMessage());
	                }
	            }

	            // 2. Upload new image
	            Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
	                    ObjectUtils.asMap("folder", "products"));
	            String imageUrl = uploadResult.get("secure_url").toString();
	            product.setImage(imageUrl);
	        } else {
	            // Keep old image
	            product.setImage(existingImage != null ? existingImage : product.getImage());
	        }

	        this.productService.updateProduct(product.getId(), product);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return "redirect:/admin/products?error";
	    }
	    return "redirect:/admin/products";
	}
	
	@GetMapping("products/delete")
	public String removeProduct(@RequestParam("id") int id)
	{
		Product product = this.productService.getProduct(id);
		String oldImageUrl = product.getImage();
        if (oldImageUrl != null && !oldImageUrl.isEmpty()) {
            try {
                // Extract public_id from the old URL
                String publicId = oldImageUrl.substring(
                        oldImageUrl.lastIndexOf("/") + 1,
                        oldImageUrl.lastIndexOf(".")
                );
                cloudinary.uploader().destroy("products/" + publicId, ObjectUtils.emptyMap());
            } catch (Exception ex) {
                System.out.println("Failed to delete old image: " + ex.getMessage());
            }
        }
		this.productService.deleteProduct(id);
		return "redirect:/admin/products";
	}
	
	@PostMapping("products")
	public String postproduct() {
		return "redirect:/admin/categories";
	}
	
	@GetMapping("customers")
	public ModelAndView getCustomerDetail() {
		ModelAndView mView = new ModelAndView("displayCustomers");
		List<User> users = this.userService.getUsers();
		mView.addObject("customers", users);
		return mView;
	}
	
	
	@GetMapping("profileDisplay")
	public String profileDisplay(Model model) {
		String displayusername,displaypassword,displayemail,displayaddress;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommjava","root","");
			PreparedStatement stmt = con.prepareStatement("select * from users where username = ?"+";");
			
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			stmt.setString(1, username);
			
			ResultSet rst = stmt.executeQuery();
			
			if(rst.next())
			{
			int userid = rst.getInt(1);
			displayusername = rst.getString(2);
			displayemail = rst.getString(3);
			displaypassword = rst.getString(4);
			displayaddress = rst.getString(5);
			model.addAttribute("userid",userid);
			model.addAttribute("username",displayusername);
			model.addAttribute("email",displayemail);
			model.addAttribute("password",displaypassword);
			model.addAttribute("address",displayaddress);
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception:"+e);
		}
		System.out.println("Hello");
		return "updateProfile";
	}
	
	@RequestMapping(value = "updateuser",method=RequestMethod.POST)
	public String updateUserProfile(@RequestParam("userid") int userid,@RequestParam("fullname") String fullname, @RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("address") String address) 
	
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommjava","root","");
			
			PreparedStatement pst = con.prepareStatement("update users set fullname= ?,email = ?,password= ?, address= ? where uid = ?;");
			pst.setString(1, fullname);
			pst.setString(2, email);
			pst.setString(3, password);
			pst.setString(4, address);
			pst.setInt(5, userid);
			int i = pst.executeUpdate();	
			
			Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
		            email,
		            password,
		            SecurityContextHolder.getContext().getAuthentication().getAuthorities());

		    SecurityContextHolder.getContext().setAuthentication(newAuthentication);
		}
		catch(Exception e)
		{
			System.out.println("Exception:"+e);
		}
		return "redirect:index";
	}

}
