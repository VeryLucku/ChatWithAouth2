package com.edu.chatapi.Model.ChatServices;

import com.edu.chatapi.Model.ChatUnits.Chat;
import com.edu.chatapi.RepoInterfaces.ChatAndMemberRepository;
import com.edu.chatapi.RepoInterfaces.ChatRepository;
import com.edu.chatapi.RepoInterfaces.MessageRepository;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLDataException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatAndMemberRepository chatAndMemberRepository;

    private final MessageRepository messageRepository;

    public ChatService(ChatRepository chatRepository, ChatAndMemberRepository chatAndMemberRepository,
                       MessageRepository messageRepository) {
        this.chatRepository = chatRepository;
        this.chatAndMemberRepository = chatAndMemberRepository;
        this.messageRepository = messageRepository;
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

    @Transactional
    public void addChatMember(UUID chatId, String username) {
        if (chatAndMemberRepository.isChatContainsMemberWithUsername(chatId, username)) {
            throw new NullPointerException("User have already joined this chat");
        }
        chatAndMemberRepository.addChatMember(chatId, username);
    }

    public void removeChatMember(UUID chatId, String username) {
        chatAndMemberRepository.deleteMember(chatId, username);
    }

    @Transactional
    public void deleteChat(UUID chatId, String username) {
        Optional<String> authorOpt = chatRepository.getChatAuthor(chatId);

        if (authorOpt.isEmpty()) {
            throw new NullPointerException("Chat with specified id does not exist");
        }

        String author = authorOpt.get();

        if (!author.equals(username)) {
            throw new NullPointerException("Only author of chat can delete it");
        }

        messageRepository.deleteMessagesFromChat(chatId);
        chatAndMemberRepository.deleteMembersFromChat(chatId);
        chatRepository.deleteChat(chatId);
    }
}
