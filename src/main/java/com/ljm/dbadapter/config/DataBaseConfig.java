package com.ljm.dbadapter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author Dominick Li
 * @CreateTime 2022/3/11 13:53
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class DataBaseConfig {
    private String url;
    private String username;
    private String password;
    private String driverClassName;
}
