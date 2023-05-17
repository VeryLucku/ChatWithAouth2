package com.edu.chatapi.Model.Mappers;

import com.edu.chatapi.Model.ChatUnits.ChatMessage;
import com.edu.chatapi.Model.DTOs.ChatMessageDTO;
import org.springframework.stereotype.Component;

@Component
public class ChatMessageMapper {

    public ChatMessageDTO toMessageDTO(ChatMessage chatMessage) {
        return new ChatMessageDTO(chatMessage.getId(), chatMessage.getMessage(), chatMessage.getAuthor(), chatMessage.getChatId());
    }

    public ChatMessage toChatMessage(ChatMessageDTO chatMessageDTO, String author) {
        return new ChatMessage(author,
                chatMessageDTO.getMessage(),
                chatMessageDTO.getChatId());
    }
}
