package com.dzdp.service;

import com.dzdp.dto.ReviewDTO;
import com.dzdp.vo.ReviewVO;

import java.util.List;

public interface ReviewService {

    List<ReviewVO> listReviewsByShopId(Long shopId);

    Long createReview(ReviewDTO dto);

    void deleteReview(Long id);
}
