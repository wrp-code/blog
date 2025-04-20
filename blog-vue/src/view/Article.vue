<!--
 * 笔记详情
 * @author: wrp
 * @since: 2025年02月19日 22:55:33
-->
<template>
    <div class="container">
        <div class="title">
            {{ data.article.title }}
        </div>
        <div class="info">
            <div class="hits">
                <i class="icon-eye"></i>
                {{ data.article.hits }}
            </div>
            <div class="username">{{ data.article.username }}</div>
            <div class="createTime">{{ data.article.createTime }}</div>
        </div>
        <div class="content" v-html="renderedMarkdown" ref="markdownContent"></div>
    </div>
</template>

<script setup lang="ts">
import { onBeforeMount, reactive, computed, onMounted, ref, defineProps } from 'vue';
import { get } from '../util/http'
import MarkdownIt from 'markdown-it';
import hljs from 'highlight.js';
import 'highlight.js/styles/github.css'; // 或其他主题
import { useCurArticleStore } from '../store/curArticle';

const props = defineProps<{
    id: string
}>();

const store = useCurArticleStore();

// 声明全局类型
declare global {
    interface Window {
        copyCode: (button: HTMLElement) => void;
    }
}

const md = new MarkdownIt({
    html: true,
    linkify: true,
    typographer: true,
    highlight: function (str: string, lang: string): string {
        if (lang && hljs.getLanguage(lang)) {
            try {
                return `<pre class="hljs"><code class="language-${lang}">${hljs.highlight(str, { language: lang }).value}</code></pre>`;
            } catch (__) {}
        }
        return `<pre class="hljs"><code>${md.utils.escapeHtml(str)}</code></pre>`;
    }
});

const data = reactive({
    article: {
        id: null,
        createTime: null,
        title: '',
        content: '',
        userId: null,
        username: '',
        catalogId: null,
        hits: null
    }
})

const renderedMarkdown = computed(() => {
  return md.render(data.article.content);
});

const markdownContent = ref<HTMLElement | null>(null);

// 添加复制功能
onMounted(() => {
    const codeBlocks = document.querySelectorAll('pre code');
    codeBlocks.forEach((block) => {
        const pre = block.parentElement;
        if (!pre) return;

        // 如果已经存在复制按钮，则跳过
        if (pre.querySelector('.copy-btn')) return;

        const button = document.createElement('button');
        button.className = 'copy-btn';
        button.textContent = '复制';
        
        button.addEventListener('click', async () => {
            try {
                await navigator.clipboard.writeText(block.textContent || '');
                button.textContent = '已复制';
                setTimeout(() => {
                    button.textContent = '复制';
                }, 2000);
            } catch (err) {
                console.error('复制失败:', err);
            }
        });
        
        pre.insertBefore(button, block);
    });
});

onBeforeMount(() => {
    detail(props.id);
})


async function detail(id:string) {
    const article = await get('/article/'+id);
    data.article = article;
    store.setCurArticle(article);
}
</script>

<style scoped>
    .container {
        padding: 24px;
    }

    .title {
        font-size: 3rem;
        font-weight: 600;
        color: #2c3e50;
        margin-bottom: 24px;
        line-height: 1.4;
    }

    .info {
        display: flex;
        align-items: center;
        gap: 16px;
        margin-bottom: 24px;
        padding-bottom: 16px;
        border-bottom: 1px solid #eaecef;
        color: #6a737d;
        font-size: 14px;
    }

    .username {
        display: flex;
        align-items: center;
    }

    .username::before {
        content: '';
        display: inline-block;
        width: 16px;
        height: 16px;
        margin-right: 6px;
        background: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='%236a737d'%3E%3Cpath d='M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 3c1.66 0 3 1.34 3 3s-1.34 3-3 3-3-1.34-3-3 1.34-3 3-3zm0 14.2c-2.5 0-4.71-1.28-6-3.22.03-1.99 4-3.08 6-3.08 1.99 0 5.97 1.09 6 3.08-1.29 1.94-3.5 3.22-6 3.22z'/%3E%3C/svg%3E") no-repeat center;
        background-size: contain;
    }

    .createTime {
        display: flex;
        align-items: center;
    }

    .createTime::before {
        content: '';
        display: inline-block;
        width: 16px;
        height: 16px;
        margin-right: 6px;
        background: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='%236a737d'%3E%3Cpath d='M11.99 2C6.47 2 2 6.48 2 12s4.47 10 9.99 10C17.52 22 22 17.52 22 12S17.52 2 11.99 2zM12 20c-4.42 0-8-3.58-8-8s3.58-8 8-8 8 3.58 8 8-3.58 8-8 8zm.5-13H11v6l5.25 3.15.75-1.23-4.5-2.67z'/%3E%3C/svg%3E") no-repeat center;
        background-size: contain;
    }
    
    :deep(pre) {
        position: relative;
        margin: 12px 0;
        padding: 16px;
        background: #fafbfc;
        border-radius: 8px;
        border: 1px solid #eaecef;
        box-shadow: inset 0 1px 2px rgba(27,31,35,0.05);
    }
    
    :deep(.copy-btn) {
        position: absolute;
        right: 8px;
        top: 8px;
        background: none;
        border: none;
        color: #0366d6;
        cursor: pointer;
        font-size: 12px;
        padding: 2px 8px;
        transition: all 0.2s;
        z-index: 1;
        opacity: 0.6;
    }
    
    :deep(.copy-btn:hover) {
        color: #0366d6;
        opacity: 1;
    }
    
    :deep(code) {
        font-family: 'Fira Code', 'Consolas', monospace;
        font-size: 14px;
        line-height: 1.6;
        color: #1a1a1a;
    }

    :deep(pre:hover) {
        border-color: #d0d7de;
    }

    .hits {
        display: flex;
        align-items: center;
        color: #6a737d;
        font-size: 14px;
    }

    .hits::before {
        content: '';
        display: inline-block;
        width: 16px;
        height: 16px;
        margin-right: 6px;
        background: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='%236a737d'%3E%3Cpath d='M12 4.5C7 4.5 2.73 7.61 1 12c1.73 4.39 6 7.5 11 7.5s9.27-3.11 11-7.5c-1.73-4.39-6-7.5-11-7.5zM12 17c-2.76 0-5-2.24-5-5s2.24-5 5-5 5 2.24 5 5-2.24 5-5 5zm0-8c-1.66 0-3 1.34-3 3s1.34 3 3 3 3-1.34 3-3-1.34-3-3-3z'/%3E%3C/svg%3E") no-repeat center;
        background-size: contain;
    }
</style>
