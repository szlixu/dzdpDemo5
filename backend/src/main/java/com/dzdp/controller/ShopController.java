package com.dzdp.controller;

import com.dzdp.common.result.PageResult;
import com.dzdp.common.result.Result;
import com.dzdp.dto.ShopDTO;
import com.dzdp.dto.ShopQueryDTO;
import com.dzdp.service.ShopService;
import com.dzdp.vo.ShopVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shops")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @GetMapping("/{id}")
    public Result<ShopVO> getShopById(@PathVariable Long id) {
        return Result.success(shopService.getShopById(id));
    }

    @GetMapping
    public Result<List<ShopVO>> listShopsByCategoryId(@RequestParam Long categoryId) {
        return Result.success(shopService.listShopsByCategoryId(categoryId));
    }

    @GetMapping("/search")
    public Result<PageResult<ShopVO>> searchShops(ShopQueryDTO queryDTO) {
        return Result.success(shopService.searchShops(queryDTO));
    }

    @PostMapping
    public Result<Long> createShop(@RequestBody @Valid ShopDTO dto) {
        return Result.success(shopService.createShop(dto));
    }

    @PutMapping("/{id}")
    public Result<Void> updateShop(@PathVariable Long id, @RequestBody @Valid ShopDTO dto) {
        shopService.updateShop(id, dto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteShop(@PathVariable Long id) {
        shopService.deleteShop(id);
        return Result.success();
    }
}
