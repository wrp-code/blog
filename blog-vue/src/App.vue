<script setup lang="ts">
import navigate from './components/common/navigate.vue'
</script>

<template>
  <div>
    <navigate />

    <main>
      <RouterView />
    </main>
  </div>
</template>

<style scoped></style>
