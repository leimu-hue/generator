package com.leimu;

import org.junit.Test;

public class TestOtherUtils {

    @Test
    public void test() {
        Class<?> t = String.class;
        System.out.println(t.getPackage().getName());
        System.out.println(t.getName());
    }

}
