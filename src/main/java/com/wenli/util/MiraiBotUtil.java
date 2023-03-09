package com.wenli.util;

import com.wenli.entity.myBot.*;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.utils.BotConfiguration;

import java.util.*;

/**
 * Mirai框架的通用启动方法
 */
@Slf4j
public class MiraiBotUtil {

	public static MyBot startMirai(String qqNumber, String qqPasswd) {
		long qq = Long.parseLong(qqNumber);
		Bot bot = Bot.getInstanceOrNull(qq);
		if(bot == null){
			bot = BotFactory.INSTANCE.newBot(qq, qqPasswd, new BotConfiguration() {{
			log.info("尝试登录{}", qq);
			fileBasedDeviceInfo("runFile/device.json");
			setProtocol(MiraiProtocol.MACOS);
				}});
			bot.login();
		}

		MyBot myBot = new MyBot();



		// 初始化分组
		bot.getFriendGroups().asCollection().forEach(friendGroup -> {
			myBot.getBotFriendGroups().put(friendGroup.getId(), new BotFriendGroup(friendGroup.getName(),
					friendGroup.getCount()));
		});

		// 默认分组
		myBot.setBotDefaultFriendGroup(bot.getFriendGroups().getDefault().getId());

		// 初始化好友
		bot.getFriends().forEach(friend -> {
			myBot.getBotFriends().put(String.valueOf(friend.getId()), new BotFriend(String.valueOf(friend.getId()), friend.getNick(),
					friend.getRemark(),
					friend.getFriendGroup().getId(), friend.getAvatarUrl()));
		});

		// 初始化群
		bot.getGroups().forEach(group -> {
			//群员
			Map<String, BotGroupMember> groupMembers = new HashMap<>();
			group.getMembers().forEach(normalMember -> {
				groupMembers.put(
						String.valueOf(normalMember.getId()),
						new BotGroupMember(
								String.valueOf(normalMember.getId()),
								normalMember.getNick(),
								normalMember.getRemark(),
								normalMember.getAvatarUrl(),
								normalMember.getPermission().toString(),
								normalMember.getRankTitle(),
								normalMember.getTemperatureTitle(),
								normalMember.isMuted())
								);
			});
			// 群里的我
			BotGroupMember me = new BotGroupMember(
					String.valueOf(group.getBotAsMember().getId()),
					group.getBotAsMember().getNick(),
					group.getBotAsMember().getRemark(),
					group.getBotAsMember().getAvatarUrl(),
					group.getBotAsMember().getPermission().toString(),
					group.getBotAsMember().getRankTitle(),
					group.getBotAsMember().getTemperatureTitle(),
					group.getBotAsMember().isMuted());
			// 群信息
			myBot.getBotGroups().put(
					String.valueOf(group.getId()),
					new BotGroup(String.valueOf(group.getId()), groupMembers, me, group.getName(), group.getAvatarUrl(),
							group.getOwner().getNick(), String.valueOf(group.getOwner().getId()),
							new Date(group.getOwner().getJoinTimestamp()))
			);
		});

		// id, count, avatar, nick, isOnline
		myBot.setId(String.valueOf(bot.getId()));
		myBot.setNick(bot.getNick());
		myBot.setAvatarUrl(bot.getAvatarUrl());
		myBot.setOnline(bot.getBot().isOnline());


		// GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessageEvent.class, event -> {
		// 	MessageChain chain = event.getMessage();
		// 	System.out.println(event.getMessage());
		// });
		//
		// GlobalEventChannel.INSTANCE.subscribeAlways(FriendMessageEvent.class, event -> {
		// 	MessageChain chain = event.getMessage();
		// 	System.out.println(event.getMessage());
		// });

		return myBot;

	}
}