package com.fallensakura.service;

import com.fallensakura.vo.UserLoginVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Fallensakura
 * @since 2025-10-16
 */
public interface UserService {

    /**
     * 登录
     * @param wechatToken
     */
    UserLoginVO login(String wechatToken);

    /**
     * 登出
     */
    void logout();
}
