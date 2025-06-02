package com.infoa.educationms.service;

import com.infoa.educationms.DTO.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
// 导入你的 UserDTO 或者相关的用户实体类
// import com.example.yourproject.dto.UserDTO; // 假设你有一个UserDTO
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Component
public class JwtTokenProvider {
    // 从 application.properties 或 application.yml 中读取密钥和过期时间
    @Value("${jwt.secret}")
    private String secret; // JWT 密钥，必须足够复杂和保密
    @Value("${jwt.expiration}")
    private long expiration; // JWT 过期时间，单位为毫秒
    private SecretKey getSigningKey() {
// 使用 Keys.hmacShaKeyFor 生成一个安全的密钥
// 确保你的 secret 字符串足够长 (例如，至少 256 位)
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
    // 从 token 中获取用户名 (subject)
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
    // 从 token 中获取过期时间
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    // 从 token 中获取指定的 claim
    public <T> T getClaimFromToken(String token, Function<Claims,
            T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    // 从 token 中获取所有的 claims
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    // 检查 token 是否已过期
    private Boolean isTokenExpired(String token) {
        final Date expirationDate =
                getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }
    /**
     * 为用户生成 token
     *
     * @param // userDetails 可以是 Spring Security 的 UserDetails 对象，
    或者你的用户实体/DTO
     * 这里我们假设它有一个 getUsername() 或 getId()
    方法作为 subject
     * @param // userDTO 你的 UserDTO 对象，可以从中获取需要放入 token 的
    额外信息
     * @return 生成的 JWT Token
     */
    public String generateToken(UserDTO userDTO) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userDTO.getUserId());
        claims.put("type", userDTO.getType());
        claims.put("name", userDTO.getName());

// 注意：不要在 claims 中存放敏感信息，因为它们是可解码的
        return doGenerateToken(claims, userDTO.getName());
    }
    // 实际生成 JWT Token 的方法
    private String doGenerateToken(Map<String, Object> claims,
                                   String subject) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expiration);
        return Jwts.builder()
                .setClaims(claims) // 设置自定义的 claims
                .setSubject(subject) // 设置主题，通常是用户名或用户ID
                .setIssuedAt(now) // 设置签发时间
                .setExpiration(expirationDate) // 设置过期时间
                .signWith(getSigningKey(),
                        SignatureAlgorithm.HS256) // 使用 HS256 算法和密钥签名
                .compact();
    }
    /**
     * 验证 token 是否有效
     *
     * @param token 要验证的 token
     * @param // userDetails 用于比对 token 中的用户名和用户实体是否一致
    (可选)
     * @return 如果 token 有效则返回 true，否则返回 false
     */
    public Boolean validateToken(String token /*, UserDetails
userDetails */) {
        try {
            final String username = getUsernameFromToken(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }
}