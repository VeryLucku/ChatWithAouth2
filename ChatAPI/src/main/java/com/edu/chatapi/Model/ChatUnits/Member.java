package com.edu.chatapi.Model.ChatUnits;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Member {

    private UUID id;

    private String username;

}
