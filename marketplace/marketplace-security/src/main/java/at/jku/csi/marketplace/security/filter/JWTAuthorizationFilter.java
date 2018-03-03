package at.jku.csi.marketplace.security.filter;

import static at.jku.csi.marketplace.security.SecurityConstants.HEADER_STRING;
import static at.jku.csi.marketplace.security.SecurityConstants.SECRET;
import static at.jku.csi.marketplace.security.SecurityConstants.TOKEN_PREFIX;
import static java.util.Collections.emptyList;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

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
		String user = parseUsernameFromJWTToken(token);
		if (user == null) {
			return null;
		}
		return new UsernamePasswordAuthenticationToken(user, null, emptyList());
	}

	private String parseUsernameFromJWTToken(String token) {
		String tokenWithPrefix = token.replace(TOKEN_PREFIX, "");
		return Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(tokenWithPrefix).getBody().getSubject();
	}
}
