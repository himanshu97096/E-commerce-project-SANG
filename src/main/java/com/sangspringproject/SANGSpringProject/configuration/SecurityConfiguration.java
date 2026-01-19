package com.sangspringproject.SANGSpringProject.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.sangspringproject.SANGSpringProject.models.User;
import com.sangspringproject.SANGSpringProject.services.userService;

@Configuration
public class SecurityConfiguration {

	@Configuration
	@Order(1)
	public static class AdminConfigurationAdapter{
		
		@Bean
		SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {
            http.antMatcher("/admin/**") 
                   .authorizeHttpRequests(requests -> requests
            		 .requestMatchers(new AntPathRequestMatcher("/admin/login")).permitAll()
                     .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasRole("ADMIN")
                    )
                    .formLogin(login -> login
                            .loginPage("/admin/login")
                            .loginProcessingUrl("/admin/loginvalidate")
                            .successHandler((request, response, authentication) -> {
                                response.sendRedirect("/admin/"); // Redirect on success
                            })
                            .failureHandler((request, response, exception) -> {
                                response.sendRedirect("/admin/login?error=true"); // Redirect on failure
                            }))
                    
                    .logout(logout -> logout.logoutUrl("/admin/logout")
                            .logoutSuccessUrl("/admin/login")
                            .deleteCookies("JSESSIONID"))
                    .exceptionHandling(exception -> exception
                            .accessDeniedPage("/403")  // Custom 403 page
                        );
            http.csrf(csrf -> csrf.disable());
			return http.build();
		}
	}
	
	@Configuration
	@Order(2)
	public static class UserConfigurationAdapter{
		
		@Bean
		SecurityFilterChain userFilterChain(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests(requests -> requests
            		.antMatchers("/login", "/register", "/newuserregister","/password_reset","/verify_otp","/update_password" ,"/test", "/test2", "/css/**", "/scripts/**", "/images/**").permitAll()
                    .antMatchers("/**").hasRole("USER"))
                    .formLogin(login -> login
                            .loginPage("/login")
                            .loginProcessingUrl("/userloginvalidate")
                            .successHandler((request, response, authentication) -> {
                                response.sendRedirect("/"); // Redirect on success
                            })
                            .failureHandler((request, response, exception) -> {
                                response.sendRedirect("/login?error=true"); // Redirect on failure
                            }))
                    
                    .logout(logout -> logout.logoutUrl("/logout")
                            .logoutSuccessUrl("/login")
                            .deleteCookies("JSESSIONID"))
                    .exceptionHandling(exception -> exception
                            .accessDeniedPage("/403")  // Custom 403 page
                        );

            http.csrf(csrf -> csrf.disable());
			return http.build();
		}
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
