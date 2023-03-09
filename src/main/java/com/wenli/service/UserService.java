package com.wenli.service;

import com.wenli.common.R;
import com.wenli.common.ResultEnum;
import com.wenli.dao.MyBotDao;
import com.wenli.dao.UserDao;
import com.wenli.entity.User;
import com.wenli.entity.myBot.MyBot;
import com.wenli.entity.vo.LoginRequestVo;
import com.wenli.util.JwtUtils;
import com.wenli.util.MiraiBotUtil;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

	@Resource
	private MyBotDao myBotDao;

	@Resource
	private UserDao userDao;

	@Resource
	private MongoTemplate mongoTemplate;

	public R login(LoginRequestVo lvo) {
		String token = JwtUtils.createJwt(lvo.getUsername());
		MyBot myBot = MiraiBotUtil.startMirai(lvo.getUsername(), lvo.getPassword());
		myBotDao.save(myBot);

		// 构造前端接口的数据
		User userInfo = new User();
		// userInfo.setMyBot(myBot);
		userInfo.setUid(myBot.getId());
		userInfo.setCode(myBot.getId());
		userInfo.setUsername(myBot.getNick());
		userInfo.setPhoto(myBot.getAvatarUrl());
		userInfo.setNickname(myBot.getNick());

		// 创建分组
		Map<Integer, String> friendGroupMap = new HashMap<>();
		myBot.getBotFriendGroups().forEach((id, botFriendGroup) -> {
			userInfo.getFriendFenZu().put(botFriendGroup.getName(), new ArrayList<>());
			friendGroupMap.put(id, botFriendGroup.getName());
		});

		myBot.getBotFriends().forEach((id, botFriend) -> {
			userInfo.getFriendFenZu()
					.get(friendGroupMap.get(botFriend.getBotFriendGroupId()))
					.add(id);

			// 放入备注信息
			userInfo.getFriendBeiZhu().put(id, botFriend.getRemark());
		});

		userDao.save(userInfo);

		return R.ok().resultEnum(ResultEnum.LOGIN_SUCCESS).data("token", token).data("userInfo", userInfo);

	}

	public User getUserInfo(String uid) {
		return mongoTemplate.findOne(Query.query(Criteria.where("uid").is(uid)), User.class);
	}
}
