## 编译JDK

1. 安装依赖

```bash
yum install zip unzip ant alsa-lib-devel gcc gcc-c++ freetype-devel libstdc++-static  make
```

2. 安装JDK

```bash
tar -zxvf jdk-11.0.15.1_linux-x64_bin.tar.gz
```

3. 编译

```bash
sudo bash ./configure --with-target-bits=64 --with-boot-jdk=/mydata/java/jdk-11.0.15.1 --with-debug-level=slowdebug ZIP_DEBUGINFO_FILES=0


sudo make all DISABLE_HOTSPOT_OS_VERSION_CHECK=OK ZIP_DEBUGINFO_FILES=0
```

