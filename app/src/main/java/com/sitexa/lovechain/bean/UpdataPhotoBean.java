package com.sitexa.lovechain.bean;

/**
 * Created by sitexa on 2018/6/19.
 */

public class UpdataPhotoBean {

    /**
     * code : 0
     * message : ok
     * data : http://shaoyuan.oss-cn-beijing.aliyuncs.com/data/image/d553e2e94b9b888341fe1d572b6720b5.jpg?Expires=1832039013&OSSAccessKeyId=LTAIFb61WAy0ZemD&Signature=tkEuyZ6si6ayT8VCgiDiZFto5ww%3D
     */

    private String code;
    private String message;
    private String data;

    public String getCode() {
        return code == null ? "" : code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message == null ? "" : message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data == null ? "" : data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
