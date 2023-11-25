package com.example.shop_online.convert;

import com.example.shop_online.entity.UserOrder;
import com.example.shop_online.vo.OrderDetailVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserOrderDetailConvert {
    UserOrderDetailConvert INSTANCE = Mappers.getMapper(UserOrderDetailConvert.class);
    OrderDetailVO convertToOrderDetailVO(UserOrder userOrder);
}
