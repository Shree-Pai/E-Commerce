package com.scem.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class WebLoggingConfig {

    @Bean
    CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter f = new CommonsRequestLoggingFilter();
        f.setIncludeQueryString(true);
        f.setIncludePayload(false);
        f.setMaxPayloadLength(2048);
        f.setIncludeHeaders(false);
        f.setAfterMessagePrefix("REQUEST DATA : ");
        return f;
    }
}
