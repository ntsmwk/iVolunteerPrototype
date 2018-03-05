package at.jku.csi.marketplace.security.filter;

import static at.jku.csi.marketplace.security.SecurityConstants.JWT_AUTHORITIES;
import static at.jku.csi.marketplace.security.SecurityConstants.HEADER_STRING;
import static at.jku.csi.marketplace.security.SecurityConstants.SECRET;
import static at.jku.csi.marketplace.security.SecurityConstants.TOKEN_PREFIX;
import static at.jku.csi.marketplace.security.SecurityConstants.JWT_USERNAME;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String header = req.getHeader(HEADER_STRING);

		if (header == null || !header.startsWith(TOKEN_PREFIX)) {
			chain.doFilter(req, res);
			return;
		}

		SecurityContextHolder.getContext().setAuthentication(getAuthentication(req));
		chain.doFilter(req, res);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		if (token == null) {
			return null;
		}
		return buildAuthentication(token.replace(TOKEN_PREFIX, ""));
	}

	private UsernamePasswordAuthenticationToken buildAuthentication(String token) {
		String username = parseUsernameFromJWTToken(token);
		Collection<? extends GrantedAuthority> authorities = parseAuthoritiesFromJWTToken(token);
		if (username == null || authorities == null) {
			return null;
		}
		return new UsernamePasswordAuthenticationToken(username, null, authorities);
	}

	private String parseUsernameFromJWTToken(String token) {
		return (String) parseClaimsJws(token).getBody().get(JWT_USERNAME);
	}

	@SuppressWarnings("unchecked")
	private Collection<? extends GrantedAuthority> parseAuthoritiesFromJWTToken(String token) {
		Collection<String> authorities = (List<String>) parseClaimsJws(token).getBody().get(JWT_AUTHORITIES);
		return authorities.stream().map(name -> new SimpleGrantedAuthority(name)).collect(Collectors.toSet());
	}

	private Jws<Claims> parseClaimsJws(String token) {
		return Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(token);
	}
}
