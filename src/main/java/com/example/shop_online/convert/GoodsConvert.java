package com.example.shop_online.convert;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.shop_online.entity.Goods;
import com.example.shop_online.vo.GoodsVO;
import com.example.shop_online.vo.RecommendGoodsVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

@Mapper
public interface GoodsConvert {
    GoodsConvert INSTANCE = Mappers.getMapper(GoodsConvert.class);


    @Mapping(expression = "java(MapStruct.strToList(goods.getProductPictures()))", target = "productPictures")
    @Mapping(expression = "java(MapStruct.strToList(goods.getMainPictures()))", target = "mainPictures")
    GoodsVO convertToGoodsVO(Goods goods);

    List<RecommendGoodsVO> convertToRecommendGoodsVOList(List<Goods> goodsList);

    class MapStruct {
        public static List<String> strToList(String str) {
            if (StringUtils.isNotEmpty(str)) {
                return Arrays.asList(str.split(","));
            }
            return null;
        }
    }
}