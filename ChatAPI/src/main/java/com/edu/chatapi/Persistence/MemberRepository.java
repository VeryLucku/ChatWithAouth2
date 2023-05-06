package com.edu.chatapi.Persistence;

import com.edu.chatapi.Model.ChatUnits.Member;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberRepository {

    Optional<Member> getMember(UUID id);

    List<Member> getAllMembers(List<UUID> id);
}
