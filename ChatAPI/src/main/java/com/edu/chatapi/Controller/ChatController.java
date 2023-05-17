package com.edu.chatapi.Controller;

import com.edu.chatapi.Model.ChatServices.ChatService;
import com.edu.chatapi.Model.ChatUnits.Chat;
import com.edu.chatapi.Model.DTOs.ChatDTO;
import com.edu.chatapi.Model.Mappers.ChatMapper;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path="/api/chats", produces="application/json")
public class ChatController {

    final Logger log = LoggerFactory.getLogger(ChatController.class);
    private final ChatService chatService;
    private final ChatMapper chatMapper;

    @Autowired
    public ChatController(ChatService chatService, ChatMapper chatMapper) {
        this.chatService = chatService;
        this.chatMapper = chatMapper;
    }

    @GetMapping(path = "/all")
    public Iterable<Chat> allChats() {
        return chatService.findAll();
    }

    @GetMapping
    public List<ChatDTO> getAllUserChats(Principal principal) {
        List<Chat> chats = chatService.findAllByUsername(principal.getName());

        return chats.stream()
                .map(chatMapper::toChatDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ChatDTO getChatById(@PathVariable UUID id) {
        Chat chat = chatService.findById(id);
        return chatMapper.toChatDTO(chat);
    }

    @PostMapping
    public ChatDTO createChat(@Valid @RequestBody ChatDTO chatDTO,
                           Principal principal) {
        Chat chat = chatMapper.toChat(chatDTO, principal.getName());
        chat = chatService.save(chat);
        log.info("Chat with id {} was created, author {}", chat.getId(), chat.getAuthor());
        return chatMapper.toChatDTO(chat);
    }

    @DeleteMapping("/{id}")
    public void deleteChat(@PathVariable UUID id) {
        chatService.deleteChat(id);
        log.info("Chat with id {} was deleted", id);
    }

    @DeleteMapping("/removeMember")
    public void removeMember(@RequestParam UUID chatId,
                             @RequestParam String username) {
        chatService.removeChatMember(chatId, username);
    }
}
