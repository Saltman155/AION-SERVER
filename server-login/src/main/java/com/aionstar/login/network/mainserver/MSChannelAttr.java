package com.aionstar.login.network.mainserver;

import com.aionstar.commons.network.BaseChannelAttr;
import io.netty.util.AttributeKey;

/**
 * 表示一些channel的属性
 * @author saltman155
 * @date 2019/11/16 20:55
 */

public class MSChannelAttr extends BaseChannelAttr {

    /**当前会话的状态*/
    public static final AttributeKey<InnerSessionState> M_SESSION_STATE = AttributeKey.newInstance("mSessionState");

}
