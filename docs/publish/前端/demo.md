## VSCode使用



## ES6

> ECMAScript是浏览器脚本语言的规范；JavaScript是规范的具体实现。ES6是2015年发布的。

1. let声明变量

```javascript
// 1.let变量存在作用域
{
  var a = 1;
  let b = 2;
}
console.log(a);
console.log(b);// b is not defined

// 2.let变量不能重复定义
var m = 1;
var m = 2;
let n = 3;
let n = 4;
console.log(m);
console.log(n);// Identifier 'n' has already been declared

// 3.let变量没有变量提升
console.log(x);// undefined
var x = 4;
console.log(y);// Cannot access 'y' before initialization
let y = 10;
```

2. const变量

```javascript
// const变量不能改变。常量
const a = 1;
a = 2;//  Assignment to constant variable.
```

3. 解构表达式

```javascript
// 1.数组解构，变量和数组元素一一对应
const arr = [1,2,3,4];
const [a,b, ,c] = arr;
console.log(a,b,c);// 1 2 4

// 2.对象解构 name:username 获取对象的name值赋值给username
const {name:username,age} = {
  name: 'zs',
  age: 18
}
console.log(username, age);// zs 18
```

4. 字符串扩展

```javascript
const {name:username,age} = {
  name: '我是张三',
  age: 18
}
// api扩展
console.log(username.startsWith("我"));
console.log(username.endsWith("三"));
console.log(username.includes("是"));
// 字符串模板 ``
let str = `
        <div>
            <span>字符串模板</span>
        </div>
        `
console.log(str);

function fun() {
  return '这是一个函数';
}
// ${} 可以引用js表达式
let info = `我是${username},今年${age + 10}, 我想说：${fun()}`;
console.log(info);
```

5. 函数

```javascript
// 1. 函数参数默认值
function add(a, b) {
  b = b || 1;//如果b为空，则设置默认值为1.这是es5写法
  return a + b;
}
console.log(add(10));
// 如果不传b,则设置默认值为1
function add2(a, b = 1) {
  return a + b;
}
console.log(add2(10));

// 2.函数不定参数
function fun(... arg) {
  console.log(arg.length);
}

fun(1, 2);// 2
fun(1,2,3,4);// 4

// 3.箭头函数
var print = (a, b) => {
	return a + b;
}
console.log(1, 2);
```

6. 对象优化

```javascript
// 1.新增api
const person = {
  name: 'jack',
  age: 21,
  language: ['java', 'js', 'python']
}
console.log(Object.keys(person));// 获取所有的key ['name', 'age', 'language']
console.log(Object.values(person));// 获取所有的value ['jack', 21, Array[3]]
console.log(Object.entries(person));// 获取[Array[2],Array[2],Array[2]]

const target = {a: 1}
const source1 = {b: 2}
const source2 = {c: 3}
Object.assign(target, source1, source2);
console.log(target);// {a:1,b:2,,c:3}
// 2.声明对简写
const name = 'zs';
const age = 20;
const obj = {age, name};// 属性和值相同，可简写
// 3.对象中的方法
const user = {
  name: 'zs',
  eat: function(foodName) {
    console.log(this.name + '在吃：' + foodName);
  },
  // 这种，箭头函数的this无法使用，应用对象.属性
  eat2: foodName => {
    // console.log(this.name + '在吃：' + foodName);
    console.log(user.name + '在吃：' + foodName);
  },
  eat3(foodName) {
    console.log(this.name + '在吃：' + foodName);
  }
  
}
user.eat('橘子');
user.eat2('葡萄');
user.eat3('香蕉');
// 4. 对象拓展运算符
let p = {name: 'zs', age: 5}
let copy_p = {...p};
console.log(copy_p);

let age = { age: 5};
let name = { name: 'zsls'};
let merge_p = {...age, ...name};
console.log(merge_p);
```

7. 数组的map和reduce

```javascript
// map():接收一个函数，遍历数组处理元素
let arr = ['1', '2','3','4'];
arr = arr.map(item=> item * 2);
console.log(arr);

// reduce(callback, [initialValue])
const initialValue = 100;
let result = arr.reduce((pre, cur) => {
  console.log("上一次处理后：" + pre);
  console.log("当前正在处理：" + cur);
  return pre + cur;
}, initialValue);
console.log(result);
```

8. promise

```javas
function get(url, data) {
            return new Promise((resolve, reject) => {
                $.ajax({
                    url,
                    data,
                    success(res) {
                        resolve(res);
                    },
                    error(err) {
                        reject(err);
                    }
                })
            })
        }

        get('url1').then((data) => {
            console.log("do something...");
            return get("url2");
        }).then(data => {
            console.log("do elsething...");
        }).catch(err=> {
            console.log("happen something badly," + err);
        })
```

9. 模块化

```javascript
// 1. export 可以导出任意数据，基本变量，数组，函数，对象等
export default {
  sum(a, b) {
    return a + b
  }
}
// 2. import
import xx from './demo.js'
console.log(xx.sum(1, 2));
```





## Node.js



## Vue



## Babel



## Webpack

