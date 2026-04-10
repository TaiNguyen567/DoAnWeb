package ntu.nguyenthanhtai_65133085.doanweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()) // Tắt bảo vệ CSRF tạm thời để dễ dàng test API qua Postman
				.authorizeHttpRequests(auth -> auth.requestMatchers("/api/users/register").permitAll() // Mở cửa hoàn
																										// toàn tự do
																										// cho API đăng
																										// ký
						.anyRequest().authenticated() // Tất cả các API khác đều bắt buộc phải đăng nhập
				);
		return http.build();
	}
}