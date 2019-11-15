package cn.moonlight035.chatserver.inforequest.user;

import cn.moonlight035.chatserver.inforequest.user.model.UserInfoForm;
import cn.moonlight035.chatserver.utils.result.Result;
import cn.moonlight035.chatserver.utils.result.ResultStatus;
import cn.moonlight035.chatserver.utils.result.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jing.liu14@ucarinc.com
 * @date 2019/11/11
 * @description:
 */
@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Autowired
    private UserInfoDao userInfoDao;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result login(String account, String password){
        if(account==null||password==null) return ResultUtils.fail(ResultStatus.PARAM_FAIL,"参数无法接收");
        UserInfoForm userInfoForm = new UserInfoForm();
        userInfoForm.setAccount(account);
        userInfoForm.setPassword(password);
        Integer result = userInfoDao.login(userInfoForm);
        if(result==null||result<=0) return ResultUtils.fail(ResultStatus.RUN_FAIL,"账号或者密码错误");
        return ResultUtils.success(true);
    }

    @RequestMapping(value = "/getUserInfo",method = RequestMethod.POST)
    public Result getUserInfo(String account, String name){
        UserInfoForm userInfoForm = new UserInfoForm();
        userInfoForm.setAccount(account);
        userInfoForm.setName(name);
        return ResultUtils.success(userInfoDao.getUserInfo(userInfoForm));
    }

    @RequestMapping(value = "/getFriendInfo",method = RequestMethod.POST)
    public Result getFriendInfo(String account){
        if(account==null) return ResultUtils.fail(ResultStatus.PARAM_FAIL,"参数无法接收");
        Result success = ResultUtils.success(userInfoDao.getFriendInfo(account));
        return success;
    }


    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public Integer addUser(UserInfoForm userInfoForm){
        //todo
        return null;
    }

    @RequestMapping(value = "/deleteUser",method = RequestMethod.POST)
    public Integer deleteUser(String account){
        //todo
        return null;
    }

}
