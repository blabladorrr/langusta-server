package pl.edu.pjwstk.langustaserver.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pl.edu.pjwstk.langustaserver.security.filter.AuthenticationFilter;
import pl.edu.pjwstk.langustaserver.security.filter.ExceptionHandlerFilter;
import pl.edu.pjwstk.langustaserver.security.filter.JWTAuthorizationFilter;
import pl.edu.pjwstk.langustaserver.security.manager.CustomAuthenticationManager;

import java.util.Arrays;

import static pl.edu.pjwstk.langustaserver.security.SecurityConstants.*;

@Configuration
public class SecurityConfiguration {
    private final CustomAuthenticationManager customAuthenticationManager;

    public SecurityConfiguration(CustomAuthenticationManager customAuthenticationManager) {
        this.customAuthenticationManager = customAuthenticationManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(customAuthenticationManager);
        authenticationFilter.setFilterProcessesUrl("/auth/login");
        http
                .cors()
                .and()
                .csrf().disable()
                .authorizeHttpRequests()
                .antMatchers(HttpMethod.OPTIONS, REGISTER_PATH).permitAll()
                .antMatchers(HttpMethod.HEAD, REGISTER_PATH).permitAll()
                .antMatchers(HttpMethod.POST, REGISTER_PATH).permitAll()
                .antMatchers(HttpMethod.POST, RECIPES_GET_BY_ID).permitAll()
                .antMatchers(HttpMethod.POST, RECIPES_GET_PUBLIC).permitAll()
                // to be deleted - test only
                .antMatchers(HttpMethod.GET, "/recipes/collections/**").permitAll()
                // *******************
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class)
                .addFilter(authenticationFilter)
                .addFilterAfter(new JWTAuthorizationFilter(), AuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }

// ALTERNATIVE SOLUTION
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedHeaders("Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method",
//                                "Access-Control-Request-Headers", "Authorization")
//                        .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
//                        .allowedOrigins("http://127.0.0.1:3333", "http://localhost:3333")
//                        .allowedMethods("GET", "HEAD", "OPTIONS", "POST", "PUT", "DELETE")
//                        .allowCredentials(true).maxAge(3600);
//
//            }
//        };
//    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList("https://pjatk-langusta-dev.netlify.app", "http://127.0.0.1:3333", "http://localhost:3333"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS", "HEAD"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList(
                "Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method",
                "Access-Control-Request-Headers", "Authorization", "Origin"));
        configuration.setExposedHeaders(Arrays.asList(
                "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"
        ));
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
