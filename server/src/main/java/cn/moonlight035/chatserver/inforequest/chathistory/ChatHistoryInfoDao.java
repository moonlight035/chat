package cn.moonlight035.chatserver.inforequest.chathistory;

import cn.moonlight035.chatserver.inforequest.chathistory.model.UserChatForm;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author jing.liu14@ucarinc.com
 * @date 2019/11/12
 * @description:
 */
@Mapper
public interface ChatHistoryInfoDao {

    Integer insertHistory(UserChatForm userChatForm);
}
