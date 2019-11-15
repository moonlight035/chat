package cn.moonlight035.chatserver.utils.result;

import lombok.Data;

@Data
public class Result {
    private ResultStatus resultStatus;
    private Object data;

    public Result(ResultStatus resultStatus, Object data) {
        this.resultStatus = resultStatus;
        this.data = data;
    }
}
