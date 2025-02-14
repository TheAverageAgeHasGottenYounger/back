package young.blaybus.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import young.blaybus.domain.member.security.filter.JwtAuthenticationFilter;
import young.blaybus.domain.member.security.filter.LoginAuthenticationFilter;
import young.blaybus.domain.member.security.jwt.provider.JwtProvider;
import young.blaybus.domain.member.security.redis.RedisService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtProvider jwtProvider;
  private final AuthenticationConfiguration authenticationConfiguration;
  private final RedisService redisService;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable);
    http.formLogin(AbstractHttpConfigurer::disable);
    http.httpBasic(AbstractHttpConfigurer::disable);
    http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    http.authorizeHttpRequests(auth -> auth
      .requestMatchers(
        "/", "member/admin-join", "/member/worker-join",
        "/member/duplication-id", "/member/duplication-name", "/member/login",
        "/health", "/center/is-registration", "/s3"
      ).permitAll().requestMatchers( // Swagger 관련 Url 요청 처리
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/swagger-resources/**",
        "/webjars/**"
      ).permitAll().anyRequest()
      .authenticated());

    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    http.addFilterAt(new LoginAuthenticationFilter(jwtProvider, authenticationManager(authenticationConfiguration), redisService),
      UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
