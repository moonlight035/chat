package cn.moonlight035.chatclient.chat;

import cn.moonlight035.chatclient.frame.Client;
import cn.moonlight035.chatclient.utils.msg.Msg;
import cn.moonlight035.chatclient.utils.msg.MsgStatus;
import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jing.liu14@ucarinc.com
 * @date 2019/11/11
 * @description:
 */
@Component
@ChannelHandler.Sharable
public class ChatHandler extends SimpleChannelInboundHandler<String> {

    @Autowired
    private Client client;



    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Msg getMsg = JSON.parseObject(msg,Msg.class);
        if(getMsg.getStatus()== MsgStatus.SEND_MSG.getStatus()){
            Map<String, List<String>> map = client.getMap();
            if(map.get(getMsg.getFromAcount())!=null){
                List<String> list = map.get(getMsg.getFromAcount());
                list.add(getMsg.getContent());
            }
            else{
                List<String> list = new ArrayList<>();
                list.add(getMsg.getContent());
                map.put(getMsg.getFromAcount(),list);
            }
            client.freshChat(getMsg.getFromAcount());
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Msg accountMsg = new Msg();
        accountMsg.setStatus(MsgStatus.SEND_ACCOUNT.getStatus());
        accountMsg.setFromAcount(client.getUserAccount());
        ctx.writeAndFlush(JSON.toJSONString(accountMsg)+"\n");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause);
        ctx.close();
    }
}
