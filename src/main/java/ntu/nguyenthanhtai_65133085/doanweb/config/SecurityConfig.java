package ntu.nguyenthanhtai_65133085.doanweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import ntu.nguyenthanhtai_65133085.doanweb.security.JwtAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	// Chain 1: Dành cho REST API (/api/**) - dùng JWT, stateless
	@Bean
	@Order(1)
	public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
		http.securityMatcher("/api/**").csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth.requestMatchers("/api/users/register", "/api/users/login")
						.permitAll().anyRequest().authenticated())
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	// Chain 2: Dành cho Web (Thymeleaf) - dùng Form Login + Session
	@Bean
	@Order(2)
	public SecurityFilterChain webFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				auth -> auth.requestMatchers("/login", "/register", "/css/**", "/js/**", "/images/**", "/error")
						.permitAll().anyRequest().authenticated())
				.formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/dashboard", true)
						.failureUrl("/login?error").permitAll())
				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login?logout").permitAll());
		return http.build();
	}
}