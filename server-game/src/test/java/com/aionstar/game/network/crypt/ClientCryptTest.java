package com.aionstar.game.network.crypt;

import com.aionstar.game.Crypt;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class ClientCryptTest {

    @Test
    public void enableKey() {
        ClientCrypt crypt1 = new ClientCrypt();
        Crypt crypt2 = new Crypt();

        crypt1.enableKey();
        crypt2.enableKey();

        byte[] data1 = new byte[]{
                0x01,0x02,0x03,0x04,0x05,
                0x06,0x07,0x08,0x09,0x10,
                0x11,0x12,0x13,0x14,0x15};
        byte[] data2 = new byte[]{
                0x01,0x02,0x03,0x04,0x05,
                0x06,0x07,0x08,0x09,0x10,
                0x11,0x12,0x13,0x14,0x15};

        ByteBuf buffer1 = Unpooled.copiedBuffer(data1);
        ByteBuffer buffer2 = ByteBuffer.wrap(data2);

        crypt1.encrypt(buffer1);
        crypt1.encrypt(buffer1);

        buffer2.position(0).limit(15);

        crypt2.encrypt(buffer2);
        crypt2.encrypt(buffer2);

        System.out.println(Arrays.toString(buffer1.array()));
        System.out.println(Arrays.toString(data2));
    }

    @Test
    public void decrypt() {
    }

    @Test
    public void encrypt() {
    }
}
