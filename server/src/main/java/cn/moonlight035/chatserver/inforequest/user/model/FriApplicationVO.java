package cn.moonlight035.chatserver.inforequest.user.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FriApplicationVO {
    String fromAccount;
    String toAccount;
    String remark;
    Integer status;
    String time;
}
