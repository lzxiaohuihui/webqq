package com.wenli.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
public class Friend{
	private ObjectId id;
	private String qqNumber;
	private String fenzu;
	private String nick;
	private String remark;
	private String avatarUrl;

	public Friend(net.mamoe.mirai.contact.Friend friend){
		this.qqNumber = String.valueOf(friend.getId());
		this.fenzu = friend.getFriendGroup().getName();
		this.nick = friend.getNick();
		this.remark = friend.getRemark();
		this.avatarUrl = friend.getAvatarUrl();
	}
}
