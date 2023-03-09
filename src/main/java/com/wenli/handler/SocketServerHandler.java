package com.wenli.handler;

import com.corundumstudio.socketio.SocketIOServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SocketServerHandler implements ApplicationRunner {

	private final Logger log = LoggerFactory.getLogger(SocketServerHandler.class);

	@Resource
	private SocketIOServer socketIOServer;


	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("-----------socket server start-----------");
		socketIOServer.start();
	}
}
