<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { get } from '../util/http';
import { RouterLink } from 'vue-router';

interface Article {
    id: string;
    title: string;
    username: string;
}

const articles = ref<Article[]>([]);

async function fetchTopArticles() {
    try {
        const response = await get('/article/top10');
        articles.value = response;
    } catch (error) {
        console.error('获取热门文章失败:', error);
    }
}

onMounted(() => {
    fetchTopArticles();
});
</script>

<template>
    <div class="top-articles">
        <div class="title">
            <i class="icon-fire"></i>
            <span>热门文章</span>
        </div>
        <div class="list">
            <RouterLink 
                v-for="(article, index) in articles" 
                :key="article.id"
                :to="'/article/' + article.id" target="_blank"
                class="article-item"
            >
                <div class="rank">{{ index + 1 }}</div>
                <div class="info">
                    <div class="article-title">{{ article.title }}</div>
                    <div class="meta">
                        <span class="username">
                            <i class="icon-user"></i>
                            {{ article.username }}
                        </span>
                    </div>
                </div>
            </RouterLink>
        </div>
    </div>
</template>

<style scoped>
.top-articles {
    background: #fff;
    border-radius: 8px;
    padding: 16px;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.title {
    display: flex;
    align-items: center;
    gap: 8px;
    font-size: 1.1rem;
    font-weight: 600;
    color: #2c3e50;
    margin-bottom: 16px;
    padding-bottom: 12px;
    border-bottom: 1px solid #eaecef;
}

.title i {
    width: 20px;
    height: 20px;
    background: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='%23ff6b6b'%3E%3Cpath d='M13.5.67s.74 2.65.74 4.8c0 2.06-1.35 3.73-3.41 3.73-2.07 0-3.63-1.67-3.63-3.73l.03-.36C5.21 7.51 4 10.62 4 14c0 4.42 3.58 8 8 8s8-3.58 8-8C20 8.61 17.41 3.8 13.5.67zM11.99 21c-3.31 0-6-2.69-6-6 0-3.32 3.13-5.68 6-5.68V12c1.1 0 2 .9 2 2s-.9 2-2 2h-.01c-2.2 0-4 1.79-4 4s1.8 4 4.01 4z'/%3E%3C/svg%3E") no-repeat center;
    background-size: contain;
}

.list {
    display: flex;
    flex-direction: column;
    gap: 12px;
}

.article-item {
    display: flex;
    align-items: center;
    gap: 12px;
    padding: 8px;
    border-radius: 6px;
    text-decoration: none;
    transition: all 0.2s;
}

.article-item:hover {
    background: #f6f8fa;
}

.rank {
    width: 24px;
    height: 24px;
    display: flex;
    align-items: center;
    justify-content: center;
    background: #f6f8fa;
    border-radius: 4px;
    font-size: 14px;
    font-weight: 600;
    color: #0366d6;
}

.info {
    flex: 1;
    min-width: 0;
}

.article-title {
    font-size: 14px;
    color: #2c3e50;
    margin-bottom: 4px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}

.meta {
    display: flex;
    align-items: center;
    gap: 12px;
    font-size: 12px;
    color: #6a737d;
}

.meta span {
    display: flex;
    align-items: center;
    gap: 4px;
}

.meta i {
    width: 14px;
    height: 14px;
}

.username i {
    background: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='%236a737d'%3E%3Cpath d='M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z'/%3E%3C/svg%3E") no-repeat center;
    background-size: contain;
}

.time i {
    background: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='%236a737d'%3E%3Cpath d='M11.99 2C6.47 2 2 6.48 2 12s4.47 10 9.99 10C17.52 22 22 17.52 22 12S17.52 2 11.99 2zM12 20c-4.42 0-8-3.58-8-8s3.58-8 8-8 8 3.58 8 8-3.58 8-8 8zm.5-13H11v6l5.25 3.15.75-1.23-4.5-2.67z'/%3E%3C/svg%3E") no-repeat center;
    background-size: contain;
}
</style>
