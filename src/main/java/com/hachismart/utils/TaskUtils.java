package com.hachismart.utils;


import com.hachismart.jpa.bean.QuartzConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @BelongsProject: spring-boot-dynamic-quartz
 * @BelongsPackage: com.hachismart.utils
 * @Author: smallodd
 * @CreateTime: 2019-04-15 17:25
 * @Description: 利用反射执行方法
 */
@Component
public class TaskUtils {

    @Autowired
    private ConfigurableListableBeanFactory beanFactory; // Spring应用上下文环境

    public void invokMethod(QuartzConfig config) {
        Object obj = null;
        Class clazz = null;
        //通过Spring上下文去找 也有可能找不到
        try {

            String className = lowerCaseInit(config.getClassPath().split("\\.")[config.getClassPath().split("\\.").length - 1]);

            if (containsBean(className)) {
                obj = beanFactory.getBean(className);
            }


            if (obj == null) {
                clazz = Class.forName(config.getClassPath());
                obj = clazz.newInstance();
            } else {
                clazz = obj.getClass();
            }
        } catch (Exception e) {
            throw new RuntimeException("ERROR:TaskUtils is Bean Create please check the classpath is`t right or not");
        }
        Method method = null;
        //获得方法名
        try {
            method = clazz.getDeclaredMethod(config.getMethodName());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("ERROR:TaskUtils is Bean the method Create please check the methodName is`t right or not");
        }
        //方法执行
        try {
            method.invoke(obj);
        } catch (Exception e) {
            throw new RuntimeException("ERROR:TaskUtils is Bean the method execute please check the methodName is`t right or not");
        }

    }

    /**
     * 首字母小写
     *
     * @return: 小写的首字母
     */

    private static String lowerCaseInit(String str) {
        if (str.length() > 0) {
            char c = str.charAt(0);
            if (c >= 65 && c <= 90) {
                int i = c + 32;
                return ((char) i) + str.substring(1);
            } else {
                return str;
            }
        } else {
            return null;
        }
    }

    /**
     * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
     *
     * @param name
     * @return boolean
     */
    public boolean containsBean(String name) {
        return beanFactory.containsBean(name);
    }
}
