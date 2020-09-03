package at.jku.cis.iVolunteer.core.service;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

import static at.jku.cis.iVolunteer.core.security.SecurityConstants.ACCESS_SECRET;
import static at.jku.cis.iVolunteer.core.security.SecurityConstants.REFRESH_SECRET;
import static at.jku.cis.iVolunteer.core.security.SecurityConstants.ACCES_EXPIRATION_TIME;
import static at.jku.cis.iVolunteer.core.security.SecurityConstants.REFRESH_EXPIRATION_TIME;
import static at.jku.cis.iVolunteer.core.security.SecurityConstants.TOKEN_PREFIX;

public class JWTTokenProvider {
    // TODO:
    // store refresh token on server side too
    // each refresh check if session is acitve and refresh token isnt blacklisted
    // delete if user logs out
    // so it is possible to "blacklist" issued refresh tokens

    private static final Logger logger = LoggerFactory.getLogger(JWTTokenProvider.class);

    public String generateAccessToken(Authentication authentication) throws Exception {
        String username = ((User) authentication.getPrincipal()).getUsername();

        // @formatter:off
        return Jwts.builder()
            .setSubject(username)
            .claim("username", username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + ACCES_EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, ACCESS_SECRET.getBytes("UTF-8"))
            .compact();
         // @formatter:on
    }

    public String generateRefreshToken(Authentication authentication) throws Exception {
        String username = ((User) authentication.getPrincipal()).getUsername();

        // @formatter:off
        return Jwts.builder()
        .setSubject(username)
        .claim("username", username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
        .signWith(SignatureAlgorithm.HS512, REFRESH_SECRET.getBytes("UTF-8"))
        .compact();
        // @formatter:on
    }

    public String getUserNameFromRefreshToken(String token) throws Exception {
        Claims claims = Jwts.parser().setSigningKey(REFRESH_SECRET.getBytes("UTF-8")).parseClaimsJws(token).getBody();

        return claims.getSubject();
    }

    public boolean validateAccessToken(String token) throws Exception {
        return validateToken(token, ACCESS_SECRET);
    }

    public boolean validateRefreshToken(String token) throws Exception {
        return validateToken(token, REFRESH_SECRET);
    }

    private boolean validateToken(String token, String secret) throws Exception {
        if (token.startsWith(TOKEN_PREFIX)) {
            token = token.substring(TOKEN_PREFIX.length(), token.length());
        }
        try {
            Jwts.parser().setSigningKey(secret.getBytes("UTF-8")).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
            throw new SignatureException("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
            throw new MalformedJwtException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
            throw new ExpiredJwtException(null, null, "Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
            throw new UnsupportedJwtException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
            throw new IllegalArgumentException("JWT claims string is empty.");
        }
    }
}