package com.wenli.util;

import com.wenli.entity.Group;
import com.wenli.entity.myBot.BotGroup;
import com.wenli.entity.vo.MyGroupResultVo;

public class GroupUtil {
	public static Group getGroupFromBotGroup(BotGroup botGroup){
		Group group = new Group();
		group.setGid(botGroup.getId());
		group.setTitle(botGroup.getName());
		group.setImg(botGroup.getAvatarUrl());
		group.setCode(botGroup.getId());
		group.setUserNum(botGroup.getMembers().size());
		group.setCreateDate(botGroup.getHolderTime());
		group.setHolderName(botGroup.getHolderName());
		group.setHolderUserId(botGroup.getHolderId());
		return group;
	}

	public static MyGroupResultVo getMyGroupResultVoFromBotGroup(BotGroup botGroup){
		MyGroupResultVo myGroupResultVo = new MyGroupResultVo();
		myGroupResultVo.setGroupId(botGroup.getId());
		myGroupResultVo.setUserId(botGroup.getBotAsMember().getId());
		myGroupResultVo.setUsername(botGroup.getBotAsMember().getNick());
		myGroupResultVo.setManager(botGroup.getBotAsMember().getPermission().equals("ADMINISTRATION") ? 1 : 0);
		myGroupResultVo.setHolder(botGroup.getBotAsMember().getPermission().equals("OWNER") ? 1 : 0);
		myGroupResultVo.setGroupId(botGroup.getId());
		return myGroupResultVo;
	}
}
