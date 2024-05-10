package com.innovation2024.chat_application.entity;

import com.innovation2024.chat_application.enums.Status;
import com.innovation2024.chat_application.enums.UserStatus;
import com.innovation2024.chat_application.model.Chat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Getter
@Setter
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String firstName;
    private UserStatus status;
    private String lastName;
    private String email;
    private String password;
    private String picturePath;
    private List<String> friends = new ArrayList<>();
    private List<String> posts = new ArrayList<>();
    private Map<String, Chat> chats = new HashMap<>();
    private String secret;
    private String location;
    private String occupation;
    private int viewedProfile;
    private int impressions;
    private long createdAt;
    private long updatedAt;

    // Constructors, getters, and setters
}
