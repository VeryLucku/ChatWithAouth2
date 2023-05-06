package com.edu.chatapi.Model.ChatServices;

import com.edu.chatapi.Model.ChatUnits.Chat;
import com.edu.chatapi.Model.ChatUnits.Member;
import com.edu.chatapi.Persistence.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class JDBCChatRepository implements ChatRepository {

    JdbcTemplate jdbcTemplate;
    JDBCMemberRepository jdbcMemberRepository;

    @Autowired
    public JDBCChatRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Chat> findById(UUID id) {
        List<Chat> results = jdbcTemplate.query(
                "select id, name, author, members from Chats where id=?",
                this::mapRowToChat,
                id
        );
        return results.size() == 0 ?
                Optional.empty() :
                Optional.of(results.get(0));
    }

    @Override
    public Optional<Chat> save(Chat chat) {
        jdbcTemplate.update(
                "insert into Chats (id, name, author, members) values (?, ?, ?, ?)",
                chat.getId(),
                chat.getName(),
                chat.getAuthor(),
                chat.getMember_ids()
        );
        return Optional.of(chat);
    }

    @Override
    public List<Chat> findAll() {
        return jdbcTemplate.query(
                "select id, name, author, members from Chats",
                this::mapRowToChat
        );
    }

    private Chat mapRowToChat(ResultSet row, int rowNum) throws SQLException {
        return new Chat(
                UUID.fromString(row.getString("id")),
                row.getString("name"),
                row.getString("author"),
                Arrays.stream((String[])row.getArray("members").getArray()).toList()
        );
    }
}
