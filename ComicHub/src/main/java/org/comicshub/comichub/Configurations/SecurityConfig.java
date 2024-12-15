package org.comicshub.comichub.Configurations;


import org.comicshub.comichub.Services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    private final CustomUserDetailsService userDetailsService;
    private final CustomAuthenticationFailureHandler failureHandler;

    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService,
                          CustomAuthenticationFailureHandler handler) {
        this.userDetailsService = userDetailsService;
        this.failureHandler = handler;

    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.sameOrigin())
                )
                .authorizeHttpRequests(
                        authorize ->
                                authorize

                                        .requestMatchers("/styles/**").permitAll()
                                        .requestMatchers("/security/**").permitAll()
                                        .requestMatchers("/images/**").permitAll()
                                        .requestMatchers("/registration", "/registration/**").permitAll()
                                        .requestMatchers("/comics/**").permitAll()
                                        .requestMatchers("/viewPdf/**").permitAll()
                                        .requestMatchers("/my_account").hasAnyRole("USER", "ADMIN")
                                        .requestMatchers("/admin/**").hasRole("ADMIN")
                                        .requestMatchers("/**").hasRole("ADMIN")
                                        .anyRequest().authenticated()
                )
                .formLogin(
                        form ->
                                form.loginPage("/login")
                                        .failureHandler(failureHandler)
                                        .defaultSuccessUrl("/comics/index", true)
                                        .failureUrl("/login?error=true")
                                        .permitAll()
                )
                .logout(
                        logout -> logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login?logout=true")
                                .invalidateHttpSession(true) // Видаляє сесію
                                .clearAuthentication(true) // Скасовує аутентифікацію
                                .deleteCookies("JSESSIONID") // Видаляє cookies
                                .permitAll()
                );

        return http.build();
    }

    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
