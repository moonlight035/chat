package cn.moonlight035.chatserver.utils.result;

public class ResultUtils {

    public static Result success(Object o){
        return new Result(ResultStatus.OK,o);
    }

    public static Result fail(ResultStatus status, String reason){
        return new Result(status,reason);
    }
}
