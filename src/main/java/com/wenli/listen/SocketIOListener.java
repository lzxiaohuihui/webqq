package com.wenli.listen;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.wenli.common.ConstValueEnum;
import com.wenli.dao.GroupMessageDao;
import com.wenli.entity.FriendMessage;
import com.wenli.entity.GroupMessage;
import com.wenli.entity.SimpleUser;
import com.wenli.entity.User;
import com.wenli.entity.vo.NewMessageVo;
import com.wenli.service.FriendMessageService;
import com.wenli.service.GroupMessageService;
import com.wenli.util.MiraiBotUtil;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Component
@Transactional(rollbackFor = Throwable.class)
public class SocketIOListener {

	private final Logger logger = LoggerFactory.getLogger(SocketIOListener.class);

	@Resource
	private SocketIOServer socketIOServer;

	@Resource
	private GroupMessageService groupMessageService;

	@Resource
	private FriendMessageService friendMessageService;


	@OnConnect
	public void eventOnConnect(SocketIOClient client){
		Map<String, List<String>> urlParams =
				client.getHandshakeData().getUrlParams();
		System.out.println("客户端唯一标识为：" + client.getSessionId());
		logger.info("链接开启，urlParams：{}", urlParams);

	}

	@OnEvent("goOnline")
	public void goOnline(SocketIOClient client, User user){
		logger.info("goOnline ---> User: {}", user);
		String clientId = client.getSessionId().toString();
		client.set(clientId, user.getUid());
	}

	@OnEvent("leave")
	public void leave(SocketIOClient client){
		logger.info("leave ---> client: {}", client);
		long qq = client.get(client.getSessionId().toString());
		Bot.getInstanceOrNull(qq).close();

	}

	// 客户端发送消息，将消息保存到数据库中
	@OnEvent("sendNewMessage")
	public void sendNewMessage(NewMessageVo newMessageVo, SocketIOClient client) {
		logger.info("sendNewMessage ---> newMessageVo：{}", newMessageVo);
		Bot bot = Bot.getInstanceOrNull(Long.parseLong(newMessageVo.getSenderId()));
		if (bot == null){
			MiraiBotUtil.startMirai(newMessageVo.getSenderId(), "xxxx");
			bot = Bot.getInstanceOrNull(Long.parseLong(newMessageVo.getSenderId()));
			bot.getEventChannel().registerListenerHost(new MyEventListener(socketIOServer, friendMessageService, groupMessageService));

		}
		client.set(client.getSessionId().toString(), bot.getId());

		if (newMessageVo.getConversationType().equals(ConstValueEnum.FRIEND)) {
			MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
			long friendId = Long.parseLong(newMessageVo.getRoomId().split("-")[1]);
			Friend friend = bot.getFriendOrFail(friendId);
			messageChainBuilder.append(new PlainText(newMessageVo.getMessage()));
			friend.sendMessage(messageChainBuilder.build());

			FriendMessage friendMessage = new FriendMessage();
			BeanUtils.copyProperties(newMessageVo, friendMessage);
			friendMessage.setSenderId(newMessageVo.getSenderId());
			// System.out.println("待插入的单聊消息为：" + singleMessage);
			friendMessageService.addNewSingleMessage(friendMessage);
		} else if (newMessageVo.getConversationType().equals(ConstValueEnum.GROUP)) {
			MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
			Group group = bot.getGroup(Long.parseLong(newMessageVo.getRoomId()));
			messageChainBuilder.append(new PlainText(newMessageVo.getMessage()));
			group.sendMessage(messageChainBuilder.build());

			GroupMessage groupMessage = new GroupMessage();
			BeanUtils.copyProperties(newMessageVo, groupMessage);
			groupMessage.setSenderId(newMessageVo.getSenderId());
			// System.out.println("待插入的群聊消息为：" + groupMessage);
			groupMessageService.addNewGroupMessage(groupMessage);
		}
		// 通知该房间收到消息接受到消息
		// Collection<SocketIOClient> clients = socketIOServer.getRoomOperations(newMessageVo.getRoomId()).getClients(); //实际上同一房间只有2个客户端
		// for (SocketIOClient item : clients) {
		// 	if (item != client) {
		// 		item.sendEvent("receiveMessage", newMessageVo);
		// 	}
		// }
		//
		// GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessageEvent.class, event -> {
		// 	String room = String.valueOf(event.getGroup().getId());
		// 	MessageChain chain = event.getMessage();
		//
		// 	System.out.println(event.getMessage());
		// });
		//
		// GlobalEventChannel.INSTANCE.subscribeAlways(FriendMessageEvent.class, event -> {
		// 	String room = String.valueOf(event.getBot().getId()) + String.valueOf(event.getSender().getId());
		// 	MessageChain chain = event.getMessage();
		//
		// 	System.out.println(event.getMessage());
		// });

	}


}
