package com.jelly.controller;

import com.jelly.server.EasyIMServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private EasyIMServer easyIMServer;

    @Autowired
    private CounterService counterServicel;

public
}
