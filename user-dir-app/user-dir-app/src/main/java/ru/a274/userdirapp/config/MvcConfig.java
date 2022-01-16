package ru.a274.userdirapp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableScheduling
@ComponentScan("ru.a274")
@PropertySource("classpath:service-info.properties")
public class MvcConfig implements WebMvcConfigurer {

}
