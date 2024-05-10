package com.innovation2024.chat_application.model;

import com.innovation2024.chat_application.entity.Message;
import com.innovation2024.chat_application.enums.UserStatus;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Builder
@Data
public class Chat {
    private String _id;
    private String name;
    private String picturePath;
    private List<Message> last_message;
    private UserStatus status;
    private Date lastUpdated;

}
