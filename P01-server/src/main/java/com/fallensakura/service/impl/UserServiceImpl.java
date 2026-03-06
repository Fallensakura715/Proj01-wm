package com.fallensakura.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fallensakura.constant.JwtClaimsConstant;
import com.fallensakura.context.BaseContext;
import com.fallensakura.dto.UserLoginDTO;
import com.fallensakura.entity.User;
import com.fallensakura.exception.BusinessException;
import com.fallensakura.mapper.UserMapper;
import com.fallensakura.properties.JwtProperties;
import com.fallensakura.properties.WechatProperties;
import com.fallensakura.service.UserService;
import com.fallensakura.utils.HttpClientUtils;
import com.fallensakura.utils.JwtUtils;
import com.fallensakura.vo.UserLoginVO;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Fallensakura
 * @since 2025-10-16
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String WX_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";
    private static final String USER_LOGIN_TOKEN_PREFIX = "user:login:token:";

    private final RedisTemplate<Object, Object> redisTemplate;
    private final JwtProperties jwtProperties;
    private final WechatProperties wechatProperties;
    private final UserMapper userMapper;

    @Override
    public UserLoginVO login(UserLoginDTO userLoginDTO) {

        User user = wxLogin(userLoginDTO.getCode());

        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());

        String token = JwtUtils.generateToken(
                claims,
                "user_auth",
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserExpirationTime()
        );

        redisTemplate.opsForValue().set(
                USER_LOGIN_TOKEN_PREFIX + user.getId(),
                token,
                jwtProperties.getUserExpirationTime(),
                TimeUnit.MILLISECONDS
        );

        return UserLoginVO.builder()
                .id(user.getId())
                .openid(user.getOpenid())
                .token(token)
                .build();
    }

    @Override
    public void logout() {
        Long userId = BaseContext.getCurrentId();
        if (userId != null) {
            redisTemplate.delete(USER_LOGIN_TOKEN_PREFIX + userId);
        }
    }

    private User wxLogin(String wechatToken) {
        String openid = getOpenid(wechatToken);

        if (openid == null) throw new BusinessException("登录失败");

        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getOpenid, openid));

        if (user == null) {
            user = User.builder()
                    .openid(openid)
                    .build();
            userMapper.insert(user);
        }

        return user;
    }

    private String getOpenid(String code) {
        Map<String, String> map = new HashMap<>();
        map.put("appid", wechatProperties.getAppid());
        map.put("secret", wechatProperties.getSecret());
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");

        String json = HttpClientUtils.doGet(WX_LOGIN_URL, map);
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

        return jsonObject.get("openid").getAsString();
    }

}
