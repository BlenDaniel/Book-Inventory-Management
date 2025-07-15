package com.book.inventory.management.bookim.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Allow requests from any origin
        config.addAllowedOriginPattern("*");

        config.addAllowedHeader("*"); // Allow all headers
        config.addAllowedMethod("GET"); // Allow only GET HTTP method

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}