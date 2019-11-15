package cn.moonlight035.chatserver.utils.msg;

/**
 * @author jing.liu14@ucarinc.com
 * @date 2019/11/12
 * @description:
 */
public enum MsgStatus {

    SEND_MSG(100),
    SEND_ACCOUNT(200);

    private int status;


    MsgStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }}
