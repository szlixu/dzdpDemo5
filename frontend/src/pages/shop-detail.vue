<template>
  <div class="shop-detail-page" v-loading="shopLoading">
    <template v-if="shop">
      <div class="back-bar">
        <el-button text @click="router.back()">
          <el-icon><ArrowLeft /></el-icon>
          返回列表
        </el-button>
      </div>

      <div class="shop-header">
        <el-image class="shop-cover" :src="shop.coverUrl" fit="cover">
          <template #error>
            <div class="image-placeholder">
              <el-icon :size="60"><Picture /></el-icon>
            </div>
          </template>
        </el-image>
        <div class="shop-meta">
          <h1 class="shop-name">{{ shop.name }}</h1>
          <div class="shop-rating-row">
            <el-rate
              :model-value="Number(shop.rating)"
              disabled
              allow-half
              show-score
              text-color="#ff6633"
              score-template="{value}"
              :colors="rateColors"
            />
          </div>
          <div class="shop-price">人均 ¥{{ shop.avgPrice }}</div>
          <div class="shop-detail-item">
            <el-icon><Location /></el-icon>
            <span>{{ shop.address || '暂无地址' }}</span>
          </div>
          <div class="shop-detail-item">
            <el-icon><Phone /></el-icon>
            <span>{{ shop.phone || '暂无电话' }}</span>
          </div>
        </div>
      </div>

      <el-divider />

      <div class="review-section">
        <h2 class="section-title">用户评价</h2>

        <div class="review-form">
          <el-card shadow="never">
            <h3 class="form-title">发表评价</h3>
            <el-form
              ref="reviewFormRef"
              :model="reviewForm"
              :rules="reviewRules"
              label-position="top"
            >
              <el-form-item label="评分" prop="rating">
                <el-rate v-model="reviewForm.rating" :colors="rateColors" />
              </el-form-item>
              <el-form-item label="评论内容" prop="content">
                <el-input
                  v-model="reviewForm.content"
                  type="textarea"
                  :rows="3"
                  placeholder="请输入您的评价内容"
                  maxlength="500"
                  show-word-limit
                />
              </el-form-item>
              <el-form-item>
                <el-button
                  type="primary"
                  :loading="submitting"
                  @click="handleSubmitReview"
                >
                  提交评价
                </el-button>
              </el-form-item>
            </el-form>
          </el-card>
        </div>

        <div class="review-list" v-loading="reviewLoading">
          <div
            v-for="review in reviewList"
            :key="review.id"
            class="review-item"
          >
            <div class="review-header">
              <el-avatar :size="36" class="review-avatar">
                {{ review.userId }}
              </el-avatar>
              <div class="review-user-info">
                <span class="review-username">用户{{ review.userId }}</span>
                <el-rate
                  :model-value="Number(review.rating)"
                  disabled
                  allow-half
                  :colors="rateColors"
                />
              </div>
              <span class="review-time">{{ formatTime(review.createTime) }}</span>
            </div>
            <div class="review-content">{{ review.content }}</div>
            <div class="review-images" v-if="review.images">
              <el-image
                v-for="(img, idx) in review.images.split(',')"
                :key="idx"
                :src="img"
                fit="cover"
                class="review-image"
                :preview-src-list="review.images.split(',')"
                :initial-index="idx"
              />
            </div>
          </div>

          <el-empty
            v-if="!reviewLoading && reviewList.length === 0"
            description="暂无评价"
          />
        </div>
      </div>
    </template>

    <el-empty v-if="!shopLoading && !shop" description="店铺不存在" />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import type { FormInstance, FormRules } from 'element-plus'
import { ArrowLeft, Picture, Location, Phone } from '@element-plus/icons-vue'
import { getShopById, type ShopVO } from '@/api/shop'
import { getReviewList, submitReview, type ReviewVO } from '@/api/review'

const route = useRoute()
const router = useRouter()

const shopId = Number(route.params.id)

const shop = ref<ShopVO | null>(null)
const shopLoading = ref(false)

const reviewList = ref<ReviewVO[]>([])
const reviewLoading = ref(false)

const reviewFormRef = ref<FormInstance>()
const submitting = ref(false)

const reviewForm = ref({
  rating: 0,
  content: '',
})

const reviewRules: FormRules = {
  rating: [
    { required: true, message: '请选择评分', trigger: 'change' },
  ],
  content: [
    { required: true, message: '请输入评论内容', trigger: 'blur' },
  ],
}

const rateColors = ['#ff9900', '#ff6633', '#ff6633']

async function fetchShop() {
  shopLoading.value = true
  try {
    const res: any = await getShopById(shopId)
    shop.value = res
  } catch {
    shop.value = null
  } finally {
    shopLoading.value = false
  }
}

async function fetchReviews() {
  reviewLoading.value = true
  try {
    const res: any = await getReviewList(shopId)
    reviewList.value = res
  } catch {
    reviewList.value = []
  } finally {
    reviewLoading.value = false
  }
}

async function handleSubmitReview() {
  if (!reviewFormRef.value) return
  await reviewFormRef.value.validate(async (valid) => {
    if (!valid) return
    submitting.value = true
    try {
      await submitReview({
        shopId,
        userId: 1,
        content: reviewForm.value.content,
        rating: reviewForm.value.rating,
      })
      ElMessage.success('评论成功')
      reviewForm.value.rating = 0
      reviewForm.value.content = ''
      reviewFormRef.value?.resetFields()
      fetchReviews()
    } catch {
    } finally {
      submitting.value = false
    }
  })
}

function formatTime(time: string) {
  if (!time) return ''
  return time.replace('T', ' ').substring(0, 16)
}

onMounted(() => {
  fetchShop()
  fetchReviews()
})
</script>

<style scoped>
.shop-detail-page {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.back-bar {
  margin-bottom: 16px;
}

.back-bar :deep(.el-button) {
  color: #ff6633;
  font-size: 14px;
}

.shop-header {
  display: flex;
  gap: 24px;
  background: #fff;
  border-radius: 8px;
  padding: 24px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
}

.shop-cover {
  width: 320px;
  height: 220px;
  border-radius: 8px;
  flex-shrink: 0;
  overflow: hidden;
}

.image-placeholder {
  width: 320px;
  height: 220px;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #f5f7fa;
  color: #c0c4cc;
}

.shop-meta {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.shop-name {
  margin: 0 0 12px;
  font-size: 24px;
  font-weight: 700;
  color: #303133;
}

.shop-rating-row {
  margin-bottom: 10px;
}

.shop-rating-row :deep(.el-rate__text) {
  color: #ff6633;
  font-weight: 600;
  font-size: 16px;
}

.shop-price {
  font-size: 16px;
  color: #ff6633;
  font-weight: 600;
  margin-bottom: 12px;
}

.shop-detail-item {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 14px;
  color: #606266;
  margin-bottom: 8px;
}

.shop-detail-item .el-icon {
  color: #909399;
}

.review-section {
  background: #fff;
  border-radius: 8px;
  padding: 24px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
}

.section-title {
  margin: 0 0 20px;
  font-size: 18px;
  font-weight: 700;
  color: #303133;
}

.review-form {
  margin-bottom: 24px;
}

.review-form :deep(.el-card__body) {
  padding: 20px;
}

.form-title {
  margin: 0 0 16px;
  font-size: 15px;
  font-weight: 600;
  color: #303133;
}

.review-form :deep(.el-button--primary) {
  background-color: #ff6633;
  border-color: #ff6633;
}

.review-form :deep(.el-button--primary:hover) {
  background-color: #e55a2b;
  border-color: #e55a2b;
}

.review-list {
  min-height: 100px;
}

.review-item {
  padding: 16px 0;
  border-bottom: 1px solid #f0f0f0;
}

.review-item:last-child {
  border-bottom: none;
}

.review-header {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 10px;
}

.review-avatar {
  background-color: #ff6633;
  color: #fff;
  font-size: 14px;
  flex-shrink: 0;
}

.review-user-info {
  display: flex;
  flex-direction: column;
  gap: 2px;
}

.review-username {
  font-size: 14px;
  font-weight: 600;
  color: #303133;
}

.review-user-info :deep(.el-rate__icon) {
  font-size: 12px;
}

.review-time {
  margin-left: auto;
  font-size: 12px;
  color: #c0c4cc;
}

.review-content {
  font-size: 14px;
  color: #606266;
  line-height: 1.6;
  margin-bottom: 8px;
  padding-left: 48px;
}

.review-images {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
  padding-left: 48px;
}

.review-image {
  width: 80px;
  height: 80px;
  border-radius: 4px;
  cursor: pointer;
}

@media (max-width: 768px) {
  .shop-header {
    flex-direction: column;
  }

  .shop-cover {
    width: 100%;
    height: 200px;
  }

  .image-placeholder {
    width: 100%;
    height: 200px;
  }

  .review-content,
  .review-images {
    padding-left: 0;
  }
}
</style>
