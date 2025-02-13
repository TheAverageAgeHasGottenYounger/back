package young.blaybus.domain.member.security.filter;

import jakarta.annotation.Nullable;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import young.blaybus.domain.member.repository.MemberRepository;
import young.blaybus.domain.member.security.jwt.provider.JwtProvider;
import young.blaybus.domain.member.security.redis.RedisService;
import young.blaybus.domain.member.security.user.details.AuthenticatedUserDetails;
import young.blaybus.domain.member.security.user.service.AuthenticatedUserDetailsService;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final RedisService redisService;
    private final MemberRepository memberRepository;

    @Autowired
    public JwtAuthenticationFilter(JwtProvider jwtProvider, RedisService redisService, MemberRepository memberRepository) {
        this.jwtProvider = jwtProvider;
        this.redisService = redisService;
        this.memberRepository = memberRepository;
    }

    @Override
    protected void doFilterInternal(@Nullable HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable FilterChain filterChain) throws ServletException, IOException {
        if (request != null) {
            String authorization = request.getHeader("Authorization");

            if (authorization == null || authorization.isEmpty()) {
                Objects.requireNonNull(filterChain).doFilter(request, response);
                return;
            }

            if (authorization.startsWith("Bearer ")) {
                String accessToken = authorization.substring("Bearer ".length());

                // 토큰의 유효시간 체크
                if (jwtProvider.getValidateToken(accessToken)) {
                    AuthenticatedUserDetailsService customUserDetailsService = new AuthenticatedUserDetailsService(memberRepository);
                    AuthenticatedUserDetails userDetails = (AuthenticatedUserDetails) customUserDetailsService.loadUserByUsername(jwtProvider.getUserId(accessToken));

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
                        AuthenticatedUserDetailsService customUserDetailsService = new AuthenticatedUserDetailsService(memberRepository);
                        AuthenticatedUserDetails userDetails = (AuthenticatedUserDetails) customUserDetailsService.loadUserByUsername(tokenUserId);

                        String userId = userDetails.getUsername();
                        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, null, authorities);
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                        // 재발급
                        if (response != null) {
                            response.addHeader("Authorization", "Bearer " + refreshToken);
                            System.out.println("리프레쉬 토큰: " + refreshToken);
                            log.info("액세스 토큰 재발급");
                        }
                        Objects.requireNonNull(filterChain).doFilter(request, response);
                    }
                }
            }
        }

        Objects.requireNonNull(filterChain).doFilter(request, response);
    }
}
