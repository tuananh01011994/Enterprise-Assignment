package com.ecommerce.backend;

import com.ecommerce.backend.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextListener;

@SpringBootApplication
public class BackEndApplication {
    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;


    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.setProperty("spring.jpa.properties.hibernate.format_sql", "true");
        configuration.setProperty("hibernate.show_sql", "true");
        SpringApplication.run(BackEndApplication.class, args);

    }

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }



}
