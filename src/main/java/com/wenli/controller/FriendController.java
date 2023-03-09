package com.wenli.controller;

import com.wenli.common.R;
import com.wenli.entity.Friend;
import com.wenli.entity.vo.MyFriendListResultVo;
import com.wenli.entity.vo.RecentConversationVo;
import com.wenli.entity.vo.SingleRecentConversationResultVo;
import com.wenli.service.FriendService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/goodFriend")
public class FriendController {

	@Resource
	private FriendService friendService;

	@GetMapping("/getMyFriendsList")
	public R getMyFriendsList(String userId){
		List<MyFriendListResultVo> myFriendList = friendService.getMyFriendsList(userId);

		return R.ok().data("myFriendsList", myFriendList);
	}

	@PostMapping("/recentConversationList")
	public R getRecentConversationList(@RequestBody RecentConversationVo recentConversationVo) {
		// List<SingleRecentConversationResultVo> resultVoList = friendService.getRecentConversation(recentConversationVo);
		return R.ok().data("singleRecentConversationList", null);
	}
}
