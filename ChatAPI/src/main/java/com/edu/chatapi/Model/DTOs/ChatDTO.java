package com.edu.chatapi.Model.DTOs;

import com.edu.chatapi.Model.ChatUnits.Chat;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class ChatDTO {

    @NotBlank
    @Size(min=3,max=255, message = "Chat size need to be between 3 and 255 symbols")
    private String name;

    public Chat toChat(String author) {
        return new Chat(name,
                author);
    }
}
