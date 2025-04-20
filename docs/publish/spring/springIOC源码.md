## 1.IOC理论

> IoC 全称为 `Inversion of Control`，翻译为 “控制反转”。所谓 IOC ，就是由 Spring IOC 容器来负责对象的生命周期和对象之间的关系

### 1.1注入方式

- 构造方法注入
- set方法注入
- 接口注入(被依赖的对象实现不必要的接口，侵入性强，不推荐)

### 1.2各个组件

![类图1](https://www.cmsblogs.com/images/group/sike-java/sike-java-spring-ioc/202105092051247892.png)

#### 1.2.1Resource

Resource，对资源的抽象，它的每一个实现类都代表了一种资源的访问策略。要自定义资源，只需要继承AbstractResource即可。

- FileSystemResource 对java.io.File的封装。实现了WritableResource接口，从5.0开始，使用NIO2 进行读写
- ByteArrayResource 对字节数组的封装
- UrlResource 对java.net.URL的封装，内部委派URL操作
- ClassPathResource class path的实现，使用给定的 ClassLoader 或者给定的 Class 来加载资源。
- InputStreamResource 对InputStream的封装

![source](D:\Projects\Learn\spring\static\ClassPathResource.png)

有了资源，就应该有资源加载，Spring 利用 ResourceLoader 来进行统一资源加载。

```java
public class DefaultResourceLoader implements ResourceLoader {
    public DefaultResourceLoader() {
		this.classLoader = ClassUtils.getDefaultClassLoader();
	}
    
    public DefaultResourceLoader(@Nullable ClassLoader classLoader) {
		this.classLoader = classLoader;
	}
    
    public void setClassLoader(@Nullable ClassLoader classLoader) {
		this.classLoader = classLoader;
	}
    
    @Override
	@Nullable
	public ClassLoader getClassLoader() {
		return (this.classLoader != null ? this.classLoader : ClassUtils.getDefaultClassLoader());
	}
    
    // ResourceLoader 的资源加载策略就封装 DefaultResourceLoader中
    @Override
	public Resource getResource(String location) {
		Assert.notNull(location, "Location must not be null");

		for (ProtocolResolver protocolResolver : this.protocolResolvers) {
			Resource resource = protocolResolver.resolve(location, this);
			if (resource != null) {
				return resource;
			}
		}

		if (location.startsWith("/")) {
			return getResourceByPath(location);
		}
		else if (location.startsWith(CLASSPATH_URL_PREFIX)) {
			return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()), getClassLoader());
		}
		else {
			try {
				// Try to parse the location as a URL...
				URL url = new URL(location);
				return (ResourceUtils.isFileURL(url) ? new FileUrlResource(url) : new UrlResource(url));
			}
			catch (MalformedURLException ex) {
				// No URL -> resolve as resource path.
				return getResourceByPath(location);
			}
		}
	}
    
}
```

ProtocolResolver ，用户自定义协议资源解决策略，作为 DefaultResourceLoader 的 **SPI**([参考地址](https://www.jianshu.com/p/3a3edbcd8f24))，它允许用户自定义资源加载协议。

1. 自定义类，实现ProtocolResolver 接口
2. 调用`DefaultResourceLoader.addProtocolResolver()`

![loader](D:\Projects\Learn\spring\static\FileSystemResourceLoader.png)

- DefaultResourceLoader ResourceLoader的默认实现，`getResource(String location) `是资源加载策略的核心实现。
- FileSystemResourceLoader 重写了`getResourceByPath(String path)`
- ClassRelativeResourceLoader 重写了`getResourceByPath(String path)`增加了一个用于加载资源的Class类型的属性
- PathMatchingResourcePatternResolver 增加了`Resource[] getResources(String locationPattern)`方法，返回多个资源；`getResource(Strin location)`直接委派给ResourceLoader。??????设计模式是什么?????

```java
public class PathMatchingResourcePatternResolver implements ResourcePatternResolver {
    private final ResourceLoader resourceLoader;
    private PathMatcher pathMatcher = new AntPathMatcher();
    
    public PathMatchingResourcePatternResolver() {
		this.resourceLoader = new DefaultResourceLoader();
	}
    
    public PathMatchingResourcePatternResolver(ResourceLoader resourceLoader) {
		Assert.notNull(resourceLoader, "ResourceLoader must not be null");
		this.resourceLoader = resourceLoader;
	}
    
    public PathMatchingResourcePatternResolver(@Nullable ClassLoader classLoader) {
		this.resourceLoader = new DefaultResourceLoader(classLoader);
	}
    // 直接委派给ResourceLoader
    @Override
	public Resource getResource(String location) {
		return getResourceLoader().getResource(location);
	}
    
    // 通过匹配路径，获取到多个资源
    @Override
	public Resource[] getResources(String locationPattern) throws IOException {
		// 以 classpath*: 开头
		if (locationPattern.startsWith(CLASSPATH_ALL_URL_PREFIX)) {

            // 路径包含通配符
			if (getPathMatcher().isPattern(locationPattern.substring(CLASSPATH_ALL_URL_PREFIX.length()))) {
				return findPathMatchingResources(locationPattern);
			}
			else {
                // 路径不包含通配符
				return findAllClassPathResources(locationPattern.substring(CLASSPATH_ALL_URL_PREFIX.length()));
			}
		}
		else {
			int prefixEnd = (locationPattern.startsWith("war:") ? locationPattern.indexOf("*/") + 1 :
					locationPattern.indexOf(':') + 1);
            // 路径包含通配符
			if (getPathMatcher().isPattern(locationPattern.substring(prefixEnd))) {
				// a file pattern
				return findPathMatchingResources(locationPattern);
			}
			else {
				// a single resource with the given name
				return new Resource[] {getResourceLoader().getResource(locationPattern)};
			}
		}
	}
    
}
```



#### 1.2.2BeanFactory

​		BeanFactory 是一个 bean 容器，它内部维护着一个 BeanDefinition map ，并可根据 BeanDefinition 的描述进行 bean 的创建和管理。DefaultListableBeanFactory是最终默认实现，他实现了所有接口。

![beanFactory](D:\Projects\Learn\spring\static\DefaultListableBeanFactory.png)

#### 1.2.3BeanDefinition

BeanDefinition 用来描述 Spring 中的 Bean 对象。

![BeanDefinition](D:\Projects\Learn\spring\static\AbstractBeanDefinition.png)



#### 1.2.4BeanDefinitionReader

读取 Spring 的配置文件的内容，并将其转换成BeanDefinition。

以XmlBeanDefinitionReader为例。

```java
protected int doLoadBeanDefinitions(InputSource inputSource, Resource resource) throws BeanDefinitionStoreException {
		try {
			Document doc = doLoadDocument(inputSource, resource);
			return registerBeanDefinitions(doc, resource);
		}
    	...
}

protected Document doLoadDocument(InputSource inputSource, Resource resource) throws Exception {
		return this.documentLoader.loadDocument(
            inputSource, 
            getEntityResolver(), 
            this.errorHandler,
			getValidationModeForResource(resource), 
            isNamespaceAware());
}
```



`doLoadBeanDefinitions()`主要就是做了三件事情：

1. 调用 `getValidationModeForResource()` 获取 xml 文件的验证模式
2. 调用 `loadDocument()` 根据 xml 文件获取相应的 Document 实例。
3. 调用 `registerBeanDefinitions()` 注册 Bean 实例。

![BeanDefinitionReader](C:\Users\wrp\Pictures\Saved Pictures\202105092051283977.png)

---

##### 获取验证模式

> XML 文件的验证模式保证了 XML 文件的正确性

- DTD 文档类型定义 规定了xml语法 不是xml文档，较差
- XSD XML Schema 语言 是xml文档，推荐使用

相对于 DTD，XSD 具有如下优势：

- XML Schema基于XML,没有专门的语法
- XML Schema可以象其他XML文件一样解析和处理
- XML Schema比DTD提供了更丰富的数据类型.
- XML Schema提供可扩充的数据模型。
- XML Schema支持综合命名空间
- XML Schema支持属性组。

```xml
<?xml version="1.0" encoding="UTF-8"?>
    <!DOCTYPE beans PUBLIC  "-//SPRING//DTD BEAN//EN"  "http://www.springframework.org/dtd/spring-beans.dtd">

```

---

##### 加载document

获取 Document 的策略由接口 DocumentLoader 定义，提供默认的实现类DefaultDocumentLoader 

```java
public Document loadDocument(InputSource inputSource, EntityResolver entityResolver,
                ErrorHandler errorHandler, int validationMode, boolean namespaceAware) throws Exception {
    
            DocumentBuilderFactory factory = createDocumentBuilderFactory(validationMode, namespaceAware);
            if (logger.isDebugEnabled()) {
                logger.debug("Using JAXP provider [" + factory.getClass().getName() + "]");
            }
            DocumentBuilder builder = createDocumentBuilder(factory, entityResolver, errorHandler);
            return builder.parse(inputSource);
        }
```

参数如下：

- inputSource 加载 Document 的 Resource 源
- entityResolver 解析文件的解析器
- errorHandler 加载 Document 对象的过程的错误处理器
- validationMode 验证模式
- namespaceAware 命名空间支持；提供对 XML 名称空间的支持，则为true

```java
public interface EntityResolver {
    // publicId: 被引用的外部实体的公共标识符，如果没有提供，则返回null
    // systemId: 被引用的外部实体的系统标识符 这两个参数的实际内容和具体的验证模式有关系。
    public abstract InputSource resolveEntity (String publicId,String systemId) throws SAXException, IOException;
}

protected EntityResolver getEntityResolver() {
    if (this.entityResolver == null) {
        ResourceLoader resourceLoader = getResourceLoader();
        if (resourceLoader != null) {
            this.entityResolver = new ResourceEntityResolver(resourceLoader);
        }
        else {
            this.entityResolver = new DelegatingEntityResolver(getBeanClassLoader());
        }
    }
    return this.entityResolver;
}
```

- ResourceEntityResolver：继承自 DelegatingEntityResolver，通过 ResourceLoader 来解析实体的引用。
- DelegatingEntityResolver：EntityResolver 的实现，分别代理了 dtd 的 BeansDtdResolver 和 xml schemas 的 PluggableSchemaResolver。
- BeansDtdResolver ： spring bean dtd 解析器。EntityResolver 的实现，用来从 classpath 或者 jar 文件加载 dtd。
- PluggableSchemaResolver：使用一系列 Map 文件将 schema url 解析到本地 classpath 资源

---

##### 注册Bean实例

```java
public int registerBeanDefinitions(Document doc, Resource resource) throws BeanDefinitionStoreException {
    BeanDefinitionDocumentReader documentReader = createBeanDefinitionDocumentReader();
    int countBefore = getRegistry().getBeanDefinitionCount();
    documentReader.registerBeanDefinitions(doc, createReaderContext(resource));
    return getRegistry().getBeanDefinitionCount() - countBefore;
}
// BeanDefinitionDocumentReader接口注册BeanDefinition
void registerBeanDefinitions(Document doc, XmlReaderContext readerContext) throws BeanDefinitionStoreException;
// DefaultBeanDefinitionDocumentReader提供默认实现
@Override
public void registerBeanDefinitions(Document doc, XmlReaderContext readerContext) {
    this.readerContext = readerContext;
    logger.debug("Loading bean definitions");
    Element root = doc.getDocumentElement();
    doRegisterBeanDefinitions(root);
}
protected void doRegisterBeanDefinitions(Element root) {
    BeanDefinitionParserDelegate parent = this.delegate;
    this.delegate = createDelegate(getReaderContext(), root, parent);

    if (this.delegate.isDefaultNamespace(root)) {
        // 处理 profile
        String profileSpec = root.getAttribute(PROFILE_ATTRIBUTE);
        if (StringUtils.hasText(profileSpec)) {
            String[] specifiedProfiles = StringUtils.tokenizeToStringArray(
                profileSpec, BeanDefinitionParserDelegate.MULTI_VALUE_ATTRIBUTE_DELIMITERS);
            if (!getReaderContext().getEnvironment().acceptsProfiles(specifiedProfiles)) {
                return;
            }
        }
    }

    // 解析前处理
    preProcessXml(root);
    // 解析
    parseBeanDefinitions(root, this.delegate);
    // 解析后处理
    postProcessXml(root);

    this.delegate = parent;
}

protected void parseBeanDefinitions(Element root, BeanDefinitionParserDelegate delegate) {
    if (delegate.isDefaultNamespace(root)) {
        NodeList nl = root.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if (node instanceof Element) {
                Element ele = (Element) node;
                if (delegate.isDefaultNamespace(ele)) {
                    parseDefaultElement(ele, delegate);
                }
                else {
                    delegate.parseCustomElement(ele);
                }
            }
        }
    }
    else {
        delegate.parseCustomElement(root);
    }
}
```

Spring的两种Bean声明方式：

- 配置文件声明 `<bean id="studentService" class="org.springframework.core.StudentService"/>` 使用`parseDefaultElement()`进行解析
- 自定义注解 `<tx:annotation-driven>`使用`delegate.parseCustomElement()`进行解析

```java
private void parseDefaultElement(Element ele, BeanDefinitionParserDelegate delegate) {
    // 对 import 标签的解析 
    if (delegate.nodeNameEquals(ele, IMPORT_ELEMENT)) {
        importBeanDefinitionResource(ele);
    }
    // 对 alias 标签的解析
    else if (delegate.nodeNameEquals(ele, ALIAS_ELEMENT)) {
        processAliasRegistration(ele);
    }
    // 对 bean 标签的解析
    else if (delegate.nodeNameEquals(ele, BEAN_ELEMENT)) {
        processBeanDefinition(ele, delegate);
    }
    // 对 beans 标签的解析
    else if (delegate.nodeNameEquals(ele, NESTED_BEANS_ELEMENT)) {
        // recurse
        doRegisterBeanDefinitions(ele);
    }
}
```



#### 1.2.5ApplicationContext

继承 BeanFactory，所以它是 BeanFactory 的扩展升级版

- 继承 ApplicationEventPublisher ，提供强大的事件机制。
- 继承 MessageSource，提供国际化的标准访问策略。
- 扩展 ResourceLoader，可以用来加载多个 Resource，可以灵活访问不同的资源。
- 对 Web 应用的支持。



![applicationContext](C:\Users\wrp\Pictures\Saved Pictures\202105092051286288.png)

### 1.3BeanDefinition注册过程

![注册过程](C:\Users\wrp\Pictures\Saved Pictures\BeanDefinition注册过程.png)

## 2.Bean加载 getBean

