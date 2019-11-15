package cn.moonlight035.chatserver.chat;

import cn.moonlight035.chatserver.inforequest.chathistory.ChatHistoryInfoDao;
import cn.moonlight035.chatserver.inforequest.chathistory.model.UserChatForm;
import cn.moonlight035.chatserver.utils.msg.Msg;
import cn.moonlight035.chatserver.utils.msg.MsgStatus;
import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
    private ChatHistoryInfoDao chatHistoryInfoDao;

    private  static Map<String, Channel> map = new ConcurrentHashMap<>();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        Msg getMsg = JSON.parseObject(msg,Msg.class);
        //获取账号信息
        if(getMsg.getStatus()== MsgStatus.SEND_ACCOUNT.getStatus()){
            map.put(getMsg.getFromAcount(),ctx.channel());
            System.out.println(getMsg.getFromAcount()+"用户上线");
            return ;
        }
        //发送消息
        if(getMsg.getStatus()==MsgStatus.SEND_MSG.getStatus()){
            String fromAccount = getMsg.getFromAcount();
            String toAccount = getMsg.getToAccount();
            String content = new String(getMsg.getContent().getBytes(),"gbk");
            UserChatForm form;
            if(map.get(toAccount)!=null){
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                getMsg.setTime(df.format(new Date()));
                map.get(toAccount).writeAndFlush(JSON.toJSONString(getMsg)+"\n");
                form = new UserChatForm(fromAccount,toAccount,content,1);
            }
            else form = new UserChatForm(fromAccount,toAccount,content,0);
            chatHistoryInfoDao.insertHistory(form);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        for(String account:map.keySet()){
            if(map.get(account)==ctx.channel()){
                map.remove(account);
                System.out.println(account+"用户下线");
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
