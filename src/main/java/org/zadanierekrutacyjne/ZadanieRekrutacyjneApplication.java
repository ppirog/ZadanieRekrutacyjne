package org.zadanierekrutacyjne;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.zadanierekrutacyjne.infrastructure.security.JwtConfigProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = JwtConfigProperties.class)
public class ZadanieRekrutacyjneApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZadanieRekrutacyjneApplication.class, args);
    }
}
