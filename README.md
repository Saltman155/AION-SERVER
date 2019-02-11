## AION-SERVER

aion3.6游戏服务端 基于AL3.9深度定制

整个后端分为四个模块，即

 * server-commons   后端公共包
 * server-game      游戏核心逻辑服务器
 * server-login     游戏登录服务器
 * server-chat      游戏聊天服务器
 
 
 
 
 **相较于AL的改进**
 * 数据持久层换用MyBatis框架来代替原先直接的jdbc操作，同时用Hikari连接池替换老的BoneCP连接池
 * 加入Redis缓存数据库替换老的直接读入内存的设置
 * 后期尝试整体迁移到IOC框架中（spring）
 * 部分网络通讯逻辑用Netty替换老的原生NIO
 
 
 
 **持续更新**
 * 2019/02/10 首次上传
=======
# AION-SERVER
aion3.6游戏服务端 基于al3.9深度定制