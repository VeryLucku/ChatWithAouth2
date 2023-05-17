package com.edu.chatapi.Model.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ChatMessageDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotNull
    @Size(min=3,max=255, message = "Length could be between 3 and 255 symbols")
    private String message;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String author;

    @NotNull
    private UUID chatId;

}
