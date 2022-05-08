package com.ljm.dbadapter.config;

import com.ljm.dbadapter.adaptation.ColumnDefinitionAdaptaion;
import com.ljm.dbadapter.adaptation.DataBaseNativeSql;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @Description 加载数据库连接池之前通过反射修改实体类字段类型
 * @Author Dominick Li
 * @CreateTime 2022/3/11 10:57
 **/
@Slf4j
@Configuration
public class InitConfig {

    @Autowired
    private DataBaseConfig dataBaseConfig;

    @Autowired
    private HikariPoolConfig HikariPoolConfig;

    @Resource
    private DataBaseNativeSql dataBaseNativeSql;

    @Bean
    @Primary
    public DataSource datasource() {
        log.info("数据库连接池加载前配置....");
        //初始化字段适配器
        new ColumnDefinitionAdaptaion().init();
        HikariDataSource dataSource = new HikariDataSource();
        //克隆配置属性到到HikariDataSource实例中
        BeanUtils.copyProperties(HikariPoolConfig, dataSource);
        dataSource.setJdbcUrl(dataBaseConfig.getUrl());
        dataSource.setUsername(dataBaseConfig.getUsername());
        dataSource.setPassword(dataBaseConfig.getPassword());
        dataSource.setDriverClassName(dataBaseConfig.getDriverClassName());
        return dataSource;
    }
}