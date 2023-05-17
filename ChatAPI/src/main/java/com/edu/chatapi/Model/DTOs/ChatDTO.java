package com.edu.chatapi.Model.DTOs;

import com.edu.chatapi.Model.ChatUnits.Member;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ChatDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotBlank
    @Size(min=3,max=255, message = "Chat size need to be between 3 and 255 symbols")
    private String name;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String author;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Member> members;
}
