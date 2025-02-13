package young.blaybus.domain.member.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import young.blaybus.domain.member.security.jwt.provider.JwtProvider;
import young.blaybus.domain.member.security.redis.RedisService;
import young.blaybus.domain.member.security.user.details.AuthenticatedUserDetails;

import java.io.IOException;

@Slf4j
public class LoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final RedisService redisService;

    public LoginAuthenticationFilter(JwtProvider jwtProvider, AuthenticationManager authenticationManager, RedisService redisService) {
        super(new AntPathRequestMatcher("/member/login", "POST"));
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
        this.redisService = redisService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String id = request.getParameter("id");
        String password = request.getParameter("password");

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(id, password);

        return authenticationManager.authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        AuthenticatedUserDetails userDetails = (AuthenticatedUserDetails) authResult.getPrincipal();
        String userId = userDetails.getUsername();
        String role = userDetails.getAuthorities().iterator().next().getAuthority();
        String accessToken = jwtProvider.createAccessToken(userId, role);
        String refreshToken = jwtProvider.createRefreshToken(userId, role);
        redisService.saveRefreshToken(userId, refreshToken);

        response.addHeader("Authorization", "Bearer " + accessToken); // 액세스 토큰 발급
        log.info("액세스 토큰 발급");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException { log.error("에러: ", failed); }
}
