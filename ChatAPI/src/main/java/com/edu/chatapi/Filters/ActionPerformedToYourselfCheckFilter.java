package com.edu.chatapi.Filters;

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

@Component
@Order(2)
public class ActionPerformedToYourselfCheckFilter extends OncePerRequestFilter {

    final Logger log = LoggerFactory.getLogger(ActionPerformedToYourselfCheckFilter.class);

    @Autowired
    public ActionPerformedToYourselfCheckFilter() {
        super();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String actionOwner = request.getUserPrincipal().getName();

        String actionPerformedTo = request.getParameter("username");

        if (actionPerformedTo == null) {
            response.sendError(400, "You don't provide the username to perform the action");
            return;
        }

        if (actionOwner.equals(actionPerformedTo)) {
            response.sendError(400, "You can't do this action with you");
            return;
        }

        log.info("Filter checked that current action not performed to action owner, owner {}, performed to {}", actionOwner, actionPerformedTo);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return !(path.startsWith("/api/chats/removeMember") && request.getMethod().equals("DELETE") ||
                path.startsWith("/api/members/role") && request.getMethod().equals("PUT"));
    }
}
