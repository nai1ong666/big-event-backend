package com.itheima.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD) // 只能写在方法上
@Retention(RetentionPolicy.RUNTIME) // 运行时保留，拦截器可以读取
public @interface RequireAdmin {
}

