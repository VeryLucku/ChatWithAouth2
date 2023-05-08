package com.edu.chatapi.Controller;

import com.edu.chatapi.ChatApiApplication;
import com.edu.chatapi.Model.ChatServices.ChatService;
import com.edu.chatapi.Repositories.JDBCChatRepository;
import com.edu.chatapi.Model.ChatUnits.Chat;
import com.edu.chatapi.Model.DTOs.ChatDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping(path="/api/chats", produces="application/json")
public class ChatController {

    final Logger log = LoggerFactory.getLogger(ChatController.class);
    ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping(path = "/all")
    public Iterable<Chat> allChats() {
        return chatService.findAll();
    }

    @GetMapping("/{id}")
    public Chat getChatById(@PathVariable UUID id) {
        return chatService.findById(id);
    }

    @PostMapping
    public Chat createChat(@Valid @RequestBody ChatDTO chatDTO,
                           Principal principal) {
        Chat chat = chatDTO.toChat(principal.getName());
        chat = chatService.save(chat);
        log.info("Chat with id {} was created, author {}", chat.getId(), chat.getAuthor());
        return chat;
    }

    @PostMapping("/join")
    public void joinToChat(@RequestParam UUID chatId, Principal principal) {
        chatService.addChatMember(chatId, principal.getName());
    }
}
