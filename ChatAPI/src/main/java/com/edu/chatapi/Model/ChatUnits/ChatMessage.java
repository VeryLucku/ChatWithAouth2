package com.edu.chatapi.Model.ChatUnits;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {

    private UUID id = UUID.randomUUID();

    @NotBlank
    private String author;

    private Date createdAt = new Date();

    @NotNull
    @Size(min=3,max=255, message = "Length could be between 3 and 255 symbols")
    private String message;

    @NotNull
    private UUID chatId;

    public ChatMessage(String author, String message, UUID chatId) {
        this.author = author;
        this.message = message;
        this.chatId = chatId;
    }
}
