package com.wenli.controller;

import com.wenli.common.R;
import com.wenli.common.ResultEnum;
import com.wenli.entity.Group;
import com.wenli.entity.vo.MyGroupResultVo;
import com.wenli.entity.vo.RecentGroupVo;
import com.wenli.service.GroupService;
import com.wenli.util.JwtUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {

	@Resource
	private GroupService groupService;

	@GetMapping("getMyGroupList")
	public R getMyGroupList(String username){
		List<MyGroupResultVo> groupResultVoList = groupService.getGroupList(username);
		return R.ok().data("myGroupList", groupResultVoList);
	}

	@GetMapping("getGroupInfo")
	public R getGroupInfo(String groupId, HttpServletRequest request){
		String token = request.getHeader(JwtUtils.TOKEN_HEADER);
		if (token == null){
			return R.error().resultEnum(ResultEnum.USER_NEED_AUTHORITIES);
		}
		Group groupInfo = groupService.getGroupInfo(groupId, token);
		List<MyGroupResultVo> groupUsers = groupService.getGroupUsersByGroupId(groupId, token);
		return R.ok().data("groupInfo", groupInfo).data("users", groupUsers);
	}

	/**
	 * 获取最近的群聊
	 */
	@PostMapping("/recentGroup")
	public R getRecentGroup(@RequestBody RecentGroupVo recentGroupVo) {
		// System.out.println("最近的群聊列表请求参数为：" + recentGroupVo);
		// List<MyGroupResultVo> recentGroups = groupUserService.getRecentGroup(recentGroupVo);
		// System.out.println("最近的群聊列表为：" + recentGroups);
		return R.ok().data("recentGroups", null);
	}

}
