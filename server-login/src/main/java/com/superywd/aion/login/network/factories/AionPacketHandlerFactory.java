package com.superywd.aion.login.network.factories;

import com.superywd.aion.login.network.aion.ClientPacket;
import com.superywd.aion.login.network.aion.LoginConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

import static com.superywd.aion.login.network.aion.LoginConnection.State;

/**
 * 数据包解析工厂，从二进制流中解析出真实数据
 */
public class AionPacketHandlerFactory {

    private static final Logger logger = LoggerFactory.getLogger(AionPacketHandlerFactory.class);

    public static ClientPacket handle(ByteBuffer buffer, LoginConnection connection){
        ClientPacket clientMessage = null;
        State state = connection.getState();
        //紧跟代表数据包长度的后一个字节，意义是这个数据包的类型
        int type = buffer.get() & 0xff;
        switch (state){
            case LOGINED:{
                switch (type){
                    case 0x07: break;
                    case 0x08: break;
                    default: unknownPacket(state,type);
                }
                break;
            }
            case AUTHENTIC:{
                switch (type){
                    case 0x0B: break;
                    default: unknownPacket(state,type);
                }
                break;
            }
            case CONNECTED:{
                switch (type){
                    case 0x05:
//                        clientMessage = new CM_SERVER_LIST(data, client);
                        break;
                    case 0x02:
//                        clientMessage = new CM_PLAY(data, client);
                        break;
                    default:
                        unknownPacket(state, type);
                }
                break;
            }
        }
        return null;

    }

    private static void unknownPacket(State state, int id){
        logger.warn("状态为{}的连接,收到了一个无法识别的数据包！类型id为 {}",state,id);
    }



}
