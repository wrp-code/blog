import { createWebHashHistory, createRouter } from 'vue-router'

import HomeView from '../view/HomeView.vue'
import AboutView from '../view/AboutView.vue'
import ArticleList from '../view/ArticleList.vue'
import LogList from '../view/LogList.vue'
import Article from '../view/Article.vue'

const routes = [
    { path: '/', component: HomeView },
    { path: '/about', component: AboutView },
    { path: '/article/list', component: ArticleList },
    { path: '/article', component: Article },
    { path: '/log', component: LogList },
]

const router = createRouter({
    history: createWebHashHistory(),
    routes,
})

export default router