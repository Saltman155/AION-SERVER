package com.aionstar.game;

import static org.junit.Assert.assertTrue;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() throws UnsupportedEncodingException {
        ByteBuf buf = Unpooled.buffer(1024);
        String str = "a样文" + "\u0000";
        ByteBuffer buffer = ByteBuffer.allocate(1024).order(ByteOrder.LITTLE_ENDIAN);
        for(int i = 0;i<str.length();i++){
            buf.writeShortLE(str.charAt(i));
        }

        System.out.println(buf.writerIndex());
        System.out.println(Arrays.toString(buf.array()));
        char c;
        while((c = (char) buf.readShortLE()) != '\u0000'){
            System.out.print(c);
        }
    }
}
