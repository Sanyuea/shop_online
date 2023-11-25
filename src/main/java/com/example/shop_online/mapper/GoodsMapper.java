package com.example.shop_online.mapper;

import com.example.shop_online.entity.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shop_online.vo.UserOrderGoodsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yule
 * @since 2023-11-07
 */
public interface GoodsMapper extends BaseMapper<Goods> {
    List<UserOrderGoodsVO> getGoodsListByOrderId(@Param("id") Integer id);
}
