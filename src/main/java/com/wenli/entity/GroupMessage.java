package com.wenli.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Document("groupmessages")
public class GroupMessage {
	@Id
	private ObjectId id;
	private String roomId;
	private String senderId;
	private String groupName;
	private String senderName;
	private String senderNickName;
	private String senderAvatar;
	private Date time = new Date();
	private String fileRawName;
	private String message;
	private String messageType;
	private List<String> isReadUser = new ArrayList<>();

}
