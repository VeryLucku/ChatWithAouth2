package com.edu.chatapi.Model.ChatServices;

import com.edu.chatapi.Model.ChatUnits.Member;
import com.edu.chatapi.Persistence.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class JDBCMemberRepository implements MemberRepository {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public JDBCMemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Member> getMember(UUID id) {
        List<Member> results = jdbcTemplate.query(
                "select id, username from user_data where id=?",
                this::mapRowToMember,
                id
        );
        return results.size() == 0 ?
                Optional.empty() :
                Optional.of(results.get(0));
    }

    @Override
    public List<Member> getAllMembers(List<UUID> ids) {
        return ids.stream().map(id -> {
            Optional<Member> member = getMember(id);
            if (member.isEmpty()) {
                throw new NullPointerException("Chat member with defined id does not exist");
            }
            return member.get();
        }).toList();
    }

    private Member mapRowToMember(ResultSet row, int rowNum) throws SQLException {
        return new Member(
                UUID.fromString(row.getString("id")),
                row.getString("username")
        );
    }
}
