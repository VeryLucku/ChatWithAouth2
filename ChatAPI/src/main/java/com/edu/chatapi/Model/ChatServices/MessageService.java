package com.edu.chatapi.Model.ChatServices;

import com.edu.chatapi.Model.ChatUnits.ChatMessage;
import com.edu.chatapi.RepoInterfaces.ChatAndMemberRepository;
import com.edu.chatapi.RepoInterfaces.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    public List<ChatMessage> findAll() {
        return messageRepository.findAll();
    }

    public ChatMessage findById(UUID id) {
        Optional<ChatMessage> chatOpt = messageRepository.findById(id);

        return chatOpt.orElseThrow(() -> new NullPointerException("Message with specified id was not found"));
    }

    public List<ChatMessage> findAllByChatId(UUID chatId) {
        return messageRepository.findAllByChatId(chatId);
    }

    @Transactional
    public void deleteChatMessage(UUID id, String username) {
        Optional<ChatMessage> message = messageRepository.findById(id);

        if (message.isEmpty()) {
            throw new NullPointerException("Message with given id does not exist");
        }

        message.ifPresent(mes -> {
            if (!mes.getAuthor().equals(username)) {
                throw new AccessDeniedException("Only author of message can delete it");
            }

            messageRepository.deleteMessage(id);
        });

    }
}
