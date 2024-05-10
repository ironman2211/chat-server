package com.innovation2024.chat_application.service.impl;

import com.innovation2024.chat_application.dto.MessageDto;
import com.innovation2024.chat_application.entity.Message;
import com.innovation2024.chat_application.repository.MessageRepository;
import com.innovation2024.chat_application.repository.UserRepository;
import com.innovation2024.chat_application.service.ProcessChats;
import com.innovation2024.chat_application.service.ProcessMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ProcessMessageImpl implements ProcessMessage {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    ProcessChats processChats;

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean process(MessageDto message) {
        try {
            Message messageModel = new Message();
            messageModel.setReceiver(message.getReceiver());
            messageModel.setSender(message.getSender());
            messageModel.setMessage(message.getMessage());
            messageModel.setDate(new Timestamp(new Date().getTime()));
            messageModel.setStatus(message.getStatus());
            processChats.updateChatList(messageModel);

//    const { id } = req.params;
//    const { userId } = req.body;
//    const user = await User.findById(userId);
//    user.chats.push(id);
//    user.save();
//    res.status(200).json(user);


            messageRepository.save(messageModel);
            return true;
        } catch (Exception e) {
            log.error("Exception during processing the data");
        }
        return false;
    }

    @Override
    public List<Message> getLatestMessage(String sender1, String receiver1) {
        String[] senders = {sender1, receiver1};
        String[] receivers = {receiver1, sender1};
        return messageRepository.findBySenderAndReceiverOrSenderAndReceiver(sender1, receiver1, receiver1,sender1);
    }
}
