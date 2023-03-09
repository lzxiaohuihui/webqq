package com.wenli.controller;

import com.wenli.common.R;
import com.wenli.entity.SensitiveMessage;
import com.wenli.entity.vo.SystemUserResponseVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sys")
public class SysController {

	@GetMapping("getSysUsers")
	@ResponseBody
	public R getSysUsers(){
		SystemUserResponseVo sysUser = new SystemUserResponseVo("9527", "9527", "9527", 0);
		return R.ok().data("sysUsers", sysUser);
	}

	@PostMapping("/filterMessage")
	@ResponseBody
	public R filterMessage(@RequestBody SensitiveMessage sensitiveMessage){
		return R.ok().data("message", sensitiveMessage.getMessage());
	}
}
