package org.chat.chatbotjava.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration

public class ConfigCors {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            public void addCorsMappings(@NotNull CorsRegistry registry) {
                registry.addMapping("/**") // Aplica para todos os endpoints
                        .allowedOrigins("*") // Permite qualquer origem
                        .allowedMethods("*") // Permite todos os métodos HTTP
                        .allowedHeaders("*") // Permite todos os cabeçalhos
                        .allowCredentials(false); // Não exige credenciais
            }
        };
    }
}
