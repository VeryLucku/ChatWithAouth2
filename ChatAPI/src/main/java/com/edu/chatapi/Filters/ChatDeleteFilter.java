package com.edu.chatapi.Filters;

import com.edu.chatapi.Helpers.UUIDExtractor;
import com.edu.chatapi.Model.ChatUnits.Member;
import com.edu.chatapi.RepoInterfaces.ChatAndMemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

@Component
public class ChatDeleteFilter extends OncePerRequestFilter {

    ChatAndMemberRepository chatAndMemberRepository;

    final Logger log = LoggerFactory.getLogger(ChatDeleteFilter.class);

    @Autowired
    public ChatDeleteFilter(ChatAndMemberRepository chatAndMemberRepository) {
        super();
        this.chatAndMemberRepository = chatAndMemberRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Principal principal = request.getUserPrincipal();
        UUID id = UUIDExtractor.extract(request.getServletPath());

        if (!chatAndMemberRepository.isChatMemberHasRole(id, principal.getName(), Member.Role.OWNER)) {
            response.sendError(400, "You aren't chat owner");
            return;
        }

        log.info("Filter checked that current action performed by chat owner, username {}", principal.getName());

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return !(path.startsWith("/api/chats") && request.getMethod().equals("DELETE")) ;
    }
}
