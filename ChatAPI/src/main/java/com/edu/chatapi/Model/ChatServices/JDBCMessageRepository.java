package com.edu.chatapi.Model.ChatServices;

import com.edu.chatapi.Model.ChatUnits.ChatMessage;
import com.edu.chatapi.Persistence.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

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
    public Optional<ChatMessage> save(ChatMessage chatMessage) {
        jdbcTemplate.update(
                "insert into Messages (id, created_at, message, username, chat_id) values (?, ?, ?, ?, ?)",
                chatMessage.getId(),
                chatMessage.getCreatedAt(),
                chatMessage.getMessage(),
                chatMessage.getUsername(),
                chatMessage.getChat_id()
        );
        return Optional.of(chatMessage);
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
