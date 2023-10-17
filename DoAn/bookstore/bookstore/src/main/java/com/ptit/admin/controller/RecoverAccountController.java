package com.ptit.admin.controller;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ptit.model.MessageNotify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ptit.config.JavaSenderService;
import com.ptit.model.User;
import com.ptit.service.UserService;

@Controller
@RequestMapping("/verify")
public class RecoverAccountController {

	private static final String verifyCodeSuccess = "VERIFY_CODE_SUCCESS"; 
	
	@Autowired
	JavaSenderService javaSenderService; 
	
	@Autowired
	UserService userService; 
	
	@Autowired
	Environment env;  
	
	@GetMapping("/username")
	public String verifyUsername() {
		return "verifyUsername"; 
	}
	
	@PostMapping("/username")
	public String verifyUsernameInput(@RequestParam String username, HttpSession session, Model model) {
		User user = userService.getUserByUsername(username); 
		if(user==null) {
			return "redirect:/verify/username?userNotExist=true&inputUsername="+username; 
		}
		if(user.getEmail()==null || user.getEmail().isEmpty()) {
				model.addAttribute("emaiMethod", false); 
		}
		else {
				session.setAttribute(env.getProperty("SESSION_EMAIL_VERIFY"), user.getEmail()); 
				model.addAttribute("emaiMethod", true); 
		}
		
		if(user.getPhone()==null || user.getPhone().isEmpty()) {
			model.addAttribute("smsMethod", false);
		}else {
			session.setAttribute(env.getProperty("SESSION_SMS_VERIFY"), user.getPhone()); 
			model.addAttribute("smsMethod", true); 
		}

		session.setAttribute(env.getProperty("SESSION_USERNAME_VERIFY"), username); 
		return "verifyMethodRecover"; 
		
	}
	
	@GetMapping("/verifyMethodRecover")
	public String pickMethod(@RequestParam int pickedMethod, HttpSession session) {
		String code = createCode(); 
		
		//1: verify by email
		if(pickedMethod==1) {
			try {
				//send mail: 
				javaSenderService.sendMailVerifyCode((String) session.getAttribute(env.getProperty("SESSION_EMAIL_VERIFY")), MessageNotify.RECOVERACCOUNT_VERIFY +code);
			} catch (Exception e) {
				// TODO: handle exception
				return "redirect:/verify/username?userNotExist=errorEmail&inputUsername="+(String) session.getAttribute(env.getProperty("SESSION_USERNAME_VERIFY"));
			}
		}
		//verify by phone
		else if(pickedMethod==2){
			try {
				javaSenderService.sendSmsVerifyCode((String) session.getAttribute(env.getProperty("SESSION_SMS_VERIFY")), code);
			} catch (Exception e) {
				// TODO: handle exception
				return "redirect:/verify/username?userNotExist=errorSMS&inputUsername="+(String) session.getAttribute(env.getProperty("SESSION_USERNAME_VERIFY")); 
			}
			
		}
		
		//remove session email because do not use it anymore 
		session.removeAttribute(env.getProperty("SESSION_EMAIL_VERIFY")); 
		session.removeAttribute(env.getProperty("SESSION_SMS_VERIFY")); 
		
		//create session
		session.setAttribute(env.getProperty("SESSION_CODE_VERIFY"), code); 
		return "redirect:/verify/code"; 
	}
	
	@GetMapping("/code")
	public String verifyCode(HttpSession session, Model model) {
		if(session.getAttribute(env.getProperty("SESSION_CODE_VERIFY"))==null) {
			return "notinfo"; 
		}
		return "verifyRecoverCode"; 
	}
	
	@PostMapping("/code")
	public String verifyInputCode(@RequestParam String verifyCode,HttpSession session) {
		String code = (String) session.getAttribute(env.getProperty("SESSION_CODE_VERIFY"));
		if(code.equals(verifyCode)) {
			session.removeAttribute(env.getProperty("SESSION_CODE_VERIFY")); 
			session.setAttribute(verifyCodeSuccess,verifyCodeSuccess); 
			
			return "redirect:/verify/update"; 
		}
		return "redirect:/verify/code?error=true"; 
	}
	
	@GetMapping("/update")
	public String updatePass(HttpSession session) {
		if(session.getAttribute(verifyCodeSuccess)==null) {
			return "notinfo";
		}
		return "recoveryPassword"; 
	}
	
	@PostMapping("/update")
	@ResponseBody
	public String updatePass(@RequestParam String password, HttpSession session) {
		if(session.getAttribute(verifyCodeSuccess)==null) {
			return "notinfo";
		}
		try {
			String username = (String) session.getAttribute(env.getProperty("SESSION_USERNAME_VERIFY")); 
			userService.updatePassword(password, username); 
			session.removeAttribute(verifyCodeSuccess); 
			session.removeAttribute(env.getProperty("SESSION_USERNAME_VERIFY"));
			return "true"; 
		}catch (Exception e) {
			return "false"; 
		}
		
	}
	
	
	String createCode() {
		String code = ""; 
		Random rand = new Random();
		
		for(int i =1;i<=6;i++) {
			int tempIntCode = rand.nextInt(10);
			code +=tempIntCode; 
		}
		
		return code;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
