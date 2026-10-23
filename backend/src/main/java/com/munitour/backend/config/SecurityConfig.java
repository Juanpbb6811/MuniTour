package com.munitour.backend.config;

import com.munitour.backend.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuración de seguridad de Spring Security 6 para la API REST.
 * - CSRF deshabilitado (API REST sin estado)
 * - CORS habilitado
 * - Sesión sin estado (JWT)
 * - Endpoints públicos para consultas de turismo y autenticación
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Cadena de filtros de seguridad principal.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Deshabilitar CSRF (API REST sin estado)
                .csrf(AbstractHttpConfigurer::disable)

                // Habilitar CORS (usa la configuración de CorsConfig)
                .cors(cors -> {})

                // Política de sesión sin estado
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Reglas de autorización
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos de bienvenida y salud
                        .requestMatchers("/", "/error", "/favicon.ico").permitAll()

                        // Endpoints de autenticación - públicos
                        .requestMatchers("/api/auth/**").permitAll()

                        // Endpoints de consulta de turismo - públicos (solo GET)
                        .requestMatchers(HttpMethod.GET, "/api/places").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/places/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/places/import-osm").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/places/sync-auto").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/places/sync-osm").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/places/self-manage").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/dishes").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/dishes/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/events").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/events/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/routes/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/reviews").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/reviews/**").permitAll()

                        // SpringDoc / Swagger - público
                        .requestMatchers("/api-docs/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/swagger-ui.html").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()

                        // Todo lo demás requiere autenticación
                        .anyRequest().authenticated()
                )

                // Agregar filtro JWT antes del filtro de autenticación por usuario/contraseña
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Bean para codificar contraseñas con BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean del AuthenticationManager para la autenticación programática.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Bean de RestTemplate para llamadas HTTP externas (ej. verificación de tokens de Google OAuth).
     */
    @Bean
    public org.springframework.web.client.RestTemplate restTemplate() {
        return new org.springframework.web.client.RestTemplate();
    }

}
