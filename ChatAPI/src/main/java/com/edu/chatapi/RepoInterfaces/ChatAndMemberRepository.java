package com.edu.chatapi.RepoInterfaces;

import com.edu.chatapi.Model.ChatUnits.Member;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatAndMemberRepository {

    List<String> getAllChatMembers(UUID chat_id);

    List<UUID> getAllMemberChats(String username);

    boolean isChatContainsMemberWithUsername(UUID chatId, String username);

    Optional<Member.Role> getChatMemberRole(UUID chatId, String username);

    void addChatMember(Member member);

    void deleteMember(Member member);

    void deleteMembersFromChat(UUID chatId);

    boolean isChatMemberHaveRole(UUID chatId, String username, Member.Role role);
}
