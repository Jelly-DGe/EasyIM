package com.jelly.controller;

import com.jelly.constant.Constants;
import com.jelly.enums.StatusEnum;
import com.jelly.res.BaseResponse;
import com.jelly.server.EasyIMServer;
import com.jelly.vo.req.SendMsgReqVO;
import com.jelly.vo.res.SendMsgResVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private EasyIMServer easyIMServer;

    @Autowired
    private CounterService counterServicel;

    @ApiOperation("服务端发送消息")
    @RequestMapping(value = "sendMsg", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<SendMsgResVO> sendMsg(@RequestBody SendMsgReqVO sendMsgReqVO) {
        BaseResponse<SendMsgResVO> res = new BaseResponse<>();
        easyIMServer.sendMsg(sendMsgReqVO);
        counterServicel.increment(Constants.COUNTER_SERVER_PUSH_COUNT);

        SendMsgResVO sendMsgResVO = new SendMsgResVO();
        sendMsgReqVO.setMsg("OK");
        res.setCode(StatusEnum.SUCCESS.getCode());
        res.setMessage(StatusEnum.SUCCESS.getMessage());
        res.setDataBody(sendMsgResVO);
        return res;
    }

}
