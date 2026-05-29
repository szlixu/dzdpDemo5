import request from '@/utils/request'

export interface CategoryVO {
  id: number
  name: string
  icon: string
  sort: number
}

export function getCategoryList() {
  return request.get<CategoryVO[]>('/categories')
}
