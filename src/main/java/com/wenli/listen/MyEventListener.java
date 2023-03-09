package com.wenli.listen;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.wenli.dao.FriendMessageDao;
import com.wenli.dao.GroupMessageDao;
import com.wenli.entity.FriendMessage;
import com.wenli.entity.GroupMessage;
import com.wenli.entity.vo.NewMessageVo;
import com.wenli.service.FriendMessageService;
import com.wenli.service.GroupMessageService;
import kotlin.coroutines.CoroutineContext;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;

@Component
@Slf4j(topic = "MyEventListener.class")
public class MyEventListener extends SimpleListenerHost {

	private SocketIOServer socketIOServer;


	private GroupMessageService groupMessageService;

	private FriendMessageService friendMessageService;

	public MyEventListener(SocketIOServer socketIOServer, FriendMessageService friendMessageService, GroupMessageService groupMessageService){
		this.socketIOServer = socketIOServer;
		this.friendMessageService = friendMessageService;
		this.groupMessageService = groupMessageService;
	}

	@Override
	public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
		exception.printStackTrace();
	}

	@EventHandler
	public void onGroupMessage(@NotNull GroupMessageEvent event) throws Exception{
		String botId = String.valueOf(event.getBot().getId());
		NewMessageVo newMessageVo = new NewMessageVo(event, "GROUP");
		newMessageVo.getIsReadUser().add(newMessageVo.getSenderId());

		GroupMessage groupMessage = new GroupMessage();
		BeanUtils.copyProperties(newMessageVo, groupMessage);
		groupMessageService.addNewGroupMessage(groupMessage);

		Collection<SocketIOClient> allClients = socketIOServer.getAllClients();
		allClients.forEach(client -> {
			if (client.get(String.valueOf(client.getSessionId())).toString().equals(String.valueOf(event.getBot().getId()))){
				client.sendEvent("receiveMessage", newMessageVo);
				log.info("receiveMessage succeed. ");
			}
		});
	}

	@EventHandler
	public void onFriendMessage(@NotNull FriendMessageEvent event) throws Exception{
		NewMessageVo newMessageVo = new NewMessageVo(event, "FRIEND");
		newMessageVo.getIsReadUser().add(newMessageVo.getSenderId());

		FriendMessage friendMessage = new FriendMessage();
		BeanUtils.copyProperties(newMessageVo, friendMessage);
		friendMessageService.addNewSingleMessage(friendMessage);

		Collection<SocketIOClient> allClients = socketIOServer.getAllClients();
		allClients.forEach(client -> {
			if (client.get(String.valueOf(client.getSessionId())).toString().equals(String.valueOf(event.getBot().getId()))){
				client.sendEvent("receiveMessage", newMessageVo);
				log.info("receiveMessage succeed. ");
			}
		});
	}

}
