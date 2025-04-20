import { defineStore } from 'pinia'
import { reactive } from 'vue'
import type { Article } from '../../types/Article';

export const useCurArticleStore = defineStore('curArticle',()=> {
    let curArticle = reactive<Article>({
        id: null,
        createTime: null,
        title: '',
        content: '',
        userId: null,
        username: '',
        catalogId: null,
        hits: null
    });

    function setCurArticle(article:any) {
        curArticle.id = article.id;
        curArticle.createTime = article.createTime;
        curArticle.title = article.title;
        curArticle.content = article.content;
        curArticle.userId = article.userId;
        curArticle.username = article.username;
        curArticle.catalogId = article.catalogId;
        curArticle.hits = article.hits;
    }

    function getCurArticle() {
        return curArticle;
    }
    return {setCurArticle, getCurArticle}
}) 