package com.monchickey.classutil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 基于Java反射用到操作类和对象的工具
 * @author monchickey
 *
 */

public class ClassUtil {
    
    /**
     * 获取并打印类的方法信息
     * @param obj
     */
    @SuppressWarnings("rawtypes")
    public static void printClassMethodMessage(Object obj) {
        //获取对象的类类型
        Class<? extends Object> c = obj.getClass();
        //获取类名称
        System.out.println("类的名称是：" + c.getName());
        //获取方法
        //一个成员方法就是一个Method对象
        //获取所有的public类型的方法，包括从父类继承而来的
        //Method[] ms = c.getMethods();
        //获取对象本类中所有的方法，不包括父类的
        Method[] ms = c.getDeclaredMethods();
        for(int i = 0;i < ms.length;i++) {
            Class<?> returnType = ms[i].getReturnType();    //获取方法返回值类型的类类型
            System.out.print(returnType.getName() + " ");
            System.out.print(ms[i].getName() + "(");    //方法名称
            //获取参数类型
            //得到参数列表的类类型
            Class[] paramTypes = ms[i].getParameterTypes();
            for(Class class1:paramTypes) {
                System.out.print(class1.getName() + ",");
            }
            
            System.out.println(")");
        }
    }

    
    /**
     * 获取并打印类的成员信息
     * @param obj
     */
    public static void printClassFieldMessage(Object obj) {
        Class<? extends Object> c = obj.getClass();
        //获取所有public的成员变量信息
        //Field[] fs = c.getFields();
        //获取所有自己声明的成员变量的信息
        Field[] fs = c.getDeclaredFields();
        
        for(Field field:fs) {
            //得到成员变量的类类型
            Class<?> fieldType = field.getType();
            //得到成员变量的类类型的名称
            String typeName = fieldType.getName();
            //得到成员变量的名称
            String fieldName = field.getName();
            
            System.out.println(typeName + " " + fieldName);
        }
    }
    
    
    /**
     * 获取并打印对象所属类构造函数的信息
     * @param obj
     */
    @SuppressWarnings("rawtypes")
    public static void printConMessage(Object obj) {
        Class<? extends Object> c = obj.getClass();
        //构造方法也是对象 java.lang.Constructor中封装了构造方法的信息
        //获取public的构造方法
        //Constructor[] cs = c.getConstructors();
        //获取自己声明的所有的构造方法
        Constructor[] cs = c.getDeclaredConstructors();
        for(Constructor<?> constructor:cs) {
            System.out.print(constructor.getName() + "(");
            //获取参数列表
            //得到参数类类型的数组列表
            Class[] paramTypes = constructor.getParameterTypes();
            for(Class<?> class1:paramTypes) {
                System.out.print(class1.getName() + ",");
            }
            System.out.println(")");
        }
        
    }
    
}
