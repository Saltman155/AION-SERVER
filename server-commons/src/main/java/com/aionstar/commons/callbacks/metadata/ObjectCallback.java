package com.aionstar.commons.callbacks.metadata;


import com.aionstar.commons.callbacks.Callback;

import java.lang.annotation.*;



/**
 *  用于标记可增强方法或类的注解。本注解不允许被使用静态，本机和抽象方法上，因为这些方法不需要被增强
 * @author: saltman155
 * @date: 2018/10/21 18:52
 */


@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ObjectCallback {

    Class<? extends Callback> value();
}
