package com.example.shop_online.controller;

import com.alibaba.fastjson2.JSONObject;
import com.example.shop_online.common.result.Result;
import com.example.shop_online.service.UserOrderService;
import com.example.shop_online.vo.SubmitOrderVO;
import com.example.shop_online.vo.UserOrderVO;
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
    @Tag(name = "订单管理")
    @RestController
    @RequestMapping("/order")
    @AllArgsConstructor
    public class UserOrderController {
        private final UserOrderService userOrderService;
        @Operation(summary = "提交订单")
        @PostMapping("submit")
        public Result<JSONObject> saveUserOrder(@RequestBody @Validated UserOrderVO userOrderVO, HttpServletRequest request) {
            userOrderVO.setUserId(getUserId(request));
            Integer orderId = userOrderService.addGoodsOrder(userOrderVO);
            JSONObject json = new JSONObject();
            json.put("id", orderId);
            return Result.ok(json);
        }

    @Operation(summary = "填写订单 - 获取预付订单")
    @GetMapping("pre")
    public Result<SubmitOrderVO> getPreOrderDetail(HttpServletRequest request) {
        Integer userId = getUserId(request);
        SubmitOrderVO preOrderDetail = userOrderService.getPreOrderDetail(userId);
        return Result.ok(preOrderDetail);
    }

    }
