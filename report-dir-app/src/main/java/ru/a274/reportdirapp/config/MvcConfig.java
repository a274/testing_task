package ru.a274.reportdirapp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("ru.a274")
@PropertySource("classpath:email-info.properties")
public class MvcConfig implements WebMvcConfigurer {
}
