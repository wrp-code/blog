## 1. 理论

### 1.1 ServletContainerInitializer

> servlet3.0规定，servlet容器在启动时，会执行ServletContainerInitializer#onStartup方法，必须在META-INF/services/javax.servlet.ServletContainerInitializer文件中声明其子类的全限定类名



![image-20221206103718406](C:\Users\wrp\AppData\Roaming\Typora\typora-user-images\image-20221206103718406.png)

```tex
org.springframework.web.SpringServletContainerInitializer
```

### 1.2 @EnableWebMvc

> @EnableWebMvc作用就是导入SpringMVC的配置类，包括了很多mvc默认实现组件都会导入到容器中。

## 2. 源码

### 2.1 ServletContainerInitializer加载过程

1. tomcat在启动的时候会去扫描所有jar包的META-INF/services/javax.servlet.ServletContainerInitializer文件

```java
public class ContextConfig implements LifecycleListener {
    protected final Map<Class<?>, Set<ServletContainerInitializer>> typeInitializerMap =
            new HashMap<>();
    
    protected void webConfig() {
        
        if (ok) {
            processServletContainerInitializers();
        }
        
        if (ok) {
            for (Map.Entry<ServletContainerInitializer,
                    Set<Class<?>>> entry :
                        initializerClassMap.entrySet()) {
                if (entry.getValue().isEmpty()) {
                    context.addServletContainerInitializer(
                            entry.getKey(), null);
                } else {
                    context.addServletContainerInitializer(
                            entry.getKey(), entry.getValue());
                }
            }
        }
    }
    
    // 加载ServletContainerInitializer
	protected void processServletContainerInitializers() {
        WebappServiceLoader<ServletContainerInitializer> loader = new WebappServiceLoader<>(context);
        // 探测jar包下的META-INF/services/javax.servlet.ServletContainerInitializer文件
        List<ServletContainerInitializer> detectedScis = loader.load(ServletContainerInitializer.class);
        
        for (ServletContainerInitializer sci : detectedScis) {
        	HandlesTypes ht = sci.getClass().getAnnotation(HandlesTypes.class);
            Class<?>[] types = ht.value();
            for (Class<?> type : types) {
                typeInitializerMap.put(type, scis);
            }
        }
    }
}
```

```java
public class WebappServiceLoader<T> {
    public List<T> load(Class<T> serviceType) throws IOException {
        // configFile就是META-INF/services/javax.servlet.ServletContainerInitializer
        String configFile = SERVICES + serviceType.getName();

            // Obtain the Container provided service configuration files.
        ClassLoader loader = context.getParentClassLoader();
        if (loader == null) {
            containerResources = ClassLoader.getSystemResources(configFile);
        } else {
            containerResources = loader.getResources(configFile);
        }
    }
}
```

```java
public class StandardContext extends ContainerBase
        implements Context, NotificationEmitter {
    private Map<ServletContainerInitializer,Set<Class<?>>> initializers =
        new LinkedHashMap<>();
    
    @Override
    public void addServletContainerInitializer(
            ServletContainerInitializer sci, Set<Class<?>> classes) {
        initializers.put(sci, classes);
    }

    @Override
    protected synchronized void startInternal() throws LifecycleException {
		for (Map.Entry<ServletContainerInitializer, Set<Class<?>>> entry :
             initializers.entrySet()) {
            try {
                // 执行SpringServletContainerInitializer#onStartup，将所有的WebApplicationInitializer作为参数传递过去
                entry.getKey().onStartup(entry.getValue(),
                                         getServletContext());
            } catch (ServletException e) {
                log.error(sm.getString("standardContext.sciFail"), e);
                ok = false;
                break;
            }
        }
    }
}
```



```java
@HandlesTypes(WebApplicationInitializer.class)
public class SpringServletContainerInitializer implements ServletContainerInitializer {}
```





## 3. 实操

### 3.1 创建Maven项目

![image-20221205122704393](C:\Users\wrp\AppData\Roaming\Typora\typora-user-images\image-20221205122704393.png)

### 3.2 pom.xml引入依赖

```xml
<dependencies>
    <!-- 添加springmvc依赖 -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>5.3.9</version>
    </dependency>

    <!-- 内嵌tomcat-->
    <dependency>
        <groupId>org.apache.tomcat.embed</groupId>
        <artifactId>tomcat-embed-core</artifactId>
        <version>9.0.44</version>
    </dependency>

    <!-- jsp解析 ：不加这个依赖会报错：java.lang.ClassNotFoundException: org.apache.jasper.servlet.JspServlet-->
    <dependency>
        <groupId>org.apache.tomcat.embed</groupId>
        <artifactId>tomcat-embed-jasper</artifactId>
        <version>9.0.44</version>
    </dependency>

    <!-- 添加jackson配置 -->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-core</artifactId>
        <version>2.11.4</version>
    </dependency>

    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.11.4</version>
    </dependency>
</dependencies>
```

### 3.3 创建配置文件

```java
@Configuration
@EnableWebMvc
@ComponentScan("com.wrp.mvc")
public class MyConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * 指定父容器的配置文件
     * 这里设置为空，不会创建父容器
     * @return
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    /**
     * 指定springmvc容器的配置文件
     * @return
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{MyConfig.class};
    }

    /**
     * 指定DispatcherServlet拦截路径
     * @return
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
```

### 3.4 创建启动类

```java
public class MainApplication {
    public static void run() {
        System.out.println("开始启动tomcat");
        Tomcat tomcat = new Tomcat();

        tomcat.setPort(8080);
        tomcat.setHostname("localhost");
        tomcat.setBaseDir(".");
        //设置contextPath和baseDir
        tomcat.addWebapp("/boot", System.getProperty("user.dir") + "/mvc/src/main");
        tomcat.getConnector();
        try {
            tomcat.start();
            System.out.println("启动tomcat完毕");
        } catch (LifecycleException e) {
            e.printStackTrace();
            System.out.println("tomcat启动异常");
        }
    }

    public static void main(String[] args) {
        MainApplication.run();
    }
}
```

### 3.5 测试类

```java
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public String hello(@RequestParam("name") String name) {
        return "hello, " + name;
    }
}
```

### 3.6 配置自定义拦截器[可选]

```java
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**");
    }
}

public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("自定义拦截器");
        return true;
    }
}
```

### 3.7 解决StringHttpMessageConverter导致的乱码问题

```java
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        StringHttpMessageConverter converter = new StringHttpMessageConverter();
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        MediaType textPlain = MediaType.valueOf("text/plain;charset=UTF-8");
        converter.setSupportedMediaTypes(Arrays.asList(textPlain));
        converters.add(converter);
    }
}
```

### 3.8 测试结果

![image-20221205132920804](C:\Users\wrp\AppData\Roaming\Typora\typora-user-images\image-20221205132920804.png)

## 4. 总结

