package com.ljm.dbadapter.annotation;

import javax.persistence.GenerationType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description 当使用oracle数据库的时候,使用注解内的类型动态替换类中的@Column注解columnDefinition属性
 * @Author Dominick Li
 * @CreateTime 2022/3/11 10:57
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OracleCloumnDefinition {

    /**
     * 数据库字段类型
     */
    String value() default "" ;

    /**
     * 主键自增策略
     */
    GenerationType strategy() default GenerationType.SEQUENCE;

}
