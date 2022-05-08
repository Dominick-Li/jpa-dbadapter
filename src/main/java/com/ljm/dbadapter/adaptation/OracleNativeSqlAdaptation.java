package com.ljm.dbadapter.adaptation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * @Description oracle数据库需要适配的方言
 * @Author Dominick Li
 * @CreateTime 2022/3/11 9:59
 **/
@Configuration
@ConditionalOnProperty(name = "spring.profiles.active", havingValue = "oracle")
public class OracleNativeSqlAdaptation extends DataBaseNativeSql {

    @Override
    public String format_year() {
        return "to_char(${field},'yyyy')";
    }

    @Override
    public String format_year_month() {
        return "to_char(${field},'yyyy-mm')";
    }

    @Override
    public String format_year_month_day() {
        return "to_char(${field},'yyyy-mm-dd')";
    }
}
