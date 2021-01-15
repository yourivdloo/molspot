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
        http.
                csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/users/new").permitAll()
                .antMatchers("/users/username").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET,"/users/{id}").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PUT,"/users/{id}").hasAnyRole("ADMIN", "USER")
                .antMatchers("/users/**").hasRole("ADMIN")
//                .antMatchers(HttpMethod.GET,"/users").hasRole("ADMIN")
//                .antMatchers(HttpMethod.DELETE,"/users").hasRole("ADMIN")
//                .antMatchers(HttpMethod.PUT,"/users").hasRole("ADMIN")
                .antMatchers("/suspicions").hasAnyRole("ADMIN","USER")
                .antMatchers(HttpMethod.GET,"/candidates/**").hasAnyRole("ADMIN", "USER")
//                .antMatchers(HttpMethod.GET, "/candidates/{id}").hasAnyRole("ADMIN", "USER")
                .antMatchers("/candidates/**").hasRole("ADMIN")
                .antMatchers("/episodes").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().httpBasic();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}