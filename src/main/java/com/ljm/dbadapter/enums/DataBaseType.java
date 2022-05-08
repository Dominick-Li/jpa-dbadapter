package com.ljm.dbadapter.enums;

/**
 * @Description 目前支持的数据库类型
 * @Author Dominick Li
 * @CreateTime 2022/3/9 17:15
 **/
public enum DataBaseType {
    MYSQL("mysql"),
    ORACLE("oracle"),
    ;
    private String name;

    DataBaseType(String name) {
        this.name = name;
    }

    public static DataBaseType nameOf(String name) {
        for (DataBaseType dataBaseType : DataBaseType.values()) {
            if (dataBaseType.name.equals(name)) {
                return dataBaseType;
            }
        }
        return null;
    }
}
