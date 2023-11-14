package com.example.shop_online.service;

import com.example.shop_online.entity.Category;
import com.example.shop_online.entity.UserShoppingCart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shop_online.query.CartQuery;
import com.example.shop_online.vo.CartGoodsVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yule
 * @since 2023-11-07
 */
public interface UserShoppingCartService extends IService<UserShoppingCart> {
    //添加购物车
    CartGoodsVO addShopCart(CartQuery query);
}
