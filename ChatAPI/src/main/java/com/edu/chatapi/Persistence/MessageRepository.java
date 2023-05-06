package com.edu.chatapi.Persistence;

import com.edu.chatapi.Model.ChatUnits.ChatMessage;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepository {
    List<ChatMessage> findAll();

    Optional<ChatMessage> findById(UUID id);

    Optional<ChatMessage> save(ChatMessage chatMessage);
}
