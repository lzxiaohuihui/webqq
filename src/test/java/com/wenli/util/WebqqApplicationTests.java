package com.wenli.util;

import com.wenli.entity.Group;
import com.wenli.entity.GroupMessage;
import com.wenli.entity.vo.GroupMessageResultVo;
import com.wenli.entity.vo.HistoryMsgRequestVo;
import com.wenli.service.GroupMessageService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Pattern;

@SpringBootTest
class WebqqApplicationTests {

	@Resource
	private GroupMessageService groupMessageService;

	@Resource
	private MongoTemplate mongoTemplate;

	@Test
	void contextLoads() {
	}

	@Test
	void testMongoDbSave(){
		for (int i = 0; i < 20; i++) {
			GroupMessage groupMessage = new GroupMessage();
			groupMessage.setMessage("hello 老六" + i);
			groupMessage.setGroupName("老六组");
			groupMessage.setGroupId("12345678");
			groupMessage.setSenderName("9527");
			groupMessage.setTime(new Date());
			groupMessage.setSenderId("123456");
			mongoTemplate.save(groupMessage);
		}
	}


	@Test
	void testMongoTemplateQuery(){
		Query query = Query.query(Criteria.where("groupNumber").is("12345678"));
		List<GroupMessage> groupMessages = mongoTemplate.find(query, GroupMessage.class);
		for (GroupMessage groupMessage : groupMessages) {
			System.out.println(groupMessage);
		}
	}

	@Test
	void testHistoryMsg(){
		HistoryMsgRequestVo historyMsgRequestVo = new HistoryMsgRequestVo();
		historyMsgRequestVo.setQuery("六");
		historyMsgRequestVo.setPageIndex(3);
		historyMsgRequestVo.setPageSize(4);
		historyMsgRequestVo.setType("all");
		historyMsgRequestVo.setGroupNumber("12345678");

		// 创建复合查询对象
		Criteria cri1 = new Criteria();
		cri1.and("groupNumber").is(historyMsgRequestVo.getGroupNumber());
		//若查询条件是全部，则模糊匹配 message 或者 fileRawName
		//若查询条件不是全部，则设置搜索类型，并且模糊匹配 fileRawName
		Criteria cri2 = null;
		if (!historyMsgRequestVo.getType().equals("all")) {
			//若查询类型是文件或图片，则模糊匹配原文件名
			cri1.and("messageType").is(historyMsgRequestVo.getType())
					.and("fileRawName").regex(Pattern.compile("^.*" + historyMsgRequestVo.getQuery() + ".*$", Pattern.CASE_INSENSITIVE));
		} else {
			cri2 = new Criteria().orOperator(Criteria.where("message").regex(Pattern.compile("^.*" + historyMsgRequestVo.getQuery() + ".*$", Pattern.CASE_INSENSITIVE)),
					Criteria.where("fileRawName").regex(Pattern.compile("^.*" + historyMsgRequestVo.getQuery() + ".*$", Pattern.CASE_INSENSITIVE)));
		}
		if (historyMsgRequestVo.getDate() != null) {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(historyMsgRequestVo.getDate());
			calendar.add(Calendar.DATE, 1);
			Date tomorrow = calendar.getTime();
			// System.out.println("today：" + groupHistoryVo.getDate() + ", tomorrow：" + tomorrow);
			cri1.and("time").gte(historyMsgRequestVo.getDate()).lt(tomorrow);
		}
		// 创建查询对象
		Query query = new Query();
		if (cri2 != null) query.addCriteria(new Criteria().andOperator(cri1, cri2)); //最后两者是且的关系
		else query.addCriteria(cri1);
		// 统计总数
		long count = mongoTemplate.count(query, GroupMessage.class);
		// 设置分页
		query.skip((long) historyMsgRequestVo.getPageIndex() * historyMsgRequestVo.getPageSize()); //页码
		query.limit(historyMsgRequestVo.getPageSize()); //每页显示数量
		List<GroupMessage> messageList = mongoTemplate.find(query,
				GroupMessage.class);

		System.out.println(count);
		for (GroupMessage message : messageList) {
			System.out.println(message);
		}

	}

	@Test
	void testMongoTemplateQueryLast(){
		String groupNumber = "12345678";
		Query query = new Query();
		query.addCriteria(Criteria.where("groupNumber").is(groupNumber))
				.with(Sort.by(Sort.Direction.DESC, "_id"));
		GroupMessageResultVo res = mongoTemplate.findOne(query, GroupMessageResultVo.class, "groupmessages");
		if (res == null)
			res = new GroupMessageResultVo();
		System.out.println(res);
	}



}
