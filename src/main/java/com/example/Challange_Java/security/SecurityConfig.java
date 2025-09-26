package com.example.Challange_Java.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.Challange_Java.Service.CustomUserDetailsService;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
public class SecurityConfig {
    private final CustomUserDetailsService userDetailsService;
    private final AccessDeniedHandler accessDeniedHandler;


    public SecurityConfig(CustomUserDetailsService userDetailsService, AccessDeniedHandler accessDeniedHandler) {

        this.userDetailsService = userDetailsService;
        this.accessDeniedHandler = accessDeniedHandler;

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/css/**", "/js/**").permitAll() // permite login e recursos públicos
                        .requestMatchers("/motos/listagem").hasAnyRole("USER", "ADMIN")  // Permite tanto para USER quanto ADMIN
                        .requestMatchers("/motos/**").hasRole("ADMIN")
                        .anyRequest().authenticated()

                )
                .formLogin(form -> form
                        .loginPage("/login") // Página de login
                        .failureHandler((request, response, exception) -> {
                            System.out.println("Falha de autenticação: " + exception.getMessage());
                            response.sendRedirect("/login?error");
                        })
                        .defaultSuccessUrl("/motos/listagem", true) // redireciona para a página de listagem de motos após login
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")  // Define a URL do logout
                        .logoutSuccessUrl("/login")  // Redireciona para a página de login após o logout
                        .invalidateHttpSession(true)  // Invalida a sessão
                        .clearAuthentication(true)  // Limpa a autenticação
                        .permitAll()  // Permite que todos acessem a URL de logout
                )
                .exceptionHandling()
                    .accessDeniedHandler(accessDeniedHandler); // Usando o AccessDeniedHandler personalizado

        return http.build();
    }
}

