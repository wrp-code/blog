## Bmob

### 与小程序集成

1. [下载](https://github.com/bmob/hydrogen-js-sdk/)

复制`./dist/Bmob-1.6.1.min.js`到微信小程序的`./utils`目录下

2. app.js文件添加

```js
// app.js
var Bmob = require('utils/Bmob-1.6.1.min.js');
Bmob.initialize("0f0cb025675c618641040294e120bdef", "043e9fb819c11667e35a1d8b0ae26a2a");
```

`Bmob.initialize("你的Application ID", "你的REST API Key");`两者可以登录bmob官网后查看应用

3. 在需要调用Bmob的页面的js文件中引入

```js
var Bmob = require('../../utils/Bmob-1.6.1.min.js'); 
```

### Bmob的简单CRUD

1. 添加

```js
// 指定表名tableName
const query = Bmob.Query('tableName');
// 设置字段，及字段值
query.set("name","Bmob")
query.set("cover","后端云")
// 调用save方法，保存数据到Bmob云端
query.save().then(res => {
  // 请求成功后的操作
  console.log(res)
}).catch(err => {
  // 请求失败了的操作
  console.log(err)
})
```

2. 删除

```js
// query绑定表
const query = Bmob.Query('tableName');
// 调用destroy方法，删除指定id的数据项
query.destroy('objectId').then(res => {
  console.log(res)
}).catch(err => {
  console.log(err)
})
```

3. 修改

```js
// 绑定表
const query = Bmob.Query('tableName');
// 设置主键id
query.set('id', 'objectId') //需要修改的objectId
// 设置需要修改的字段
query.set('nickName', 'Bmob后端云')
// 调用save进行修改，如果没有设置主键id，则保存新记录；如果有则修改；saveOrUpdate
query.save().then(res => {
  console.log(res)
}).catch(err => {
  console.log(err)
})
```

4. 查询

```js
// 根据id查询
const query = Bmob.Query('tableName');
query.get('objectId').then(res => {
  console.log(res)
}).catch(err => {
  console.log(err)
})

// 查询表的所有记录
const query = Bmob.Query("tableName");
query.find().then(res => {
    console.log(res)
});

// 根据条件查询 equalTo 方法支持 "==","!=",">",">=","<","<="
query.equalTo("isLike", "==", 100);
// 指定列
query.select("title");

// 分页查询
// limit限制返回的记录数 作用：pageSize
query.limit(10);
// skip用于跳过第几条查询数据 作用：pageNo
query.skip(10); 

// 统计表记录
query.count().then(res => {
  console.log(`共有${res}条记录`)
});
```

## 小程序

### 数据绑定

1. 显示

```html
<text>{{ msg }}<text>
```

msg在js的`data:{}`中定义

```js
Page({
  data: {
    msg: 'hello'
  }
})
```

2. 修改数据

```js
Page({
  data: {
    count: 0
  },
  // 事件回调函数
  changeCount(event){
    // 类似于React的绑定机制
    this.setData({
      count: event.detail.value
    })
  }
})
```

### 事件绑定

1. 点击事件

```html
<button bindtap="clickHandle">点击</button>
```

2. 输入事件

```html
<input bindinput="inputHandle"></input>
```

3. 改变事件

```html
... bindchange=""
```

事件处理函数有一个参数

```js
Page({
  clickHandle(event) {
    console.log(evnet.detail.value);
  }
})
```

**传参**

```html
<button bindtap="btnHandle" data-info="{{2}}">
  传参
</button>
```

- 以data-*自定义参数
- info会被解析为参数名
- btnHandle中通过`event.target.dataset.info`获取参数值。所有参数都被封装到`event.target.dataset`对象中。

### 条件渲染

1. wx:if 动态创建\移除元素

```html
<block wx:if="{{true}}">
	<view>1</view>
</block>
```

2. hidden 设置hidden样式切换

```html
<view hidden="{{ condition }}">ti</view>
```

### 列表循环

```html
<view wx:for="{{arr}}" wx:for-index="index" wx:for-item="item" wx:key="index">
  {{item.name}}
</view>
```

- wx:for-index 指定索引index的名称
- wx:for-item 指定循环遍历的每一个item名

### 路由跳转

1. 声明式导航 <navigator>

```html
// 导航到tabBar页面
<navigator url="/pages/index/index" open-type="switchTab">导航到tabBar</navigator>

// 导航到非tabBar页面
<navigator url="/pages/index/index" open-type="navigate">导航到tabBar</navigator>
```

- url 跳转的页面
- open-type 跳转的方式
  - tabBar时，为 switchTab
  - 非tabBar时，为 navigate， 可以省略

后退时：

```html
<navigator open-type='navigateBack' delta='1'></navigator>
```

- delta 要后退的层级，默认1 可以省略

2. 编程式导航

```js
// 导航到tabBar
wx.switchTab({
  url:''
});

// 导航到非tabBar
wx.navigateTo({
  url:''
})

// 后退
wx.navigateBack({
  delta: 1
})
```

可以再url中传参 `?name=zs&age=18`

```js
// 在onLoad生命钩子中获取参数
onLoad:function(options) {
  console.log(options);
}
```

