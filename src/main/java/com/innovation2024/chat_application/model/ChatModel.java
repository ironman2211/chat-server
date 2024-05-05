package com.innovation2024.chat_application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatModel {
    private String senderName;
    private String receiverName;
    private String date;
    private String status;
}
