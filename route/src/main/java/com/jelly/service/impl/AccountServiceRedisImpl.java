package com.jelly.service.impl;

import com.jelly.constant.Constant;
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
        String key = Constant.ACCOUNT_PREFIX + info.getUserId();
        String name = redisTemplate.opsForValue().get(info.getUserId());
        if (null == name) {
            //为了方便查询，冗余一份
            redisTemplate.opsForValue().set(key, info.getUserName());
            redisTemplate.opsForValue().set(info.getUserName(), key);
        } else {
            long userId = Long.parseLong(name.split(":")[1]);
            info.setUserId(userId);
            info.setUserName(info.getUserName());
        }
        return info;
    }

    
    @Override
    public StatusEnum login(LoginReqVO loginReqVO) throws Exception {
        String key = Constant.ACCOUNT_PREFIX + loginReqVO.getUserId();
        String username = redisTemplate.opsForValue().get(key);
        if (null == username) {
            return StatusEnum.ACCOUNT_NOT_MATCH;
        }
        if (!username.equals(loginReqVO.getUserName())) {
            return StatusEnum.ACCOUNT_NOT_MATCH;
        }

        boolean status = userInfoCacheService.saveAndCheckUserLoginStatus(loginReqVO.getUserId());
        if (status == false) {
            return StatusEnum.REPEAT_LOGIN;
        }
        return StatusEnum.SUCCESS;
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
