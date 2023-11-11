package com.example.shop_online.controller;

import com.example.shop_online.common.result.Result;
import com.example.shop_online.entity.User;
import com.example.shop_online.query.UserLoginQuery;
import com.example.shop_online.service.UserService;
import com.example.shop_online.vo.LoginResultVO;
import com.example.shop_online.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.example.shop_online.common.utils.ObtainUserIdUtils.getUserId;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yule
 * @since 2023-11-07
 */
@RestController
@Tag(name = "用户模块")
@RequestMapping("user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "微信登录")
    @PostMapping("login/wxMin")
    public Result<LoginResultVO> wxLogin(@RequestBody @Validated UserLoginQuery query) {
        LoginResultVO userVO = userService.login(query);
        return Result.ok(userVO);
    }

    @Operation(summary = "用户详情")
    @GetMapping("/profile")
    private Result<User> getUserInfo(HttpServletRequest request) {
        Integer userId = getUserId(request);
        User userInfo = userService.getUserInfo(userId);
        return Result.ok(userInfo);
    }

    @Operation(summary = "修改用户信息")
    @PutMapping("/profile")
    private Result<UserVO> editUserInfo(HttpServletRequest request, @RequestBody @Validated UserVO userVO) {
        Integer userId = getUserId(request);
        userVO.setId(userId);
        UserVO userInfo = userService.editUserInfo(userVO);
        return Result.ok(userInfo);
    }

    @Operation(summary = "修改用户头像")
    @PostMapping("/profile/avatar")
    private Result<String> editUserAvatar(HttpServletRequest request, MultipartFile file) {
        Integer userId = getUserId(request);
        String uploadFileName = userService.editUserAvatar(userId, file);
        return Result.ok(uploadFileName);
    }
}
