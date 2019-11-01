package com.saltman155.aion.game.commons.callbacks.metadata;

import com.saltman155.aion.game.commons.callbacks.Callback;

public @interface GlobalCallback {

    /**
     * 返回将用作侦听器的回调类
     * @return
     */
    Class<? extends Callback> value();

}
