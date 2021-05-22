package com.ecommerce.backend.config;


import com.ecommerce.backend.security.MyAuthenticationFailureHandler;
import com.ecommerce.backend.security.MyAuthenticationSuccessfulHandler;
import com.ecommerce.backend.security.MyLogOutSuccessfulHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    protected void configure(HttpSecurity http) throws Exception {

        http
                .anonymous().principal("guest").authorities("READ_PRIVILEGE")
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/api/seller/*").permitAll()
//                .antMatchers("/login*").permitAll()
                .antMatchers("/user/**").permitAll()
                .antMatchers("/admin").hasAuthority("WRITE_PRIVILEGE")
//                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()

//                .loginPage("/home")
/*
                .defaultSuccessUrl("/login.html", true)
*/
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailureHandler)
                .and()
                .logout()
//                .logoutSuccessUrl("/perform_logout")
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(myLogOutSuccessfulHandler)
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied");

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
