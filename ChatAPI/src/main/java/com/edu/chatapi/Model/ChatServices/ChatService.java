package com.edu.chatapi.Model.ChatServices;

import com.edu.chatapi.Model.ChatUnits.Chat;
import com.edu.chatapi.RepoInterfaces.ChatAndMemberRepository;
import com.edu.chatapi.RepoInterfaces.ChatRepository;
import com.edu.chatapi.Repositories.JDBCChatAndMemberRepository;
import com.edu.chatapi.Repositories.JDBCChatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChatService {

    ChatRepository chatRepository;
    ChatAndMemberRepository chatAndMemberRepository;

    public ChatService(ChatRepository chatRepository, ChatAndMemberRepository chatAndMemberRepository) {
        this.chatRepository = chatRepository;
        this.chatAndMemberRepository = chatAndMemberRepository;
    }

    @Transactional
    public Chat findById(UUID chatId) {
        Optional<Chat> chatOpt = chatRepository.findById(chatId);

        if (chatOpt.isEmpty()) {
            throw new NullPointerException("Chat with specified id does not exist");
        }

        Chat chat = chatOpt.get();

        chat.setMemberNames(
                chatAndMemberRepository.getAllChatMembers(chatId));

        return chat;
    }

    @Transactional
    public Chat save(Chat chat) {
        chat = chatRepository.save(chat);
        addChatMember(chat.getId(), chat.getAuthor());
        chat.setMemberNames(List.of(chat.getAuthor()));
        return chat;
    }

    @Transactional
    public Iterable<Chat> findAll() {
        List<Chat> chats = chatRepository.findAll();

        for (Chat chat : chats) {
            chat.setMemberNames(
                    chatAndMemberRepository.getAllChatMembers(chat.getId()));
        }

        return chats;
    }

    public void addChatMember(UUID chatId, String username) {
        chatAndMemberRepository.addChatMember(chatId, username);
    }
}
