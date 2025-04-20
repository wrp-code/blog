<script setup lang="ts">
import { ref, watch } from 'vue';
import { useCurArticleStore } from '../store/curArticle';
import { useRoute } from 'vue-router';

interface Heading {
    id: string;
    level: number;
    text: string;
}

const curArticle = useCurArticleStore();
const route = useRoute();
const headings = ref<Heading[]>([]);
const isVisible = ref(false);

// 滚动到指定标题
function scrollToHeading(id: string) {
    const element = document.getElementById(id);
    if (element) {
        element.scrollIntoView({ behavior: 'smooth' });
    }
}

// 监听路由变化
watch(() => route.path, (newPath) => {
    isVisible.value = newPath.startsWith('/article/') && !newPath.startsWith('/article/list');
}, { immediate: true });

// 监听文章内容变化
watch(() => curArticle.getCurArticle().content, (newContent) => {
    headings.value = extractHeadings(newContent);
}, { immediate: true });

// 提取标题
function extractHeadings(content: string) {
    const headingRegex = /^(#{1,6})\s+(.+)$/gm;
    const matches = content.matchAll(headingRegex);
    const result: Heading[] = [];

    for (const match of matches) {
        const level = match[1].length;
        const text = match[2];
        const id = text.toLowerCase().replace(/[^\w]+/g, '-');
        result.push({ id, level, text });
    }

    return result;
}
</script>

<template>
    <div class="toc" v-if="isVisible && headings.length > 0">
        <div class="toc-title">目录</div>
        <div class="toc-list">
            <div v-for="heading in headings" :key="heading.id" :class="['toc-item', `toc-level-${heading.level}`]"
                @click="scrollToHeading(heading.id)">
                {{ heading.text }}
            </div>
        </div>
    </div>
</template>

<style scoped>
.toc {
    background: #f6f8fa;
    border-radius: 8px;
    padding: 16px;
    margin-bottom: 24px;
}

.toc-title {
    font-size: 1.1rem;
    font-weight: 600;
    color: #2c3e50;
    margin-bottom: 12px;
}

.toc-list {
    display: flex;
    flex-direction: column;
    gap: 8px;
}

.toc-item {
    color: #2c3e50;
    cursor: pointer;
    transition: all 0.2s;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    position: relative;
}

.toc-item:hover {
    overflow: visible;
    z-index: 1;
    background: #fff;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    border-radius: 4px;
    padding: 4px 8px;
}

.toc-level-1 {
    padding-left: 0;
}

.toc-level-2 {
    padding-left: 16px;
}

.toc-level-3 {
    padding-left: 32px;
}

.toc-level-4 {
    padding-left: 48px;
}

.toc-level-5 {
    padding-left: 64px;
}

.toc-level-6 {
    padding-left: 80px;
}

:deep(h1),
:deep(h2),
:deep(h3),
:deep(h4),
:deep(h5),
:deep(h6) {
    scroll-margin-top: 20px;
}
</style>
