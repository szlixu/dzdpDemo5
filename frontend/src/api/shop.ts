import request from '@/utils/request'

export interface ShopVO {
  id: number
  categoryId: number
  name: string
  address: string
  longitude: number
  latitude: number
  phone: string
  coverUrl: string
  avgPrice: number
  rating: number
}

export interface ShopSearchParams {
  keyword?: string
  categoryId?: number
  sortBy?: string
  sortOrder?: string
  page?: number
  pageSize?: number
}

export interface PageResult<T> {
  list: T[]
  total: number
  page: number
  pageSize: number
}

export function searchShops(params: ShopSearchParams) {
  return request.get<PageResult<ShopVO>>('/shops/search', { params })
}

export function getShopById(id: number) {
  return request.get<ShopVO>(`/shops/${id}`)
}
