package com.example.shop_online.service;

import com.example.shop_online.entity.UserShippingAddress;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shop_online.vo.AddressVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yule
 * @since 2023-11-07
 */
public interface UserShippingAddressService extends IService<UserShippingAddress> {
    Integer saveShippingAddress(AddressVO addressVO);
    Integer editShippingAddress(AddressVO addressVO);
    List<AddressVO> getList(Integer userId);
    AddressVO getAddress(Integer id);
    Integer deleteShippingAddress(Integer id);
}
