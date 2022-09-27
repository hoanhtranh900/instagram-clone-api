package com.sangnk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@EnableScheduling
@SpringBootApplication
public class IgCloneApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(IgCloneApplication.class);
    }

	public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(IgCloneApplication.class, args);
	}

}
