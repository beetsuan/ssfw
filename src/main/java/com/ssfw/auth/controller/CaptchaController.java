package com.ssfw.auth.controller;

import com.ssfw.auth.contant.UserConstants;
import com.ssfw.common.util.Captcha;
import com.ssfw.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * 验证码 管理控制器
 * @author a
 */
@Controller
@RequestMapping("/security/captcha")
public class CaptchaController {

	private final Logger log = LoggerFactory.getLogger(CaptchaController.class);

	private static final String SUCCESS="success";
	private static final String FAILURE="failure";

	/**
	 * 验证码核对
	 * @param captcha 验证码
	 * @return success|failure
	 */
	@RequestMapping(value="/validate/{captcha}",method= {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String validateCaptcha(@PathVariable("captcha")String captcha,HttpServletRequest request){

		if (StringUtil.isNull(captcha)){
			return FAILURE;
		}
		HttpSession session = request.getSession(false);
		if (null == session){
			return FAILURE;
		}
		//获取session中的验证码
		String sessionCaptcha = (String)session.getAttribute(UserConstants.SESSION_SECURITY_CAPTCHA);
		if (StringUtil.isNull(sessionCaptcha)){
			return FAILURE;
		}
		if (!sessionCaptcha.toUpperCase().equalsIgnoreCase(captcha.toUpperCase().trim())){
			return FAILURE;
		}
		return  SUCCESS;
	}

	/**
	 * 获取base64编码后的验证码图片
	 * @param request HttpServletRequest
	 * @return base64 plain
	 */
	@GetMapping(value = "/base64",produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String base64(HttpServletRequest request){

		try {
			HttpSession session = request.getSession(true);
			if (null == session){
				return "NO SESSION";
			}
			String[] captcha = Captcha.base64();
			session.setAttribute(UserConstants.SESSION_SECURITY_CAPTCHA, captcha[0]);
			return captcha[1];
		} catch (Exception e) {
			log.error("获取base64编码后的验证码图片",e);
		}
		return null;
	}

	/**
	 * 获取验证码图片
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param theme 颜色 {black|light}
	 * @download
	 */
	@GetMapping()
	@ResponseBody
	public void generate(HttpServletRequest request,HttpServletResponse response,@RequestParam(required = false) String theme){

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String captcha = Captcha.drawImg(output, "light".equals(theme) ? Captcha.ColorTheme.Light : Captcha.ColorTheme.Black, Captcha.SecurityCodeLevel.Arithmetic, 32);
		HttpSession session = request.getSession(true);
		session.setAttribute(UserConstants.SESSION_SECURITY_CAPTCHA, captcha);
		response.setContentType("image/png");
		try {
			ServletOutputStream out = response.getOutputStream();
			output.writeTo(out);
			output.close();
		} catch (IOException e) {
			log.error("获取验证码图片",e);
		}
	}

}
