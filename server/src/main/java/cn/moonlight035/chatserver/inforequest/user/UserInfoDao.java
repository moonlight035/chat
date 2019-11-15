package cn.moonlight035.chatserver.inforequest.user;

import cn.moonlight035.chatserver.inforequest.user.model.UserInfoForm;
import cn.moonlight035.chatserver.inforequest.user.model.UserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author jing.liu14@ucarinc.com
 * @date 2019/11/11
 * @description:
 */
@Mapper
public interface UserInfoDao {

    /**查询用户信息*/
    List<UserVO> getUserInfo(UserInfoForm userInfoForm);

    /**登录*/
    Integer login(UserInfoForm userInfoForm);

    /**获取好友信息*/
    List<UserVO> getFriendInfo(String id);

    /**添加用户*/
    Integer addUser(UserInfoForm userInfoForm);

    /**删除用户*/
    Integer deleteUser(String account);
}
