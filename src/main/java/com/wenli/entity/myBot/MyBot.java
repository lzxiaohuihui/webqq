package com.wenli.entity.myBot;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@Document
public class MyBot {
	@Indexed(unique = true)
	private String id;
	private boolean isOnline;
	private String nick;
	private String avatarUrl;
	private Map<Integer, BotFriendGroup> botFriendGroups = new HashMap<>();
	private int botDefaultFriendGroup;
	private Map<String, BotFriend> botFriends = new HashMap<>();
	private Map<String, BotGroup> botGroups= new HashMap<>();
}
