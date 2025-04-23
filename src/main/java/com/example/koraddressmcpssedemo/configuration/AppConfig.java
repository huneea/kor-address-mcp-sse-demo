package com.example.koraddressmcpssedemo.configuration;

import com.example.koraddressmcpssedemo.interceptor.RestClientLoggingInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    private final RestClientLoggingInterceptor restClientLoggingInterceptor;


    @Bean
    public RestClient restClient() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(3));
        factory.setReadTimeout(Duration.ofSeconds(3));
        return RestClient.builder()
                         .requestInterceptor(restClientLoggingInterceptor)
                         .requestFactory(factory)
                         .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                         .build();
    }


    @Profile("!test")
    @Bean
    public String apiKey(Environment env) {
        return env.getProperty("API_KEY");
    }

}
