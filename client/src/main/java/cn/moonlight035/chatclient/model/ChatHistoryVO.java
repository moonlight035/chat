package cn.moonlight035.chatclient.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ChatHistoryVO {
    String fromAccount;
    String toAccount;
    String content;
    String time;
}
