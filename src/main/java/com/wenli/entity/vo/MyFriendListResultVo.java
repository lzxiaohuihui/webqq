package com.wenli.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyFriendListResultVo {
	private String nickName;
	private String photo;
	private String signature;
	private String id;
	private Integer level; //grade
	private String roomId;
}
