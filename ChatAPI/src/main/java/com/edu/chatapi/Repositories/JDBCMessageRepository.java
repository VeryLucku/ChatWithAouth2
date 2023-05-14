package com.edu.chatapi.Repositories;

import com.edu.chatapi.Model.ChatUnits.ChatMessage;
import com.edu.chatapi.RepoInterfaces.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JDBCMessageRepository implements MessageRepository {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public JDBCMessageRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<ChatMessage> findAll() {
        return jdbcTemplate.query(
                "select id, created_at, message, username, chat_id from Messages",
                this::mapRowToMessage
        );
    }

    @Override
    public ChatMessage save(ChatMessage chatMessage) {
        jdbcTemplate.update(
                "insert into Messages (id, created_at, message, username, chat_id) values (?, ?, ?, ?, ?)",
                chatMessage.getId(),
                chatMessage.getCreatedAt(),
                chatMessage.getMessage(),
                chatMessage.getAuthor(),
                chatMessage.getChatId()
        );
        return chatMessage;
    }

    @Override
    public Optional<ChatMessage> findById(UUID id) {
        List<ChatMessage> results = jdbcTemplate.query(
                "select id, created_at, message, username, chat_id from Messages where id=?",
                this::mapRowToMessage,
                id
        );
        return results.size() == 0 ?
                Optional.empty() :
                Optional.of(results.get(0));
    }

    @Override
    public List<ChatMessage> findAllByChatId(UUID chatId) {

        return jdbcTemplate.query(
                "select id, created_at, message, username, chat_id from Messages where chat_id=? order by created_at",
                this::mapRowToMessage,
                chatId
        );
    }

    @Override
    public void deleteMessage(UUID id) {
        jdbcTemplate.update(
                "delete from messages where id = ?",
                id
        );
    }

    @Override
    public void deleteMessagesFromChat(UUID chatId) {
        jdbcTemplate.update(
                "delete from messages where chat_id = ?",
                chatId
        );
    }

    private ChatMessage mapRowToMessage(ResultSet row, int rowNum) throws SQLException {
        return new ChatMessage(
                UUID.fromString(row.getString("id")),
                row.getString("username"),
                row.getDate("created_at"),
                row.getString("message"),
                UUID.fromString(row.getString("chat_id"))
        );
    }

}
