package young.blaybus.domain.member.security.filter;

import jakarta.annotation.Nullable;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import young.blaybus.domain.member.repository.MemberRepository;
import young.blaybus.domain.member.security.jwt.provider.JwtProvider;
import young.blaybus.domain.member.security.redis.RedisService;
import young.blaybus.domain.member.security.user.details.CustomUserDetails;
import young.blaybus.domain.member.security.user.service.CustomUserDetailsService;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final RedisService redisService;
    private final MemberRepository memberRepository;

    public JwtAuthenticationFilter(JwtProvider jwtProvider, RedisService redisService, MemberRepository memberRepository) {
        this.jwtProvider = jwtProvider;
        this.redisService = redisService;
        this.memberRepository = memberRepository;
    }

    @Override
    protected void doFilterInternal(@Nullable HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable FilterChain filterChain) throws ServletException, IOException {
        // 토큰이 있는지 체크
        System.out.println("들어오는지 체크");
        String authorization = request.getHeader("Authorization");

        if (authorization == null || authorization.isEmpty()) {
            Objects.requireNonNull(filterChain).doFilter(request, response);
            return;
        }

        if (!authorization.startsWith("Bearer ")) {
            Objects.requireNonNull(filterChain).doFilter(request, response);
            return;
        }

        if (authorization.startsWith("Bearer ")) {
            String accessToken = request.getHeader("Authorization").substring("Bearer ".length());

            // 토큰의 유효시간 체크
            if (jwtProvider.getValidateToken(accessToken)) {
                CustomUserDetailsService customUserDetailsService = new CustomUserDetailsService(memberRepository);
                CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(jwtProvider.getUserId(accessToken));

                String userId = userDetails.getUsername();
                Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                Objects.requireNonNull(filterChain).doFilter(request, response);
                return;
            } else {
                String tokenUserId = jwtProvider.getUserId(accessToken);
                String refreshToken = redisService.getRefreshToken(tokenUserId);

                if (refreshToken != null) {
                    CustomUserDetailsService customUserDetailsService = new CustomUserDetailsService(memberRepository);
                    CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(tokenUserId);

                    String userId = userDetails.getUsername();
                    Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    response.addHeader("Authorization", "Bearer " + refreshToken);
                    Objects.requireNonNull(filterChain).doFilter(request, response);
                }
            }
        }

        Objects.requireNonNull(filterChain).doFilter(request, response);
    }
}
