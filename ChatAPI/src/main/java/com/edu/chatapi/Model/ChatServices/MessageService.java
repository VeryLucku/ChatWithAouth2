package com.edu.chatapi.Model.ChatServices;

import com.edu.chatapi.Model.ChatUnits.ChatMessage;
import com.edu.chatapi.RepoInterfaces.ChatAndMemberRepository;
import com.edu.chatapi.RepoInterfaces.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class MessageService {

    MessageRepository messageRepository;
    ChatAndMemberRepository chatAndMemberRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository,
                          ChatAndMemberRepository chatAndMemberRepository) {
        this.messageRepository = messageRepository;
        this.chatAndMemberRepository = chatAndMemberRepository;
    }

    @Transactional
    public ChatMessage save(ChatMessage message) {
        if (chatAndMemberRepository.isChatContainsMemberWithUsername(message.getChatId(), message.getAuthor())) {
            messageRepository.save(message);
        } else {
            throw new NullPointerException("User must join into chat before start to send messages");
        }
        return message;
    }

    public Iterable<ChatMessage> findAll() {
        return messageRepository.findAll();
    }

    public ChatMessage findById(UUID id) {
        Optional<ChatMessage> chatOpt = messageRepository.findById(id);

        if (chatOpt.isEmpty()) {
            throw new NullPointerException("Message with specified id was not found");
        }

        return chatOpt.get();
    }

    public Iterable<ChatMessage> findAllByChatId(UUID chatId) {
        return messageRepository.findAllByChatId(chatId);
    }

    public void deleteChatMessage(UUID id) {
        messageRepository.deleteMessage(id);
    }
}
