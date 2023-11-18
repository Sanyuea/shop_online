package com.example.shop_online.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.shop_online.common.exception.ServerException;
import com.example.shop_online.convert.AddressConvert;
import com.example.shop_online.entity.UserShoppingAddress;
import com.example.shop_online.enums.AddressDefaultEnum;
import com.example.shop_online.enums.DeleteFlagEnum;
import com.example.shop_online.mapper.UserShoppingAddressMapper;
import com.example.shop_online.service.UserShoppingAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shop_online.vo.AddressVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yule
 * @since 2023-11-07
 */
@Service
public class UserShoppingAddressServiceImpl extends ServiceImpl<UserShoppingAddressMapper, UserShoppingAddress> implements UserShoppingAddressService {
    @Override
    public Integer saveShoppingAddress(AddressVO addressVO){
        UserShoppingAddress convert = AddressConvert.INSTANCE.convert(addressVO);
        if (addressVO.getIsDefault()== AddressDefaultEnum.DEFAULT_ADDRESS.getValue()){
            List<UserShoppingAddress> list = baseMapper.selectList(new LambdaQueryWrapper<UserShoppingAddress>().eq(UserShoppingAddress::getIsDefault,AddressDefaultEnum.DEFAULT_ADDRESS.getValue()));
            if (list.size()>0){
                throw new ServerException("已经存在默认地址，请勿重复操作");
            }
        }
        save(convert);
        return convert.getId();
    }

    @Override
    public Integer editShoppingAddress(AddressVO addressVO){
        UserShoppingAddress userShoppingAddress = baseMapper.selectById(addressVO.getId());
        if (userShoppingAddress == null){
            throw new ServerException("地址不存在");
        }
        if (addressVO.getIsDefault() == AddressDefaultEnum.DEFAULT_ADDRESS.getValue()){
            UserShoppingAddress address = baseMapper.selectOne(new LambdaQueryWrapper<UserShoppingAddress>().eq(UserShoppingAddress::getUserId,addressVO.getUserId()).eq(UserShoppingAddress::getIsDefault,AddressDefaultEnum.DEFAULT_ADDRESS.getValue()));
            if (address != null){
                address.setIsDefault(AddressDefaultEnum.NOT_DEFAULT_ADDRESS.getValue());
                updateById(address);
            }
        }
        UserShoppingAddress address = AddressConvert.INSTANCE.convert(addressVO);
        updateById(address);
        return address.getId();
    }

    @Override
    public List<AddressVO> getList(Integer userId) {
        List<UserShoppingAddress> list = baseMapper.selectList(new LambdaQueryWrapper<UserShoppingAddress>().eq(UserShoppingAddress::getUserId,userId));
        List<AddressVO> addressVOList = AddressConvert.INSTANCE.convertToAddressVOList(list);
        return addressVOList;
    }

    @Override
    public AddressVO getAddress(Integer id) {
        UserShoppingAddress userShoppingAddress = baseMapper.selectById(id);
        if (userShoppingAddress == null) {
            throw new ServerException("地址不存在");
        }
        LambdaQueryWrapper<UserShoppingAddress> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserShoppingAddress::getId, id);
        UserShoppingAddress address = baseMapper.selectOne(wrapper);
        return AddressConvert.INSTANCE.convertToAddressVO(address);
    }

    @Override
    public void removeShoppingAddress(Integer id) {
        removeById(id);
    }
}
