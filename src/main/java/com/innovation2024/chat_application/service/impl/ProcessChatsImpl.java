package com.innovation2024.chat_application.service.impl;

import com.innovation2024.chat_application.entity.Message;
import com.innovation2024.chat_application.entity.User;
import com.innovation2024.chat_application.enums.UserStatus;
import com.innovation2024.chat_application.model.Chat;
import com.innovation2024.chat_application.repository.UserRepository;
import com.innovation2024.chat_application.service.ProcessChats;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class ProcessChatsImpl implements ProcessChats {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void updateChatList(Message message) {
        try {
            Optional<User> senderOptional = userRepository.findById(new ObjectId(message.getSender()));
            Optional<User> receiverOptional = userRepository.findById(new ObjectId(message.getReceiver()));

            if (senderOptional.isPresent() && receiverOptional.isPresent()) {
                User sender = senderOptional.get();
                User receiver = receiverOptional.get();
                Map<String, Chat> chats = sender.getChats();
                Chat chat = chats.compute(message.getReceiver(), (key, existingChat) -> updateUserChatList(existingChat, message, receiver));
                sender.setStatus(UserStatus.ONLINE);
                userRepository.save(sender);
                log.info("Updated chat: {}", chat);
            } else {
                log.warn("Sender or receiver not found for message: {}", message);
            }
        } catch (Exception e) {

            log.error("Exception during updating chats", e);
        }
    }

    private Chat updateUserChatList(Chat existingChat, Message message, User receiver) {
        UserStatus status = receiver.getStatus(); // Get receiver status
        String name = receiver.getFirstName() + "_" + receiver.getLastName(); // Get receiver name
        if (existingChat != null) {
            List<Message> lastMessage = existingChat.getLast_message();
            if (existingChat.getLast_message().size() >= 5) {
                lastMessage.remove(0);
            }
            lastMessage.add(message);
            return Chat.builder()
                    ._id(message.getReceiver())
                    .picturePath(receiver.getPicturePath())
                    .last_message(lastMessage)
                    .status(status != null ? status : UserStatus.ONLINE) // Default to ONLINE status if receiver status is null
                    .name(name)
                    .lastUpdated(new Date())
                    .build();
        } else {
            return Chat.builder()
                    ._id(message.getReceiver())
                    .picturePath(receiver.getPicturePath())
                    .last_message(List.of(message))
                    .status(status != null ? status : UserStatus.ONLINE) // Default to ONLINE status if receiver status is null
                    .name(name)
                    .lastUpdated(new Date())
                    .build();
        }
    }
}
