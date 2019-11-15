package cn.moonlight035.chatserver.inforequest.chathistory.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @author jing.liu14@ucarinc.com
 * @date 2019/11/12
 * @description:
 */
@Data
@ToString
@AllArgsConstructor
public class UserChatForm {
    String fromAccount;
    String toAccount;
    String content;
    Integer status;
}
