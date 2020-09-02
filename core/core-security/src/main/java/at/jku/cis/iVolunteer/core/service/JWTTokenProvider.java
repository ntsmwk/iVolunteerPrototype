package at.jku.cis.iVolunteer.core.service;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.Date;

import org.springframework.security.core.userdetails.User;

import static at.jku.cis.iVolunteer.core.security.SecurityConstants.ACCESS_SECRET;
import static at.jku.cis.iVolunteer.core.security.SecurityConstants.REFRESH_SECRET;

import static at.jku.cis.iVolunteer.core.security.SecurityConstants.ACCES_EXPIRATION_TIME;
import static at.jku.cis.iVolunteer.core.security.SecurityConstants.REFRESH_EXPIRATION_TIME;
import static at.jku.cis.iVolunteer.core.security.SecurityConstants.TOKEN_PREFIX;

public class JWTTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JWTTokenProvider.class);

    public String generateAccessToken(Authentication authentication) throws Exception {
        // KeyStore keystore = getKeyStore();
        // Key key = keystore.getKey(alias, keyPassword.toCharArray());
        // SECRET = key: should be in keystore

        String username = ((User) authentication.getPrincipal()).getUsername();

        // @formatter:off
        return Jwts.builder()
            .setSubject(username)
            .claim("username", username)
            .claim("authorities", authentication.getAuthorities())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + ACCES_EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, ACCESS_SECRET.getBytes("UTF-8"))
            .compact();
         // @formatter:on
    }

    public String generateRefreshToken(Authentication authentication) throws Exception {
        // KeyStore keystore = getKeyStore();
        // Key key = keystore.getKey(alias, keyPassword.toCharArray());
        // SECRET = key: should be in keystore

        String username = ((User) authentication.getPrincipal()).getUsername();

        // @formatter:off
        return Jwts.builder()
            .setSubject(username)
            .claim("username", username)
            .claim("authorities", authentication.getAuthorities())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, REFRESH_SECRET.getBytes("UTF-8"))
            .compact();
        // @formatter:on
    }

    public String getUserNameFromAccessToken(String token) throws Exception {
        Claims claims = Jwts.parser().setSigningKey(ACCESS_SECRET.getBytes("UTF-8")).parseClaimsJws(token).getBody();

        return claims.getSubject();
    }

    public String getUserNameFromRefreshToken(String token) throws Exception {
        Claims claims = Jwts.parser().setSigningKey(REFRESH_SECRET.getBytes("UTF-8")).parseClaimsJws(token).getBody();

        return claims.getSubject();
    }

    public boolean validateAccessToken(String authToken) throws Exception {
        // return true;

        if (authToken.startsWith(TOKEN_PREFIX)) {
            authToken = authToken.substring(TOKEN_PREFIX.length(), authToken.length());
        }
        try {
            Jwts.parser().setSigningKey(ACCESS_SECRET.getBytes("UTF-8")).parseClaimsJws(authToken);
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

    // private PublicKey getPublicKey() throws Exception {
    // Certificate cert = getKeyStore().getCertificate(alias);
    // PublicKey publicKey = cert.getPublicKey();
    // return publicKey;
    // }

    // private KeyStore getKeyStore() throws Exception {
    // ClassPathResource resource = new ClassPathResource(certificate);
    // KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
    // keystore.load(resource.getInputStream(), storePassword.toCharArray());
    // return keystore;
    // }

}