package com.edu.chatapi.Repositories;

import com.edu.chatapi.Model.ChatUnits.Member;
import com.edu.chatapi.RepoInterfaces.ChatAndMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JDBCChatAndMemberRepository implements ChatAndMemberRepository {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public JDBCChatAndMemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Member> getAllChatMembers(UUID chat_id) {
        return jdbcTemplate.query(
                "select member_name, role from \"chat-member\" where chat_id=?",
                this::mapRowToMember,
                chat_id
        );
    }

    @Override
    public List<UUID> getAllMemberChats(String username) {
        return jdbcTemplate.query(
                "select chat_id from \"chat-member\" where member_name=?",
                this::mapRowToChatId,
                username
        );
    }

    @Override
    public boolean isChatContainsMemberWithUsername(UUID chatId, String username) {
        List<String> usernames = jdbcTemplate.query(
                "select member_name from \"chat-member\" where chat_id=? and member_name=?",
                this::mapRowToMemberName,
                chatId,
                username
        );

        return usernames.size() == 1;
    }

    @Override
    public Optional<Member.Role> getChatMemberRole(UUID chatId, String username) {
        List<Member.Role> results = jdbcTemplate.query(
                "select role from \"chat-member\" where chat_id=? and member_name=?",
                this::mapRowToMemberRole,
                chatId,
                username
        );

        return results.size() == 0 ?
        Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public void changeChatMemberRole(Member member) {
        jdbcTemplate.update(
                "update \"chat-member\" set role = ? where chat_id = ? and member_name = ?",
                member.getRole().toString(),
                member.getChatId(),
                member.getMemberName()
        );
    }

    @Override
    public boolean isChatMemberHasRole(UUID chatId, String username, Member.Role role) {
        Optional<Member.Role> realRole = getChatMemberRole(chatId, username);
        return realRole.isPresent() && realRole.get().equals(role);
    }

    @Override
    public void addChatMember(Member member) {
        jdbcTemplate.update(
                "insert into \"chat-member\" (chat_id, member_name, role) values (?, ?, ?)",
                member.getChatId(),
                member.getMemberName(),
                member.getRole().toString()
        );
    }

    @Override
    public void deleteMember(Member member) {

        int count = jdbcTemplate.update(
                "delete from \"chat-member\" where chat_id = ? and member_name = ?",
                member.getChatId(),
                member.getMemberName()
        );
        if (count == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You aren't joined this chat");
        }
    }

    @Override
    public void deleteMembersFromChat(UUID chatId) {
        jdbcTemplate.update(
                "delete from \"chat-member\" where chat_id = ?",
                chatId
        );
    }

    private String mapRowToMemberName(ResultSet row, int rowNum) throws SQLException {
        return row.getString("member_name");
    }

    private UUID mapRowToChatId(ResultSet row, int rowNum) throws SQLException {
        return UUID.fromString(row.getString("chat_id"));
    }

    private Member.Role mapRowToMemberRole(ResultSet row, int rowNum) throws SQLException {
        return Member.Role.valueOf(row.getString("role"));
    }

    private Member mapRowToMember(ResultSet row, int rowNum) throws SQLException {
        return new Member(row.getString("member_name"), Member.Role.valueOf(row.getString("role")));
    }
}
