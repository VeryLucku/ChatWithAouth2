package com.edu.chatapi.Model.ChatUnits;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
