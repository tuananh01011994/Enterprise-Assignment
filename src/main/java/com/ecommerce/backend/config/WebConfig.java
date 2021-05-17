package com.ecommerce.backend.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    String dbUrl = "jdbc:postgresql://localhost:5432/backend";
    String username = "postgres";
    String password = "admin";

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(dbUrl);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //super.addViewControllers(registry);
//        registry.addViewController("/home").setViewName("home");
//        registry.addViewController("/login.html").setViewName("login");
        registry.addViewController("/home.html").setViewName("home");
//        registry.addViewController("/register.html").setViewName("register");
//        registry.addViewController("/forgot.html").setViewName("forgot");
//        registry.addViewController("/reset.html").setViewName("reset");
//        registry.addViewController("/login").setViewName("home");
    }


}
