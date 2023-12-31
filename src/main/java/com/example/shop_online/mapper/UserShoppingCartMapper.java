package com.example.shop_online.mapper;

import com.example.shop_online.entity.UserShoppingCart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shop_online.vo.CartGoodsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zero
 * @since 2023-11-14
 */
public interface UserShoppingCartMapper extends BaseMapper<UserShoppingCart> {
    /**
     * 获取购物车信息
     *
     * @param id
     * @return list
     */
    List<CartGoodsVO> getCartGoodsInfo(@Param("id") Integer id);
}
