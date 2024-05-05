package com.innovation2024.chat_application.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Message {
    private String sender;
    private String receiver;
    private String message;
    private String date;
    private Status status;
}
