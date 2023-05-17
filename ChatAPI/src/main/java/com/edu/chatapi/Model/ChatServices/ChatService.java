package com.edu.chatapi.Model.ChatServices;

import com.edu.chatapi.Model.ChatUnits.Chat;
import com.edu.chatapi.Model.ChatUnits.Member;
import com.edu.chatapi.RepoInterfaces.ChatAndMemberRepository;
import com.edu.chatapi.RepoInterfaces.ChatRepository;
import com.edu.chatapi.RepoInterfaces.MessageRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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

        chat.setMembers(
                chatAndMemberRepository.getAllChatMembers(chatId));

        return chat;
    }

    @Transactional
    public Chat save(Chat chat) {
        chat = chatRepository.save(chat);
        Member member = new Member(chat.getId(), chat.getAuthor(), Member.Role.OWNER);
        addChatMember(member);
        chat.setMembers(List.of(member));
        return chat;
    }

    @Transactional
    public Iterable<Chat> findAll() {
        List<Chat> chats = chatRepository.findAll();

        for (Chat chat : chats) {
            chat.setMembers(
                    chatAndMemberRepository.getAllChatMembers(chat.getId()));
        }

        return chats;
    }

    @Transactional
    public List<Chat> findAllByUsername(String username) {
        List<UUID> chatIds = chatAndMemberRepository.getAllMemberChats(username);

        List<Chat> chats = new ArrayList<>();

        for (UUID id : chatIds) {
            Chat chat = findById(id);
            chats.add(chat);
        }

        return chats;
    }

    @Transactional
    public void deleteChat(UUID chatId) {

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
        if (chatAndMemberRepository.isChatMemberHasRole(chatId, username, Member.Role.OWNER)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Chat owner can't be removed");
        }

        chatAndMemberRepository.deleteMember(new Member(chatId, username, null));
    }

    @Transactional
    public void changeChatMemberRole(String owner, Member member) {
        if (!chatAndMemberRepository.isChatContainsMemberWithUsername(member.getChatId(), member.getMemberName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Chat doesn't contain specified user");
        }

        chatAndMemberRepository.changeChatMemberRole(member);

        if (member.getRole() == Member.Role.OWNER) {
            chatAndMemberRepository.changeChatMemberRole(new Member(member.getChatId(), owner, Member.Role.MEMBER));
        }
    }
}
