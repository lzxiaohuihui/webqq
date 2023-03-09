package com.wenli.service;

import com.wenli.entity.Group;
import com.wenli.entity.SimpleUser;
import com.wenli.entity.myBot.BotGroup;
import com.wenli.entity.myBot.MyBot;
import com.wenli.entity.vo.MyGroupResultVo;
import com.wenli.util.GroupUtil;
import com.wenli.util.JwtUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GroupService {

	@Resource
	private MongoTemplate mongoTemplate;

	public List<MyGroupResultVo> getGroupList(String username) {
		List<MyGroupResultVo> res = new ArrayList<>();

		Query query = Query.query(Criteria.where("nick").is(username));
		MyBot myBot = mongoTemplate.findOne(query, MyBot.class);

		assert myBot != null;
		myBot.getBotGroups().forEach((gid, botGroup) -> {
			Group group = GroupUtil.getGroupFromBotGroup(botGroup);
			MyGroupResultVo myGroupResultVo = GroupUtil.getMyGroupResultVoFromBotGroup(botGroup);
			myGroupResultVo.setId(gid);
			myGroupResultVo.setGroupInfo(group);
			res.add(myGroupResultVo);
		});

		return res;
	}

	public Group getGroupInfo(String groupId, String token) {
		BotGroup resGroup = getBotGroup(groupId, token);
		return GroupUtil.getGroupFromBotGroup(resGroup);
	}

	public List<MyGroupResultVo> getGroupUsersByGroupId(String groupId, String token) {
		BotGroup targetGroup = getBotGroup(groupId, token);

		Group group = GroupUtil.getGroupFromBotGroup(targetGroup);

		List<MyGroupResultVo> res = new ArrayList<>();
		targetGroup.getMembers()
				.forEach((id, botGroupMember) -> {
					SimpleUser simpUser = new SimpleUser();
					simpUser.setUid(botGroupMember.getId());
					simpUser.setNickname(botGroupMember.getNick());
					simpUser.setUsername(botGroupMember.getNick());
					simpUser.setPhoto(botGroupMember.getAvatarUrl());
					simpUser.setOnlineTime(9527L);
					simpUser.setLastLoginTime(new Date());

					MyGroupResultVo myGroupResultVo = GroupUtil.getMyGroupResultVoFromBotGroup(targetGroup);
					myGroupResultVo.setId(botGroupMember.getId());
					myGroupResultVo.setUserId(botGroupMember.getId());
					myGroupResultVo.setUsername(botGroupMember.getNick());
					myGroupResultVo.setGroupInfo(group);
					myGroupResultVo.setCard(botGroupMember.getRemark());
					myGroupResultVo.setTime(new Date());
					myGroupResultVo.setManager(botGroupMember.getPermission().equals("ADMINISTRATION") ? 1 : 0);
					myGroupResultVo.setHolder(botGroupMember.getPermission().equals("OWNER") ? 1 : 0);
					myGroupResultVo.setUserInfo(simpUser);

					res.add(myGroupResultVo);
				});
		return res;

	}

	private BotGroup getBotGroup(String groupId, String token){
		String username = JwtUtils.parseJwt(token).get("username").toString();
		MyBot mybot = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(username)), MyBot.class);

		assert mybot != null;
		return mybot.getBotGroups().get(groupId);
	}

}
