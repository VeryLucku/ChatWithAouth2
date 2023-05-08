package com.edu.chatapi.Controller;

import com.edu.chatapi.ChatApiApplication;
import com.edu.chatapi.Model.ChatServices.MessageService;
import com.edu.chatapi.Repositories.JDBCMessageRepository;
import com.edu.chatapi.Model.ChatUnits.ChatMessage;
import com.edu.chatapi.Model.DTOs.MessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.UUID;


@RestController
@RequestMapping(path="/api/messages", produces="application/json")
public class MessageController {

    final Logger log = LoggerFactory.getLogger(MessageController.class);
    final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/all")
    public Iterable<ChatMessage> allMessages() {
        return messageService.findAll();
    }

    @GetMapping
    public Iterable<ChatMessage> allChatMessages(@RequestParam UUID chatId) {
        return messageService.findAllByChatId(chatId);
    }

    @GetMapping("/{id}")
    public ChatMessage getChatMessage(@PathVariable UUID id) {
        return messageService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteChatMessage(@PathVariable UUID id) {
        messageService.deleteChatMessage(id);
    }

    @PostMapping
    public ChatMessage saveMessage(@Valid @RequestBody MessageDTO messageDTO,
                                   Principal principal) {
        ChatMessage chatMessage = messageDTO.toChatMessage(principal.getName());
        chatMessage = messageService.save(chatMessage);
        log.info("Message with id {} was created, author {}", chatMessage.getId(), principal.getName());
        return chatMessage;
    }
}
