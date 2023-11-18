package com.example.shop_online.convert;

import com.example.shop_online.entity.UserShoppingAddress;
import com.example.shop_online.vo.AddressVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AddressConvert {
    AddressConvert INSTANCE = Mappers.getMapper(AddressConvert.class);


    UserShoppingAddress convert(AddressVO addressVO);


    List<AddressVO> convertToAddressVOList(List<UserShoppingAddress> addressList);


    AddressVO convertToAddressVO(UserShoppingAddress userShoppingAddress);
}