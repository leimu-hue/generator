package com.leimu;

import com.leimu.constant.ConstantTest;
import com.leimu.utils.LoadJar;
import org.junit.Test;

public class testLoadJar {

    /**
     * 进行测试
     */
    @Test
    public void testLoadJar(){
        try {
            LoadJar.loadJar(ConstantTest.test);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
