package com.zxf.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;

/**
 * Created by thinkpad on 2017/5/16.
 */
public class ReflectTest {

    public static void main(String[] args) {
        printFieldsAndMethods(UserBean.class);
        InvokeMethod(UserBean.class);
    }

    private static void  printFieldsAndMethods(Class userBeanClazz){

        //Fileds
        Field[] feilds=userBeanClazz.getDeclaredFields();
        System.out.println("UserBean 中的字段：");
        for (int i = 0; i <feilds.length ; i++) {
            String fieldString="";
            fieldString+= Modifier.toString(feilds[i].getModifiers());
            fieldString+=" "+feilds[i].getType().getSimpleName();
            fieldString+=" "+feilds[i].getName()+";";
            System.out.println(fieldString);
        }

        //Method
        Method[] methods=userBeanClazz.getDeclaredMethods();
        System.out.println("UserBean中的方法：");
        for (int i = 0; i <methods.length ; i++) {
            String methodString="";
            methodString+=Modifier.toString(methods[i].getModifiers());
            methodString+=" "+methods[i].getReturnType().getName();
            methodString+=" "+methods[i].getName()+"(";

            Class[] parameter=methods[i].getParameterTypes();
            for (int j = 0; j <parameter.length ; j++) {
                methodString+=parameter[j].getName()+" ";
            }
            methodString+=")";
            System.out.println(methodString);
        }

        //Constructor
        Constructor[] constructors=userBeanClazz.getDeclaredConstructors();
        System.out.println("UserBean中的构造函数：");
        for (int i = 0; i <constructors.length ; i++) {
            String constructorString="";
            constructorString+=Modifier.toString(constructors[i].getModifiers());
            constructorString+=" "+constructors[i].getName()+"(";

            Class[] parameters=constructors[i].getParameterTypes();
            for (int j = 0; j < parameters.length; j++) {
                constructorString+=parameters[i].getSimpleName()+" ";
            }
            constructorString+=")";
            System.out.println(constructorString);
        }
    }

    private static void InvokeMethod(Class userBeanClazz){
        System.out.println("执行注解的方法：");
        Method[] methods=userBeanClazz.getDeclaredMethods();
        for (int i = 0; i <methods.length ; i++) {
            if(methods[i].isAnnotationPresent(Invoke.class)){
                if(Modifier.isStatic(methods[i].getModifiers())){
                    try {
                        methods[i].invoke(null,"zxf");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }else{
                    Class[] param={String.class,long.class};
                    try {
                        Constructor constructor=userBeanClazz.getConstructor(param);
                        Object obj=constructor.newInstance("臧雪峰",12);
                        if(Modifier.isPrivate(methods[i].getModifiers())){
                            methods[i].setAccessible(true);
                        }
                        methods[i].invoke(obj);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }
}
