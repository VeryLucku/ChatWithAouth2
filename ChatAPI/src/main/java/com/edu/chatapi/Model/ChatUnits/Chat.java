package com.edu.chatapi.Model.ChatUnits;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chat {

    private UUID id = UUID.randomUUID();

    private String name;

    private String author;


    private List<String> member_ids;
}
