package com.proyecto.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

        /**
         * Configuración de la cadena de filtros de seguridad.
         * Se habilita Basic Auth para facilitar pruebas en Postman y se deshabilita
         * CSRF
         * temporalmente para permitir peticiones POST externas sin tokens en entorno de
         * desarrollo.
         */
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/login", "/registro").permitAll()
                                                .anyRequest().authenticated())
                                .httpBasic(org.springframework.security.config.Customizer.withDefaults()) // Permite
                                                                                                          // pruebas en
                                                                                                          // Postman
                                .formLogin(login -> login
                                                .loginPage("/login")
                                                .defaultSuccessUrl("/", true)
                                                .failureUrl("/login?error=true")
                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutUrl("/logout")
                                                .logoutSuccessUrl("/login?logout=true")
                                                .permitAll())
                                .csrf(csrf -> csrf.disable()); // Deshabilitar CSRF para facilitar pruebas en Postman
                                                               // (solo desarrollo)

                return http.build();
        }

        @Bean
        public BCryptPasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}
