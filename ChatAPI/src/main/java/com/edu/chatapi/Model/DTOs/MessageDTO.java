package com.edu.chatapi.Model.DTOs;

import com.edu.chatapi.Model.ChatUnits.ChatMessage;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
public class MessageDTO {
    @NotNull
    @Size(min=3,max=255, message = "Length could be between 3 and 255 symbols")
    private String message;

    @NotNull
    private UUID chat_id;

    public ChatMessage toChatMessage(String author) {
        return new ChatMessage(author,
                message,
                chat_id);
    }
}
