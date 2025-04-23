package com.example.koraddressmcpssedemo.configuration;

import com.example.koraddressmcpssedemo.client.JusoService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbacks;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class McpConfig {
    private final JusoService jusoService;


    @Bean
    public List<ToolCallback> jusoTools() {
        return List.of(ToolCallbacks.from(jusoService));
    }
}
