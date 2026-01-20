package com.kirana.finalphase1.ratelimit;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RateLimitingFilter extends OncePerRequestFilter {

    private final RateLimitService rateLimitService;

    @Value("${ratelimit.transactions.per.minute}")
    private int transactionLimit;

    @Value("${ratelimit.reports.per.minute}")
    private int reportLimit;

    public RateLimitingFilter(RateLimitService rateLimitService) {
        this.rateLimitService = rateLimitService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return !(path.startsWith("/transactions") || path.startsWith("/reports"));
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            filterChain.doFilter(request, response);
            return;
        }

        // userId (Mongo ObjectId string) from JWT subject
        String userId = auth.getName();

        String path = request.getRequestURI();

        int limit = path.startsWith("/transactions")
                ? transactionLimit
                : reportLimit;

        String redisKey = "ratelimit:" + userId + ":" + path;

        if (!rateLimitService.isAllowed(redisKey, limit)) {
            response.setStatus(429);
            response.getWriter().write("Rate limit exceeded");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
