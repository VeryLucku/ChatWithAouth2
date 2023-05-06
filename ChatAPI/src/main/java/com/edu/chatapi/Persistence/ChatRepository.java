package com.edu.chatapi.Persistence;

import com.edu.chatapi.Model.ChatUnits.Chat;
import com.edu.chatapi.Model.ChatUnits.ChatMessage;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatRepository {

    Optional<Chat> findById(UUID id);

    Optional<Chat> save(Chat chat);

    List<Chat> findAll();
}
