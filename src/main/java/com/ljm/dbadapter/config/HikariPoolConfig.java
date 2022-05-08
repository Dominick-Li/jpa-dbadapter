package com.ljm.dbadapter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description 用于获取配置文件里连接池的配置信息
 * @Author Dominick Li
 * @CreateTime 2022/3/11 13:58
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.hikari")
public class HikariPoolConfig {
    private String poolName;
    private int minimumIdle;
    private int maximumPoolSize;
    private long idleTimeout;
    private long maxLifetime;
    private long connectionTimeout;
    private String connectionTestQuery;
}
