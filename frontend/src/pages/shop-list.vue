<template>
  <div class="shop-list-page">
    <!-- 搜索栏 -->
    <div class="search-bar">
      <el-input
        v-model="keyword"
        placeholder="搜索店铺名称"
        clearable
        size="large"
        class="search-input"
        @keyup.enter="handleSearch"
      >
        <template #append>
          <el-button :icon="Search" @click="handleSearch">搜索</el-button>
        </template>
      </el-input>
    </div>

    <!-- 分类筛选栏 -->
    <div class="category-bar">
      <el-tag
        class="category-tag"
        :type="selectedCategoryId === null ? 'primary' : 'info'"
        effect="dark"
        @click="handleCategoryClick(null)"
      >
        全部
      </el-tag>
      <el-tag
        v-for="category in categoryList"
        :key="category.id"
        class="category-tag"
        :type="selectedCategoryId === category.id ? 'primary' : 'info'"
        effect="dark"
        @click="handleCategoryClick(category.id)"
      >
        {{ category.name }}
      </el-tag>
    </div>

    <!-- 排序控制 -->
    <div class="sort-bar">
      <el-radio-group v-model="sortOption" @change="handleSortChange">
        <el-radio-button value="default">默认排序</el-radio-button>
        <el-radio-button value="rating-desc">评分高到低</el-radio-button>
        <el-radio-button value="rating-asc">评分低到高</el-radio-button>
      </el-radio-group>
    </div>

    <!-- 店铺卡片列表 -->
    <el-row :gutter="20" class="shop-cards" v-loading="loading">
      <el-col
        v-for="shop in shopList"
        :key="shop.id"
        :xs="24"
        :sm="12"
        :md="8"
      >
        <el-card
          class="shop-card"
          shadow="hover"
          @click="handleCardClick(shop.id)"
        >
          <el-image
            class="shop-cover"
            :src="shop.coverUrl"
            fit="cover"
          >
            <template #error>
              <div class="image-placeholder">
                <el-icon :size="40"><Picture /></el-icon>
              </div>
            </template>
          </el-image>
          <div class="shop-info">
            <h3 class="shop-name">{{ shop.name }}</h3>
            <div class="shop-rating">
              <el-rate
                :model-value="Number(shop.rating)"
                disabled
                allow-half
                show-score
                text-color="#ff6633"
                score-template="{value}"
                :colors="['#ff9900', '#ff6633', '#ff6633']"
              />
            </div>
            <div class="shop-price">¥{{ shop.avgPrice }}/人</div>
            <div class="shop-address">{{ shop.address }}</div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 空状态 -->
    <el-empty
      v-if="!loading && shopList.length === 0"
      description="暂无店铺数据"
    />

    <!-- 分页组件 -->
    <div class="pagination-bar" v-if="total > 0">
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[9, 18, 27, 36]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        background
        @current-change="handlePageChange"
        @size-change="handleSizeChange"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { Search, Picture } from '@element-plus/icons-vue'
import { getCategoryList, type CategoryVO } from '@/api/category'
import { searchShops, type ShopVO, type ShopSearchParams } from '@/api/shop'

const router = useRouter()

// 组件状态
const keyword = ref('')
const selectedCategoryId = ref<number | null>(null)
const sortOption = ref<'default' | 'rating-desc' | 'rating-asc'>('default')
const currentPage = ref(1)
const pageSize = ref(9)
const shopList = ref<ShopVO[]>([])
const total = ref(0)
const categoryList = ref<CategoryVO[]>([])
const loading = ref(false)

/** 获取店铺列表 */
async function fetchShops() {
  loading.value = true
  try {
    const params: ShopSearchParams = {
      page: currentPage.value,
      pageSize: pageSize.value,
    }
    if (keyword.value.trim()) {
      params.keyword = keyword.value.trim()
    }
    if (selectedCategoryId.value !== null) {
      params.categoryId = selectedCategoryId.value
    }
    if (sortOption.value !== 'default') {
      params.sortBy = 'rating'
      params.sortOrder = sortOption.value === 'rating-desc' ? 'desc' : 'asc'
    }
    const res: any = await searchShops(params)
    shopList.value = res.list
    total.value = res.total
  } catch (error) {
    console.error('[店铺列表][查询] 请求失败', error)
  } finally {
    loading.value = false
  }
}

/** 获取分类列表 */
async function fetchCategories() {
  try {
    const res: any = await getCategoryList()
    categoryList.value = res
  } catch (error) {
    console.error('[店铺列表][分类] 请求失败', error)
  }
}

/** 搜索 */
function handleSearch() {
  currentPage.value = 1
  fetchShops()
}

/** 分类切换 */
function handleCategoryClick(categoryId: number | null) {
  selectedCategoryId.value = categoryId
  currentPage.value = 1
  fetchShops()
}

/** 排序切换 */
function handleSortChange() {
  currentPage.value = 1
  fetchShops()
}

/** 翻页 */
function handlePageChange() {
  fetchShops()
}

/** 每页条数变化 */
function handleSizeChange() {
  currentPage.value = 1
  fetchShops()
}

/** 点击卡片跳转详情 */
function handleCardClick(shopId: number) {
  router.push(`/shops/${shopId}`)
}

/** 页面加载 */
onMounted(() => {
  fetchCategories()
  fetchShops()
})
</script>

<style scoped>
.shop-list-page {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

/* 搜索栏 */
.search-bar {
  margin-bottom: 20px;
}

.search-input {
  max-width: 500px;
}

.search-input :deep(.el-input-group__append) {
  background-color: #ff6633;
  color: #fff;
  border-color: #ff6633;
}

.search-input :deep(.el-input-group__append:hover) {
  background-color: #e55a2b;
}

/* 分类筛选栏 */
.category-bar {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-bottom: 20px;
}

.category-tag {
  cursor: pointer;
  transition: transform 0.2s;
}

.category-tag:hover {
  transform: scale(1.05);
}

.category-tag :deep(.el-tag__content) {
  color: inherit;
}

/* 排序控制 */
.sort-bar {
  margin-bottom: 20px;
}

.sort-bar :deep(.el-radio-button__inner:hover) {
  color: #ff6633;
}

.sort-bar :deep(.el-radio-button__original-radio:checked + .el-radio-button__inner) {
  background-color: #ff6633;
  border-color: #ff6633;
}

/* 店铺卡片列表 */
.shop-cards {
  min-height: 200px;
}

.shop-card {
  margin-bottom: 20px;
  cursor: pointer;
  transition: transform 0.3s, box-shadow 0.3s;
  border-radius: 8px;
  overflow: hidden;
}

.shop-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.15);
}

.shop-card :deep(.el-card__body) {
  padding: 0;
}

/* 封面图 */
.shop-cover {
  width: 100%;
  height: 180px;
  display: block;
}

.image-placeholder {
  width: 100%;
  height: 180px;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #f5f7fa;
  color: #c0c4cc;
}

/* 店铺信息 */
.shop-info {
  padding: 12px 16px 16px;
}

.shop-name {
  margin: 0 0 8px;
  font-size: 16px;
  font-weight: 700;
  color: #303133;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.shop-rating {
  margin-bottom: 6px;
}

.shop-rating :deep(.el-rate__text) {
  color: #ff6633;
  font-weight: 600;
}

.shop-price {
  font-size: 14px;
  color: #ff6633;
  font-weight: 600;
  margin-bottom: 6px;
}

.shop-address {
  font-size: 13px;
  color: #909399;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

/* 分页组件 */
.pagination-bar {
  display: flex;
  justify-content: center;
  padding: 20px 0;
}

.pagination-bar :deep(.el-pagination.is-background .el-pager li.is-active) {
  background-color: #ff6633;
}
</style>
