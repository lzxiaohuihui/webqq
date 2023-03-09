package com.wenli.dao;

import com.wenli.entity.FriendMessage;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FriendMessageDao extends MongoRepository<FriendMessage, ObjectId> {
}
