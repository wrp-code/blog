# 基础

1. Vue 是什么

>Vue (读音 /vjuː/，类似于 **view**) 是一套用于构建用户界面的**渐进式框架**。

- 渐进式框架

用你想用或者能用的功能特性，你不想用的部分功能可以先不用。**VUE不强求你一次性接受并使用它的全部功能特性**。

2. Vue是响应式的==待深入==
3. 数据绑定方式

```html
方式1：双大括号。可以使用单个表达式，如三目运算符
<div id="counter">
  Counter: {{ counter }}
</div>

方式2：v-bind指令：将这个元素节点的 title attribute 和当前活跃实例的 message property 保持一致。不渲染null和undefine
<!-- 完整语法 -->
<a v-bind:href="url"> ... </a>

<!-- 缩写 -->
<a :href="url"> ... </a>

<!-- 动态参数的缩写 -->
<a :[key]="url"> ... </a>

方式3：v-model指令：双向绑定
<div id="two-way-binding">
  <p>{{ message }}</p>
  <input v-model="message" />
</div>

方式4：v-once指令：一次性地插值
<span v-once>这个将不会改变: {{ msg }}</span>

方式5：v-html指令：渲染HTML文本
<p>Using v-html directive: <span v-html="rawHtml"></span></p>
```

4. 绑定事件 v-on

```html
<!-- 完整语法 -->
<a v-on:click="doSomething"> ... </a>

<!-- 缩写 -->
<a @click="doSomething"> ... </a>

<!-- 动态参数的缩写 -->
<a @[event]="doSomething"> ... </a>
```

5. 条件渲染

```html
<div id="conditional-rendering">
  <span v-if="seen">现在你看到我了</span>
</div>
```

6. Vue的过渡效果==待深入==
7. 循环遍历

```html
还需要指定key
<div id="list-rendering">
  <ol>
    <li v-for="todo in todos">
      {{ todo.text }}
    </li>
  </ol>
</div>
```

8. 组件化
   1. `template`声明组件模板
   2. `props`属性接收父组件的参数
   3. 父组件中使用`components`属性声明子组件

```html
const TodoItem = {
  props: ['todo'],
  template: `<li>{{ todo.text }}</li>`
}

<div id="todo-list-app">
  <ol>
     <!--
      现在我们为每个 todo-item 提供 todo 对象
      todo 对象是变量，即其内容可以是动态的。
      我们也需要为每个组件提供一个“key”，稍后再
      作详细解释。
    -->
    <todo-item
      v-for="item in groceryList"
      v-bind:todo="item"
      v-bind:key="item.id"
    ></todo-item>
  </ol>
</div>

Vue.createApp({
  data() {
    return {
      groceryList: [
        { id: 0, text: 'Vegetables' },
        { id: 1, text: 'Cheese' },
        { id: 2, text: 'Whatever else humans are supposed to eat' }
      ]
    }
  },
  components: {
    TodoItem
  }
}).mount('#todo-list-app')
```

9. 生命周期钩子

https://v3.cn.vuejs.org/images/lifecycle.svg

![lifecircle](C:\Users\wrp\Pictures\Saved Pictures\前端\Vue3\life.png)

10. 动态参数

```html
<a v-bind:[attributeName]="url"> ... </a>
<a v-on:[eventName]="doSomething"> ... </a>
attributeName、eventName均是Vue data中的字段
```

11. 修饰符==待深入==

```html
.prevent 修饰符告诉 v-on 指令对于触发的事件调用 event.preventDefault()
<form v-on:submit.prevent="onSubmit">...</form>
```

# data Property

1. data

直接将不包含在 `data` 中的新 property 添加到组件实例是可行的。但由于该 property 不在背后的响应式 `$data` 对象内，所以 [Vue 的响应性系统](https://v3.cn.vuejs.org/guide/reactivity.html)不会自动跟踪它。

访问方式

- vm.count
- vm.$data.count
- vm._data.count

```js
const app = Vue.createApp({
  data() {
    return { count: 4 }
  },
  methods: {
    increment() {
      // `this` 指向该组件实例
      this.count++
    }
  }
})
const vm = app.mount('#app')
```

2. method

避免使用箭头函数。this指向本vue组件实例