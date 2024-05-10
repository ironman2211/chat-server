package com.innovation2024.chat_application.repository;

import com.innovation2024.chat_application.entity.Message;
import com.innovation2024.chat_application.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Long> {
    Optional<User> findById(ObjectId objectId);
}
