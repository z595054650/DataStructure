package com.zxf.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by thinkpad on 2017/5/16.
 */
public class HelloAnnotation {

    public static void main(String[] args) {
        printAlias(UserBean.class);
        invokeAnnotationMehods(new UserBean("zxf",27));
    }

    public static void printAlias(Class clazz){
        for(Field field: clazz.getDeclaredFields()){
            if(field.isAnnotationPresent(Alias.class)){
                Alias alias=field.getAnnotation(Alias.class);

                System.out.println(alias.value());
            }
        }
    }

    public static void invokeAnnotationMehods(Object obj){
        Method[] methods=obj.getClass().getDeclaredMethods();
        for(Method method:methods){
            if(method.isAnnotationPresent(Test.class)){
                Test test=method.getAnnotation(Test.class);
                String methodName=test.value().length()==0 ? method.getName() :test.value();
                System.out.println("method name :"+methodName);

                try {
                    if(Modifier.isStatic(method.getModifiers())) {
                        method.invoke(null, "小明很帅");
                    }else if(Modifier.isPrivate(method.getModifiers())){
                        method.setAccessible(true);
                        method.invoke(obj);
                    }else{
                        method.invoke(obj);
                    }

                }  catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
