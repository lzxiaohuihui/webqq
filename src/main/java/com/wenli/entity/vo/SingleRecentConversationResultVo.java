package com.wenli.entity.vo;

import com.wenli.entity.SimpleUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SingleRecentConversationResultVo {
	private String id;
	private String createDate;//用字符串显示
	private SimpleUser userM;
	private SimpleUser userY;
}
