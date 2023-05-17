package com.edu.chatapi.Controller;

import com.edu.chatapi.Model.ChatServices.MessageService;
import com.edu.chatapi.Model.ChatUnits.ChatMessage;
import com.edu.chatapi.Model.DTOs.ChatMessageDTO;
import com.edu.chatapi.Model.Mappers.ChatMessageMapper;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping(path="/api/messages", produces="application/json")
public class MessageController {

    private final Logger log = LoggerFactory.getLogger(MessageController.class);
    private final MessageService messageService;

    private final ChatMessageMapper chatMessageMapper;



    @Autowired
    public MessageController(MessageService messageService, ChatMessageMapper chatMessageMapper) {
        this.messageService = messageService;
        this.chatMessageMapper = chatMessageMapper;
    }

    @GetMapping("/all")
    public List<ChatMessageDTO> allMessages() {
        List<ChatMessage> chatMessages = messageService.findAll();
        return chatMessages.stream()
                .map(chatMessageMapper::toMessageDTO)
                .toList();
    }

    @GetMapping
    public List<ChatMessageDTO> allChatMessages(@RequestParam UUID chatId) {
        List<ChatMessage> chatMessages = messageService.findAllByChatId(chatId);
        return chatMessages.stream()
                .map(chatMessageMapper::toMessageDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ChatMessageDTO getChatMessage(@PathVariable UUID id) {
        return chatMessageMapper.toMessageDTO(messageService.findById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteChatMessage(@PathVariable UUID id, Principal principal) {
        messageService.deleteChatMessage(id, principal.getName());
    }

    @PostMapping
    public ChatMessageDTO saveMessage(@Valid @RequestBody ChatMessageDTO chatMessageDTO,
                                      Principal principal) {
        ChatMessage chatMessage = chatMessageMapper.toChatMessage(chatMessageDTO, principal.getName());
        chatMessage = messageService.save(chatMessage);
        log.info("Message with id {} was created, author {}", chatMessage.getId(), principal.getName());
        return chatMessageMapper.toMessageDTO(chatMessage);
    }
}
