package com.edu.chatapi.Model.ChatUnits;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Member {

    private UUID chatId;

    private String memberName;

    @JsonIgnore
    private Role role;

    public enum Role {
        MEMBER,
        OWNER,
        ADMIN
    }
}
