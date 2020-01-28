package at.jku.cis.iVolunteer.marketplace.security.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import static org.springframework.http.HttpMethod.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import at.jku.cis.iVolunteer.marketplace.security.entrypoint.UnauthorizedAuthenticationEntryPoint;
import at.jku.cis.iVolunteer.marketplace.security.filter.JWTAuthorizationFilter;
import at.jku.cis.marketplace.security.service.ParticipantDetailsService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired private ParticipantDetailsService participantDetailsService;
	@Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired private UnauthorizedAuthenticationEntryPoint authenticationEntryPoint;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable();
		http.authorizeRequests()
		    .antMatchers("/api/**").permitAll()
		    .antMatchers("/push-task-from-api").permitAll()
		    .antMatchers("/fahrtenspange-fake").permitAll()
		    .antMatchers("/reset").permitAll()
		    .antMatchers("/init/**").permitAll()
		    .antMatchers("/volunteer").permitAll()
			.anyRequest().authenticated();

		http.addFilter(new JWTAuthorizationFilter(authenticationManager())).sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
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
		corsConfiguration
				.setAllowedMethods(Arrays.asList(HEAD.name(), GET.name(), DELETE.name(), POST.name(), PUT.name()));
		source.registerCorsConfiguration("/**", corsConfiguration);
		return source;
	}
}
