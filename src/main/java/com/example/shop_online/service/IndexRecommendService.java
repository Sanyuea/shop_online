package com.example.shop_online.service;

import com.example.shop_online.entity.IndexRecommend;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shop_online.vo.IndexRecommendVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yule
 * @since 2023-11-07
 */
public interface IndexRecommendService extends IService<IndexRecommend> {
    List<IndexRecommendVO> getList();
}
