package com.aionstar.game.network.loginserver;

import com.aionstar.commons.network.BaseChannelAttr;
import io.netty.util.AttributeKey;

/**
 * @author saltman155
 * @date 2020/1/18 19:17
 */

public class LSChannelAttr extends BaseChannelAttr {

    /**当前会话的状态*/
    public static final AttributeKey<InnerSessionState> LS_SESSION_STATE = AttributeKey.newInstance("lsSessionState");

}
