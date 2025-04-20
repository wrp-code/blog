<!--
 * 学习笔记
 * @author: wrp
 * @since: 2025年02月19日 22:39:40
-->
<template>
    <div class="container">
        <!-- 文章列表 -->
        <div class="article-list" v-for="article in articles" :key="article.id">
            <RouterLink :to="'/article/' + article.id" target="_blank">
                <div class="title">
                    {{ article.title }}
                </div>
                <div class="info">
                    <div class="tags">
                        <span v-for="tag in article.tags" :key="tag" class="tag">#{{ tag }}</span>
                    </div>
                    <div class="meta">
                        <span class="hits">
                            <i class="icon-eye"></i>
                            {{ article.hits }}
                        </span>
                        <span class="username">
                            <i class="icon-username"></i>
                            {{ article.username }}
                        </span>
                        <span class="date">
                            <i class="icon-date"></i>
                            {{ article.createTime }}
                        </span>
                    </div>
                </div>
            </RouterLink>
        </div>

        <!-- 分页 -->
        <div class="pagination">
            <button 
                class="page-btn" 
                :disabled="currentPage === 1"
                @click="changePage(currentPage - 1)"
            >
                上一页
            </button>
            <span class="page-info">{{ currentPage }} / {{ totalPages }}</span>
            <button 
                class="page-btn" 
                :disabled="currentPage === totalPages"
                @click="changePage(currentPage + 1)"
            >
                下一页
            </button>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { get } from '../util/http';

interface Article {
    id: string;
    title: string;
    tags: string[];
    hits: number;
    createTime: string;
    username: string;
}

const articles = ref<Article[]>([]);
const currentPage = ref(1);
const pageSize = 10;
const totalPages = ref(1);

// 获取文章列表
async function fetchArticles() {
    try {
        const response = await get(`/article/list?pageNum=${currentPage.value}&pageSize=${pageSize}`);
        articles.value = response.records;
        totalPages.value = Math.ceil(response.total / pageSize);
    } catch (error) {
        console.error('获取文章列表失败:', error);
    }
}

// 切换页码
function changePage(page: number) {
    if (page < 1 || page > totalPages.value) return;
    currentPage.value = page;
    fetchArticles();
}

onMounted(() => {
    fetchArticles();
});
</script>

<style scoped>
.container {
    padding: 24px;
}

.article-list {
    padding: 20px;
    margin-bottom: 16px;
    background: #fff;
    border-radius: 8px;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
    transition: all 0.3s ease;
}

.article-list:hover {
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
    transform: translateY(-2px);
    cursor: pointer;
}

.title {
    font-size: 1.3rem;
    font-weight: 600;
    color: #2c3e50;
    text-decoration: none;
    transition: color 0.2s;
}

.title a:hover {
    color: #0366d6;
}

.info {
    display: flex;
    justify-content: space-between;
    align-items: center;
    color: #6a737d;
    font-size: 14px;
}

.tags {
    display: flex;
    gap: 8px;
}

.tag {
    padding: 2px 8px;
    background: #f1f8ff;
    color: #0366d6;
    border-radius: 4px;
    font-size: 12px;
}

.meta {
    display: flex;
    gap: 16px;
}

.meta span {
    display: flex;
    align-items: center;
    gap: 4px;
}

.meta i {
    width: 16px;
    height: 16px;
    background-size: contain;
    background-repeat: no-repeat;
    background-position: center;
}

.icon-eye {
    background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='%236a737d'%3E%3Cpath d='M12 4.5C7 4.5 2.73 7.61 1 12c1.73 4.39 6 7.5 11 7.5s9.27-3.11 11-7.5c-1.73-4.39-6-7.5-11-7.5zM12 17c-2.76 0-5-2.24-5-5s2.24-5 5-5 5 2.24 5 5-2.24 5-5 5zm0-8c-1.66 0-3 1.34-3 3s1.34 3 3 3 3-1.34 3-3-1.34-3-3-3z'/%3E%3C/svg%3E");
}

.icon-username {
    background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='%236a737d'%3E%3Cpath d='M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 3c1.66 0 3 1.34 3 3s-1.34 3-3 3-3-1.34-3-3 1.34-3 3-3zm0 14.2c-2.5 0-4.71-1.28-6-3.22.03-1.99 4-3.08 6-3.08 1.99 0 5.97 1.09 6 3.08-1.29 1.94-3.5 3.22-6 3.22z'/%3E%3C/svg%3E");
}

.icon-date {
    background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='%236a737d'%3E%3Cpath d='M19 3h-1V1h-2v2H8V1H6v2H5c-1.11 0-2 .9-2 2v14c0 1.1.89 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zm0 16H5V8h14v11zM9 10H7v2h2v-2zm4 0h-2v2h2v-2zm4 0h-2v2h2v-2zm-8 4H7v2h2v-2zm4 0h-2v2h2v-2zm4 0h-2v2h2v-2z'/%3E%3C/svg%3E");
}

.pagination {
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 16px;
    margin-top: 32px;
}

.page-btn {
    padding: 8px 16px;
    border: 1px solid #e1e4e8;
    background: #fff;
    color: #0366d6;
    border-radius: 6px;
    cursor: pointer;
    transition: all 0.2s;
}

.page-btn:hover:not(:disabled) {
    background: #f6f8fa;
    border-color: #0366d6;
}

.page-btn:disabled {
    color: #959da5;
    cursor: not-allowed;
}

.page-info {
    color: #6a737d;
    font-size: 14px;
}
</style>
