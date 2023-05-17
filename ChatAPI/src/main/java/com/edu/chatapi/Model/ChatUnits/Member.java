package com.edu.chatapi.Model.ChatUnits;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Member {

    @JsonIgnore
    private UUID chatId;

    @NotBlank
    private String memberName;

    @NotNull
    private Role role;

    public enum Role {
        MEMBER,
        OWNER,
        ADMIN
    }

    public Member(String memberName, Role role) {
        this.memberName = memberName;
        this.role = role;
    }
}
