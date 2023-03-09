package com.wenli.dao;

import com.wenli.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserDao extends MongoRepository<User, ObjectId> {
}
