package at.jku.csi.marketplace.security.filter;

import static at.jku.csi.marketplace.security.SecurityConstants.EXPIRATION_TIME;
import static at.jku.csi.marketplace.security.SecurityConstants.HEADER_STRING;
import static at.jku.csi.marketplace.security.SecurityConstants.SECRET;
import static at.jku.csi.marketplace.security.SecurityConstants.TOKEN_PREFIX;
import static java.util.Collections.emptyList;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import at.jku.csi.marketplace.security.model.Credentials;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		try {
			Credentials credentials = new ObjectMapper().readValue(req.getInputStream(), Credentials.class);
			return authenticationManager.authenticate(buildAuthenticationToken(credentials));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private UsernamePasswordAuthenticationToken buildAuthenticationToken(Credentials credentials) {
		String username = credentials.getUsername();
		String password = credentials.getPassword();
		return new UsernamePasswordAuthenticationToken(username, password, emptyList());
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		String username = ((User) auth.getPrincipal()).getUsername();
		String token = Jwts.builder()
				.setSubject(username)
				.claim("username", username)
				.claim("authorities", auth.getAuthorities())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET.getBytes()).compact();
		res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
	}
}