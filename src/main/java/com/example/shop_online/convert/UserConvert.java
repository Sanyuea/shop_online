package com.example.shop_online.convert;

import com.example.shop_online.entity.User;
import com.example.shop_online.vo.LoginResultVO;
import org.apache.ibatis.annotations.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserConvert {
    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);


    LoginResultVO convertToLoginResultVO(User user);
}