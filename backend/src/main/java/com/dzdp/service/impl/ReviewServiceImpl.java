package com.dzdp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dzdp.common.exception.BusinessException;
import com.dzdp.common.result.ResultCode;
import com.dzdp.dto.ReviewDTO;
import com.dzdp.entity.Review;
import com.dzdp.mapper.ReviewMapper;
import com.dzdp.service.ReviewService;
import com.dzdp.vo.ReviewVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewMapper reviewMapper;

    @Override
    public List<ReviewVO> listReviewsByShopId(Long shopId) {
        log.info("[评论][查询] [请求] shopId={}", shopId);
        LambdaQueryWrapper<Review> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Review::getShopId, shopId)
                .orderByDesc(Review::getCreateTime);
        List<Review> reviews = reviewMapper.selectList(wrapper);
        List<ReviewVO> result = reviews.stream().map(this::toVO).collect(Collectors.toList());
        log.info("[评论][查询] [响应] shopId={}, count={}", shopId, result.size());
        return result;
    }

    @Override
    public Long createReview(ReviewDTO dto) {
        log.info("[评论][创建] [请求] shopId={}, userId={}, rating={}", dto.getShopId(), dto.getUserId(), dto.getRating());
        Review review = new Review();
        BeanUtils.copyProperties(dto, review);
        reviewMapper.insert(review);
        log.info("[评论][创建] [响应] reviewId={}, shopId={}", review.getId(), review.getShopId());
        return review.getId();
    }

    @Override
    public void deleteReview(Long id) {
        log.info("[评论][删除] [请求] reviewId={}", id);
        Review review = reviewMapper.selectById(id);
        if (review == null) {
            log.warn("[评论][删除] [异常] 评论不存在, reviewId={}", id);
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        reviewMapper.deleteById(id);
        log.info("[评论][删除] [响应] reviewId={}, shopId={}", id, review.getShopId());
    }

    private ReviewVO toVO(Review review) {
        ReviewVO vo = new ReviewVO();
        BeanUtils.copyProperties(review, vo);
        return vo;
    }
}
