package edu.tku.web.config;

import edu.tku.web.service.LoginService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@Log4j2
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeRequests(authorizeRequests ->
                authorizeRequests
                        .antMatchers("/login", "/static/**", "/h2-console/**").permitAll()
                        .antMatchers("/api/v1/**").permitAll()
                        .anyRequest().authenticated()).formLogin(formLogin -> formLogin.loginPage("/login").permitAll()
                .usernameParameter("uid").passwordParameter("pwd")
                .failureHandler(((request, response, exception) -> {
                    log.error("Loging failed, " + exception.getMessage());
                    response.setStatus(401);
                })).defaultSuccessUrl("/", true)).csrf().disable().build();
    }

    @Bean
    public AuthenticationProvider daoAuthenticationProvider(LoginService userService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authenticationProvider;
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, AuthenticationProvider authenticationProvider) {
        auth.authenticationProvider(authenticationProvider);
    }

}
