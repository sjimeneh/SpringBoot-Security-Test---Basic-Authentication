package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain FilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // Deshabilitar CSRF para evitar errores de seguridad en las solicitudes
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(auth -> auth
                        //.requestMatchers("/api/v1/user/").permitAll()
                        //.requestMatchers(HttpMethod.POST, "/api/v1/user/create").permitAll()
                        //.requestMatchers(HttpMethod.POST, "/api/v1/user/create").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(AbstractAuthenticationFilterConfigurer -> AbstractAuthenticationFilterConfigurer
                        .permitAll()
                        // Luego de iniciar sesión nos debe redirigir a las especificaciones
                        .successHandler(successHandler())
                )
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        // Hay que tener mucho cuidado con estas configuraciones, debido a que la que se escoja,
                        // podría dejar cookies almacenadas en el cliente.
                        // ALWAYS - IF_REQUIRED -NEVER - STATELESS
                        //ALWAYS: Creará una sesión siempre y cuando no exista ninguna, si existe la reutiliza
                        // IF_REQUIRED: Crea una sesión solo si es necesario: Si no existe la crea
                        // NEVER: No crea ninguna sesión pero, si ya existe alguna la reutiliza
                        // STATELESS: No crea ninguna sessión, no guardará ningún tipo de dato de la sesión.

                        // Si la sesión no es válida, a donde se redirige el usuario
                        .invalidSessionUrl("/login")
                        //Máximo numero de sesiones
                        .maximumSessions(1)
                        //Si, se expira la sessión a donde se redirige
                        .expiredUrl("/login")
                        // Podemos crear un objeto que administre todas las sesiones
                        // Habilitamos el objeto para que mapee los datos del objeto que se ha autenticado
                        .sessionRegistry(sessionRegistry())

                )
                // Configurar autenticación HTTP básica
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    // Crear un registro de sesiones para administrar las sesiones activas
@Bean
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }

    public AuthenticationSuccessHandler successHandler(){
        // Manejar la redirección después de iniciar sesión correctamente
        return (((request, response, authentication) -> {
            response.sendRedirect("/api/v1/user/index");
        }));
    }
}
