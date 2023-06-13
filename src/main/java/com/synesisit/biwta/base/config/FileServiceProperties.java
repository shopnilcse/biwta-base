package com.synesisit.biwta.base.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("fileservice")
@Getter
@Setter
public class FileServiceProperties {
    private String url;
    private String port;
    private String internalurl;
}
