package com.wenli.controller;

import com.wenli.common.R;
import com.wenli.entity.vo.HistoryMsgRequestVo;
import com.wenli.entity.vo.IsReadMessageRequestVo;
import com.wenli.entity.vo.SingleHistoryResultVo;
import com.wenli.entity.vo.SingleMessageResultVo;
import com.wenli.service.FriendMessageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/singleMessage")
public class SingleMessageController {

	@Resource
	private FriendMessageService friendMessageService;

	@PostMapping("/isRead")
	public R userIsReadMessage(@RequestBody IsReadMessageRequestVo ivo) {
		// singleMessageService.userIsReadMessage(ivo);
		return R.ok();
	}

	@GetMapping("/getLastMessage")
	public R getLastMessage(String roomId) {
		SingleMessageResultVo lastMessage = friendMessageService.getLastMessage(roomId);
		return R.ok().data("singleLastMessage", lastMessage);
	}

	@GetMapping("/getRecentSingleMessages")
	public R getRecentSingleMessages(String roomId, Integer pageIndex, Integer pageSize) {
		List<SingleMessageResultVo> recentMessage = friendMessageService.getRecentMessage(roomId, pageIndex, pageSize);
		return R.ok().data("recentMessage", recentMessage);
	}

	@PostMapping("/historyMessage")
	public R getSingleHistoryMessages(@RequestBody HistoryMsgRequestVo historyMsgRequestVo) {
		// System.out.println("查看历史消息的请求参数为：" + historyMsgVo);
		SingleHistoryResultVo singleHistoryMsg = friendMessageService.getSingleHistoryMsg(historyMsgRequestVo);
		return R.ok().data("total", singleHistoryMsg.getTotal()).data("msgList", singleHistoryMsg.getMsgList());
	}


}
