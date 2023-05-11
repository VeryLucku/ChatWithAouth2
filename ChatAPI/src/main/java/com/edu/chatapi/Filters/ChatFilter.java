package com.edu.chatapi.Filters;

import com.edu.chatapi.Model.ChatUnits.Member;
import com.edu.chatapi.RepoInterfaces.ChatAndMemberRepository;
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
public class ChatFilter extends OncePerRequestFilter {

    ChatAndMemberRepository chatAndMemberRepository;

    @Autowired
    public ChatFilter(ChatAndMemberRepository chatAndMemberRepository) {
        super();
        this.chatAndMemberRepository = chatAndMemberRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Principal principal = request.getUserPrincipal();
        UUID id = UUID.fromString(request.getServletPath().split("/")[3]);

        if (chatAndMemberRepository.isChatMemberHaveRole(id, principal.getName(), Member.Role.OWNER)) {
            response.sendError(400, "You aren't chat owner");
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return !(path.startsWith("/api/chats") && request.getMethod().equals("DELETE")) ;
    }
}
