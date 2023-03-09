package com.wenli.controller;

import com.wenli.common.R;
import com.wenli.entity.vo.GroupHistoryResultVo;
import com.wenli.entity.vo.GroupMessageResultVo;
import com.wenli.entity.vo.HistoryMsgRequestVo;
import com.wenli.service.GroupMessageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("groupMessage")
public class GroupMessageController {

	@Resource
	private GroupMessageService groupMessageService;

	@GetMapping("/getRecentGroupMessages")
	public R getRecentGroupMessages(String roomId, Integer pageIndex, Integer pageSize) {
		List<GroupMessageResultVo> recentGroupMessages = groupMessageService.getRecentGroupMessages(roomId, pageIndex, pageSize);
		return R.ok().data("recentGroupMessages", recentGroupMessages);
	}

	@PostMapping("/historyMessages")
	public R getGroupHistoryMessages(@RequestBody HistoryMsgRequestVo historyMsgRequestVo) {
		GroupHistoryResultVo historyMessages = groupMessageService.getGroupHistoryMessages(historyMsgRequestVo);
		return R.ok().data("total", historyMessages.getCount()).data("msgList", historyMessages.getGroupMessages());
	}

	@GetMapping("/lastMessage")
	public R getGroupLastMessage(String roomId) {
		GroupMessageResultVo groupLastMessage = groupMessageService.getGroupLastMessage(roomId);
		return R.ok().data("groupLastMessage", groupLastMessage);
	}
}

