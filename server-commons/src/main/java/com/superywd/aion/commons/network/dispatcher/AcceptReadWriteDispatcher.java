package com.superywd.aion.commons.network.dispatcher;

import com.superywd.aion.commons.network.AConnection;
import com.superywd.aion.commons.network.Acceptor;
import com.superywd.aion.commons.network.DisConnectionTask;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * 这个调度器既处理登录事件，又处理通讯时的数据读写事件
 * @author: 迷宫的中心
 * @date: 2019/3/22 16:17
 */
public class AcceptReadWriteDispatcher extends Dispatcher {


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
                    case SelectionKey.OP_ACCEPT:
                        break;
                    case SelectionKey.OP_READ:
                        break;
                    case SelectionKey.OP_WRITE:
                        break;
                    case SelectionKey.OP_READ | SelectionKey.OP_WRITE:
                        break;
                    default:break;
                }
            }
        }

    }

    protected void accept(SelectionKey key){
        try {
            //取出通道注册时设置的附件（连接请求处理对象），调用它的连接请求处理方法
            ((Acceptor)key.attachment()).accept(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 准备关闭连接
     * 将一个连接放入关闭队列中，它稍后将在调度方法 dispatch() 执行时关闭
     * @param connection    待关闭的连接
     */
    protected void pendingCloseConnection(AConnection connection) {
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
