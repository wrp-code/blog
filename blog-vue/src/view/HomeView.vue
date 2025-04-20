<!--
 * 首页
 * @author: wrp
 * @since: 2025年02月19日 22:19:58
-->
<template>
    <div class="container">
        <div class="hero">
            <div class="hero-content">
                <div class="logo">
                    <svg viewBox="0 0 200 200" xmlns="http://www.w3.org/2000/svg">
                        <path d="M100 20 L180 60 L180 140 L100 180 L20 140 L20 60 Z" fill="#0366d6" />
                        <path d="M100 40 L160 70 L160 130 L100 160 L40 130 L40 70 Z" fill="#fff" />
                        <path d="M100 60 L140 80 L140 120 L100 140 L60 120 L60 80 Z" fill="#0366d6" />
                        <text x="100" y="110" text-anchor="middle" fill="#fff" font-size="24" font-weight="bold">BLOG</text>
                    </svg>
                </div>
                <h1 class="title">欢迎来到我的博客</h1>
                <p class="subtitle">记录技术，分享生活</p>
                <div class="cta-buttons">
                    <RouterLink to="/article/list" class="btn primary">
                        浏览文章
                    </RouterLink>
                    <RouterLink to="/about" class="btn secondary">
                        关于我
                    </RouterLink>
                </div>
            </div>
            <div class="hero-image">
                <div class="floating-image">
                    <svg viewBox="0 0 500 400" xmlns="http://www.w3.org/2000/svg">
                        <path d="M100 100 C 200 0, 300 0, 400 100" stroke="#0366d6" stroke-width="2" fill="none" />
                        <path d="M100 200 C 200 100, 300 100, 400 200" stroke="#0366d6" stroke-width="2" fill="none" />
                        <path d="M100 300 C 200 200, 300 200, 400 300" stroke="#0366d6" stroke-width="2" fill="none" />
                        <circle cx="250" cy="200" r="50" fill="#0366d6" opacity="0.1" />
                        <path d="M200 150 L300 150 L250 250 Z" fill="#0366d6" opacity="0.2" />
                    </svg>
                </div>
            </div>
        </div>

        <div class="features">
            <div class="feature-card">
                <i class="icon-article"></i>
                <h3>技术文章</h3>
                <p>分享编程技术、开发经验和学习心得</p>
            </div>
            <div class="feature-card">
                <i class="icon-code"></i>
                <h3>代码实践</h3>
                <p>记录项目开发过程和问题解决方案</p>
            </div>
            <div class="feature-card">
                <i class="icon-share"></i>
                <h3>生活分享</h3>
                <p>分享生活中的点滴和思考</p>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue';

onMounted(() => {
    // 添加滚动动画效果
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add('animate');
            }
        });
    });

    document.querySelectorAll('.feature-card').forEach(card => {
        observer.observe(card);
    });
});
</script>

<style scoped>
.container {
    min-height: 100vh;
    padding: 0;
    margin: 0;
}

.hero {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 80px 24px;
    background: linear-gradient(135deg, #f6f8fa 0%, #e1e4e8 100%);
    min-height: 600px;
}

.hero-content {
    max-width: 600px;
}

.logo {
    width: 80px;
    height: 80px;
    margin-bottom: 24px;
}

.logo svg {
    width: 100%;
    height: 100%;
    filter: drop-shadow(0 2px 4px rgba(0, 0, 0, 0.1));
}

.title {
    font-size: 3.5rem;
    font-weight: 700;
    color: #2c3e50;
    margin-bottom: 16px;
    line-height: 1.2;
}

.subtitle {
    font-size: 1.5rem;
    color: #6a737d;
    margin-bottom: 32px;
}

.cta-buttons {
    display: flex;
    gap: 16px;
}

.btn {
    padding: 12px 24px;
    border-radius: 6px;
    font-size: 16px;
    font-weight: 500;
    text-decoration: none;
    transition: all 0.2s;
}

.btn.primary {
    background: #0366d6;
    color: #fff;
}

.btn.primary:hover {
    background: #0056b3;
    transform: translateY(-2px);
}

.btn.secondary {
    background: #fff;
    color: #0366d6;
    border: 1px solid #0366d6;
}

.btn.secondary:hover {
    background: #f6f8fa;
    transform: translateY(-2px);
}

.hero-image {
    position: relative;
    width: 500px;
    height: 400px;
}

.floating-image {
    position: absolute;
    animation: float 6s ease-in-out infinite;
}

.floating-image svg {
    width: 100%;
    height: 100%;
}

@keyframes float {
    0% {
        transform: translateY(0px);
    }
    50% {
        transform: translateY(-20px);
    }
    100% {
        transform: translateY(0px);
    }
}

.features {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
    gap: 24px;
    padding: 80px 24px;
    max-width: 1280px;
    margin: 0 auto;
}

.feature-card {
    background: #fff;
    padding: 32px;
    border-radius: 8px;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
    text-align: center;
    transition: all 0.3s;
    opacity: 0;
    transform: translateY(20px);
}

.feature-card.animate {
    opacity: 1;
    transform: translateY(0);
}

.feature-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.feature-card i {
    width: 48px;
    height: 48px;
    margin-bottom: 16px;
    background-size: contain;
    background-repeat: no-repeat;
    background-position: center;
    display: inline-block;
}

.icon-article {
    background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='%230366d6'%3E%3Cpath d='M19 3H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zm0 16H5V5h14v14zM7 10h2v7H7zm4-3h2v10h-2zm4 6h2v4h-2z'/%3E%3C/svg%3E");
}

.icon-code {
    background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='%230366d6'%3E%3Cpath d='M9.4 16.6L4.8 12l4.6-4.6L8 6l-6 6 6 6 1.4-1.4zm5.2 0l4.6-4.6-4.6-4.6L16 6l6 6-6 6-1.4-1.4z'/%3E%3C/svg%3E");
}

.icon-share {
    background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='%230366d6'%3E%3Cpath d='M18 16.08c-.76 0-1.44.3-1.96.77L8.91 12.7c.05-.23.09-.46.09-.7s-.04-.47-.09-.7l7.05-4.11c.54.5 1.25.81 2.04.81 1.66 0 3-1.34 3-3s-1.34-3-3-3-3 1.34-3 3c0 .24.04.47.09.7L8.04 9.81C7.5 9.31 6.79 9 6 9c-1.66 0-3 1.34-3 3s1.34 3 3 3c.79 0 1.5-.31 2.04-.81l7.12 4.16c-.05.21-.08.43-.08.65 0 1.61 1.31 2.92 2.92 2.92 1.61 0 2.92-1.31 2.92-2.92s-1.31-2.92-2.92-2.92z'/%3E%3C/svg%3E");
}

.feature-card h3 {
    font-size: 1.5rem;
    color: #2c3e50;
    margin-bottom: 12px;
}

.feature-card p {
    color: #6a737d;
    line-height: 1.6;
}

@media (max-width: 768px) {
    .hero {
        flex-direction: column;
        text-align: center;
        padding: 40px 24px;
    }

    .hero-content {
        margin-bottom: 40px;
    }

    .logo {
        margin: 0 auto 24px;
    }

    .title {
        font-size: 2.5rem;
    }

    .subtitle {
        font-size: 1.25rem;
    }

    .cta-buttons {
        justify-content: center;
    }

    .hero-image {
        width: 100%;
        height: 300px;
    }
}
</style>
