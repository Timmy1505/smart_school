package zm.schools.smartschool.Security.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.AllArgsConstructor;
import zm.schools.smartschool.Services.AuthUserService;

@Configuration
@AllArgsConstructor
public class WebSecurityConfig {

    private final AuthUserService authUserService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CustomLoginHandler successHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/").permitAll()
                .requestMatchers("/v1/login").permitAll()
                .requestMatchers("/v1/authorised/teacher/**").hasAuthority("Subject Teacher")
                .requestMatchers("/v1/authorised/accountant/**").hasAuthority("Accountant")
                .requestMatchers("/v1/authorised/parent/**").hasAuthority("Parrent")
                .requestMatchers("/v1/authorised/admin/**").hasAuthority("Admin")
                .requestMatchers("/v1/authorised/student/**").hasAuthority("Student")
                .anyRequest().authenticated()
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/v1/login")
                .loginProcessingUrl("/login")
                .failureUrl("/v1/login?error=true")
                .successHandler(successHandler)
                .usernameParameter("userName")
                .passwordParameter("password")
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
            )
            .exceptionHandling(exceptionHandling -> exceptionHandling
                .accessDeniedPage("/access-denied")
            );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(authUserService);
        return provider;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                "/img/**", "/css/**", "/js/**", "/dashboard/**");
    }
}