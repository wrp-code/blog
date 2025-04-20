<!--
 * 关于
 * @author: wrp
 * @since: 2025年02月19日 22:20:21
-->
<template>
    <div class="container">
        <div class="profile-card">
            <div class="avatar">
                <img src="../assets/images/avatar.jpg" alt="Avatar" />
            </div>
            <div class="info">
                <h1 class="name">wrp</h1>
                <div class="meta">
                    <div class="meta-item">
                        <i class="icon-birthday"></i>
                        <span>1998.02</span>
                    </div>
                    <div class="meta-item">
                        <i class="icon-location"></i>
                        <span>成都</span>
                    </div>
                    <div class="meta-item">
                        <i class="icon-email"></i>
                        <span>wrphaha9@gmail.com</span>
                    </div>
                </div>
            </div>
        </div>

    
    </div>
</template>

<script setup lang="ts">
</script>

<style scoped>
.container {
    padding: 40px 24px;
    max-width: 800px;
    margin: 0 auto;
}

.profile-card {
    background: #fff;
    border-radius: 12px;
    padding: 32px;
    display: flex;
    align-items: center;
    gap: 32px;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
    margin-bottom: 24px;
}

.avatar {
    width: 120px;
    height: 120px;
    border-radius: 50%;
    overflow: hidden;
    border: 4px solid #f6f8fa;
}

.avatar img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.info {
    flex: 1;
}

.name {
    font-size: 2rem;
    color: #2c3e50;
    margin-bottom: 16px;
}

.meta {
    display: flex;
    flex-direction: column;
    gap: 12px;
}

.meta-item {
    display: flex;
    align-items: center;
    gap: 8px;
    color: #6a737d;
}

.meta-item i {
    width: 20px;
    height: 20px;
    background-size: contain;
    background-repeat: no-repeat;
    background-position: center;
}

.icon-birthday {
    background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='%236a737d'%3E%3Cpath d='M19 3h-1V1h-2v2H8V1H6v2H5c-1.11 0-2 .9-2 2v14c0 1.1.89 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zm0 16H5V8h14v11zM9 10H7v2h2v-2zm4 0h-2v2h2v-2zm4 0h-2v2h2v-2zm-8 4H7v2h2v-2zm4 0h-2v2h2v-2zm4 0h-2v2h2v-2z'/%3E%3C/svg%3E");
}

.icon-location {
    background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='%236a737d'%3E%3Cpath d='M12 2C8.13 2 5 5.13 5 9c0 5.25 7 13 7 13s7-7.75 7-13c0-3.87-3.13-7-7-7zm0 9.5c-1.38 0-2.5-1.12-2.5-2.5s1.12-2.5 2.5-2.5 2.5 1.12 2.5 2.5-1.12 2.5-2.5 2.5z'/%3E%3C/svg%3E");
}

.icon-email {
    background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='%236a737d'%3E%3Cpath d='M20 4H4c-1.1 0-1.99.9-1.99 2L2 18c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V6c0-1.1-.9-2-2-2zm0 4l-8 5-8-5V6l8 5 8-5v2z'/%3E%3C/svg%3E");
}

.quote-card {
    background: #fff;
    border-radius: 12px;
    padding: 32px;
    margin-bottom: 24px;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
    position: relative;
}

.quote-content {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 16px;
}

.quote-content p {
    font-size: 1.25rem;
    color: #2c3e50;
    line-height: 1.6;
    text-align: center;
    font-style: italic;
}

.quote-content i {
    width: 24px;
    height: 24px;
    background-size: contain;
    background-repeat: no-repeat;
    background-position: center;
    opacity: 0.2;
}

.icon-quote-left {
    background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='%232c3e50'%3E%3Cpath d='M6 17h3l2-4V7H5v6h3zm8 0h3l2-4V7h-6v6h3z'/%3E%3C/svg%3E");
}

.icon-quote-right {
    background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='%232c3e50'%3E%3Cpath d='M14 7h-6v6h3l-2 4h3l2-4h3V7zm8 0h-6v6h3l-2 4h3l2-4h3V7z'/%3E%3C/svg%3E");
}

.quote-author {
    text-align: right;
    color: #6a737d;
    margin-top: 16px;
    font-size: 0.9rem;
}

.skills-card {
    background: #fff;
    border-radius: 12px;
    padding: 32px;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.skills-card h2 {
    font-size: 1.5rem;
    color: #2c3e50;
    margin-bottom: 24px;
}

.skills-list {
    display: flex;
    flex-direction: column;
    gap: 16px;
}

.skill-item {
    display: flex;
    flex-direction: column;
    gap: 8px;
}

.skill-name {
    font-size: 1rem;
    color: #2c3e50;
}

.skill-bar {
    height: 8px;
    background: #f6f8fa;
    border-radius: 4px;
    overflow: hidden;
}

.skill-progress {
    height: 100%;
    background: #0366d6;
    border-radius: 4px;
    transition: width 0.3s ease;
}

@media (max-width: 768px) {
    .profile-card {
        flex-direction: column;
        text-align: center;
    }

    .meta {
        align-items: center;
    }
}
</style>
