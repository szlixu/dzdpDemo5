<template>
  <div class="layout">
    <header class="layout-header">
      <div class="header-content">
        <h1 class="logo" @click="router.push('/shops')">大众点评</h1>
        <nav class="nav">
          <router-link to="/shops" class="nav-link">店铺</router-link>
        </nav>
      </div>
    </header>
    <main class="layout-main">
      <router-view />
    </main>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'

const router = useRouter()
</script>

<style scoped>
.layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.layout-header {
  background: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  position: sticky;
  top: 0;
  z-index: 100;
}

.header-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 24px;
  height: 56px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.logo {
  font-size: 20px;
  color: #ff6633;
  cursor: pointer;
  margin: 0;
  font-weight: 700;
}

.nav {
  display: flex;
  gap: 24px;
}

.nav-link {
  color: #333;
  text-decoration: none;
  font-size: 15px;
  font-weight: 500;
  transition: color 0.2s;
}

.nav-link:hover,
.nav-link.router-link-active {
  color: #ff6633;
}

.layout-main {
  flex: 1;
  background: #f5f5f5;
}
</style>
