package com.dzdp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dzdp.common.exception.BusinessException;
import com.dzdp.common.result.PageResult;
import com.dzdp.common.result.ResultCode;
import com.dzdp.dto.ShopDTO;
import com.dzdp.dto.ShopQueryDTO;
import com.dzdp.entity.Shop;
import com.dzdp.mapper.ShopMapper;
import com.dzdp.service.ShopService;
import com.dzdp.vo.ShopVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ShopMapper shopMapper;

    @Override
    public ShopVO getShopById(Long id) {
        log.info("[店铺][详情] [请求] shopId={}", id);
        Shop shop = shopMapper.selectById(id);
        if (shop == null) {
            log.warn("[店铺][详情] [异常] 店铺不存在, shopId={}", id);
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        ShopVO vo = new ShopVO();
        BeanUtils.copyProperties(shop, vo);
        log.info("[店铺][详情] [响应] shopId={}, name={}", id, shop.getName());
        return vo;
    }

    @Override
    public List<ShopVO> listShopsByCategoryId(Long categoryId) {
        log.info("[店铺][列表] [请求] categoryId={}", categoryId);
        LambdaQueryWrapper<Shop> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Shop::getCategoryId, categoryId);
        List<Shop> shops = shopMapper.selectList(wrapper);
        List<ShopVO> result = shops.stream().map(this::toVO).collect(Collectors.toList());
        log.info("[店铺][列表] [响应] categoryId={}, count={}", categoryId, result.size());
        
        return result;
    }

    @Override
    public PageResult<ShopVO> searchShops(ShopQueryDTO queryDTO) {
        log.info("[店铺][搜索] [请求] keyword={}, categoryId={}, name={}, minPrice={}, maxPrice={}, minRating={}, maxRating={}, address={}, sortBy={}, sortOrder={}, page={}, pageSize={}",
                queryDTO.getKeyword(), queryDTO.getCategoryId(), queryDTO.getName(), queryDTO.getMinPrice(),
                queryDTO.getMaxPrice(), queryDTO.getMinRating(), queryDTO.getMaxRating(), queryDTO.getAddress(),
                queryDTO.getSortBy(), queryDTO.getSortOrder(), queryDTO.getPage(), queryDTO.getPageSize());

        // 构建分页对象
        Page<Shop> page = new Page<>(queryDTO.getPage(), queryDTO.getPageSize());

        // 构建查询条件
        LambdaQueryWrapper<Shop> wrapper = new LambdaQueryWrapper<>();

        // keyword 模糊匹配 name
        if (StringUtils.hasText(queryDTO.getKeyword())) {
            wrapper.like(Shop::getName, queryDTO.getKeyword());
        }

        // categoryId 精确匹配
        if (queryDTO.getCategoryId() != null) {
            wrapper.eq(Shop::getCategoryId, queryDTO.getCategoryId());
        }

        // name 模糊匹配
        if (StringUtils.hasText(queryDTO.getName())) {
            wrapper.like(Shop::getName, queryDTO.getName());
        }

        // 价格区间
        if (queryDTO.getMinPrice() != null) {
            wrapper.ge(Shop::getAvgPrice, queryDTO.getMinPrice());
        }
        if (queryDTO.getMaxPrice() != null) {
            wrapper.le(Shop::getAvgPrice, queryDTO.getMaxPrice());
        }

        // 评分区间
        if (queryDTO.getMinRating() != null) {
            wrapper.ge(Shop::getRating, queryDTO.getMinRating());
        }
        if (queryDTO.getMaxRating() != null) {
            wrapper.le(Shop::getRating, queryDTO.getMaxRating());
        }

        // address 模糊匹配
        if (StringUtils.hasText(queryDTO.getAddress())) {
            wrapper.like(Shop::getAddress, queryDTO.getAddress());
        }

        // 排序
        if ("rating".equals(queryDTO.getSortBy())) {
            boolean isAsc = "asc".equalsIgnoreCase(queryDTO.getSortOrder());
            wrapper.orderBy(true, isAsc, Shop::getRating);
        }

        // 执行分页查询
        Page<Shop> shopPage = shopMapper.selectPage(page, wrapper);

        // 转换为 VO
        List<ShopVO> voList = shopPage.getRecords().stream().map(this::toVO).collect(Collectors.toList());

        log.info("[店铺][搜索] [响应] count={}, total={}", voList.size(), shopPage.getTotal());

        return PageResult.of(voList, shopPage.getTotal(), queryDTO.getPage(), queryDTO.getPageSize());
    }

    @Override
    public Long createShop(ShopDTO dto) {
        log.info("[店铺][创建] [请求] name={}, categoryId={}", dto.getName(), dto.getCategoryId());
        Shop shop = new Shop();
        BeanUtils.copyProperties(dto, shop);
        shopMapper.insert(shop);
        log.info("[店铺][创建] [响应] shopId={}", shop.getId());
        return shop.getId();
    }

    @Override
    public void updateShop(Long id, ShopDTO dto) {
        log.info("[店铺][更新] [请求] shopId={}", id);
        Shop existing = shopMapper.selectById(id);
        if (existing == null) {
            log.warn("[店铺][更新] [异常] 店铺不存在, shopId={}", id);
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        BeanUtils.copyProperties(dto, existing);
        existing.setId(id);
        shopMapper.updateById(existing);
        log.info("[店铺][更新] [响应] shopId={}", id);
    }

    @Override
    public void deleteShop(Long id) {
        log.info("[店铺][删除] [请求] shopId={}", id);
        Shop existing = shopMapper.selectById(id);
        if (existing == null) {
            log.warn("[店铺][删除] [异常] 店铺不存在, shopId={}", id);
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        shopMapper.deleteById(id);
        log.info("[店铺][删除] [响应] shopId={}", id);
    }

    private ShopVO toVO(Shop shop) {
        ShopVO vo = new ShopVO();
        BeanUtils.copyProperties(shop, vo);
        return vo;
    }
}
