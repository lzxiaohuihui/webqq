package com.wenli.entity.myBot;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;

@Data
@AllArgsConstructor
public class BotGroup {
	private String id;
	private Map<String, BotGroupMember> members = new HashMap<>();
	private BotGroupMember botAsMember;
	private String name;
	private String avatarUrl;
	private String holderName; // 群主昵称
	private String holderId;  // 群主账号
	private Date holderTime; // 群主加入时间
}
