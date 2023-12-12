package com.jtspringproject.JtSpringProject.controller;

import com.jtspringproject.JtSpringProject.models.Cart;
import com.jtspringproject.JtSpringProject.models.Product;
import com.jtspringproject.JtSpringProject.models.User;

import java.io.Console;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.jtspringproject.JtSpringProject.services.cartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.jtspringproject.JtSpringProject.services.userService;
import com.jtspringproject.JtSpringProject.services.productService;
import com.jtspringproject.JtSpringProject.services.cartService;



@Controller
public class UserController{
	
	@Autowired
	private userService userService;

	@Autowired
	private productService productService;

	private User yo;

	@GetMapping("/register")
	public String registerUser()
	{
		return "register";
	}

	@GetMapping("/buy")
	public String buy()
	{
		return "buy";
	}
	

	@GetMapping("/")
	public String userlogin(Model model) {
		
		return "userLogin";
	}

	@GetMapping("/logout")
	public String userLogout(Model model) {

		return "userLogin";
	}

	@GetMapping("/cartproduct")
	public String cartproduct(Model model) {

		return "cartproduct";
	}


	@RequestMapping(value = "userloginvalidate", method = RequestMethod.POST)
	public ModelAndView userlogin( @RequestParam("username") String username, @RequestParam("password") String pass,Model model,HttpServletResponse res) {
		
		System.out.println(pass);
		User u = this.userService.checkLogin(username, pass);
		yo = u;
		System.out.println(u.getUsername());

		if(u.getUsername()!=null && u.getRole().equals("ROLE_ADMIN"))
		{
			ModelAndView mView = new ModelAndView("userLogin");
			mView.addObject("message", "Please go to admin login page to login as an admin");
			return mView;
		}


		if(u.getUsername()!=null && u.getUsername().equals(username)) {	//added null exception handling
			
			res.addCookie(new Cookie("username", u.getUsername()));
			ModelAndView mView  = new ModelAndView("index");	
			mView.addObject("user", u);
			List<Product> products = this.productService.getProducts();

			if (products.isEmpty()) {
				mView.addObject("msg", "No products are available");
			} else {
				mView.addObject("products", products);
			}
			return mView;

		}else {
			ModelAndView mView = new ModelAndView("userLogin");
			mView.addObject("message", "Please enter correct email and password");
			return mView;
		}
		
	}
	
	
	@GetMapping("/user/products")
	public ModelAndView getproduct() {

		ModelAndView mView = new ModelAndView("uproduct");

		List<Product> products = this.productService.getProducts();

		if(products.isEmpty()) {
			mView.addObject("msg","No products are available");
		}else {
			mView.addObject("products",products);
		}

		return mView;
	}
	@RequestMapping(value = "newuserregister", method = RequestMethod.POST)
	public ModelAndView newUseRegister(@ModelAttribute User user)
	{
		System.out.println("resgiter user clicked!");
		//checking if any required feild is not entered correct or not?
		if(user.getUsername() != null && !user.getUsername().trim().isEmpty()
				&& user.getPassword() != null && !user.getPassword().trim().isEmpty()
		 && user.getEmail() != null && !user.getEmail().trim().isEmpty())
		{
			System.out.println(user.getEmail());
			user.setRole("ROLE_NORMAL");
			this.userService.addUser(user);
//			return "redirect:/";
			ModelAndView mView = new ModelAndView("userLogin");
			mView.addObject("success", "User has been registered you can login now");
			return mView;
		}
		else{
			ModelAndView mView = new ModelAndView("register");
			mView.addObject("message", "error in register");
			return mView;
		}


	}
	
	
	
	   //for Learning purpose of model
		@GetMapping("/test")
		public String Test(Model model)
		{
			System.out.println("test page");
			model.addAttribute("author","jay gajera");
			model.addAttribute("id",40);
			
			List<String> friends = new ArrayList<String>();
			model.addAttribute("f",friends);
			friends.add("xyz");
			friends.add("abc");
			
			return "test";
		}
		
		// for learning purpose of model and view ( how data is pass to view)
		
		@GetMapping("/test2")
		public ModelAndView Test2()
		{
			System.out.println("test page");
			//create modelandview object
			ModelAndView mv=new ModelAndView();
			mv.addObject("name","jay gajera 17");
			mv.addObject("id",40);
			mv.setViewName("test2");
			
			List<Integer> list=new ArrayList<Integer>();
			list.add(10);
			list.add(25);
			mv.addObject("marks",list);
			return mv;
			
			
		}


//	@GetMapping("/cartproduct")
//	public ModelAndView  cartProduct()
//	{
//		ModelAndView mv= new ModelAndView("cartproduct");
//		return mv;
//	}

	@GetMapping("profileDisplay")
	public ModelAndView profileDisplay()
	{
//		userService.
		System.out.println("username====>" + yo.getUsername());
		ModelAndView mv= new ModelAndView("updateProfile");
		mv.addObject("userid",yo.getId());
		mv.addObject("username",yo.getUsername());
		mv.addObject("email",yo.getEmail());
		mv.addObject("password",yo.getPassword());
		mv.addObject("address",yo.getAddress());
		return mv;
	}

	@RequestMapping(value = "updateuser", method = RequestMethod.POST)
public ModelAndView profileUpdate(@RequestParam("username") String username, @RequestParam("password") String password,@RequestParam("email") String email, @RequestParam("address") String address, Model model)
	{
		if(yo!=null)
		{
			yo.setUsername(username.trim());
			yo.setPassword(password);
			yo.setEmail(email);
			yo.setAddress(address);

			userService.updateUser(yo);
		}
		ModelAndView mv= new ModelAndView("updateProfile");
//		mv.addObject("userid",yo.getId());
		mv.addObject("username",yo.getUsername());
		mv.addObject("email",yo.getEmail());
		mv.addObject("password",yo.getPassword());
		mv.addObject("address",yo.getAddress());
		mv.addObject("success","Profile has been updated successfully");
		return mv;
	}
}
