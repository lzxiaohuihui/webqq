package com.wenli.entity.vo;

import com.wenli.entity.BrowserSetting;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestVo {
	private String id;
	private String username;
	private String avatar;
	private String password;
	private String cvCode;
	private BrowserSetting setting;
}
