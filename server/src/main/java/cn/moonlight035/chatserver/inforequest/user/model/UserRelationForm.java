package cn.moonlight035.chatserver.inforequest.user.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserRelationForm {
    String fromAccount;
    String toAccount;
}
