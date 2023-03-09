package com.wenli.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemUserResponseVo {
	private String sid;
	private String code;
	private String nickname;
	private Integer status;
}
