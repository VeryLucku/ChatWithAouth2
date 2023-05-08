package com.edu.chatapi.Repositories;

import com.edu.chatapi.Model.ChatUnits.Chat;
import com.edu.chatapi.RepoInterfaces.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class JDBCChatRepository implements ChatRepository {

    JdbcTemplate jdbcTemplate;
    JDBCChatAndMemberRepository jdbcChatAndMemberRepository;

    @Autowired
    public JDBCChatRepository(JdbcTemplate jdbcTemplate,
                              JDBCChatAndMemberRepository jdbcChatAndMemberRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcChatAndMemberRepository = jdbcChatAndMemberRepository;
    }

    @Override
    public Optional<Chat> findById(UUID id) {
        List<Chat> results = jdbcTemplate.query(
                "select id, name, author from Chats where id=?",
                this::mapRowToChat,
                id
        );
        return results.size() == 0 ?
                Optional.empty() :
                Optional.of(results.get(0));
    }

    @Override
    public Chat save(Chat chat) {
        jdbcTemplate.update(
                "insert into Chats (id, name, author) values (?, ?, ?)",
                chat.getId(),
                chat.getName(),
                chat.getAuthor()
        );
        return chat;
    }

    @Override
    public List<Chat> findAll() {
        return jdbcTemplate.query(
                "select id, name, author from Chats",
                this::mapRowToChat
        );
    }

    private Chat mapRowToChat(ResultSet row, int rowNum) throws SQLException {
        UUID id = UUID.fromString(row.getString("id"));
        String name = row.getString("name");
        String author = row.getString("author");

        return new Chat(
                id,
                name,
                author
        );
    }
}
