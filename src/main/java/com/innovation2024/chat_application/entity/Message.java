package com.innovation2024.chat_application.entity;


import com.innovation2024.chat_application.enums.Status;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Getter
@Setter
@Document(collection = "messages")
public class Message {
    @Id
    private ObjectId chat_id;
    private String sender;
    private String receiver;
    private String message;
    private Date date;
    private Status status;
}
