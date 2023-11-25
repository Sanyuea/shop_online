package com.example.shop_online.service;

import com.example.shop_online.entity.UserOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shop_online.vo.UserOrderVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yule
 * @since 2023-11-07
 */
public interface UserOrderService extends IService<UserOrder> {
    //提交订单功能
    Integer addGoodsOrder(UserOrderVO orderVO);
}
