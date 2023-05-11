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

    @Autowired
    public JDBCChatRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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



    @Override
    public void deleteChat(UUID chatId) {
        jdbcTemplate.update(
                "delete from chats where id = ?",
                chatId
        );
    }

    @Override
    public Optional<String> getChatAuthor(UUID id) {
        List<String> results =  jdbcTemplate.query(
                "select author from chats where id = ?",
                (rs, rowNum) -> rs.getString("author"),
                id
        );

        return results.size() == 0 ?
                Optional.empty() :
                Optional.of(results.get(0));
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
