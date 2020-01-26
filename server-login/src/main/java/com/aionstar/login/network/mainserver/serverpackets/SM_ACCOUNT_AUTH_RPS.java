package com.aionstar.login.network.mainserver.serverpackets;

import com.aionstar.commons.network.packet.ServerPacket;
import com.aionstar.login.model.entity.Account;
import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;

public class SM_ACCOUNT_AUTH_RPS extends ServerPacket {

    private static final byte OPCODE = 0x01;

    /**账号ID*/
    private final int accountId;
    /**是否验证成功*/
    private final boolean ok;
    /**账号信息*/
    private Account account;

    /**
     * 失败数据包的构造器
     * @param accountId     用户id
     */
    public SM_ACCOUNT_AUTH_RPS(int accountId){
        super(OPCODE);
        this.accountId = accountId;
        this.ok = false;
    }

    /**
     * 成功的数据包的构造器
     * @param account       用户id
     * @param ok            是否成功
     */
    public SM_ACCOUNT_AUTH_RPS(Account account, boolean ok){
        super(OPCODE);
        this.accountId =account.getId();
        this.ok = ok;
        this.account = account;
    }

    @Override
    protected void appendBody(ByteBuf buf) {
        buf.writeIntLE(accountId);
        buf.writeByte(ok ? 0x01 : 0x00);
        //如果验证通过了，就发送其他的数据给服务端
        if(ok){
            //写入name
            byte[] name = account.getName().getBytes(CharsetUtil.UTF_8);
            byte len = (byte) name.length;
            buf.writeByte(len);
            buf.writeBytes(name);                                                   //账户名称
            buf.writeLongLE(account.getAccountTime().getAccumulatedOnlineTime());   //累计在线时间
            buf.writeLongLE(account.getAccountTime().getAccumulatedRestTime());     //累计休息时间
            buf.writeByte(account.getAccessLevel());                                //访问权限
            buf.writeByte(account.getMembership());                                 //会员等级
            buf.writeLongLE(account.getToll());                                     //虚拟货币数量
        }
    }

}
