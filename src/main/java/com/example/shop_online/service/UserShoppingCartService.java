package com.example.shop_online.service;

import com.example.shop_online.entity.Category;
import com.example.shop_online.entity.UserShoppingCart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shop_online.query.CartQuery;
import com.example.shop_online.query.EditCartQuery;
import com.example.shop_online.vo.CartGoodsVO;
import io.swagger.models.auth.In;

import java.util.List;

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
    //获取购物车列表
    List<CartGoodsVO> shopCartList(Integer userId);
    //修改购物车
    CartGoodsVO editCart(EditCartQuery query);
    //删除清空购物车
    void removeCartGoods(Integer userId,List<Integer> ids);
}
