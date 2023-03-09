package com.wenli.entity.myBot;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BotFriend {
	private String id;
	private String nick;
	private String remark;
	private int botFriendGroupId;
	private String avatarUrl;
}
