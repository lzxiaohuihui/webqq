package com.wenli.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import com.wenli.common.R;
import com.wenli.entity.User;
import com.wenli.entity.vo.LoginRequestVo;
import com.wenli.service.UserService;
import com.wenli.util.RedisKeyUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {

	@Resource
	private UserService userService;

	@Resource
	private RedisTemplate<String, String> redisTemplate;

	@GetMapping("/getCode")
	public R getKaptcha(HttpServletResponse response){
		String kaptchaOwner = UUID.randomUUID().toString();
		Cookie cookie = new Cookie("KaptchaOwner", kaptchaOwner);

		cookie.setMaxAge(60 + 8*60*60);
		cookie.setPath("/");
		response.addCookie(cookie);

		String redisKey = RedisKeyUtil.getKaptchaKey(kaptchaOwner);
		String verificationCode = RandomUtil.randomString(4);
		redisTemplate.opsForValue().set(redisKey, verificationCode, 5, TimeUnit.MINUTES);

		return R.ok().data("code", verificationCode);
	}

	@PostMapping("/login")
	public R login(@RequestBody LoginRequestVo lvo){
		return userService.login(lvo);
	}


	@GetMapping("/getUserInfo")
	public R getUserInfo(String uid){
		User userInfo = userService.getUserInfo(uid);
		return R.ok().data("userInfo", userInfo);
	}



}
