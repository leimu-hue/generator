package com.leimu.enumtype;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * 存放了数据库与java类型的对应
 */
public enum JdbcTypeToJavaType {

    TINYINT(Integer.class, "TINYINT(\\(.*?\\))*"),
    SMALLINT(Integer.class, "SMALLINT(\\(.*?\\))*"),
    INTEGER(Integer.class, "integer(\\(.*?\\))*"),
    INT(Integer.class, "INT(\\(.*?\\))*"),
    DECIMAL(BigDecimal.class, "decimal(\\(.*?\\))*"),
    BIGINT(Long.class, "BIGINT"),
    REAL(Float.class, "REAL"),
    FLOAT(Float.class, "FLOAT(\\(.*?\\))*"),
    DOUBLE(Double.class, "DOUBLE(\\(.*?\\))*"),
    NUMERIC(BigDecimal.class, "NUMERIC"),
    CHAR(String.class, "CHAR(\\(.*?\\))*"),
    BOOLEAN(Boolean.class, "BIT"),
    VARCHAR(String.class, "VARCHAR(\\(.*?\\))*"),
    DATE(Date.class, "DATE"),
    TIME(Time.class, "TIME"),
    DATETIME(Date.class, "DATETIME"),
    TIMESTAMP(Timestamp.class, "TIMESTAMP"),
    BINARY(Byte[].class, "BINARY"),
    LINGERINGLY(Byte[].class, "LONGVARBINARY"),
    ARRAY(Array.class, "ARRAY"),
    STRUCT(Struct.class, "STRUCT"),
    VARBINARY(Byte[].class, "VARBINARY"),
    CLOB(Clob.class, "CLOB"),
    BLOB(Blob.class, "BLOB"),
    REF(Ref.class, "REF"),
    YEAR(Date.class, "YEAR");


    private final Class<?> javaType;

    private final String jdbcType;

    JdbcTypeToJavaType(Class javaType, String jdbcType) {
        this.javaType = javaType;
        this.jdbcType = jdbcType;
    }

    /**
     * 进行数据类型的转换
     *
     * @param dataType 转换数据类型
     * @return 返回对应的类型数据, 如果找不到对应数据类型，就一律返回String.class
     */
    public static Class<?> toConvertJdbcTypeToJavaType(String dataType) {
        dataType = dataType.trim();
        JdbcTypeToJavaType[] values = JdbcTypeToJavaType.values();
        for (JdbcTypeToJavaType data : values) {
            String jdbcType = data.jdbcType;
            Pattern compile = Pattern.compile(jdbcType, Pattern.CASE_INSENSITIVE);
            if (compile.matcher(dataType).matches()) {
                return data.javaType;
            }
        }
        return String.class;
    }


}
