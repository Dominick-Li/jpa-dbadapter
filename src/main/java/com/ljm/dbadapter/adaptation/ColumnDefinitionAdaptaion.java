package com.ljm.dbadapter.adaptation;


import com.ljm.dbadapter.annotation.OracleCloumnDefinition;
import com.ljm.dbadapter.enums.DataBaseType;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description 对数据库字段类型和主键自增策略自动适配
 * @Author Dominick Li
 * @CreateTime 2022/3/11 14:24
 **/
@Slf4j
public class ColumnDefinitionAdaptaion {

    public void init() {
        try {
            //只有oracle需要对字段特殊处理,其它的按照默认类型即可，如需要扩充其它数据库加上 || 判断条件即可
            if (DataBaseNativeSql.getDataBaseType() == DataBaseType.ORACLE) {
                log.info("*****************************对实体类字段进行自动适配开始******************************");
                //第一步 加载需要扫描的class文件
                List<Class<?>> classList = getClassList();
                if (classList == null) {
                    return;
                }
                //第二步 通过反射机制修改类的Cloumn的columnDefinition属性
                for (Class<?> clazz : classList) {
                    modifyCloumnDefinition(clazz);
                }
                log.info("*****************************对实体类字段进行自动适配结束******************************");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ColumnDefinitionAdaptaion error:{}", e.getMessage());
        }
    }

    /**
     * 读取className文件获取需要加载的class文件
     */
    private List<Class<?>> getClassList() {
        try (InputStreamReader isr = new InputStreamReader(getClass().getResourceAsStream("/className.txt"), "UTF-8")) {
            List<Class<?>> classList = new ArrayList<>();
            Class<?> beanClass;
            BufferedReader br = new BufferedReader(isr);
            String className = "";
            while ((className = br.readLine()) != null) {
                beanClass = Class.forName(className);
                //只加载包含@Table注解的类
                if (beanClass.isAnnotationPresent(Table.class)) {
                    classList.add(beanClass);
                }
            }
            return classList;
        } catch (Exception e) {
            log.error("getClass error:{}", e.getMessage());
            return null;
        }
    }

    /**
     * 修改注解里的字段类型
     */
    public static void modifyCloumnDefinition(Class<?> clas) throws Exception {
        DataBaseType dataBaseType = DataBaseNativeSql.getDataBaseType();
        Column column;
        GeneratedValue generatedValue;
        OracleCloumnDefinition oracleCloumnDefinition;
        Map generatedValueMemberValues;
        boolean modify;
        InvocationHandler invocationHandler;
        Field hField;
        Field[] fields = clas.getDeclaredFields();
        for (Field field : fields) {
            generatedValueMemberValues = null;
            modify = false;
            if (field.isAnnotationPresent(Column.class)) {
                if (dataBaseType == DataBaseType.ORACLE && field.isAnnotationPresent(OracleCloumnDefinition.class)) {
                    modify = true;
                } else if (true) {
                    //可以在if逻辑处扩展其它数据库判断逻辑
                }
            }
            if (modify) {
                column = field.getAnnotation(Column.class);
                // 获取column这个代理实例所持有的 InvocationHandler
                invocationHandler = Proxy.getInvocationHandler(column);
                // 获取 AnnotationInvocationHandler 的 memberValues 字段
                hField = invocationHandler.getClass().getDeclaredField("memberValues");
                // 因为这个字段事 private修饰，所以要打开访问权限
                hField.setAccessible(true);
                // 获取 memberValues
                Map memberValues = (Map) hField.get(invocationHandler);
                //判断是否为主键并设置了自增策略
                if (field.isAnnotationPresent(Id.class) && field.isAnnotationPresent(GeneratedValue.class)) {
                    //修改自增策略
                    generatedValue = field.getAnnotation(GeneratedValue.class);
                    invocationHandler = Proxy.getInvocationHandler(generatedValue);
                    hField = invocationHandler.getClass().getDeclaredField("memberValues");
                    hField.setAccessible(true);
                    generatedValueMemberValues = (Map) hField.get(invocationHandler);
                }
                // 修改 value 属性值
                if (dataBaseType == DataBaseType.ORACLE) {
                    oracleCloumnDefinition = field.getAnnotation(OracleCloumnDefinition.class);
                    memberValues.put("columnDefinition", oracleCloumnDefinition.value());
                    if (generatedValueMemberValues != null) {
                        //修改主键的自增策略
                        generatedValueMemberValues.put("strategy", oracleCloumnDefinition.strategy());
                        log.info("字段名称:{},需要注解自增策略为为:{}", field.getName(), oracleCloumnDefinition.strategy());
                    }
                } else if (true) {
                    //可以在if逻辑处扩展其它数据库需要修改的字段类型注解
                }
                log.info("字段名称:{},需要修改类型为:{}", field.getName(), column.columnDefinition());
            }
        }
    }
}
