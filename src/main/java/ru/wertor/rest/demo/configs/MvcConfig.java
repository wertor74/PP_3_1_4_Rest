package ru.wertor.rest.demo.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/admin/users").setViewName("/admin/users");
        registry.addViewController("/user").setViewName("/user");
        registry.addViewController("/logout").setViewName("index");
    }
}
