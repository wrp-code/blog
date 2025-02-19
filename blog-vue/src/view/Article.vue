<!--
 * 笔记详情
 * @author: wrp
 * @since: 2025年02月19日 22:55:33
-->
<template>
    <div class="container">
        <vue3-markdown-it :source="target" />
    </div>
</template>

<script setup lang="ts">
import Vue3MarkdownIt from 'vue3-markdown-it';
import { ref } from 'vue';

const target = ref('# Hello, Vue3-Markdown-It! `java` \r - hahaa ')
</script>

<style scoped></style>
