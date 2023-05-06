package com.edu.chatapi.Controller;

import com.edu.chatapi.Model.ChatServices.JDBCChatRepository;
import com.edu.chatapi.Model.ChatServices.JDBCMessageRepository;
import com.edu.chatapi.Model.ChatUnits.Chat;
import com.edu.chatapi.Model.ChatUnits.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/chats", produces="application/json")
public class ChatController {

    JDBCChatRepository jdbcChatRepository;

    @Autowired
    public ChatController(JDBCChatRepository jdbcChatRepository) {
        this.jdbcChatRepository = jdbcChatRepository;
    }

    @GetMapping
    public Iterable<Chat> allMessage() {
        return jdbcChatRepository.findAll();
    }

    @PostMapping
    public Chat saveMessage(@RequestBody Chat chat) {
        return jdbcChatRepository.save(chat).get();
    }
}
