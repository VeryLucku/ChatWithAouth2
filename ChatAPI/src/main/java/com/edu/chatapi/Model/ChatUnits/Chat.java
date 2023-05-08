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

    private List<String> memberNames;

    public Chat(String name, String author) {
        this.name = name;
        this.author = author;
    }

    public Chat(UUID id, String name, String author) {
        this.id = id;
        this.name = name;
        this.author = author;
    }
}
