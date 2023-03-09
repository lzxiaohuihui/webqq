package com.wenli.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecentConversationVo {
	private String userId;
	private List<String> recentFriendIds;
}
