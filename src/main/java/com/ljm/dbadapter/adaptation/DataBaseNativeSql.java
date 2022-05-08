package com.ljm.dbadapter.adaptation;

import com.ljm.dbadapter.enums.DataBaseType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

/**
 * @Description 数据库原始Sql需要适配的函数  使用设计模式中的模版模式
 * @Author Dominick Li
 * @CreateTime 2022/3/11 9:51
 **/
@Slf4j
public abstract class DataBaseNativeSql {

    /**
     * 数据库兼容性问题
     * 1.实体类中的文本块字段
     * oracle   使用clob
     * mysql    使用 text
     * 2.各个数据库的sql函数不一样,当前类做了一些定制化适配
     * 3. mysql支持实体类使用boolear类型字段,oracle不支持需要修改 @Column的columnDefinition為number
     */

    @Value("${spring.profiles.active}")
    private String activeDatabase;

    private static DataBaseType dataBaseType;

    public static DataBaseType getDataBaseType() {
        return dataBaseType;
    }

    /**
     * 把日期字段格式化成只包含年的字符  例如:2021-12-24 10:20  返回2021
     */
    public abstract String format_year();

    /**
     * 把日期字段格式化成只包含年月的字符 例如:2021-12-24 10:20  返回2021-12
     */
    public abstract String format_year_month();

    /**
     * 把日期字段格式化成只包含年月日的字符  例如:2021-12-24 10:20  返回2021-12-24
     */
    public abstract String format_year_month_day();


    @PostConstruct
    public void supportsAdvice() {
        log.info("当前系统使用的数据库是{}", activeDatabase);
        dataBaseType = DataBaseType.nameOf(activeDatabase);
        if (dataBaseType == null) {
            log.error("未适配的数据库类型:{} ,系统异常退出!", activeDatabase);
            throw new RuntimeException("未适配的数据库类型,系统异常退出!");
        }
    }

}
