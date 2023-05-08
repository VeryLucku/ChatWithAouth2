package com.edu.chatapi.Model.ChatUnits;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
