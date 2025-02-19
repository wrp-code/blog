<!--
 * 导航组件
 * @author: wrp
 * @since: 2025年02月19日 22:15:28
-->
<template>
    <div class="container">
        <div>
            <RouterLink to="/">首页</RouterLink>
        </div>
        <div>
            <RouterLink to="/article/list">学习笔记</RouterLink>
        </div>
        <div>
            <RouterLink to="/article">笔记详情</RouterLink>
        </div>
        <div>
            <RouterLink to="/log">工作记录</RouterLink>
        </div>
        <div>
            <RouterLink to="/about">关于</RouterLink>
        </div>
    </div>
</template>

<script setup lang="ts"></script>

<style scoped>
.container {
    display: flex;
    justify-content: center;
    padding-bottom: 10px;
    margin-bottom: 20px;
    border-bottom: 1px grey solid;
    font-size: 28px;
}

.container div {
    width: 150px;
    border-right: 1px black solid;
}

.container div:nth-last-child(1) {
    border-right: 0px;
}
</style>
