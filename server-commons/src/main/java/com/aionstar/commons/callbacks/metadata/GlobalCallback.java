package com.aionstar.commons.callbacks.metadata;

import com.aionstar.commons.callbacks.Callback;

public @interface GlobalCallback {

    /**
     * 返回将用作侦听器的回调类
     * @return
     */
    Class<? extends Callback> value();

}
