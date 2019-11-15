package cn.moonlight035.chatserver.utils.result;

public enum ResultStatus {

    OK("成功",200),
    PARAM_FAIL("接收参数失败",201),
    RUN_FAIL("操作失败",202);

    private String code;
    private int status;

    ResultStatus(String code, int status) {
        this.code = code;
        this.status = status;
    }

}
