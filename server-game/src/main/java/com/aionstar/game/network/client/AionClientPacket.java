package com.aionstar.game.network.client;

import com.aionstar.commons.network.packet.ClientPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * 客户端发送给主服务端的封包
 * 与 ${@link ClientPacket} 没有区别，唯一的区别就是 对部分方法进行了重写
 * @author saltman155
 * @date 2020/1/24 0:55
 */

public abstract class AionClientPacket extends ClientPacket {

    private static final Logger logger = LoggerFactory.getLogger(AionClientPacket.class);

    /**
     * 保存了该数据包所有正确的状态
     * 说明：请保证该列表会被浅克隆到各个数据包中，否则可能大量数据包会造成内存爆炸
     * */
    protected Set<ClientChannelAttr.SessionState> validState;

    protected AionClientPacket(byte opcode) {
        super(opcode, null, null);
        buildValidState();
    }

    /**
     * 数据包结构复制
     * @return          新的封包结构对象
     */
    public AionClientPacket clonePacket(){
        try {
            return (AionClientPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    @Override
    public void run() {
        try{
            //这里再进行一次连接状态与数据包的可用性判断，更健壮
            if(isValid()) {
                handler();
            }else{
                logger.error("opcode为 {} 的数据包状态无法验证成功！",getOpcode());
            }
        }catch (Exception e){
            logger.error("opcode为 {} 的数据包处理发生异常！", getOpcode());
            logger.error(e.getMessage(),e);
        }
    }


    /**
     * 判断数据包对应的当前连接状态是否是正常的
     * @return      是否正常
     */
    private boolean isValid(){
        ClientChannelAttr.SessionState state = channel.attr(ClientChannelAttr.SESSION_STATE).get();
        return validState.contains(state);
    }

    /**
     * 获取该数据包对应的可用连接状态
     * @return      可用连接状态
     */
    public Set<ClientChannelAttr.SessionState> getValidState(){
        return this.validState;
    }

    /**
     * 构建数据包的可用状态
     * 这个方法理论上只会被执行一次（因为上面调用的构造器方法只会被执行一次）
     * 后续该类创建对象会通过clone方法进行
     */
    protected abstract void buildValidState();

}
