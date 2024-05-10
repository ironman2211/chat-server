package com.innovation2024.chat_application.contoller;

import com.innovation2024.chat_application.dto.MessageDto;
import com.innovation2024.chat_application.dto.Users;
import com.innovation2024.chat_application.entity.Message;
import com.innovation2024.chat_application.service.ProcessMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Slf4j
@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ProcessMessage processMessage;

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    private MessageDto receivePublicMessage(@Payload MessageDto message) {
        return message;
    }


    @MessageMapping("/private-message")
    private MessageDto receivePrivateMessage(@Payload MessageDto message) {
        try {
            if (processMessage.process((message)))
                simpMessagingTemplate.convertAndSendToUser(message.getReceiver(), "/private", message);
            return message;
        } catch (Exception e) {
            log.error("Exception During Getting Message");
            throw e;
        }
    }
}
