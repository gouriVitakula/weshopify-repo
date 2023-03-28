package com.weshopify.platform.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.weshopify.platform.exceptions.FilterChainExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class AppSecurityConfig {

	@Autowired
	private JwtAuthenticationService authnService;
	
	@Autowired
    private FilterChainExceptionHandler filterChainExceptionHandler;
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authz) -> {
			try {
				authz
				        .anyRequest().authenticated()
				        .and().csrf().disable().anonymous().disable()
				        .addFilterBefore(new JwtAuthnFilter(authnService), BasicAuthenticationFilter.class)
				        .addFilterAfter(filterChainExceptionHandler, LogoutFilter.class);
				        
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
        		
        return http.build();
    }
	
	@Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
		log.info("ingnoring the security");
		return (web) -> web.ignoring().requestMatchers("/swagger-ui.html","/swagger-ui/**","/v3/api-docs/**");
    }
}
