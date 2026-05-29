package com.dzdp.service;

import com.dzdp.common.result.PageResult;
import com.dzdp.dto.ShopDTO;
import com.dzdp.dto.ShopQueryDTO;
import com.dzdp.vo.ShopVO;

import java.util.List;

public interface ShopService {

    ShopVO getShopById(Long id);

    List<ShopVO> listShopsByCategoryId(Long categoryId);

    PageResult<ShopVO> searchShops(ShopQueryDTO queryDTO);

    Long createShop(ShopDTO dto);

    void updateShop(Long id, ShopDTO dto);

    void deleteShop(Long id);
}
