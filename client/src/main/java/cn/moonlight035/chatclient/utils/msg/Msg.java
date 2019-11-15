package cn.moonlight035.chatclient.utils.msg;

import lombok.Data;
import lombok.ToString;

/**
 * @author jing.liu14@ucarinc.com
 * @date 2019/11/12
 * @description:
 */
@Data
@ToString
public class Msg {
    private String fromAcount;
    private String toAccount;
    private String content;
    private String time;
    private Integer status;
}
