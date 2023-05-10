package com.edu.chatapi.Model.ChatServices;

import com.edu.chatapi.CustomExceptions.InvalidActionException;
import com.edu.chatapi.Model.ChatUnits.Chat;
import com.edu.chatapi.Model.ChatUnits.Member;
import com.edu.chatapi.RepoInterfaces.ChatAndMemberRepository;
import com.edu.chatapi.RepoInterfaces.ChatRepository;
import com.edu.chatapi.RepoInterfaces.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatAndMemberRepository chatAndMemberRepository;

    private final MessageRepository messageRepository;

    public ChatService(ChatRepository chatRepository, ChatAndMemberRepository chatAndMemberRepository,
                       MessageRepository messageRepository) {
        this.chatRepository = chatRepository;
        this.chatAndMemberRepository = chatAndMemberRepository;
        this.messageRepository = messageRepository;
    }

    @Transactional
    public Chat findById(UUID chatId) {
        Optional<Chat> chatOpt = chatRepository.findById(chatId);

        if (chatOpt.isEmpty()) {
            throw new NullPointerException("Chat with specified id does not exist");
        }

        Chat chat = chatOpt.get();

        chat.setMemberNames(
                chatAndMemberRepository.getAllChatMembers(chatId));

        return chat;
    }

    @Transactional
    public Chat save(Chat chat) {
        chat = chatRepository.save(chat);
        Member member = new Member(chat.getId(), chat.getAuthor(), Member.Role.OWNER);
        addChatMember(member);
        chat.setMemberNames(List.of(chat.getAuthor()));
        return chat;
    }

    @Transactional
    public Iterable<Chat> findAll() {
        List<Chat> chats = chatRepository.findAll();

        for (Chat chat : chats) {
            chat.setMemberNames(
                    chatAndMemberRepository.getAllChatMembers(chat.getId()));
        }

        return chats;
    }

    @Transactional
    public void deleteChat(UUID chatId, String username) {
        //TODO add filter of requests to not search member role with every request
        Member.Role role = chatAndMemberRepository.getChatMemberRole(chatId, username);

        if (role != Member.Role.OWNER) {
            throw new NullPointerException("Only author of chat can delete it");
        }

        messageRepository.deleteMessagesFromChat(chatId);
        chatAndMemberRepository.deleteMembersFromChat(chatId);
        chatRepository.deleteChat(chatId);
    }

    @Transactional
    public void addChatMember(Member member) {
        if (chatAndMemberRepository.isChatContainsMemberWithUsername(member.getChatId(), member.getMemberName())) {
            throw new NullPointerException("User have already joined this chat");
        }
        chatAndMemberRepository.addChatMember(member);
    }

    @Transactional
    public void removeChatMember(UUID chatId, String username) {

        Member.Role role = chatAndMemberRepository.getChatMemberRole(chatId, username);

        if (role == Member.Role.OWNER) {
            throw new InvalidActionException("Chat owner can't leave it's own chat");
        }

        chatAndMemberRepository.deleteMember(new Member(chatId, username, role));
    }
}
