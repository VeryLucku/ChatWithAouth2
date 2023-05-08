package com.edu.chatapi.RepoInterfaces;

import com.edu.chatapi.Model.ChatUnits.Chat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatRepository {

    Optional<Chat> findById(UUID id);

    Chat save(Chat chat);

    List<Chat> findAll();
}
