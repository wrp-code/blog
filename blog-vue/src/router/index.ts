import { createWebHashHistory, createRouter } from 'vue-router'

import AboutView from '../view/AboutView.vue'
import ArticleList from '../view/ArticleList.vue'
import Article from '../view/Article.vue'
import HomeView from '../view/HomeView.vue'

const routes = [
    { path: '/', component: HomeView },
    { path: '/about', component: AboutView },
    { path: '/article/list', component: ArticleList },
    { path: '/article/:id', component: Article, props: true }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes,
})

export default router