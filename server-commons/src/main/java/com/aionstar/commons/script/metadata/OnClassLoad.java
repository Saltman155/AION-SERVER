package com.aionstar.commons.script.metadata;

import java.lang.annotation.*;

/**
 * 这个注解用于加在脚本类的方法层级，当这个脚本类被编译加载后，
 * 被这个注解所修饰的方法将会运行
 * @author: saltman155
 * @date: 2019/4/18 14:42
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface OnClassLoad {
}
