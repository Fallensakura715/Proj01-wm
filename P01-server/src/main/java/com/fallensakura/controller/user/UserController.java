package com.fallensakura.controller.user;

import com.fallensakura.dto.UserLoginDTO;
import com.fallensakura.result.Result;
import com.fallensakura.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fallensakura.vo.UserLoginVO;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Fallensakura
 * @since 2025-10-16
 */
@RestController
@RequestMapping("/user/user")
@RequiredArgsConstructor
@Tag(name = "C端-用户接口")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    @Operation(summary = "登录")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        return Result.success(userService.login(userLoginDTO));
    }

    @PostMapping("/logout")
    @Operation(summary = "登出")
    public Result<?> logout() {
        userService.logout();
        return Result.success();
    }
}
