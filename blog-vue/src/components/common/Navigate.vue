<!--
 * 导航组件
 * @author: wrp
 * @since: 2025年02月19日 22:15:28
-->
<template>
    <div class="nav-wrapper">
        <div class="container">
            <div class="nav-center">
                <div class="search-box">
                    <i class="icon-search"></i>
                    <input 
                        type="text" 
                        placeholder="搜索文章..." 
                        v-model="searchText"
                        @keyup.enter="handleSearch"
                    />
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const searchText = ref('');

function handleSearch() {
    if (searchText.value.trim()) {
        router.push(`/search?q=${encodeURIComponent(searchText.value)}`);
    }
}


</script>

<style scoped>
.nav-wrapper {
    background: #fff;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
    position: sticky;
    top: 0;
    z-index: 100;
}

.container {
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 16px 24px;
    width: 1280px;
    margin: 0 auto;
    position: relative;
}

.nav-center {
    display: flex;
    align-items: center;
    justify-content: center;
    flex: 1;
}

.nav-right {
    position: absolute;
    right: 24px;
}

.search-box {
    display: flex;
    align-items: center;
    background: #f6f8fa;
    border-radius: 8px;
    padding: 8px 16px;
    width: 400px;
    border: 1px solid #e1e4e8;
    transition: all 0.2s;
    box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
}

.search-box:hover {
    background: #fff;
    border-color: #0366d6;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.search-box:focus-within {
    background: #fff;
    border-color: #0366d6;
    box-shadow: 0 0 0 3px rgba(3, 102, 214, 0.1);
}

.search-box input {
    flex: 1;
    border: none;
    background: none;
    outline: none;
    font-size: 14px;
    color: #2c3e50;
    margin-left: 8px;
    line-height: 1.5;
}

.search-box input::placeholder {
    color: #959da5;
}

.icon-search {
    width: 20px;
    height: 20px;
    background-size: contain;
    background-repeat: no-repeat;
    background-position: center;
    background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='%236a737d'%3E%3Cpath d='M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z'/%3E%3C/svg%3E");
    opacity: 0.8;
    transition: opacity 0.2s;
}

.search-box:hover .icon-search,
.search-box:focus-within .icon-search {
    opacity: 1;
}

.create-btn {
    display: flex;
    align-items: center;
    gap: 8px;
    background: #0366d6;
    color: #fff;
    border: none;
    padding: 8px 16px;
    border-radius: 6px;
    font-size: 14px;
    cursor: pointer;
    transition: all 0.2s;
    box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
    white-space: nowrap;
}

.create-btn:hover {
    background: #0056b3;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.icon-create {
    width: 20px;
    height: 20px;
    background-size: contain;
    background-repeat: no-repeat;
    background-position: center;
    background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='%23fff'%3E%3Cpath d='M19 13h-6v6h-2v-6H5v-2h6V5h2v6h6v2z'/%3E%3C/svg%3E");
}
</style>
