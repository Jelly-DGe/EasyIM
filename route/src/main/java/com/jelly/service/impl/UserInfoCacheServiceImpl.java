package com.jelly.service.impl;

import com.jelly.constant.Constant;
import com.jelly.pojo.EasyIMUserInfo;
import com.jelly.service.UserInfoCacheService;
import com.jelly.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserInfoCacheServiceImpl implements UserInfoCacheService {

    private final static Map<Long, EasyIMUserInfo> USER_INFO_MAP = new ConcurrentHashMap<>(64);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public EasyIMUserInfo loadUserInfoByUserId(Long userId) {
        EasyIMUserInfo easyIMUserInfo = USER_INFO_MAP.get(userId);
        if (easyIMUserInfo != null) {
            return easyIMUserInfo;
        }

        String sendUserName = redisTemplate.opsForValue().get(Constant.ACCOUNT_PREFIX + userId);
        if (null != sendUserName) {
            easyIMUserInfo = new EasyIMUserInfo(userId, sendUserName);
            USER_INFO_MAP.put(userId, easyIMUserInfo);
        }
        return easyIMUserInfo;
    }

    @Override
    public boolean saveAndCheckUserLoginStatus(Long userId) throws Exception {
        Long add = redisTemplate.opsForSet().add(Constant.LOGIN_STATUS_PREFIX, userId.toString());
        if (add == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void removeLoginStatus(Long userId) throws Exception {
        redisTemplate.opsForSet().remove(Constant.LOGIN_STATUS_PREFIX, userId);
    }

    @Override
    public Set<EasyIMUserInfo> onlineUser() {
        Set<EasyIMUserInfo> set = null;
        Set<String> members = redisTemplate.opsForSet().members(Constant.LOGIN_STATUS_PREFIX);
        for (String member : members) {
            if (set == null) {
                set = new HashSet<>(64);
            }
            EasyIMUserInfo easyIMUserInfo = loadUserInfoByUserId(Long.valueOf(member));
            set.add(easyIMUserInfo);
        }
        return set;
    }
}
