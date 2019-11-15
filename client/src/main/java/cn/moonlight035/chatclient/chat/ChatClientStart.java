package cn.moonlight035.chatclient.chat;

import cn.moonlight035.chatclient.utils.msg.Msg;
import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.FutureTask;

/**
 * @author jing.liu14@ucarinc.com
 * @date 2019/11/11
 * @description:
 */
@Component
public class ChatClientStart {

    private EventLoopGroup eventExecutors;

    private Bootstrap bootstrap;

    private Channel channel;

    private String ip;


    @Autowired
    private ChatClientInitializer chatClientInitializer;

    public ChatClientStart() throws InterruptedException {
        eventExecutors = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        ip="122.51.136.185";
    }

    private void start() throws InterruptedException {
        try {
            bootstrap.group(eventExecutors).channel(NioSocketChannel.class).
                    handler(chatClientInitializer);
            ChannelFuture channelFuture = bootstrap.connect("122.51.136.185",8898).sync();
            channel = channelFuture.channel();
            channelFuture.channel().closeFuture().sync();
        } finally {
            eventExecutors.shutdownGracefully();
        }
    }

    public void syncStart(){
        FutureTask<Boolean> futureTask = new FutureTask(()->{
            start();
            return true;
        });
        new Thread(futureTask).start();
    }

    public void send(Msg msg){
        channel.writeAndFlush(JSON.toJSONString(msg)+"\n");
    }
}
