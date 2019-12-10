package com.jelly.vo.req;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class SendMsgVO {
    @NotNull(message = "msg 不能为空")
    @ApiModelProperty(required = true, value = "msg", example = "hello")
    private String msg;

    @NotNull(message = "id 不能为空")
    @ApiModelProperty(required = true, value = "id", example = "11")
    private long id;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
