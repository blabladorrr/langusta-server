package pl.edu.pjwstk.langustaserver.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import pl.edu.pjwstk.langustaserver.security.filter.AuthenticationFilter;
import pl.edu.pjwstk.langustaserver.security.filter.ExceptionHandlerFilter;
import pl.edu.pjwstk.langustaserver.security.manager.CustomAuthenticationManager;

import static pl.edu.pjwstk.langustaserver.security.SecurityConstants.REGISTER_PATH;

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
                .csrf().disable()
                .authorizeHttpRequests()
                .antMatchers(HttpMethod.POST, REGISTER_PATH).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class)
                .addFilter(authenticationFilter)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }
}