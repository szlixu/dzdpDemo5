package com.dzdp.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShopQueryDTO {

    private Long categoryId;

    private String name;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private BigDecimal minRating;

    private BigDecimal maxRating;

    private String address;

    private String keyword;

    private String sortBy;

    private String sortOrder;

    private Integer page = 1;

    private Integer pageSize = 10;
}
