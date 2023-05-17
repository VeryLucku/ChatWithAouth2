package com.edu.chatapi.Model.Mappers;

import com.edu.chatapi.Model.ChatUnits.Chat;
import com.edu.chatapi.Model.DTOs.ChatDTO;
import org.springframework.stereotype.Component;

@Component
public class ChatMapper {
    public ChatDTO toChatDTO(Chat chat) {
        return new ChatDTO(chat.getId(), chat.getName(), chat.getAuthor(), chat.getMembers());
    }

    public Chat toChat(ChatDTO chatDTO, String author) {
        return new Chat(chatDTO.getName(),
                author);
    }
}
