package com.wenli.service;

import com.wenli.entity.SimpleUser;
import com.wenli.entity.myBot.MyBot;
import com.wenli.entity.vo.MyFriendListResultVo;
import com.wenli.entity.vo.MyFriendListVo;
import com.wenli.entity.vo.RecentConversationVo;
import com.wenli.entity.vo.SingleRecentConversationResultVo;
import com.wenli.util.DateUtil;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class FriendService {

	@Resource
	private MongoTemplate mongoTemplate;

	public List<MyFriendListResultVo> getMyFriendsList(String userId) {
		List<MyFriendListResultVo> res = new ArrayList<>();

		Query query = Query.query(Criteria.where("_id").is(userId));

		MyBot myBot = mongoTemplate.findOne(query, MyBot.class);

		assert myBot != null;
		myBot.getBotFriends().forEach((id, botFriend) -> {
			res.add(new MyFriendListResultVo(botFriend.getNick(), botFriend.getAvatarUrl(), botFriend.getRemark(),
					botFriend.getId(), 9527, myBot.getId() + "-" + botFriend.getId()));
		});

		return res;
	}

	public List<SingleRecentConversationResultVo> getRecentConversation(RecentConversationVo recentConversationVo) {
		List<ObjectId> friendIds = new ArrayList<>();
		for (String son : recentConversationVo.getRecentFriendIds()) {
			friendIds.add(new ObjectId(son));
		}
		Criteria criteriaA = Criteria.where("userM").in(friendIds).and("userY").is(new ObjectId(recentConversationVo.getUserId()));
		Criteria criteriaB = Criteria.where("userY").in(friendIds).and("userM").is(new ObjectId(recentConversationVo.getUserId()));
		Criteria criteria = new Criteria();
		criteria.orOperator(criteriaA, criteriaB);
		Aggregation aggregation = Aggregation.newAggregation( // 注意查询类型ObjectId
				Aggregation.match(
						/**
						 * $or: [
						 *       {userM: {$in: recentFriendIds}, userY: userId},
						 *       {userY: {$in: recentFriendIds}, userM: userId}
						 *     ]
						 */
						criteria
				),
				Aggregation.lookup(
						"users",
						"userY",
						"_id",
						"uList1"
				),
				Aggregation.lookup(
						"users",
						"userM",
						"_id",
						"uList2"
				)
		);
		// TODO 获取好友列表
		List<MyFriendListVo> friendlies = mongoTemplate.aggregate(aggregation, "goodfriends", MyFriendListVo.class).getMappedResults();
		// System.out.println("查询最近的好友列表为：" + friendlies);
		List<SingleRecentConversationResultVo> resultVoList = new ArrayList<>();
		SingleRecentConversationResultVo item;
		SimpleUser userM, userY;
		for (MyFriendListVo son : friendlies) {
			item = new SingleRecentConversationResultVo();
			//时间格式化
			item.setCreateDate(DateUtil.format(son.getCreateDate(), DateUtil.yyyy_MM_dd_HH_mm_ss));
			item.setId(son.getId());
			userM = new SimpleUser();
			userY = new SimpleUser();
			//保持原来添加好友时userM和userY的顺序，不然获取最近会话时roomId顺序会出错
			//根据 MyFriendListVo 中来判断
			if (son.getUList1().get(0).getUid().equals(son.getUserM())) {
				BeanUtils.copyProperties(son.getUList1().get(0), userM);
				BeanUtils.copyProperties(son.getUList2().get(0), userY);
			} else {
				BeanUtils.copyProperties(son.getUList1().get(0), userY);
				BeanUtils.copyProperties(son.getUList2().get(0), userM);
			}
			item.setUserM(userM);
			item.setUserY(userY);
			resultVoList.add(item);
		}
		return resultVoList;
	}
}
