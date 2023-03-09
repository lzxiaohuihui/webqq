package com.wenli.dao;

import com.wenli.entity.myBot.MyBot;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MyBotDao extends MongoRepository<MyBot, String> {
}
