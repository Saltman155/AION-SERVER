package com.aionstar.chat.network.mainserver;

import com.aionstar.commons.network.BaseChannelAttr;
import io.netty.util.AttributeKey;

public class MSChannelAttr extends BaseChannelAttr {

    /**当前会话的状态*/
    public static final AttributeKey<InnerSessionState> M_SESSION_STATE = AttributeKey.newInstance("mSessionState");

}
