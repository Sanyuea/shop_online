package com.example.shop_online.service;

import com.example.shop_online.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shop_online.query.UserLoginQuery;
import com.example.shop_online.vo.LoginResultVO;
import com.example.shop_online.vo.UserVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yule
 * @since 2023-11-07
 */
public interface UserService extends IService<User> {
    LoginResultVO login(UserLoginQuery query);
    User getUserInfo(Integer userId);
    UserVO editUserInfo(UserVO userVO);
}
