package com.edu.chatapi.Controller;

import com.edu.chatapi.Model.ChatServices.ChatService;
import com.edu.chatapi.Model.ChatUnits.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/chats/members", produces = "application/json")
public class ChatAndMemberController {

    final Logger log = LoggerFactory.getLogger(ChatAndMemberController.class);
    final ChatService chatService;

    public ChatAndMemberController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public void joinToChat(@RequestParam UUID chatId, Principal principal) {
        String username = principal.getName();
        chatService.addChatMember(new Member(chatId, username, Member.Role.MEMBER));
        log.info("User {} join to chat with id {}", username, chatId);
    }

    @DeleteMapping
    public void leaveFromChat(@RequestParam UUID chatId, Principal principal) {
        String username = principal.getName();
        chatService.removeChatMember(chatId, username);
        log.info("User {} leave from chat with id {}", username, chatId);
    }
}
