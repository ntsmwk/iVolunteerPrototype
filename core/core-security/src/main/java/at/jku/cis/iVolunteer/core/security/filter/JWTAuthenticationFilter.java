package at.jku.cis.iVolunteer.core.security.filter;

import static java.util.Collections.emptyList;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import at.jku.cis.iVolunteer.core.security.model.Credentials;
import at.jku.cis.iVolunteer.core.security.model.TokenResponse;
import at.jku.cis.iVolunteer.core.service.JWTTokenProvider;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private JWTTokenProvider tokenProvider;
	private AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTTokenProvider tokenProvider) {
		this.authenticationManager = authenticationManager;
		this.tokenProvider = tokenProvider;

		setFilterProcessesUrl("/auth/login");
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

		try {
			String accessToken = this.tokenProvider.generateAccessToken(auth);
			String refreshToken = this.tokenProvider.generateRefreshToken(auth);

			res.setContentType("application/json");
			res.setCharacterEncoding("UTF-8");
			res.getWriter().write(new TokenResponse(accessToken, refreshToken).toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}