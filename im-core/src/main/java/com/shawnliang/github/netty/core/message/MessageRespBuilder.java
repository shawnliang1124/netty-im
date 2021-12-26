package com.shawnliang.github.netty.core.message;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/12/26
 */
public class MessageRespBuilder {

    /**
     * 构建成功的返回体
     *
     * @return 返回体
     */
    public static MessageResp success() {
       return success(null);
    }

    /**
     * 构建成功的返回体
     *
     * @param data 返回体内容
     * @param <T>  Obj
     * @return 返回体
     */
    public static <T> MessageResp success(T data) {
        MessageResp resp = new MessageResp();
        resp.setSuccess(true);
        resp.setResult(data);

        return resp;
    }

    /**
     * 构建失败的返回体
     * @return 返回体
     */
    public static MessageResp failure() {
        return failure(null);
    }

    /**
     * 构建失败的返回体
     *
     * @param data 返回体内容
     * @param <T>  Obj
     * @return 返回体
     */
    public static <T> MessageResp failure(T data) {
        MessageResp resp = new MessageResp();
        resp.setSuccess(false);
        resp.setResult(data);

        return resp;
    }


}
