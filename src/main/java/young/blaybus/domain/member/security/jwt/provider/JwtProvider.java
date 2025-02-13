package young.blaybus.domain.member.security.jwt.provider;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {
    private final Key secretKey;
    private final Claims claims;

    public JwtProvider(@Value("${jwt.secret}") String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8))));
        this.claims = Jwts.claims();
    }

    // 액세스 토큰 생성
    public String createAccessToken(String userId, String role) {
        claims.put("userId", userId);
        claims.put("role", role);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .addClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (60 * 1000)))
                .signWith(secretKey)
                .compact();
    }

    // 리프레쉬 토큰 생성
    public String createRefreshToken(String userId, String role) {
        claims.put("userId", userId);
        claims.put("role", role);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .addClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (10000 * 1000)))
                .signWith(secretKey)
                .compact();
    }

    // 유저 이름 꺼내기
    public String getUserId(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get("userId", String.class);
        } catch (ExpiredJwtException e) {
            return e.getClaims().get("userId", String.class);
        }
    }

    // 권한 꺼내기
    public String getRole(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get("role", String.class);
        } catch (ExpiredJwtException e) {
            return e.getClaims().get("role", String.class);
        }
    }

    // 만료 시간 확인
    public boolean getValidateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            System.out.println("잘못된 JWT 토큰: " + e);
        } catch (ExpiredJwtException e) {
            System.out.println("만료된 JWT 토큰: " + e);
        } catch (UnsupportedJwtException e) {
            System.out.println("지원되지 않는 JWT 토큰: " + e);
        } catch (IllegalArgumentException e) {
            System.out.println("JWT 주장 문자열이 비어 있습니다: " + e);
        }

        return false;
    }
}
