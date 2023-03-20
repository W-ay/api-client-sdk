package com.way.apiclient;

import com.way.apiclient.client.ApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Way
 */
@Configuration
@ConfigurationProperties(prefix = "way.api")
@Data
@ComponentScan
public class ApiClientConfig {
    /**
     * AppID
     */
    private String accessKey;
    /**
     * 密钥
     */
    private String secretKey;

    @Bean
    public ApiClient apiClient(){
        return new ApiClient(accessKey, secretKey);
    }
}
