package com.jelly.vo.req;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class RegisterInfoVO {
    @NotNull(message = "用户名不能为空")
    @ApiModelProperty(required = true, value = "userName", example = "zhangsan")
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "RegisterInfoReqVO{" +
                "userName='" + userName + '\'' +
                "} " + super.toString();
    }
}
