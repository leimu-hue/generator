package com.leimu.utils;

import com.leimu.constant.Constant;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class LoadJarUtils {

    /**
     * 动态加载jar包
     *
     * @param jarFile
     * @throws NoSuchMethodException
     */
    public static void loadJar(String jarFile) throws NoSuchMethodException {
        File file = new File(jarFile);
        //首先获取到系统类加载器
        URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        //获取到加载jar到扫描路径的方法
        Method addURL = null;
        Class temp = classLoader.getClass();
        if ("URLClassLoader".equals(temp.getSuperclass().getSimpleName())) {
            addURL = findSuperClassMethod(temp.getSuperclass());
        } else {
            addURL = findSuperClassMethod(temp);
        }
        if (addURL == null) {
            throw new NoSuchMethodException(Constant.loadJarNullMessage);
        }
        //设置访问权限
        if (!addURL.isAccessible()) {
            addURL.setAccessible(true);
        }
        //调用方法加载
        try {
            addURL.invoke(classLoader, file.toURI().toURL());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 寻找方法
     *
     * @param c
     * @return
     * @throws NoSuchMethodException
     */
    public static Method findSuperClassMethod(Class c) throws NoSuchMethodException {
        return c.getDeclaredMethod(Constant.loadJarMethodName, URL.class);
    }

}
