package com.edu.chatapi.Controller;

import com.edu.chatapi.ChatApiApplication;
import com.edu.chatapi.Model.ChatServices.JDBCMessageRepository;
import com.edu.chatapi.Model.ChatUnits.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path="/api/messages", produces="application/json")
public class MessageController {

    final Logger log = LoggerFactory.getLogger(ChatApiApplication.class);
    JDBCMessageRepository JDBCMessageRepository;

    @Autowired
    public MessageController(JDBCMessageRepository JDBCMessageRepository) {
        this.JDBCMessageRepository = JDBCMessageRepository;
    }

    @GetMapping
    public Iterable<ChatMessage> allMessage() {
        return JDBCMessageRepository.findAll();
    }

    @PostMapping
    public ChatMessage saveMessage(@RequestBody ChatMessage message) {
        return JDBCMessageRepository.save(message).get();
    }
}
