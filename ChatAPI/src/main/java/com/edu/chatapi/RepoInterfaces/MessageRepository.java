package com.edu.chatapi.RepoInterfaces;

import com.edu.chatapi.Model.ChatUnits.ChatMessage;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepository {
    List<ChatMessage> findAll();

    Optional<ChatMessage> findById(UUID id);

    ChatMessage save(ChatMessage chatMessage);

    List<ChatMessage> findAllByChatId(UUID chatId);
}
