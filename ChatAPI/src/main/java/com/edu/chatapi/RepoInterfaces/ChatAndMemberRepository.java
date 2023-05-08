package com.edu.chatapi.RepoInterfaces;

import java.util.List;
import java.util.UUID;

public interface ChatAndMemberRepository {

    List<String> getAllChatMembers(UUID chat_id);

    boolean isChatContainsMemberWithUsername(UUID chat_id, String username);

    void addChatMember(UUID chatId, String username);

    void deleteMember(UUID chatId, String username);

    void deleteMembersFromChat(UUID chatId);
}
