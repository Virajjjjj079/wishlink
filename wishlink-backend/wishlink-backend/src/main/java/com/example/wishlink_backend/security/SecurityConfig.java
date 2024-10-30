// package com.example.wishlink_backend.security;

// import static org.springframework.security.config.Customizer.*;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.web.SecurityFilterChain;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig  {
//     // extends WebSecurityConfiguration

// //     @Bean
// // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
// //           return     http
// // 					.authorizeHttpRequests(
// // 						auth -> 
// // 							auth
// // 							.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
// //                             .requestMatchers("/create-user", "/login").permitAll()
// // 							.anyRequest().authenticated()
// // 						)
// // 					.httpBasic(withDefaults())
// // 					.sessionManagement(
// // 						session -> session.sessionCreationPolicy
// // 						(SessionCreationPolicy.STATELESS))
// // 						// .csrf().disable() Deprecated in SB 3.1.x
// // 					.csrf(csrf -> csrf.disable()) // Starting from SB 3.1.x using Lambda DSL
// // 			     // .csrf(AbstractHttpConfigurer::disable) // Starting from SB 3.1.x using Method Reference
// // 					.build();
// // }

// @Bean
// public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//     http
//         .csrf(csrf -> csrf.disable())  // Disable CSRF for simplicity
//         .authorizeHttpRequests(auth -> auth
//             .requestMatchers("/create-user", "/login").permitAll()  // Allow access to public endpoints
//             .anyRequest().authenticated()  // Secure all other endpoints
//         )
//         .httpBasic(withDefaults())  // Enable HTTP Basic Authentication
//         .sessionManagement(session -> session
//             .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // Make session stateless
//         );

//     return http.build();
// }

// }

