package com.edu.chatapi.Model.ChatServices;

import com.edu.chatapi.CustomExceptions.InvalidActionException;
import com.edu.chatapi.Model.ChatUnits.Member;
import com.edu.chatapi.RepoInterfaces.ChatAndMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ChatAndMemberService {

    ChatAndMemberRepository chatAndMemberRepository;

    public ChatAndMemberService(ChatAndMemberRepository chatAndMemberRepository) {
        this.chatAndMemberRepository = chatAndMemberRepository;
    }


}
