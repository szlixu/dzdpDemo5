package com.dzdp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dzdp.dto.ShopQueryDTO;
import com.dzdp.entity.Shop;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShopMapper extends BaseMapper<Shop> {

    List<Shop> selectByCondition(ShopQueryDTO queryDTO);
}
