package com.dzdp.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ShopVO {

    private Long id;

    private Long categoryId;

    private String name;

    private String address;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private String phone;

    private String coverUrl;

    private BigDecimal avgPrice;

    private BigDecimal rating;

    private LocalDateTime createTime;
}
