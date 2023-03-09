package com.wenli.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BrowserSetting {
	private String browser;
	private String country;
	private String ip;
	private String os;
}
