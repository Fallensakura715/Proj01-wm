package com.fallensakura.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "fallensakura.wechat")
public class WechatProperties {

    private String appid;
    private String secret;

}
