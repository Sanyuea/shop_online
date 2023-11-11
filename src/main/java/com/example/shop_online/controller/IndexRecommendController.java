package com.example.shop_online.controller;

import com.example.shop_online.common.result.Result;
import com.example.shop_online.service.IndexRecommendService;
import com.example.shop_online.vo.IndexRecommendVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yule
 * @since 2023-11-07
 */
@Tag(name = "推荐管理")
@RestController
@RequestMapping("home")
@AllArgsConstructor
public class IndexRecommendController {
    private final IndexRecommendService indexRecommendService;

    @Operation(summary = "首页-热门推荐")
    @GetMapping("/hot")
    public Result<List<IndexRecommendVO>> getList() {
        List<IndexRecommendVO> list = indexRecommendService.getList();
        return Result.ok(list);

    }

}