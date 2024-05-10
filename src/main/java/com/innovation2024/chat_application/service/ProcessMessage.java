package com.innovation2024.chat_application.service;


import com.innovation2024.chat_application.dto.MessageDto;
import com.innovation2024.chat_application.entity.Message;

import java.util.List;

public interface ProcessMessage {
    public boolean process(MessageDto message);

    public List<Message> getLatestMessage(String sender1, String receiver1);
}
