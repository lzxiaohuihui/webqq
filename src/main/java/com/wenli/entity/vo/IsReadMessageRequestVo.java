package com.wenli.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IsReadMessageRequestVo {
	private String roomId;
	private String userId;
}
