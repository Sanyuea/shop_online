package com.example.shop_online.service.impl;

import com.example.shop_online.common.exception.ServerException;
import com.example.shop_online.entity.Goods;
import com.example.shop_online.entity.UserOrder;
import com.example.shop_online.entity.UserOrderGoods;
import com.example.shop_online.enums.OrderStatusEnum;
import com.example.shop_online.mapper.GoodsMapper;
import com.example.shop_online.mapper.UserOrderMapper;
import com.example.shop_online.query.OrderGoodsQuery;
import com.example.shop_online.service.UserOrderGoodsService;
import com.example.shop_online.service.UserOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shop_online.vo.UserOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yule
 * @since 2023-11-07
 */
@Service
public class UserOrderServiceImpl extends ServiceImpl<UserOrderMapper, UserOrder> implements UserOrderService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private UserOrderGoodsService userOrderGoodsService;

    private ScheduledExecutorService executorService =  Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> cancelTask;

    @Async
    public void scheduleOrderCancel(UserOrder userOrder){
        cancelTask = executorService.schedule(()->{
            if (userOrder.getStatus()== OrderStatusEnum.WAITING_FOR_PAYMENT.getValue()){
                userOrder.setStatus(OrderStatusEnum.CANCELLED.getValue());
                baseMapper.updateById(userOrder);
            }
        },30, TimeUnit.MINUTES);
    }

    public void cancelScheduledTask(){
        if (cancelTask != null && !cancelTask.isDone()){
            cancelTask.cancel(true);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addGoodsOrder(UserOrderVO orderVO){
        BigDecimal totalPrice = new BigDecimal(0);
        Integer totalCount = 0;
        BigDecimal totalFregiht = new BigDecimal(0);
        UserOrder userOrder = new UserOrder();
        userOrder.setUserId(orderVO.getUserId());
        userOrder.setAddressId(orderVO.getAddressId());
        userOrder.setOrderNumber(UUID.randomUUID().toString());
        userOrder.setDeliveryTimeType(orderVO.getDeliveryType());
        userOrder.setStatus(OrderStatusEnum.WAITING_FOR_PAYMENT.getValue());
        if (orderVO.getBuyerMessage() != null){
            userOrder.setBuyerMessage(orderVO.getBuyerMessage());
        }
        userOrder.setPayType(orderVO.getPayType());
        userOrder.setPayChannel(orderVO.getPayChannel());
        baseMapper.insert(userOrder);
        scheduleOrderCancel(userOrder);
        List<UserOrderGoods> orderGoodsList = new ArrayList<>();
        for (OrderGoodsQuery goodsVO : orderVO.getGoods()){
            Goods goods = goodsMapper.selectById(goodsVO.getId());
            if (goodsVO.getCount()>goods.getInventory()){
                throw new ServerException(goods.getName()+"库存数量不足");
            }
            UserOrderGoods userOrderGoods = new UserOrderGoods();
            userOrderGoods.setGoodsId(goods.getId());
            userOrderGoods.setName(goods.getName());
            userOrderGoods.setCover(goods.getCover());
            userOrderGoods.setOrderId(userOrder.getId());
            userOrderGoods.setCount(goodsVO.getCount());
            userOrderGoods.setAttrsText(goodsVO.getSkus());
            userOrderGoods.setFreight(goods.getFreight());
            userOrderGoods.setPrice(goods.getPrice());

            BigDecimal freight = new BigDecimal(userOrderGoods.getFreight().toString());
            BigDecimal goodsPrice = new BigDecimal(userOrderGoods.getPrice().toString());
            BigDecimal count = new BigDecimal(userOrderGoods.getCount().toString());
            goods.setInventory(goods.getInventory() - goodsVO.getCount());
            goods.setSalesCount(goodsVO.getCount());
            BigDecimal price = goodsPrice.multiply(count).add(freight);
            totalPrice = totalPrice.add(price);
            totalCount += goodsVO.getCount();
            totalFregiht = totalFregiht.add(freight);
            orderGoodsList.add(userOrderGoods);
            goodsMapper.updateById(goods);
        }

        userOrderGoodsService.batchUserOrderGoods(orderGoodsList);
        userOrder.setTotalPrice(totalPrice.doubleValue());
        userOrder.setTotalCount(totalCount);
        userOrder.setTotalFreight(totalFregiht.doubleValue());
        baseMapper.updateById(userOrder);
        return userOrder.getId();
    }

}
