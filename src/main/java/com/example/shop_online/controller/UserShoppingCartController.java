package com.example.shop_online.controller;

import com.example.shop_online.common.result.Result;
import com.example.shop_online.query.CartQuery;
import com.example.shop_online.service.UserShoppingCartService;
import com.example.shop_online.vo.CartGoodsVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.example.shop_online.common.utils.ObtainUserIdUtils.getUserId;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yule
 * @since 2023-11-07
 */
    @Tag(name = "购物车管理")
    @RestController
    @RequestMapping("/cart")
    @AllArgsConstructor
    public class UserShoppingCartController {
        private final UserShoppingCartService userShoppingCartService;

        @Operation(summary = "加入购物车")
        @PostMapping("add")
        public Result<CartGoodsVO> addShopCart(@RequestBody @Validated CartQuery query, HttpServletRequest request) {
            query.setUserId(getUserId(request));
            CartGoodsVO goodsVO = userShoppingCartService.addShopCart(query);
            return Result.ok(goodsVO);
        }

    }