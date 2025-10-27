package com.fallensakura.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "fallensakura.jwt")
public class JwtProperties {

    private String adminSecretKey;
    private Long expirationTime;
    private String adminTokenName;
}
