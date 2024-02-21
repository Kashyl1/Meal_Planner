package com.example.mealplanner.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

/**
 * Web configuration class for the Meal Planner application.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Registers resource handlers for static resources.
     * @param registry the resource handler registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/styles/**")
                .addResourceLocations("classpath:/static/styles/")
                .setCachePeriod(3600)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }

}