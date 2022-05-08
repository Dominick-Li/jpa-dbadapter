package com.ljm.dbadapter.adaptation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * @Description mysql 原生sql模版
 * @Author Dominick Li
 * @CreateTime 2022/3/11 9:55
 **/
@Configuration
@ConditionalOnProperty(name = "spring.profiles.active", havingValue = "mysql")
public class MysqlNativeSqlAdaptation extends DataBaseNativeSql {

    @Override
    public String format_year() {
        return "date_format(${field},'%Y')";
    }

    @Override
    public String format_year_month() {
        return "date_format(${field},'%Y-%m')";
    }

    @Override
    public String format_year_month_day() {
        return "date_format(${field},'%Y-%m-%d')";
    }
}
