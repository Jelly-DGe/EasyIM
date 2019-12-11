package com.jelly.service.impl;

import com.jelly.enums.StatusEnum;
import com.jelly.service.AccountService;
import com.jelly.service.UserInfoCacheService;
import com.jelly.vo.req.ChatReqVO;
import com.jelly.vo.req.LoginReqVO;
import com.jelly.vo.res.EasyIMServerResVO;
import com.jelly.vo.res.RegisterInfoResVO;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AccountServiceRedisImpl implements AccountService {
    private final static Logger LOGGER = LoggerFactory.getLogger(AccountServiceRedisImpl.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserInfoCacheService userInfoCacheService;

    @Autowired
    private OkHttpClient okHttpClient;

    private MediaType mediaType = MediaType.parse("application/json");

    @Override
    public RegisterInfoResVO register(RegisterInfoResVO info) throws Exception {
        return null;
    }

    @Override
    public StatusEnum login(LoginReqVO loginReqVO) throws Exception {
        return null;
    }

    @Override
    public void saveRouteInfo(LoginReqVO loginReqVO, String msg) throws Exception {

    }

    @Override
    public Map<Long, EasyIMServerResVO> loadRouteRelated() {
        return null;
    }

    @Override
    public EasyIMServerResVO loadRouteRelatedByUserId(Long userId) {
        return null;
    }

    @Override
    public void pushMsg(String url, long sendUserId, ChatReqVO groupReqVO) throws Exception {

    }

    @Override
    public void offLine(Long userId) throws Exception {

    }
}
