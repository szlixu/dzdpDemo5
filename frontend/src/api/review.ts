import request from '@/utils/request'

export interface ReviewVO {
  id: number
  shopId: number
  userId: number
  content: string
  rating: number
  images: string
  createTime: string
}

export interface ReviewSubmitParams {
  shopId: number
  userId: number
  content: string
  rating: number
  images?: string
}

export function getReviewList(shopId: number) {
  return request.get<ReviewVO[]>('/reviews', { params: { shopId } })
}

export function submitReview(data: ReviewSubmitParams) {
  return request.post<number>('/reviews', data)
}
