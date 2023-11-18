package com.example.shop_online.controller;

import com.example.shop_online.common.exception.ServerException;
import com.example.shop_online.common.result.Result;
import com.example.shop_online.service.UserShoppingAddressService;
import com.example.shop_online.vo.AddressVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static com.example.shop_online.common.utils.ObtainUserIdUtils.getUserId;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yule
 * @since 2023-11-07
 */
@Tag(name = "地址管理")
@RestController
@RequestMapping("member")
@AllArgsConstructor
public class UserShoppingAddressController {
    private final UserShoppingAddressService userShoppingAddressService;

    @Operation(summary = "添加收货地址")
    @PostMapping("address")
    public Result<Integer> saveAddress(@RequestBody @Validated AddressVO addressVO, HttpServletRequest request) {
        Integer userId = getUserId(request);
        addressVO.setUserId(userId);
        Integer addressId = userShoppingAddressService.saveShoppingAddress(addressVO);
        return Result.ok(addressId);
    }

    @Operation(summary = "修改收货地址")
    @PutMapping("address")
    public Result<Integer> editAddress(@RequestBody @Validated AddressVO addressVO, HttpServletRequest request) {
        if (addressVO.getId() == null) {
            throw new ServerException("请求参数不能为空");
        }
        addressVO.setUserId(getUserId(request));
        Integer addressId = userShoppingAddressService.editShoppingAddress(addressVO);
        return Result.ok(addressId);
    }

    @Operation(summary = "获取收获地址")
    @GetMapping("address")
    public Result<List<AddressVO>> getList(HttpServletRequest request){
        Integer userId = getUserId(request);
        List<AddressVO> list = userShoppingAddressService.getList(userId);
        return Result.ok(list);
    }
    @Operation(summary = "收货地址详情")
    @GetMapping("address/detail")
    public Result<AddressVO> getAddress(@RequestParam Integer id, HttpServletRequest request) {
        AddressVO addressVO = userShoppingAddressService.getAddress(id);
        return Result.ok(addressVO);
    }

    @Operation(summary = "删除收货地址")
    @DeleteMapping("address")
    public Result removeAddress(@RequestParam Integer id, HttpServletRequest request) {
        if (id == null) {
            throw new ServerException("请求参数不能为空");
        }
        userShoppingAddressService.removeShoppingAddress(id);
        return Result.ok();
    }
}