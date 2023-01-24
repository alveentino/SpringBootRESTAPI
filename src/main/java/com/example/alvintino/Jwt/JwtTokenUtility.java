package com.example.alvintino.Jwt;

import com.example.alvintino.User.UserInfo;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
@Component
public class JwtTokenUtility {
    @Value("${app.jwt.issuer-name}")
    private String ISSUER_NAME;

    @Value("${app.jwt.period-of-validity}")
    private long PERIOD_OF_VALIDITY;

    @Value("${app.jwt.secret}")
    private String SECRET_KEY;

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtility.class);

    public String generateAccessToken(UserInfo userInfo){
        return Jwts.builder()
                .setSubject(userInfo.getUsername())
                .setIssuer(ISSUER_NAME)
                .claim("roles", userInfo.getRoles().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+PERIOD_OF_VALIDITY))
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                .compact();
    }

    public boolean validateAccessToken(String accesstoken){
        if (parseClaim(accesstoken) != null){
            return true;
        }else {
            return false;
        }
    }

    public String getUsername(String accessToken){
        return getSubject(accessToken);
    }

    private String getSubject(String accessToken){
        return parseClaim(accessToken).getBody().getSubject();
    }

    public Jws<Claims> parseClaim(String accessToken){
        try {
            return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(accessToken);
        } catch (IllegalArgumentException ex) {
            LOGGER.error("Token is null, empty or only whitespace", ex.getMessage());
        } catch (MalformedJwtException ex) {
            LOGGER.error("JWT is invalid format", ex);
        } catch (UnsupportedJwtException ex) {
            LOGGER.error("JWT is not supported", ex);
        } catch (SignatureException ex) {
            LOGGER.error("Signature validation failed");
        } catch (ExpiredJwtException ex) {
            LOGGER.error("JWT expired", ex.getMessage());
        }catch (Exception e){
            LOGGER.error("Another Error", e.getMessage());
        }
        return null;
    }
}