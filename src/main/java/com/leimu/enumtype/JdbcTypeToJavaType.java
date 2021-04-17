package com.leimu.enumtype;

import java.math.BigDecimal;
import java.util.Date;
import java.sql.Blob;

public enum JdbcTypeToJavaType {

    INTEGER_1(Integer.class,"TINYINT"),
    INTEGER_2(Integer.class,"SMALLINT"),
    INTEGER_3(Integer.class,"integer"),
    LONG(Long.class,"BIGINT"),
    FLOAT(Float.class,"FLOAT"),
    DOUBLE(Double.class,"DOUBLE"),
    NUMERIC(BigDecimal.class,"NUMERIC"),
    STRING_1(String.class,"CHAR"),
    BOOLEAN(Boolean.class,"BIT"),
    STRING_2(String.class,"VARCHAR"),
    DATE(Date.class,"DATE"),
    TIME(Date.class,"TIME"),
    TIMESTAMP(Date.class,"TIMESTAMP"),
    VARBINARY(Byte[].class,"VARBINARY"),
    VARBINARY_1(Byte[].class,"BLOB"),
    CLOB(String.class,"CLOB"),
    BLOB(Blob.class,"BLOB");


    private Class<?> javaType;

    private String jdbcType;

    JdbcTypeToJavaType(Class javaType,String jdbcType){
        this.javaType = javaType;
        this.jdbcType = jdbcType;
    }


}
