package com.example.demo.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    // CONSTRUCTOR GÜNCELLENDİ
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();
        System.out.println("🌐 İstek yolu: " + path);

        if (path.startsWith("/api/auth")) {
            System.out.println("✅ Auth endpoint, filtre atlandı");
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        System.out.println("🔑 Authorization header: " + authHeader);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            System.out.println("🎫 Token alındı, uzunluk: " + token.length());

            if (jwtTokenProvider.validateToken(token)) {
                System.out.println("✅ Token GEÇERLI");

                String username = jwtTokenProvider.getUsernameFromToken(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                System.out.println("👤 Username: " + username);

                // Claims kısmına artık ihtiyacın yok çünkü rolleri doğrudan userDetails'ten alabilirsin
                // Claims claims = Jwts.parserBuilder()...

                // Artık userDetails'ten gelen rolleri kullan
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(auth);
                System.out.println("✅ Authentication context'e SET EDİLDİ");
            } else {
                System.out.println("❌ Token GEÇERSİZ");
            }
        } else {
            System.out.println("❌ Authorization header YOK veya Bearer ile başlamıyor");
        }

        System.out.println("🚀 Filter chain devam ediyor...");
        filterChain.doFilter(request, response);
    }
}