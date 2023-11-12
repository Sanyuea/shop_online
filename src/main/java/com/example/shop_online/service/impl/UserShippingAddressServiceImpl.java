package com.example.shop_online.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.shop_online.common.exception.ServerException;
import com.example.shop_online.convert.AddressConvert;
import com.example.shop_online.convert.IndexRecommendConvert;
import com.example.shop_online.entity.IndexRecommend;
import com.example.shop_online.entity.UserShippingAddress;
import com.example.shop_online.enums.AddressDefaultEnum;
import com.example.shop_online.enums.DeleteFlagEnum;
import com.example.shop_online.mapper.UserShippingAddressMapper;
import com.example.shop_online.service.UserShippingAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shop_online.vo.AddressVO;
import com.example.shop_online.vo.IndexRecommendVO;
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
public class UserShippingAddressServiceImpl extends ServiceImpl<UserShippingAddressMapper, UserShippingAddress> implements UserShippingAddressService {
    @Override
    public Integer saveShippingAddress(AddressVO addressVO){
        UserShippingAddress convert = AddressConvert.INSTANCE.convert(addressVO);
        if (addressVO.getIsDefault()== AddressDefaultEnum.DEFAULT_ADDRESS.getValue()){
            List<UserShippingAddress> list = baseMapper.selectList(new LambdaQueryWrapper<UserShippingAddress>().eq(UserShippingAddress::getIsDefault,AddressDefaultEnum.DEFAULT_ADDRESS.getValue()));
            if (list.size()>0){
                throw new ServerException("已经存在默认地址，请勿重复操作");
            }
        }
        save(convert);
        return convert.getId();
    }

    @Override
    public Integer editShippingAddress(AddressVO addressVO){
        UserShippingAddress userShippingAddress = baseMapper.selectById(addressVO.getId());
        if (userShippingAddress == null){
            throw new ServerException("地址不存在");
        }
        if (addressVO.getIsDefault() == AddressDefaultEnum.DEFAULT_ADDRESS.getValue()){
            UserShippingAddress address = baseMapper.selectOne(new LambdaQueryWrapper<UserShippingAddress>().eq(UserShippingAddress::getUserId,addressVO.getUserId()).eq(UserShippingAddress::getIsDefault,AddressDefaultEnum.DEFAULT_ADDRESS.getValue()));
            if (address != null){
                address.setIsDefault(AddressDefaultEnum.NOT_DEFAULT_ADDRESS.getValue());
                updateById(address);
            }
        }
        UserShippingAddress address = AddressConvert.INSTANCE.convert(addressVO);
        updateById(address);
        return address.getId();
    }

    @Override
    public List<AddressVO> getList(Integer userId) {
        List<UserShippingAddress> list = baseMapper.selectList(new LambdaQueryWrapper<UserShippingAddress>().eq(UserShippingAddress::getUserId,userId));
        List<AddressVO> addressVOList = AddressConvert.INSTANCE.convertToAddressVOList(list);
        return addressVOList;
    }

    @Override
    public AddressVO getAddress(Integer id) {
        UserShippingAddress userShippingAddress = baseMapper.selectById(id);
        if (userShippingAddress == null) {
            throw new ServerException("地址不存在");
        }
        LambdaQueryWrapper<UserShippingAddress> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserShippingAddress::getId, id);
        UserShippingAddress address = baseMapper.selectOne(wrapper);
        return AddressConvert.INSTANCE.convertToAddressVO(address);
    }

    @Override
    public Integer deleteShippingAddress(Integer id) {
        LambdaQueryWrapper<UserShippingAddress> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserShippingAddress::getId, id);
        UserShippingAddress address = baseMapper.selectOne(wrapper);
        address.setDeleteFlag(DeleteFlagEnum.OPEN_DELETE_FLAG.getValue());
        updateById(address);
        return address.getId();
    }
}
