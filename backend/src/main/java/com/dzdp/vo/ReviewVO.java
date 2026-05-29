package com.dzdp.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewVO {

    private Long id;

    private Long shopId;

    private Long userId;

    private String content;

    private Integer rating;

    private String images;

    private LocalDateTime createTime;
}
