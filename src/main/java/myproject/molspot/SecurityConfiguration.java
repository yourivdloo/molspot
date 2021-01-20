package myproject.molspot;

import myproject.molspot.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    MyUserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String admin = "ADMIN";
        String user = "USER";
        http.
                csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/users/new").permitAll()
                .antMatchers("/users/username").hasAnyRole(admin, user)
                .antMatchers(HttpMethod.GET,"/users/{id}").hasAnyRole(admin, user)
                .antMatchers(HttpMethod.PUT,"/users/{id}").hasAnyRole(admin, user)
                .antMatchers("/users/**").hasRole(admin)
                .antMatchers("/suspicions").hasAnyRole(admin,user)
                .antMatchers(HttpMethod.GET,"/candidates/**").hasAnyRole(admin, user)
                .antMatchers("/candidates/**").hasRole(admin)
                .antMatchers("/episodes").hasRole(admin)
                .anyRequest().authenticated()
                .and().httpBasic();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}