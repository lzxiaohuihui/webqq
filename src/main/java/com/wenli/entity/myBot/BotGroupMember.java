package com.wenli.entity.myBot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BotGroupMember {
	private String id;
	private String nick;
	private String remark;
	private String avatarUrl = "/img/picture.png";
	private String permission;
	private String rankTitle;
	private String temperatureTitle;
	private boolean isMuted;
}
