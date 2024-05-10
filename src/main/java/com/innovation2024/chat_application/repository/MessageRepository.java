package com.innovation2024.chat_application.repository;


import com.innovation2024.chat_application.entity.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends MongoRepository<Message, Long> {
    List<Message> findBySenderAndReceiverOrSenderAndReceiver(String sender1, String receiver1, String sender2, String receiver2);
    Optional<Message> findTopBySenderInAndReceiverInOrderByDateDesc(String[] senders, String[] receivers);

}
