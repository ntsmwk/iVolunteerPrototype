package at.jku.cis.iVolunteer.marketplace.security.filter;

import static at.jku.cis.iVolunteer.marketplace.security.SecurityConstants.ACCESS_HEADER_STRING;
import static at.jku.cis.iVolunteer.marketplace.security.SecurityConstants.JWT_AUTHORITIES;
import static at.jku.cis.iVolunteer.marketplace.security.SecurityConstants.JWT_USERNAME;
import static at.jku.cis.iVolunteer.marketplace.security.SecurityConstants.ACCESS_SECRET;
import static at.jku.cis.iVolunteer.marketplace.security.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Collections;
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
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private static final String NOT_AUTHORISED = "Sorry, You're not authorized to access this resource due to : ";

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		String header = req.getHeader(ACCESS_HEADER_STRING);

		if (header == null || !header.startsWith(TOKEN_PREFIX)) {
			chain.doFilter(req, res);
			return;
		}

		String token = req.getHeader(ACCESS_HEADER_STRING);

		if (StringUtils.hasText(token) && this.validateAccessToken(token)) {
			SecurityContextHolder.getContext().setAuthentication(getAuthentication(req));
			chain.doFilter(req, res);
		} else {
			res.sendError(HttpServletResponse.SC_UNAUTHORIZED, NOT_AUTHORISED + "Problem with Token");
		}

	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(ACCESS_HEADER_STRING);
		if (token == null) {
			return null;
		}
		return buildAuthentication(token.replace(TOKEN_PREFIX, ""));
	}

	private UsernamePasswordAuthenticationToken buildAuthentication(String token) {
		String username = parseUsernameFromJWTToken(token);
		// Collection<? extends GrantedAuthority> authorities =
		// parseAuthoritiesFromJWTToken(token);
		Collection<? extends GrantedAuthority> authorities = Collections.emptyList();

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
		try {
			return Jwts.parser().setSigningKey(ACCESS_SECRET.getBytes("UTF-8")).parseClaimsJws(token);
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException
				| IllegalArgumentException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	private boolean validateAccessToken(String authToken) {
		if (authToken.startsWith(TOKEN_PREFIX)) {
			authToken = authToken.substring(TOKEN_PREFIX.length(), authToken.length());
		}

		try {
			Jwts.parser().setSigningKey(ACCESS_SECRET.getBytes("UTF-8")).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException
				| IllegalArgumentException | UnsupportedEncodingException ex) {
			return false;
		}
	}
}