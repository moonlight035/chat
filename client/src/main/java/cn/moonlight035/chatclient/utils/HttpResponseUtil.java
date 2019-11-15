package cn.moonlight035.chatclient.utils;

import cn.moonlight035.chatclient.model.UserVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author jing.liu14@ucarinc.com
 * @date 2019/11/12
 * @description:
 */
public class HttpResponseUtil {
    public static List<UserVO> getUserInfo(Object o){
        if(o==null) return new ArrayList<>();
        List<Map<String,String>> list = (List<Map<String, String>>) o;
        List<UserVO> result = new ArrayList<>();
        list.forEach(map->{
            UserVO userVO = new UserVO();
            userVO.setAccount(map.get("account"));
            userVO.setName(map.get("name"));
            result.add(userVO);
        });
        return result;
    }
}
