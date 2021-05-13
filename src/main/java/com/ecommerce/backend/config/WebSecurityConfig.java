package com.ecommerce.backend.config;


import com.ecommerce.backend.security.MyAuthenticationSuccessfulHandler;
import com.ecommerce.backend.security.MyLogOutSuccessfulHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private MyAuthenticationSuccessfulHandler myAuthenticationSuccessHandler;

    @Autowired
    private MyLogOutSuccessfulHandler myLogOutSuccessfulHandler;

    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/seller/*").permitAll()
//                .antMatchers("/login*").permitAll()
                .antMatchers("/user/**").permitAll()
//                .anyRequest().authenticated()
                .and()
                .formLogin()
/*
                .defaultSuccessUrl("/login.html", true)
*/
                .successHandler(myAuthenticationSuccessHandler)
                .failureUrl("/login.html?error=true")
//                .failureHandler(authenticationFailureHandler());
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/perform_logout")
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(myLogOutSuccessfulHandler);

//                .successHandler(myAuthenticationSuccessHandler)
//                .failureHandler(authenticationFailureHandler)
//                .authenticationDetailsSource(authenticationDetailsSource)
    }




    // todo: encode or not
    @Bean
    public DaoAuthenticationProvider authProvider() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        authProvider.setUserDetailsService(userDetailsService);
        return authProvider;
    }

    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
