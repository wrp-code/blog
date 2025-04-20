<script setup lang="ts">
import TitleView from './components/TitleView.vue';
import ArticleTop10 from './components/ArticleTop10.vue';
import { useRoute } from 'vue-router';

const route = useRoute();
</script>

<template>
  <div class="container">
    <div class="left" v-if="route.path !== '/'">
      <ArticleTop10/>
    </div>
    <main class="main">
      <RouterView />
    </main>
    <div class="toc-container">
      <TitleView />
    </div>
  </div>
</template>

<style scoped>
.container {
  position: relative;
}

.left {
  position: fixed;
  left: calc((100vw - 1280px) / 2 - 270px);
  top: 24px;
  width: 240px;
}

.main {
  width: 1280px;
  margin: 0 auto;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  background-color: #fff;
}

.toc-container {
  position: fixed;
  right: calc((100vw - 1280px) / 2 - 270px);
  top: 24px;
  width: 240px;
  max-height: calc(100vh - 48px);
  overflow-y: auto;
}

@media (max-width: 1600px) {
  .toc-container {
    display: none;
  }
  
  .left {
    display: none;
  }
}
</style>
