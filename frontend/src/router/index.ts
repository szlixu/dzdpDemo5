import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      redirect: '/shops',
    },
    {
      path: '/shops',
      name: 'ShopList',
      component: () => import('@/pages/shop-list.vue'),
    },
    {
      path: '/shops/:id',
      name: 'ShopDetail',
      component: () => import('@/pages/shop-detail.vue'),
    },
  ],
})

export default router
