package com.code.springbootmultisource.common.annotation;

import java.lang.annotation.*;

/**
 * 多数据源标识注解
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DataSource {

    String name() default "";
}
