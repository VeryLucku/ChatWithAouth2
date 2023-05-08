package com.edu.chatapi.Repositories;

import com.edu.chatapi.RepoInterfaces.ChatAndMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
public class JDBCChatAndMemberRepository implements ChatAndMemberRepository {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public JDBCChatAndMemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<String> getAllChatMembers(UUID chat_id) {
        return jdbcTemplate.query(
                "select member_name from \"chat-member\" where chat_id=?",
                this::mapRowToMember,
                chat_id
        );
    }

    @Override
    public boolean isChatContainsMemberWithUsername(UUID chatId, String username) {
        List<String> usernames = jdbcTemplate.query(
                "select member_name from \"chat-member\" where chat_id=? and member_name=?",
                this::mapRowToMember,
                chatId,
                username
        );

        return usernames.size() == 1;
    }

    @Override
    public void addChatMember(UUID chatId, String username) {
        jdbcTemplate.update(
                "insert into \"chat-member\" (chat_id, member_name) values (?, ?)",
                chatId,
                username
        );
    }

    @Override
    public void deleteMember(UUID chatId, String username) {
        jdbcTemplate.update(
                "delete from \"chat-member\" where chat_id = ? and member_name = ?",
                chatId,
                username
        );
    }

    @Override
    public void deleteMembersFromChat(UUID chatId) {
        jdbcTemplate.update(
                "delete from \"chat-member\" where chat_id = ?",
                chatId
        );
    }

    private String mapRowToMember(ResultSet row, int rowNum) throws SQLException {
        return row.getString("member_name");
    }
}
