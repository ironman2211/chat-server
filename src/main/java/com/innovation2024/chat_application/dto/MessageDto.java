package com.innovation2024.chat_application.dto;


import com.innovation2024.chat_application.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageDto extends Users{
    private String message;
    private String date;
    private Status status;
}
