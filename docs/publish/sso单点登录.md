## 概念

> 单点登录英文全称Single Sign On，简称就是SSO。它的解释是：**在多个应用系统中，只需要登录一次，就可以访问其他相互信任的应用系统。**

## 传统方式登录

![login](C:\Users\wrp\Pictures\Saved Pictures\login.png)

1. 输入用户信息，调用登录接口
2. 服务器验证成功后，在用户的Session中标记登录状态为yes，返回给浏览器Cookie
3. 再次请求时，带上Cookie（默认是jsessionid）
4. 服务器通过Cookie找到session，判断这个用户是否登录

## 同域下的单点登录

问题与解决方案:

- Cookie是不能跨域的（Cookie的domain属性采用顶域，子域可以访问）
- session存在自己的应用内，是不共享的（共享Session，如采用Spring-Session）

## 不同域下的单点登录

问题：

不同域的Cookie是不共享的

---

单点登录的标准流程：



## Session跨域

> 客户端请求的时候，请求的服务器，不是同一个IP，端口，域名，主机名的时候，都称为跨域