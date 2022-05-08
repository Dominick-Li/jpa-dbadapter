package com.ljm.dbadapter.model;

import com.ljm.dbadapter.annotation.OracleCloumnDefinition;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author Dominick Li
 * @CreateTime 2022/5/4 21:01
 * @Description
 **/
@Data
@Entity
@Table(name = "sys_user")
public class SysUser {

    /**
     * 主键 自增策略  oracle=GenerationType.SEQUENCE, mysql=GenerationType.IDENTITY, 如果Id使用雪花算法生成或者UUID则可设置为默认值GenerationType.AUTO
     * mysql数据库只有int类型支持主键自增，Long类型默认对应的Mysql数据库的bigint类型不支持自增
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(columnDefinition = "int")
    @OracleCloumnDefinition("number")
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 喜欢看的书 字段类型设置,默认用Mysql数据库配置为文本块text类型存储,oracle的文本块用clob存储
     * 如果需要扩展其它的数据库,可根据Column配置的columnDefinition类型是否兼容,不兼容需要自定义扩展和@OracleCloumnDefinition类似的注解
     */
    @OracleCloumnDefinition("clob")
    @Column(columnDefinition = "text")
    private String likeBookList;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否可用
     * mysql中Column默认使用的bit类型存储boolean类型的值,oracle默认不支持boolean,需要动态修改columnDefinition成number类型
     */
    @OracleCloumnDefinition("number")
    @Column
    private boolean enabled;

}
