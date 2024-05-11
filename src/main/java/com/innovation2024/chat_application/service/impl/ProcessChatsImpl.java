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
import java.util.Optional;

@Slf4j
@Service
public class ProcessChatsImpl implements ProcessChats {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void updateChatList(Message message) {
        try {
            ObjectId senderId = new ObjectId(message.getSender());
            ObjectId receiverId = new ObjectId(message.getReceiver());

            Optional<User> senderOptional = userRepository.findById(senderId);
            Optional<User> receiverOptional = userRepository.findById(receiverId);

            if (senderOptional.isPresent() && receiverOptional.isPresent()) {
                User sender = senderOptional.get();
                User receiver = receiverOptional.get();

                updateUserChat(sender, receiver, message);
                updateUserChat(receiver, sender, message);

                sender.setStatus(UserStatus.ONLINE);
                userRepository.saveAll(List.of(sender, receiver));
                log.info("Updated chat for sender {} and receiver {}", sender.getId(), receiver.getId());
            } else {
                log.warn("Sender or receiver not found for message: {}", message);
            }
        } catch (Exception e) {
            log.error("Exception during updating chats", e);
        }
    }

    private void updateUserChat(User user, User counterParty, Message message) {
        Chat chat = user.getChats().get(counterParty.getId());
        if (chat == null) {
            chat = createNewChat(counterParty,message);
            user.getChats().put(counterParty.getId(), chat);
        } else {
            List<Message> lastMessages = chat.getLast_message();
            if (lastMessages.size() >= 5) {
                lastMessages.remove(0);
            }
            lastMessages.add(message);
            chat.setLast_message(lastMessages);
            chat.setLastUpdated(new Date());
        }
    }

    private Chat createNewChat(User counterParty,Message message) {
        return Chat.builder()
                ._id(counterParty.getId())
                .picturePath(counterParty.getPicturePath())
                .status(counterParty.getStatus() != null ? counterParty.getStatus() : UserStatus.ONLINE)
                .name(counterParty.getFirstName() + " " + counterParty.getLastName())
                .lastUpdated(new Date())
                .last_message(List.of(message))
                .build();
    }
}
