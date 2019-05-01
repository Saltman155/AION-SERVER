package com.superywd.aion.commons.script.metadata;

import java.lang.annotation.*;

/**
 * 这个注解用于加在脚本类的方法层级，当这个脚本Class被卸载前
 * 被这个注解所修饰的方法将会运行（可以用于进行一些清理操作）
 * @author 迷宫的中心
 * @date 2019/4/18 14:42
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface OnClassUnLoad {
}
