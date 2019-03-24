package com.superywd.aion.commons.network.dispatcher;

import com.superywd.aion.commons.network.Acceptor;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.util.Iterator;

/**
 * 连接事件处理调度器，这个调度器只能处理连接请求
 * @author: 迷宫的中心
 * @date: 2019/3/22 16:21
 */
public class AcceptDispatcher extends Dispatcher {


    /**
     * 创建一个调度器，并指定它的名称以及工作线程池
     *
     * @param name     调度器名称
     * @throws IOException 随便抛抛
     */
    public AcceptDispatcher(String name) throws IOException {
        super(name, null);
    }


    @Override
    protected void dispatch() throws IOException {
        //如果存在已经为I/O操作准备就绪的通道，就开始处理
        if(this.selector.select() != 0){
            Iterator<SelectionKey> selectionKeys = this.selector.selectedKeys().iterator();
            while(selectionKeys.hasNext()){
                SelectionKey key = selectionKeys.next();
                //从待处理返回键集中移除要处理的键
                selectionKeys.remove();
                //然后处理这个返回键
                if(key.isValid()){
                    accept(key);
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

}
