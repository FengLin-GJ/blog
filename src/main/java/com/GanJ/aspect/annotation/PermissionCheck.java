package com.GanJ.aspect.annotation;

import java.lang.annotation.*;

/**
 * @author: GanJ
 * @Date: 2021/21/1 13:25
 * Describe:注解类
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PermissionCheck {

    String value();

}
