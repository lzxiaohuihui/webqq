package com.wenli.dao;

import com.wenli.entity.GroupMessage;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupMessageDao extends MongoRepository<GroupMessage, ObjectId> {

}
