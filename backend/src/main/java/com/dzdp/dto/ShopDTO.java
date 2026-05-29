package com.dzdp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShopDTO {

    @NotNull(message = "分类ID不能为空")
    private Long categoryId;

    @NotBlank(message = "店铺名称不能为空")
    private String name;

    private String address;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private String phone;

    private String coverUrl;

    private BigDecimal avgPrice;

    private BigDecimal rating;
}
