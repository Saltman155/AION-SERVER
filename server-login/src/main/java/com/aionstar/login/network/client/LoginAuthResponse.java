package com.aionstar.login.network.client;

/**
 * 该枚举类列举了登录时各种翻皮水的状态
 * （不同的状态码给客户端后，客户端会做出不同的 响应/提示信息，所以应该根据具体的情况来正确的反馈，以免引起玩家误解）
 * @author saltman155
 * @date 2019/10/20 20:30
 */

public enum LoginAuthResponse {

    // 正常状态（但这个状态不会在与客户端的沟通中被使用 -_-||）
    AUTHED(0),
    // 系统翻皮水
    SYSTEM_ERROR(1),
    // 密码错误（类型一）
    INVALID_PASSWORD(2),
    // 密码错误（类型二）
    INVALID_PASSWORD2(3),
    // 无法载入账户信息
    FAILED_ACCOUNT_INFO(4),
    // 无法载入国家/地区 编号
    FAILED_SOCIAL_NUMBER(5),
    // 当前登录服务器没有被注册的游戏服务器
    NO_GS_REGISTERED(6),
    // 账户已经登录咧
    ALREADY_LOGGED_IN(7),
    // 试图访问的服务器已经 关闭&翻皮水 了
    SERVER_DOWN(8),
    // 第三种登录错误
    INVALID_PASSWORD3(9),
    // 没有这个账号
    NO_SUCH_ACCOUNT(10),
    // 连接中断
    DISCONNECTED(11),
    // 没到玩这个游戏的年龄（惊了）
    AGE_LIMIT(12),
    // 检测到双重登录
    ALREADY_LOGGED_IN2(13),
    // 你已经登录了
    ALREADY_LOGGED_IN3(14),
    // 当前用户太多了，所以无法登录
    SERVER_FULL(15),
    // 服务器正在初始化，请稍后登录
    GM_ONLY(16),
    // 需要修改密码后再登录
    ERROR_17(17),
    // 已经使用了所有的游戏时间
    TIME_EXPIRED(18),
    // 已经用完了分配的时间，此账户上没有剩余时间
    TIME_EXPIRED2(19),
    // 系统异常
    SYSTEM_ERROR2(20),
    // ip已经在使用中
    ALREADY_USED_IP(21),
    // 无法通过该ip登录
    BAN_IP(22);


    private int id;

    LoginAuthResponse(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
