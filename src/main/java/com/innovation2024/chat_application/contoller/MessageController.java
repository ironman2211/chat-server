package com.innovation2024.chat_application.contoller;

import com.innovation2024.chat_application.dto.Users;
import com.innovation2024.chat_application.entity.Message;
import com.innovation2024.chat_application.service.ProcessMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/chats")
@CrossOrigin("*")
public class MessageController {
    @Autowired
    private ProcessMessage processMessage;

    @PostMapping("/getAll")
    private List<Message> getAllChats(@RequestBody Users users) {
        return processMessage.getLatestMessage(users.getSender(), users.getReceiver());
    }
}
