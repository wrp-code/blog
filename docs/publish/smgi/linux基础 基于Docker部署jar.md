# linux基础 基于Docker部署jar

## 1 概念

### 1.1 http 

>超文本传输协议（Hypertext Transfer Protocol，HTTP）是一个简单的请求-响应协议。基于TCP协议，无状态的，对于事务处理没有记忆能力，即第二次请求时并不知道前一个请求的是谁。默认端口80

```http
http://www.baidu.com/
```

### 1.2 https

> 超文本传输安全协议HTTPS （全称：Hypertext Transfer Protocol Secure ），是以安全为目标的 HTTP 通道，在HTTP的基础上通过传输加密和身份认证保证了传输过程的安全性 。比http更安全，使用时需要申请SSL证书。默认端口443

```http
https://www.baidu.com/
```

### 1.3 ssh

> SSH 为 Secure Shell的缩写，由 IETF 的网络工作小组(Network Working Group)所制定;SSH 为建立在应用层和传输层基础上的安全协议。SSH 是目前较可靠，专为远程登录会话和其他网络服务提供安全性的协议。利用 SSH 协议可以有效防止远程管理过程中的信息泄露问题。

```tex
ssh://root:************@39.107.183.228:22
```

### 1.4 端口

>在TCP、UDP协议的开头，会分别有16位来存储源端口号和目标端口号，所以端口个数是
>2^16-1=65535个。

分类

- **知名端口 (0-1023)** – 由IANA（互联网号码分配机构）分配给特定服务。在 Linux 系统上，只有以 root 身份运行的特权程序才能使用 1024 以下的端口。
- **注册端口 (1024-49151)** – 组织可以向 IANA 注册以用于特定服务的端口。
- **动态端口 (49152-65535)** – 由客户端程序使用。

常见端口

```tex
端口：21 FTP
端口：22 SSH
端口：80 HTTP
端口：443 HTTPS
端口：8080 TOMCAT
端口：5432 POSTGRESQL
```

### 1.5 TCP & UDP

> **传输控制协议（TCP）**：TCP（传输控制协议）定义了两台计算机之间进行可靠的传输而交换的数据和确认信息的格式，以及计算机为了确保数据的正确到达而采取的措施。就像打电话，需要对方接听才能对话，数据传输更可靠
>
> **用户数据报协议（UDP）**：UDP（用户数据报协议）是一个简单的面向数据报的传输层协议。就像发邮件，不需要对方确认收到邮件，速度更快

## 2 linux基础

> 和windows一样，是操作系统的一种

### 2.1 根据发行版本分类

- ***Ubuntu***：基于Debian发行版的一个优秀的Linux系统，非常适合初学者。
- ***CentOS***：以Red Hat Enterprise Linux为基础的一个免费操作系统，适合企业服务器和网络设备使用。
- Debian：一个稳定、安全、高效的开源Linux操作系统，适合服务器或桌面应用。
- Fedora：由Red Hat公司支持的社区项目，是一个快速、稳定、有创新力的Linux系统。
- Arch Linux：以简单、轻量、灵活、可定制为特点的Linux系统。
- openSUSE：一个基于SUSE Linux Enterprise的免费开源发行版，适合桌面和服务器。
- Manjaro：一款易用、兼容、快速、简洁的Linux系统，基于Arch Linux发行版。
- Slackware：最古老的Linux发行版之一，致力于稳定简单的设计。

### 2.2 目录结构

- /：目录属于==根目录==，是所有目录的绝对路径的起始点，Ubuntu 中的所有文件和目录都在跟目录下。

- ***/etc***：此目录非常重要，绝大多数系统和相关服务的==配置文件==都保存在这里，这个目录的内容一般只能由管理员进行修改。像密码文件、设置网卡信息、环境变量的设置等都在此目录中。此目录的 rcn.d 目录中存放不同启动级别所启动的服务，network 目录放置网卡的配置信息等。

- /home：此目录是所有==普通用户的宿主目录==所在地，在一般情况下，如果想要对用户进行磁盘限额功能，最好将此目录单独分区。

- ***/bin*** :此目录中放置了所有用户==能够执行的命令==。

- /sbin：此目录中放置了一般是只有==系统管理员才能执行的命令==。

- /dev：此目录中保存了所有==设备文件==，例如，使用的分区：/dev/hda1，/dev/cdrom 等。
- /mnt：此目录主要是作为==挂载点==使用。
- ***/usr***：此目录包含了==所有的命令、说明文件、程序库==等，此目录下有很多重要的目录，常见的有：/usr/local 这个目录包含管理员自己安装的程序；/usr/share 包含文件的帮助文件；/usr/bin 和/usr/sbin 包含了所有的命令。
- /var：包含了==日志文件、计划性任务和邮件==等内容。
- /lib：包含了系统的==函数库==文件。
- /lost+found：包含了==系统修复时的回复文件==。
- /tmp：包含了==临时文件==。
- /boot：系统的==内核==所在地，也是启动分区。
- /media：主要用于==挂载多媒体设备==。
- /root：==系统管理员的宿主目录==。

### 2.3 权限

```bash
drwx------  8 root root  4096 Jun 25 19:35 ./
drwxr-xr-x 20 root root  4096 Dec 13  2022 ../
-rw-------  1 root root 23173 Jun 25 18:56 .bash_history
-rw-r--r--  1 root root  3106 Oct 15  2021 .bashrc
drwx------  3 root root  4096 Nov  4  2022 .cache/
drwx------  3 root root  4096 Nov  4  2022 .config/
-rw-------  1 root root    20 Jun 25 16:10 .lesshst
-rw-------  1 root root    67 Apr 11 08:59 nohup.out
drwxr-xr-x  2 root root  4096 Nov  4  2022 .pip/
-rw-r--r--  1 root root   161 Jul  9  2019 .profile
-rw-r--r--  1 root root   206 Dec 13  2022 .pydistutils.cfg
-rw-------  1 root root    12 Dec 13  2022 .rediscli_history
drwxr-xr-x  2 root root  4096 Nov  4  2022 .rpmdb/
drwx------  3 root root  4096 Nov  4  2022 snap/
drwx------  2 root root  4096 Nov  4  2022 .ssh/
-rw-------  1 root root 15125 Jun 19 15:03 .viminfo
-rw-------  1 root root   276 Jun 25 19:35 .Xauthority

```

第一位 

```tex
当为[ d ]则是目录
当为[ - ]则是文件；
若是[ l ]则表示为链接文档 ( link file )；
若是[ b ]则表示为装置文件里面的可供储存的接口设备 ( 可随机存取装置 )；
若是[ c ]则表示为装置文件里面的串行端口设备，例如键盘、鼠标 ( 一次性读取装置 )。
```

后面共9位，三组，每组三位,分别代表创建者权限、组权限和其他用户权限

每位

```tex
[ r ]代表可读(read)
[ w ]代表可写(write)
[ x ]代表可执行(execute)
```

### 2.4 常用命令

- 1.文件和目录管理命令
```bash
ll: 列出当前目录下的文件和文件夹。
ls：列出当前目录下的文件和文件夹。
cd：切换到指定目录。
pwd：显示当前工作目录的路径。
mkdir：创建新目录。
rm：删除文件或目录。
cp：复制文件或目录。
mv：移动文件或目录。
touch：创建空文件或更新文件的时间戳。
cat：查看文件内容。
grep：在文件中搜索指定的模式
```

- 2.系统管理命令

```bash
top：显示系统资源的实时使用情况。
ps：列出当前运行的进程。
kill：终止指定的进程。
shutdown：关机或重启系统。
reboot：重新启动系统。
```

- 3.网络管理命令

```bash
ping：测试与另一台计算机之间的连接。
ifconfig：显示和配置网络接口信息。
netstat：显示网络连接和路由表信息。
ssh：通过安全shell协议远程登录到其他计算机。
```

- 4.软件管理命令

```bash
apt：包管理器，用于安装、更新和卸载软件包。
dpkg：基于Debian的软件包管理器。
snap：用于安装和管理Snap软件包。
```

- 5.用户管理命令

```bash
sudo：以管理员权限执行命令。
adduser：创建新用户。
passwd：更改用户密码。
su：切换用户。
```

- 6.文件查找和搜索命令

```bash
find：在指定目录中查找文件或目录。
locate：通过数据库快速查找文件。
whereis：显示指定命令的位置。
which：显示指定命令的完整路径。
```

- 7.压缩和解压缩命令

```bash
tar：创建、提取和压缩tar文件。
gzip：压缩文件。
gunzip：解压缩文件。
```

### 2.5 防火墙

> Ubuntu自带的防火墙是一种非常强大的工具，能够保护系统免受来自互联网的攻击。

Ubuntu的防火墙配置文件是 `ufw`。该文件默认情况下是处于禁止状态的，不会拒绝任何入站或出站的流量。

首先，您需要安装 `ufw`（如果这个工具未安装）

```bash
sudo apt-get install ufw
```

要查看当前的防火墙设置(显示防火墙的状态及其规则列表)

```bash
sudo ufw status verbose
```

添加需要打开的==端口==或服务到防火墙规则中

```bash
sudo ufw allow ssh
sudo ufw allow 22/tcp #允许22端口的TCP
sudo ufw allow 22/udp #允许22端口的UDP
sudo ufw denty 22/tcp #阻止22端口的TCP
sudo ufw denty 22/udp #阻止22端口的UDP
```

打开Ubuntu的防火墙

```bash
sudo ufw enable
```

禁用Ubuntu的防火墙

```bash
sudo ufw disable
```

## 3 常见部署方式

### 3.1 nginx部署前端

将静态文件放在html目录下即可

### 3.2 jar部署

```bash
java -jar xxx.jar 当退出或关闭shell时，程序就会停止掉。
以下方法可让jar运行后一直在后台运行。
1. java -jar xxx.jar & 
2. nohup java -jar xxxx.jar &
nohup java -jar -Dspring.profiles.active=pro -Dserver.port=11001 province_patrol-0.0.1-SNAPSHOT.jar  &
```

## 4 docker

>Docker 是一个开源的应用容器引擎，让开发者可以打包他们的应用以及依赖包到一个可移植的镜像中，然后发布到任何流行的 Linux或Windows操作系统的机器上，也可以实现虚拟化

### 4.1 镜像命令

```bash
docker pull nginx[:标签] 标签即版本 默认下载最新
docker images 镜像信息
docker rmi nginx/镜像ID 移除最新版nginx
```

### 4.2 容器命令

```bash
docker rm 名称/id   删除已经停止的docker应用，删除正在运行的话加-f
docker stop 名称/id 停止docker应用
docker start 名称/id 再次启动docker应用
docker restart 名称/id 重启docker应用
docker update 名称/id --restart=always 修改docker应用的配置项
docker exec -it 名称/id /bin/bash 或/bin/sh进入到应用的控制台
exit 退出应用的控制台
```

### 4.3 其他常用命令

```bash
docker ps [-a]查看启动的docker镜像 -a启动过的也会被查询到
docker logs -f 名称/id 查看docker应用日志
docker cp id:目录 本机目录 复制文件到本机
docker cp 本机目录  id:目录 复制文件到容器
docker run [OPTIONS] IMAGE [COMMAND] [ARG...] 启动
docker run 设置项 镜像名 镜像启动运行的命令（镜像里面默认有，一般不写）
常用配置项 
	--name=mynginx 设置应用名称
	-d 后台运行
	--restart=always 开机自启
	-p 88:80 配置端口主机的88到80
	-v 主机目录/docker应用目录:ro 挂载 主机目录和应用目录挂载  ro只读
```

### 4.4 docker构建镜像

创建DockerFile文件

```dockerfile
FROM openjdk:8
ADD jmeter-demo-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
```

将jar文件放在DockerFile同级目录

docker 构建镜像

```bash
docker build -f DockerFile -t app:1.0 .
```

启动

```bash
docker run -d -p 8080:8080 --name app --restart always app:1.0
```

查看日志

```bash
docker logs -f 容器id
```

## 5 CI/CD

> CI、CD 其实是三个概念，包含了一个 CI 和两个 CD，
>
> CI全称 Continuous Integration，表示持续集成，
>
> CD包含 Continuous Delivery和 Continuous Deployment，分别是持续交付和持续部署。
>
> 这三个概念之间是有前后依赖关系的。
> CI/CD 并不是一个工具，它是一种软件开发实践，核心是通过引入自动化的手段来提高软件交付效率。
>
> CI/CD 最终目的：让工程师更快 & 更高质量 & 更简单的交付软件！

### 5.1 定义

- 持续集成  持续频繁的（每天多次）将本地代码“集成”到主干分支，并保证主干分支可用

- 持续交付 持续频繁地将软件的新版本交付到类生产环境（类似于预发），交付给测试、产品验收。
- 持续部署 “自动”将代码部署到生产环境

### 5.2 实现方式

```tex
Git+Jenkins+Harbor+Docker
```

批量重启
docker restart $(docker ps | grep redis | awk '{print $1}')
