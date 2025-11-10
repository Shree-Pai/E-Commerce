package com.scem.ecommerce.security;


import com.scem.ecommerce.service.impl.CustomUserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) 
public class SecurityConfig {

    private final CustomUserDetailsServiceImpl userDetailsService;
    private final JwtAuthenticationEntryPoint unauthorizedHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(CustomUserDetailsServiceImpl userDetailsService,
                          JwtAuthenticationEntryPoint unauthorizedHandler,
                          JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Password encoder bean (BCrypt).
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @SuppressWarnings("deprecation")
	@Bean
    AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


    @SuppressWarnings("unused")
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> {}) // CORS handled by WebCorsConfig
            .csrf(csrf -> csrf.disable())
            .exceptionHandling(ex -> ex.authenticationEntryPoint(unauthorizedHandler))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Public Routes
            		.requestMatchers(
                            "/swagger-ui.html",
                            "/swagger-ui/**",
                            "/v3/api-docs",
                            "/v3/api-docs/**"
                        ).permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/products/**").permitAll()


                // Admin-only Routes
                .requestMatchers("/api/admin/**").hasRole("ADMIN")

                // Everything else requires authentication
                .anyRequest().authenticated()
            );

      
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
