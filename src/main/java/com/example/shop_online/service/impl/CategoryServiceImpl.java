package com.example.shop_online.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.shop_online.convert.GoodsConvert;
import com.example.shop_online.entity.Category;
import com.example.shop_online.entity.Goods;
import com.example.shop_online.enums.CategoryRecommendEnum;
import com.example.shop_online.mapper.CategoryMapper;
import com.example.shop_online.mapper.GoodsMapper;
import com.example.shop_online.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shop_online.vo.CategoryChildrenGoodsVO;
import com.example.shop_online.vo.CategoryVO;
import com.example.shop_online.vo.RecommendGoodsVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
@AllArgsConstructor
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    private final GoodsMapper goodsMapper;
    @Override
    public List<Category> getIndexCategoryList(){
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getIsRecommend, CategoryRecommendEnum.ALL_RECOMMEND.getValue());
        wrapper.orderByDesc(Category::getCreateTime);
        List<Category> list = baseMapper.selectList(wrapper);
        return list;
    }

    @Override
    public List<CategoryVO> getCategoryList(){
        List<CategoryVO> list = new ArrayList<>();
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getIsRecommend,CategoryRecommendEnum.ALL_RECOMMEND.getValue()).or();
        List<Category> categories = baseMapper.selectList(wrapper);
        LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
        for (Category category : categories){
            CategoryVO categoryVO = new CategoryVO();
            categoryVO.setId(categoryVO.getId());
            categoryVO.setName(categoryVO.getName());
            categoryVO.setIcon(categoryVO.getIcon());
            wrapper.clear();
            wrapper.eq(Category::getParentId,category.getId());
            List<Category> childCategories = baseMapper.selectList(wrapper);
            List<CategoryChildrenGoodsVO> categoryChildrenGoodsList = new ArrayList<>();
            for (Category item : childCategories){
                CategoryChildrenGoodsVO childrenGoodsVO = new CategoryChildrenGoodsVO();
                childrenGoodsVO.setId(item.getId());
                childrenGoodsVO.setName(item.getName());
                childrenGoodsVO.setIcon(item.getIcon());
                childrenGoodsVO.setParentId(category.getId());
                childrenGoodsVO.setParentName(category.getName());
                queryWrapper.clear();
                List<Goods> goodsList = goodsMapper.selectList(queryWrapper.eq(Goods::getCategoryId,item.getId()));
                List<RecommendGoodsVO> goodsVOList = GoodsConvert.INSTANCE.convertToRecommendGoodsVOList(goodsList);
                childrenGoodsVO.setGoods(goodsVOList);
                categoryChildrenGoodsList.add(childrenGoodsVO);
            }
            categoryVO.setChildren(categoryChildrenGoodsList);
            list.add(categoryVO);
        }

        return list;
    }

}
