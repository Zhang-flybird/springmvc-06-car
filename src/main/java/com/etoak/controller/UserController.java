package com.etoak.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etoak.bean.User;
import com.etoak.commons.VerifyCode;
import com.etoak.service.UserService;
import com.etoak.vo.PageVo;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

	@Autowired
	UserService userService;
	@RequestMapping("/{id}")
	@ResponseBody
	public User queryById(@PathVariable("id") int id) {
		log.info("param id:",id);
		return userService.queryById(id);
	}

	@RequestMapping("/pages")
	@ResponseBody
	public PageVo<User> pages(@RequestParam(required = false,defaultValue ="1")int pageNum,
			@RequestParam(required = false,defaultValue ="10") int pageSize) {
		log.info("param pageNum{},pageSize {}",pageNum,pageSize);
		return userService.queryList(pageNum, pageSize);
	}
	
	/**1.跳转页面 
	 * 2.登录请求
	 * 3.退出系统请求
	 * 4.验证码请求
	*/
	
	@RequestMapping("/toLogin")
	public String toLogin() {
		return "login";
	}
	
	//获取验证码
	@RequestMapping("/code")
	public void code(HttpServletRequest request,HttpServletResponse response) throws IOException {
		VerifyCode verifyCode = new VerifyCode();
        // 这个图片就是也写到前端的图片
        BufferedImage image = verifyCode.createImage();
        // 获取计算结果
        int result = verifyCode.getResult();
        // 将计算结果设置Session中
        request.getSession().setAttribute("code", result + "");
        
        // 向前端写图片
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ImageIO.write(image, "JPEG", response.getOutputStream());
	}
	
	/*
	 * public static void main(String[] args) { //MD5加密工具 md5不可逆 String md5 =
	 * DigestUtils.md5Hex("etoak"); System.out.println(md5); }
	 */
	@RequestMapping("/login")
	public String login(@RequestParam String name,@RequestParam String password,
			@RequestParam String code,HttpServletRequest request) {
		HttpSession session = request.getSession();
		//对比验证码
		String saveCode =(String)session.getAttribute("code");
		if(!StringUtils.equals(code, saveCode)) {
			log.warn("验证码错误：{}" + code);
			return "redirect:/user/toLogin";
		}
		session.invalidate();
		//对比密码
		password = DigestUtils.md5Hex(password);
		User user = userService.queryByNameAndPassword(name,password);
		if(user == null) {
			log.warn("用户名或密码错误");
			return "redirect:/user/toLogin";
		}
		//如果用户存在，那么吧用户放到session里面
		session =request.getSession();
		session.setAttribute("user", user);
		return "redirect:/";	
	}
	
	//退出登录
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session =request.getSession();
		session.invalidate();
		return "redirct:/user/toLogin";
	
	}
	
}
