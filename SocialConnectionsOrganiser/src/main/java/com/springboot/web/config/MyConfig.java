package com.springboot.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class MyConfig{

	@Bean
	public UserDetailsService getUserDetailsService() {
		return new UserDetailsServiceImpl();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}

//	//Configure method..
//	@Bean
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.authenticationProvider(authenticationProvider());
//	}
	
	//here we need to configure to which url need to protected
		
//	
//	 @Bean
//	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//	        http.authorizeHttpRequests()
//	                 .requestMatchers("/admin/**").hasRole("ADMIN")
//	                .requestMatchers("/user/**").hasRole("USER")
//	                .requestMatchers("/**").permitAll().and().formLogin().and().csrf().disable();
//
//	        http.formLogin()
//	        .loginPage("/signin")
//	        .loginProcessingUrl("/dologin")
//	        .defaultSuccessUrl("/user/index")
//	        .failureUrl("/login_fail")
//	        .defaultSuccessUrl("/user/index", true);
//
//	        return http.build();
//	 }
	 
	 @Bean
	 public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	  
	     http.authorizeHttpRequests(
	             auth -> auth.requestMatchers("/**").permitAll()
	             .requestMatchers("/admin/**").hasAuthority("ADMIN")
	             .requestMatchers("/user/**").hasAuthority("USER")
	             .anyRequest().authenticated()
	            )
	             .formLogin(formLogin -> formLogin
	                     .loginPage("/signin")
	                     
	                     .loginProcessingUrl("/dologin")
	                     .defaultSuccessUrl("/user/index", true)
	                     .permitAll()
	             );
//	             .rememberMe(rememberMe -> rememberMe.key("AbcdEfghIjkl..."))
//	             .logout(logout -> logout.logoutUrl("/signout").permitAll());
	  
	  
	     return http.build();
	 }
	
//	@Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
////		.csrf(AbstractHttpConfigurer::disable)
//		http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
//                    authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
//                            .requestMatchers("/admin/**").hasRole("ADMIN")
//                            .requestMatchers("/user/**").hasRole("USER")
//                            .requestMatchers("/**").permitAll()
//                            .anyRequest().authenticated()
//                            )
//            				.formLogin(formLogin->formLogin
//            				.loginPage("/signin")
//            				.loginProcessingUrl("/dologin")
//            				.defaultSuccessUrl("/user/index")//if login successful
//            				.permitAll()
//            	            )
//            .httpBasic(Customizer.withDefaults())
//            .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//        return http.build();
//    }
	
	
	
	
	
}
