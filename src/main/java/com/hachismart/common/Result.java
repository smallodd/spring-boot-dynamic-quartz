package com.hachismart.common;

/**
 * @BelongsProject: spring-boot-dynamic-quartz
 * @BelongsPackage: ccom.hachismart.common
 * @Author: smallodd
 * @CreateTime: 2019-04-15 17:00
 * @Description: 返回结果
 */

public class Result {
    /**
     * 结果 true:false
     */
    private boolean result;
    /**
     * 信息
     */
    private String msg;


    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
