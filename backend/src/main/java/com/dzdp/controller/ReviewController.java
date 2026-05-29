package com.dzdp.controller;

import com.dzdp.common.result.Result;
import com.dzdp.dto.ReviewDTO;
import com.dzdp.service.ReviewService;
import com.dzdp.vo.ReviewVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public Result<List<ReviewVO>> listReviewsByShopId(@RequestParam Long shopId) {
        return Result.success(reviewService.listReviewsByShopId(shopId));
    }

    @PostMapping
    public Result<Long> createReview(@RequestBody @Valid ReviewDTO dto) {
        return Result.success(reviewService.createReview(dto));
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return Result.success();
    }
}
