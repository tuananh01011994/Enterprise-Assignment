package com.ecommerce.backend.config;

import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.security.ActiveSessionList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfigContext {

    @Bean
    public ActiveSessionList activeUserStore() {
        return new ActiveSessionList();
    }

    @Bean
    public User user() { return  new User();}
}
