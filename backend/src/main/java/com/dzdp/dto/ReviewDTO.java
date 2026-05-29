package com.dzdp.dto;

import lombok.Data;

import jakarta.validation.constraints.*;

@Data
public class ReviewDTO {

    @NotNull(message = "店铺ID不能为空")
    private Long shopId;

    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @NotBlank(message = "评论内容不能为空")
    private String content;

    @NotNull(message = "评分不能为空")
    @Min(value = 1, message = "评分最低1")
    @Max(value = 5, message = "评分最高5")
    private Integer rating;

    private String images;
}
