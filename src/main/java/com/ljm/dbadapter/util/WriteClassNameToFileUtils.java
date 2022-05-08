package com.ljm.dbadapter.util;


import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @Description 生成需要通过反射修改字段类型类文件路径存储文件
 * @Author Dominick Li
 * @CreateTime 2022/3/24 19:32
 **/
public class WriteClassNameToFileUtils {
    public static void main(String[] args) throws Exception {
        List<String> packNameList = Arrays.asList("com.ljm.dbadapter.model" );
        StringBuilder sb=new StringBuilder();
        for (String packageName : packNameList) {
            String path = packageName.replaceAll("\\.", "/");
            File dir = org.springframework.util.ResourceUtils.getFile("classpath:" + path);
            for (File file : dir.listFiles()) {
                if (file.isDirectory()) {
                    continue;
                } else {
                    if(sb.length()!=0){
                        sb.append("\n");
                    }
                    String className = packageName + "." + file.getName().replace(".class", "");
                    sb.append(className);
                }
            }
        }
        System.out.println(sb.toString());
        String resourcePath= new File("src/main/resources/").getAbsolutePath();
        File file=new File(resourcePath+ File.separator+"className.txt");
        FileOutputStream fileOutputStream=new FileOutputStream(file);
        fileOutputStream.write(sb.toString().getBytes());
        fileOutputStream.flush();
        fileOutputStream.close();
    }
}
