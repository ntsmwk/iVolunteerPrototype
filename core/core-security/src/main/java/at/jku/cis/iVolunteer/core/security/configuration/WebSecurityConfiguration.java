package at.jku.cis.iVolunteer.core.security.configuration;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.HEAD;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import at.jku.cis.iVolunteer.core.security.entrypoint.UnauthorizedAuthenticationEntryPoint;
import at.jku.cis.iVolunteer.core.security.filter.JWTAuthenticationFilter;
import at.jku.cis.iVolunteer.core.security.filter.JWTAuthorizationFilter;
import at.jku.cis.iVolunteer.core.service.JWTTokenProvider;
import at.jku.cis.iVolunteer.core.service.ParticipantDetailsService;

//@EnableGlobalMethodSecurity(securedEnabled = true)


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private ParticipantDetailsService participantDetailsService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private UnauthorizedAuthenticationEntryPoint authenticationEntryPoint;

	private JWTTokenProvider tokenProvider = new JWTTokenProvider();

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http.cors().and().csrf().disable();
		http.authorizeRequests()
			.antMatchers("/api/**").permitAll() 
			.antMatchers("/v2/api-docs").permitAll() 
			.antMatchers("/swagger-resources/**").permitAll() 
			.antMatchers("/swagger-ui.html").permitAll() 
			.antMatchers("/init/**").permitAll()
			.antMatchers("/trustifier/contractor/classInstance").permitAll()
			.antMatchers("/trustifier/contractor/classInstances").permitAll()
			.antMatchers("/login/**").permitAll()
			.antMatchers("/register/**").permitAll()
			.antMatchers("/tenant/**").permitAll()
			.anyRequest().authenticated();
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), tokenProvider))
				.addFilter(new JWTAuthorizationFilter(authenticationManager(), tokenProvider)).sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);		 
		// @formatter:on
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/login/refreshToken").and().ignoring().antMatchers("/login/activation-status");
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(participantDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.applyPermitDefaultValues();
		corsConfiguration.setAllowedMethods(Arrays.asList(HEAD.name(), GET.name(), POST.name(), PUT.name()));
		source.registerCorsConfiguration("/**", corsConfiguration);
		return source;
	}
}
