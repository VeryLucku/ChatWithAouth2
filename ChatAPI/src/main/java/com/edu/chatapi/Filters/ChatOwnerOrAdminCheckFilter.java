package com.edu.chatapi.Filters;

import com.edu.chatapi.Helpers.UUIDExtractor;
import com.edu.chatapi.Model.ChatUnits.Member;
import com.edu.chatapi.RepoInterfaces.ChatAndMemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@Component
@Order(1)
public class ChatOwnerOrAdminCheckFilter extends OncePerRequestFilter {

    ChatAndMemberRepository chatAndMemberRepository;
    UUIDExtractor uuidExtractor;

    final Logger log = LoggerFactory.getLogger(ChatOwnerOrAdminCheckFilter.class);

    @Autowired
    public ChatOwnerOrAdminCheckFilter(ChatAndMemberRepository chatAndMemberRepository,
                                UUIDExtractor uuidExtractor) {
        super();
        this.chatAndMemberRepository = chatAndMemberRepository;
        this.uuidExtractor = uuidExtractor;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Principal principal = request.getUserPrincipal();

        Optional<UUID> id = uuidExtractor.extract(request);

        if (id.isEmpty()) {
            response.sendError(400, "You don't provide chatId");
            return;
        }

        if (!chatAndMemberRepository.isChatMemberHasRole(id.get(), principal.getName(), Member.Role.OWNER) &&
                !chatAndMemberRepository.isChatMemberHasRole(id.get(), principal.getName(), Member.Role.ADMIN)) {
            response.sendError(400, "You don't allow to perform this action");
            return;
        }

        log.info("Filter checked that current action performed by chat owner, username {}", principal.getName());

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return !(path.startsWith("/api/chats/removeMember") && request.getMethod().equals("DELETE"));
    }
}
