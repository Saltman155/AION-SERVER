package com.superywd.aion.commons.network.dispatcher;

import com.superywd.aion.commons.network.AConnection;
import com.superywd.aion.commons.network.Acceptor;
import com.superywd.aion.commons.network.DisConnectionTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * 连接或读写事件处理调度器
 * @author: saltman155
 * @date: 2019/3/22 16:17
 */
public class AcceptReadWriteDispatcher extends Dispatcher {

    private static final Logger logger = LoggerFactory.getLogger(AcceptReadWriteDispatcher.class);

    /**等待被关闭连接的容器*/
    private final List<AConnection> pendingClose = new ArrayList<>();

    /**
     * 创建一个调度器，并指定它的名称以及工作线程池
     *
     * @param name     调度器名称
     * @param executor 内部工作线程池
     * @throws IOException 随便抛抛
     */
    public AcceptReadWriteDispatcher(String name, Executor executor) throws IOException {
        super(name, executor);

    }

    @Override
    protected void dispatch() throws IOException {
        //获取已经有事件被准备处理的注册通道数
        int selected = selector.select();
        //每次处理前把那些等待被移除的连接移除
        processPendingClose();
        if(selected > 0){
            Iterator<SelectionKey> selectedKeys = this.selector.selectedKeys().iterator();
            while(selectedKeys.hasNext()){
                SelectionKey key = selectedKeys.next();
                selectedKeys.remove();
                switch (key.readyOps()){
                    //请求连接事件
                    case SelectionKey.OP_ACCEPT: this.accept(key); break;
                    //请求读事件
                    case SelectionKey.OP_READ: this.read(key); break;
                    //请求写事件
                    case SelectionKey.OP_WRITE: this.write(key); break;
                    //请求读+写事件
                    case SelectionKey.OP_READ | SelectionKey.OP_WRITE:
                        this.read(key);
                        if(key.isValid()){
                            this.write(key);
                        }
                        break;
                    default:break;
                }
            }
        }

    }

    /**
     * 接受一个连接请求
     * @param key
     */
    protected void accept(SelectionKey key){
        try {
            //取出通道注册时设置的附件（连接请求处理对象），调用它的连接请求处理方法
            ((Acceptor)key.attachment()).accept(key);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
    }

    /**
     * 处理读事件
     * @param key
     */
    protected void read(SelectionKey key){
        //将这个连接关联的那些对象都拿出来
        SocketChannel socketChannel = (SocketChannel) key.channel();
        AConnection con = (AConnection) key.attachment();
        ByteBuffer readBuffer = con.readBuffer;
        int readLen;
        try {
            readLen = socketChannel.read(readBuffer);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
            return;
        }
        //如果没有读到数据，说明远程连接已经关闭了，那我们也关闭
        if(readLen == -1){
            closeConnection(con);
            return;
        }
        if(readLen == 0){
            return;
        }
        //这个nio的buffer模型设计真是太反人类了...
        readBuffer.flip();
        //每次读两个字节解析，前两个字节转换的值表示数据包大小
        while(readBuffer.remaining() > 2 &&
                readBuffer.remaining() >= readBuffer.getShort(readBuffer.position())){
            if(!parse(con,readBuffer)){
                closeConnection(con);
                return;
            }
        }
        if(readBuffer.hasRemaining()){
            con.readBuffer.compact();
        }else{
            con.readBuffer.clear();
        }

    }

    /**
     * 处理写事件
     * @param key
     */
    protected void write(SelectionKey key){
        SocketChannel socketChannel = (SocketChannel) key.channel();
        AConnection connection = (AConnection) key.attachment();
        int writeLen;
        ByteBuffer writeBuffer = connection.writeBuffer;
        if(writeBuffer.hasRemaining()){
            try {
                //把数据写进去
                writeLen = socketChannel.write(writeBuffer);
            } catch (IOException e) {
                //异常直接关闭
                closeConnection(connection);
                logger.error(e.getMessage(),e);
                return;
            }
            if(writeLen == 0){
                logger.info("读写线程向远程ip {} 写入了0个字节？",connection.getIp());
                return;
            }
            //如果缓冲区中还有数据没有被发送，直接返回
            if(writeBuffer.hasRemaining()){
                return;
            }
            while(true){
                //清空一下缓冲区
                writeBuffer.clear();
                //把连接里待发送的数据包的数据，都写到缓冲区里
                boolean writeResult = connection.writePackData(writeBuffer);
                if(!writeResult){
                    //TODO: 我不是很懂，为什么这里要把缓冲区给设置成这样
                    writeBuffer.limit(0);
                    break;
                }
                try{
                    writeLen = socketChannel.write(writeBuffer);
                }catch (Exception e){
                    closeConnection(connection);
                    return;
                }
                if(writeLen == 0){
                    logger.info("读写线程向远程ip {} 写入了0个字节？",connection.getIp());
                    return;
                }
                //如果还有没写完的，也给返回了？我真不知道是为什么
                if(writeBuffer.hasRemaining()){
                    return;
                }
            }
            //这个通道已经写完了所有的数据，我们不在关注它的写入操作了，取消对它的写入事件监听
            key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);
            //如果它是个即将被关闭的连接，关闭它
            if(connection.isPendingClose()){
                closeConnection(connection);
            }

        }
    }

    /**解析读到的数据包*/
    protected boolean parse(AConnection con, ByteBuffer buffer){
        short size;
        try{
            size = buffer.getShort();
            size -= size > 1 ? 2 : 0;
            //创建实际数据的视图
            ByteBuffer tmp =  (ByteBuffer) buffer.slice().limit(size);
            tmp.order(ByteOrder.LITTLE_ENDIAN);
            return con.processData(tmp);
        }catch (IllegalArgumentException e){
            logger.warn("解析错误！");
            return false;
        }
    }

    /**
     * 准备关闭连接
     * 将一个连接放入关闭队列中，它稍后将在调度方法 dispatch() 执行时被处理
     * @param connection    待关闭的连接
     */
    public void pendingCloseConnection(AConnection connection) {
        synchronized (pendingClose){
            pendingClose.add(connection);
        }
    }

    /**
     * 关闭并移除待关闭连接队列里的所有连接
     */
    private void processPendingClose() {
        synchronized (pendingClose) {
            for (AConnection connection : pendingClose) {
                closeConnection(connection);
            }
            pendingClose.clear();
        }
    }

    /**
     * 关闭连接
     *      注意：这里的连接可能已经被关闭了
     * @param connection    需要被关闭的连接
     */
    protected void closeConnection(AConnection connection){
        //关闭连接 对于第一次执行关闭操作的，执行它的销毁清理方法
        if(connection.onlyClose()){
            workPool.execute(new DisConnectionTask(connection));
        }
    }

}
