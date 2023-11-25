package com.example.shop_online.convert;

import com.example.shop_online.entity.UserShoppingAddress;
import com.example.shop_online.vo.UserAddressVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserAddressConvert {

    UserAddressConvert INSTANCE = Mappers.getMapper(UserAddressConvert.class);


    UserAddressVO convertToUserAddressVO(UserShoppingAddress userShoppingAddress);


    List<UserAddressVO> convertToUserAddressVOList(List<UserShoppingAddress> list);
}