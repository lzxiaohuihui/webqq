package com.wenli.entity.vo;

import com.wenli.util.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.mamoe.mirai.event.events.MessageEvent;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewMessageVo {
	private String roomId;
	private String senderName;// 发送者登录名
	private String senderId;// 发送者Id
	private String senderNickname;// 发送者昵称
	private String senderAvatar; // 发送者头像
	private Date time;// 消息发送时间
	private String fileRawName; //文件的原始名字
	private String message;// 消息内容
	private String messageType;// 消息的类型：emoji/text/img/file/sys
	private List<String> isReadUser; // 判断已经读取的用户，在发送消息时默认发送方已读取
	private String conversationType;

	public NewMessageVo(MessageEvent event, String conversationType){
		if ("GROUP".equals(conversationType)){
			this.roomId = String.valueOf(event.getSubject().getId());
		}else{
			this.roomId = String.valueOf(event.getBot().getId()) +"-"+String.valueOf(event.getSender().getId());
		}
		this.senderId = String.valueOf(event.getSender().getId());
		this.senderName = event.getSenderName();
		this.senderNickname = event.getSender().getNick();
		this.senderAvatar = event.getSender().getAvatarUrl();
		this.time = new Date((long)event.getTime() * 1000);
		this.conversationType = conversationType;
		this.isReadUser = new ArrayList<>();
		this.message = event.getMessage().contentToString();
		this.messageType = "text";
	}
}
