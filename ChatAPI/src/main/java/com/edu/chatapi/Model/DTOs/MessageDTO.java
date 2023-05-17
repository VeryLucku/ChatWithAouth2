package com.edu.chatapi.Model.DTOs;

import com.edu.chatapi.Model.ChatUnits.ChatMessage;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class MessageDTO {
    @NotNull
    @Size(min=3,max=255, message = "Length could be between 3 and 255 symbols")
    private String message;

    @NotNull
    private UUID chatId;

    public ChatMessage toChatMessage(String author) {
        return new ChatMessage(author,
                message,
                chatId);
    }
}
