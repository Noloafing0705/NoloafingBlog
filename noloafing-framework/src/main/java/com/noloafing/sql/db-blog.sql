-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: nl_blog
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `nl_article`
--

DROP TABLE IF EXISTS `nl_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nl_article` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(256) DEFAULT NULL COMMENT '标题',
  `content` longtext COMMENT '文章内容',
  `summary` varchar(1024) DEFAULT NULL COMMENT '文章摘要',
  `category_id` bigint DEFAULT NULL COMMENT '所属分类id',
  `thumbnail` varchar(256) DEFAULT NULL COMMENT '缩略图',
  `is_top` char(1) DEFAULT '0' COMMENT '是否置顶（0否，1是）',
  `status` char(1) DEFAULT '1' COMMENT '状态（0已发布，1草稿）',
  `view_count` bigint DEFAULT '0' COMMENT '访问量',
  `is_comment` char(1) DEFAULT '1' COMMENT '是否允许评论 1是，0否',
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nl_article`
--

LOCK TABLES `nl_article` WRITE;
/*!40000 ALTER TABLE `nl_article` DISABLE KEYS */;
INSERT INTO `nl_article` VALUES (1,'SpringSecurity从入门到精通','## SpringSecurity\n![image20211219121555979.png](https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/31/e7131718e9e64faeaf3fe16404186eb4.png)\n\n## 0. 简介1\n\n​	**Spring Security** 是 Spring 家族中的一个安全管理框架。相比与另外一个安全框架**Shiro**，它提供了更丰富的功能，社区资源也比Shiro丰富。\n\n​	一般来说中大型的项目都是使用**SpringSecurity** 来做安全框架。小项目有Shiro的比较多，因为相比与SpringSecurity，Shiro的上手更加的简单。\n\n​	 一般Web应用的需要进行**认证**和**授权**。\n\n​		**认证：验证当前访问系统的是不是本系统的用户，并且要确认具体是哪个用户**\n\n​		**授权：经过认证后判断当前用户是否有权限进行某个操作**\n\n​	而认证和授权也是SpringSecurity作为安全框架的核心功能。\n\n\n\n## 1. 快速入门\n\n### 1.1 准备工作\n\n​	我们先要搭建一个简单的SpringBoot工程\n\n① 设置父工程 添加依赖\n\n~~~~\n    <parent>\n        <groupId>org.springframework.boot</groupId>\n        <artifactId>spring-boot-starter-parent</artifactId>\n        <version>2.5.0</version>\n    </parent>\n    <dependencies>\n        <dependency>\n            <groupId>org.springframework.boot</groupId>\n            <artifactId>spring-boot-starter-web</artifactId>\n        </dependency>\n        <dependency>\n            <groupId>org.projectlombok</groupId>\n            <artifactId>lombok</artifactId>\n            <optional>true</optional>\n        </dependency>\n    </dependencies>\n~~~~\n\n② 创建启动类\n\n~~~~\n@SpringBootApplication\npublic class SecurityApplication {\n\n    public static void main(String[] args) {\n        SpringApplication.run(SecurityApplication.class,args);\n    }\n}\n\n~~~~\n\n③ 创建Controller\n\n~~~~java\n\nimport org.springframework.web.bind.annotation.RequestMapping;\nimport org.springframework.web.bind.annotation.RestController;\n\n@RestController\npublic class HelloController {\n\n    @RequestMapping(\"/hello\")\n    public String hello(){\n        return \"hello\";\n    }\n}\n\n~~~~\n\n\n\n### 1.2 引入SpringSecurity\n\n​	在SpringBoot项目中使用SpringSecurity我们只需要引入依赖即可实现入门案例。\n\n~~~~xml\n        <dependency>\n            <groupId>org.springframework.boot</groupId>\n            <artifactId>spring-boot-starter-security</artifactId>\n        </dependency>\n~~~~\n\n​	引入依赖后我们在尝试去访问之前的接口就会自动跳转到一个SpringSecurity的默认登陆页面，默认用户名是user,密码会输出在控制台。\n\n​	必须登陆之后才能对接口进行访问。\n\n\n\n## 2. 认证\n\n### 2.1 登陆校验流程\n![image20211215094003288.png](https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/31/414a87eeed344828b5b00ffa80178958.png)','SpringSecurity框架教程-Spring Security+JWT实现项目级前端分离认证授权',15,'https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/31/948597e164614902ab1662ba8452e106.png','0','0',118,'0',NULL,'2022-01-23 23:20:11',NULL,'2022-11-02 22:20:00',0),(2,'文章2','\n\n### Vue 笔记\n\n**一、Vue基础语法**\n\n**1.语法的使用方式**\n\n| 语法用途 |                         插值语法                          | 指令语法                                                     |\n| :------: | :-------------------------------------------------------: | ------------------------------------------------------------ |\n|   作用   | 只能用于解析标签体中间的内容例如```<h3>{{content}}</h3``` | 用于解析标签体的属性，js表达式,例如:绑定标签事件：href=\'url.toUpperCase()\'，以及标签体的内容：例如v-text... |\n\n**2.单向绑定与双向绑定**\n\n| 对比 | v-bind:属性 简写为 :属性                           | v-model:value=\' \' 简写为 v-model=\' \'                         |\n| ---- | -------------------------------------------------- | ------------------------------------------------------------ |\n| 用处 | 单向绑定数据，只有服务端数据变化才会影响客户端渲染 | 双向绑定，但是并不是所有标签能使用：属于表单类标签的可使用范围，例如input等带有value属性的 |\n\n**3.data与el的两种写法**\n\n写法一：对象式\n\n```vue\n<body>\n<p id=\'root\'>{{tip}}</p>\n</body>\n<script text=\"javascript\">\n        const v = new Vue({\n            el:\'#root\',\n            data:{\n                tip:\'学习Vue\'\n            }\n        })\n</script>\n```\n\n写法二：函数式\n\n```vue\n<body>\n<p id=\'root\'>{{tip}}</p>\n</body>\n<script text=\"javascript\">\n        const v = new Vue({\n            data(){  //第二种data方式是使用了函数体返回，是Vue实例自身的函数调用，涉及到组件的使用之后，必须使用函数式，第一种会报错\n                return{\n                    tip:\'学习Vue\'\n                }\n            }\n            v.$mount(\'#root\') //第二种el方法比较灵活\n        })\n</script>\n```\n\n**注意：只要是Vue管理的函数（例如这里的data(){...})，一定不要使用箭头函数，不然this对象指代的不再是Vue实例！！！**\n\n\n\n**4.MVVM模型（Vue参考了该模型）**\n\n![image-20220630111902421](C:\\Users\\Noloafing\\AppData\\Roaming\\Typora\\typora-user-images\\image-20220630111902421.png)\n\n```vue\n<body>\n    <div id=\'root\'> <!-- body里的dom元素组成的内容是view -->\n        <p>{{username}}</p>\n   		<p>{{gender}}</p>\n    </div>\n	\n</body>\n<script text=\"javascript\">\n        const v = new Vue({ //这里Vue实例就是 ViewModel 负责管理model以及监听Dom进行二者的交互\n            el:\'#root\',\n            data:{\n                username:\'李华\', //data是一个对象，封装了model\n                gender:\'男\'\n            }\n        })\n</script>\n```\n\n**注意：Vue实例中的所有属性都可以直接使用**\n\n\n\n**5. 数据代理**\n\nES6关于数据代理的一个重要的方法(Object.defineProperty)：\n\n```vue\n//Object.defineProperty(对象,属性,{value,配置,方法})\n//例子：\n<script text=\"javascript\">\nlet number = 21\nlet person = {\n    name:\'李华\',\n    gender:\'男\',\n}\n    \nObject.defineProperty(person,\'age\',{\n    value:21,\n    //常用属性配置 默认FALSE\n    enumerable:true //是否开启可枚举，开启代表：遍历时有该值，关闭则遍历是没有该值\n    writable:true //是否该值能被修改\n    configurable:true //是否属性可以被删除\n    \n    //get(){...}:当该属性被读取时，返回一个属性值\n    get(){\n    	return number\n	}\n    //set(value){...}:当该属性被修改（value）时，返回一个属性值\n    set(value){\n    	number = value\n	}\n})\n</script>\n\n```\n\n**数据代理：通过一个对象代理访问与修改另一个对象的属性**\n\n```javascript\n//数据代理例子：\n        let obj1 = {\n            x:100\n        }\n        let obj2 = {\n            y:200\n        }\n        Object.defineProperty(obj2,\'x\',{\n            get(){\n                return obj1.x\n            },\n            set(value){\n                obj1.x = value\n            }\n        })\n```\n\n**Vue中的数据代理：**\n\n当新建一个vm(Vue的实例)，在data中定义数据时(data{message:\'\'})，vm将data进行了数据代理，因此vm能够直接读取data中的属性，即 vm.message=\'\' 而此时的data也仍然存在于vm实例中，只是经过了一次处理，使得data对象变为了_data对象，这其中添加了数据劫持的方法用于数据响应与渲染。实际上：在插值语法中:```{{ _data.message }}```与```{{ message }}```是一样的，但是vm对此做了数据代理以便于代码的书写简洁。\n\n#### 二、事件处理\n\n**1.事件的基本使用：**\n\n- 使用v-on:Xxx或@xxx绑定事件，Xxx是事件名；\n- 事件的回调需要配置在methods.对象中，最终会在vm上；\n- **methods中配置的函数，不要用箭头函数！否则this就不是vm了,而是window对象；**\n- methods中配置的函数，都是被Vue所管理的函数，this的指向是vm或组件实例对象；\n- **5.@click=\"demo\"和@click=-\"demo($event)\"效果一致，但后者可以传参，参数event是一个事件对象，可以传入其他参数，这里$是event占位符，代表必须传入event，而不加$（number,event）event不会被传入;**\n- data中可以写方法，但方法写入data中Vue也会错误的对方法进行数据代理，所以不用这种方式\n\n**2.事件修饰符**\n\n用处：使用修饰符可以不用在方法中传入event事件然后对其操作，直接在事件处理后```.修饰符```\n\nVue中的事件修饰符：\n1.prevent: 阻止默认事件(常用)\n2.stop: 阻止事件冒泡(常用)\n3.once: 事件只触发一次(常用）\n4.capture: 使用事件的捕获模式\n5.self: 只有event.target是当前操作的元素时才触发事件\n6.passive: 事件的默认行为立即执行，**无需等待事件回调执行完毕**\n\n**注意：修饰符是可以连写的：```xxx.prevent.once...```**\n\n例如：\n\n```vue\n//不使用事件修饰符\n<a :href=\'url\' @click.prevent=\"showTip($event,name)\">点我去博客</a>\n<script>\n	new Vue({\n        el:#id,\n        data:{\n            name:\'...\',\n            url:\'...‘\n        }\n        methods:{\n        	showTip($event,name){\n        		event.preventDefault()//阻止事件默认行为\n        		alert(name)\n    		}\n    	}\n    })\n</script>\n\n//使用修饰符\n//.prevent阻止标签的默认行为（跳转）\n<a :href=\'url\' @click.prevent=\"showTip(name)\">点我去博客</a>\n<script>\n	new Vue({\n        el:#id,\n        data:{\n            name:\'...\',\n            url:\'...‘\n        }\n        methods:{\n        	showTip(name){\n        		alert(name)\n    		}\n    	}\n    })\n</script>\n```\n\n**3.键盘事件**\n\n用法：@keydown/up.键盘修饰符=\'事件方法\'\n\n```vue\n<input type=\"text\" placeholder=\"按下回车录入信息\" @keyup.enter=\"showTip\">\n```\n\n**注意：修饰符是可以连写的：```xxx.ctrl.y...```**\n\n@keydown键按下就触发  \n\n@keyup键按下并释放才触发\n\n9个键盘修饰符(别名)：\n\n回车 => enter\n删除 => delete（捕获“删除”和“退格”键）\n退出 => esc\n空格 => space\n\n换行 => tab(特殊，必须配合keydown去使用，因为tab本身具有切换焦点的作用)\n上 => up  下 => down  左 => left  右 => right\n\n特别：\n\n- alt , ctrl,shift,win为系统修饰件（配合keyup必须按下其他键并且将其释放才能触发）所以用keydown\n- 像CapsClock键名是单词组合的形式为：caps-clock\n\n#### 三、Vue的计算属性\n\n**示例：由姓，名计算全名**\n\ncomputed:{}将data中的属性进行计算，最终返回的属性放到Vue实例中\n\n**重点：computed方法主要是从已有的属性中计算需要的属性时使用，并且computed相较于使用methods写函数实现，其优势在于，computed计算结果会进行缓存，如果在computed中其计算属性（例子中：get方法中使用到的属性）没有修改，则直接从缓存读取，而不会再次进行同样的运算，而后者使用method则会在属性用到的地方都进行函数调用，浪费资源。**\n\n```vue\n<body>\n    <div id=\"root\">\n        姓：<input type=\"text\" v-model=\'firstName\'><br>\n        名：<input type=\"text\" v-model=\'lastName\'><br>\n        <p>全名：{{ fullname }}</p>\n    </div>\n    <script text=\"javascript\">\n        const vm = new Vue({\n            data(){\n                return{\n                    firstName:\'李\',\n                    lastName:\'华\'\n                }\n            },\n            //计算属性：计算之后得到的属性返回放到vm当中\n            computed:{\n                fullname:{\n                    //getter 与 setter 本质上是通过数据代理实现的操作\n                    get(){\n                        //此处this是指Vue实例对象vm\n                        return this.firstName+\'-\'+this.lastName\n                    },\n                    //此处如果需要设置修改的方法 需要写set()方法\n                    set(value){ // value接修改后的属性\n                		arr = value.slice(\'-\')\n                		this.firstName = arr[0]\n        				this.lastName = arr[1]\n            		}\n                }\n            }\n        })\n        vm.$mount(\'#root\')\n    </script>\n</body>\n```\n\n**计算属性简写方式：**\n\n#### 四、监视属性\n\n监视属性watch:\n\n- 1.当被监视的属性变化时，回调函数自动调用，进行相关操作\n\n- **2.监视的属性必须存在，才能进行监视！！**\n\n- 3.监视的两种写法：\n\n  **(1).new Vuel时传入watch配置**\n  **(2),通过vm.$watch监视**\n\n```vue\n<body>\n    <div id=\"root\">\n        <h4>今天天气很{{ info }}</h4>\n        <button @click=\"changeInfo\">点击</button>\n    </div>\n    <script text=\"javascript\">\n        const vm = new Vue({\n            data() {\n                return {\n                    isHot: true\n                }\n            },\n            methods: {\n                changeInfo() {\n                    this.isHot = !this.isHot\n                }\n            },\n            computed: {\n                info() {\n                    return this.isHot == true ? \'炎热\' : \'凉爽\'\n                }\n            },\n            //watch写法一：\n            watch: {\n                isHot: {\n                    handler(newValue, oldValue) {\n                        console.log(oldValue, newValue)\n                    }\n                }\n            }\n        })\n        vm.$mount(\'#root\')\n        //监视属性书写方式二：\n        vm.$watch(\'isHot\', {\n//immediate,这个默认FALSE,开启true 代表初始化时 就执行handler函数一次\n//handler函数：参数1：代表改变后的属性值，2:改变前的值 \n//函数的作用是一旦改变，就会执行handler函数\n            handler(newValue, oldValue) {\n                console.log(oldValue, newValue)\n            }\n        }) \n    </script>\n</body>示例：\n\n```\n\n**深度监视：**\n\n**当数据具有多层结构想要监视其内部的属性的方法**\n\n示例：\n\n```vue\n <script text=\"javascript\">\n        const vm = new Vue({\n            data() {\n                return {\n                    numbers:{\n                        a:1,\n                        b:1\n                    }      \n                }\n            },\n            watch:{\n                //当要监视属性内部的某个值的时候，这里必须加引号，否则无法识别表达式\n                \'numbers.a\':{\n                    handler(newValue,oldValue){\n                        console.log(\'a改变了\')\n                    }\n                },\n                //如果要监视内部任意个属性的变化，需要开启深度监视\n                numbers:{\n                    deep:true,\n                    handler(newValue,oldValue){\n                        console.log(\'numbers改变了\')\n                    }\n                }\n            }\n        })\n        vm.$mount(\'#root\')\n    </script>\n```\n\n**监视属性简写的前提：不修改默认配置，只使用handler()**\n\n简写形式：\n\n```vue\n方式一：watch:{\n		isHot(newValue,oldValue){...}\n	}\n方式二：vm.$watch(\'isHot\',function(newValue,oldValue){...})\n```\n\n| 比较 | computed                                     | watch                                  |\n| ---- | -------------------------------------------- | -------------------------------------- |\n|      | 代码简介效率高，但不能开启异步任务，例如延时 | 代码功能需要自定义，但可以开启异步任务 |\n\n**computed和watch.之间的区别：**\n1.computedi能完成的功能，watch都可以完成。\n2.watch能完成的功能，computed.不一定能完成，例如：watch可以进行异步操作。\n**两个重要的小原则：**\n\n- 1.所被Vue管理的函数，最好写成普通函数，这样this的指向才是vm或组件实例对象。\n- 2,所有不被Vue所管理的函数（定时器的回调函数、ajax的回调函数等），最好写成箭头函数，\n  这样this的指向才是vm或组件实例对象。\n\n#### **五、绑定样式**\n\n绑定样式：\n\n- 1.c1ass样式\n  写法：classa=\"Xxx” Xxx**可以是字符串、对象、数组**\n  字符串写法适用于：类名不确定，要动态获取。\n  对象写法适用于：要绑定多个样式，个数不确定，名字也不确定。\n  数组写法适用于：要绑定多个样式，个数确定，名字也确定，但不确定用不用。\n- 2.sty1e样式\n  :style=\"{fontsize:Xxx)\"其中xxx是动态值。\n  :style=\"[a,b]\"其中a、b是样式对象。（很少用）\n\n#### **六、条件渲染**\n\n条件渲染两种操作：\n\n- v-if : 如果为false,则标签会从结构中移去，因此考虑到**如果有其他操作导致v-if的标签消失会影响其他内容则建议不要使用或尽量避免此情况**，使用了v-if如果为false,源码中看不到标签，与v-if同时使用的还有v-else-if,以及v-else,但要记住这三者一起使用时必须整体在一快，不能在中间插入其他标签，否则无法识别到整体的v-if-else结构。\n- v-show : 如果为false,标签不会从结构中移去，而是标签的display属性为：none,**如果对于标签的展示操作比较频繁，一般建议选择v-show。**\n\n**注意：当我们要对几个标签同时共用一个条件作为判断显示与否，这时最好使用v-if+template**\n\n因为如果使用 div 标签套住这些标签内容 会影响结构和这几个标签在dom中的层级，而**使用template则只会对范围内的标签实现解析，不影响结构。** template在这里只能与v-if使用，v-show用不了。\n\n示例：\n\n```vue\n<body>\n    <template v-if=\"...\">\n		<h2>...<h2><br>\n        <h2>...<h2><br>\n        <h2>...<h2><br>\n    </template>\n</body>\n```\n\n#### **七、列表渲染**\n\n**v-for指令（一般用于数据渲染）：**\n\n- 1.用于展示列表数据\n- 2.语法：v-for=\"(item,index)in xxx\'\":key=\"yyy\"\n- 3.可遍历：数组、对象、字符串（用的很少）、指定次数（用的很少）\n\n示例：\n\n```vue\n<body>\n    <div id=\"list\">\n        <ul v-for=\"book,index in books\" :key=\"index\">\n            <li>{{book}}---{{index}}</li>\n        </ul>\n        <hr>\n        <ul v-for=\"val,key in person\" :key=\"key\">\n            <li>{{key}}---{{val}}</li>\n        </ul>\n    </div>\n    <script text=\"javascript\">\n        new Vue({\n            el:\'#list\',\n            data() {\n                return {\n                    books:[\n                        {\n                            id:\'001\',\n                            name:\'明朝那些事儿\',\n                            author:\'当年明月\',\n                            price:\'45.00\'\n                        },\n                        {\n                            id:\'002\',\n                            name:\'java开发手册\',\n                            author:\'孤尽\',\n                            price:\'35.00\'\n                        },\n                        {\n                            id:\'003\',\n                            name:\'白夜行\',\n                            author:\'东野圭吾\',\n                            price:\'59.00\'\n                        },\n                        {\n                            id:\'004\',\n                            name:\'挪威的森林\',\n                            author:\'村上春树\',\n                            price:\'59.00\'\n                        }\n                    ],\n                    person:{\n                        name:\'李华\',\n                        gender:\'男\',\n                        age:21,\n                        phone:\'1234567\'\n                    }\n                }\n            }\n        })\n    </script>\n</body>\n```\n\n注意：使用v-for遍历时，遍历的参数有两个：\n\n- 遍历数组时：参数为数组元素、索引 index ，而 :key=index 或者 元素中的 唯一标识 例如 id\n- 遍历对象时：对象由key:value组成，因此参数为val,key，val为属性，key为索引，:key=key\n\n- 遍历字符串，则是每个字符，以及对应的索引...\n\n**key的作用以及原理**\n\n```vue\n面试题：react、vue中的key有什么作用？(key的内部原理)\n\n1.虚拟DoM中key的作用：\nkey是虚拟DOM对象的标识，当数据发生变化时，Vue会根据【新数据】生成【新的虚拟DoM】,\n随后Vue进行【新虚拟DoM】与【旧虚拟DoM】的差异比较，比较规则如下：\n\n2.对比规则：\n(1).旧虚拟DoM中找到了与新虚拟DoM相同的key:\n-虚拟DOM中内容没变，直接使用之前的真实DOM!\n-若虚拟D0M中内容变了，则生成新的真实D0M,随后替换掉页面中之前的真实D0M。\n\n(2).旧虚拟DoM中未找到与新虚拟DoM相同的key：创建新的真实D0M,随后渲染到到页面。\n3.用index作为key可能会引发的问题：\n	1.若对数据进行：逆序添加、逆序删除等破环顺序操作：会产生没有必要的真实D0M更新=>界面效果没问题，但效率低。\n	\n	2.如果结构中还包含输入类的DOM:会产生错误D0M更新=>界面有问题。\n4.开发中如何选择key?:\n   (1)最好使用每条数据的唯一标识作为key,比如id、手机号、身份证号、学号等唯一值。\n   (2)如果不存在对数据的逆序添加、逆序删除等破坏顺序操作，仅用于渲染列表用于展示，使用index作为key是没有问题的。\n```\n\n#### 八、Vue监视数据的原理\n\n**Vue监视数据的原理：**\n\n1. **vue会监视data中所有层次的数据。**\n\n2. **如何监测对象中的数据？**\n\n   通过setter实现监视，且要在new Vue时就传入要监测的数据。\n\n​        (1). **对象中后追加的属性**，Vue默认不做响应式处理\n\n​        (2). 如需给后添加的属性做响应式，请使用如下API：\n\n​                 Vue.set (target，propertyName/index，value) 或 \n\n​                 vm.$set (target，propertyName/index，value)\n\n​    3.  **如何监测数组中的数据？**\n\n​        通过包裹数组更新元素的方法实现，本质就是做了两件事：\n\n​        (1). 调用原生对应的方法对数组进行更新。\n\n​        (2). 重新解析模板，进而更新页面。\n\n4. 在**Vue修改数组中的某个元素**一定要用如下方法：\n\n​              **1.使用这些API:push()、pop()、shift()、unshift()、splice()、sort()、reverse()**\n\n​              2.Vue.set() 或 vm.$set()\n\n​        **特别注意：Vue.set() 和 vm.$set() 不能给vm 或 vm的根数据对象 添加属性！！！**\n\n','修改更新',1,'https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/08/28/7659aac2b74247fe8ebd9e054b916dbf.png','1','0',23,'0',NULL,'2022-01-21 14:58:30',NULL,'2022-11-02 22:20:00',0),(3,'Redis实战','## 利用Redis解决企业开发中实际问题：\n- 缓存问题\n- 秒杀机制\n- 验证问题\n- 分布式锁','实战练习Redis，用Redis实现相关应用功能',1,'http://cdn.noloafing.icu/2022/10/06ce976aa2d2d54264ada8a38f05c15801.png','0','0',33,'1',NULL,'2022-01-18 14:58:34',NULL,'2022-11-02 22:20:00',0),(5,'sdad','![Snipaste_20220115_165812.png](https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/15/1d9d283f5d874b468078b183e4b98b71.png)\r\n\r\n## sda \r\n\r\n222\r\n### sdasd newnewnew',NULL,2,'','1','0',47,'0',NULL,'2022-01-17 14:58:37',NULL,'2022-10-06 07:03:07',1),(16,'基于vue+springBoot+Mybatis的博客项目','![QQ图片20220814165021.jpg](http://cdn.noloafing.icu/2022/08/297ec919591db441ebaafb7006394cab09.jpg)基于vue+springBoot+Mybatis的博客项目','基于vue+springBoot+Mybatis的博客项目',1,'http://cdn.noloafing.icu/2022/08/297d2c7ce14d8246599ac9106021761968.jpg','0','0',2,'0',1,'2022-08-29 09:23:58',NULL,'2022-11-02 22:20:00',0),(17,'hahahhah','# asdfaskldfjksadjfkasd','asdfjasdkfjkasd',2,'','0','0',2,'0',1,'2022-08-29 09:29:43',NULL,'2022-10-06 07:44:58',1),(18,'aaaaaaaa','# bbbbbbbb','aaaaaaaaaaa',1,'http://cdn.noloafing.icu/2022/08/29cf2b785be09b4ca0b7b5fb11f79a9548.jpg','1','0',3,'0',1,'2022-08-29 09:44:45',NULL,'2022-10-06 07:44:58',1),(19,'bbbbbbbbbbb','# bbbbbbbbbbbb','bbbbbbbbbbbbbbb',2,'http://cdn.noloafing.icu/2022/08/29a2fccd7f197e49d6abcb9634c49e5879.jpg','1','0',1,'0',1,'2022-08-29 09:54:39',NULL,'2022-10-06 07:44:58',1),(20,'ccccccccc','# cccccccccccccccc','cccccccccccc',1,'http://cdn.noloafing.icu/2022/08/290dcf4910874540f5811f6ddfe6f4afe9.jpg','1','0',0,'0',1,'2022-08-29 10:06:55',NULL,'2022-10-06 07:44:39',1),(21,'ddddddddddd','# ddddddddddd','dddddddddddddd',2,'http://cdn.noloafing.icu/2022/08/29f00468b90cc945758c018c518acf479f.jpg','1','0',0,'0',1,'2022-08-29 10:08:54',NULL,'2022-10-06 07:44:39',1),(22,'1023','111','111',1,'','0','0',1,'0',1,'2022-10-23 04:22:14',NULL,'2022-11-02 22:20:00',0),(23,'1024','111','111',15,'','0','0',0,'0',1,'2022-10-23 04:22:58',NULL,'2022-11-02 22:20:00',0),(24,'Vue 基础语法笔记','\n\n\n**一、Vue基础语法**\n\n**1.语法的使用方式**\n\n| 语法用途 |                         插值语法                          | 指令语法                                                     |\n| :------: | :-------------------------------------------------------: | ------------------------------------------------------------ |\n|   作用   | 只能用于解析标签体中间的内容例如```<h3>{{content}}</h3``` | 用于解析标签体的属性，js表达式,例如:绑定标签事件：href=\'url.toUpperCase()\'，以及标签体的内容：例如v-text... |\n\n**2.单向绑定与双向绑定**\n\n| 对比 | v-bind:属性 简写为 :属性                           | v-model:value=\' \' 简写为 v-model=\' \'                         |\n| ---- | -------------------------------------------------- | ------------------------------------------------------------ |\n| 用处 | 单向绑定数据，只有服务端数据变化才会影响客户端渲染 | 双向绑定，但是并不是所有标签能使用：属于表单类标签的可使用范围，例如input等带有value属性的 |\n\n**3.data与el的两种写法**\n\n写法一：对象式\n\n```vue\n<body>\n<p id=\'root\'>{{tip}}</p>\n</body>\n<script text=\"javascript\">\n        const v = new Vue({\n            el:\'#root\',\n            data:{\n                tip:\'学习Vue\'\n            }\n        })\n</script>\n```\n\n写法二：函数式\n\n```vue\n<body>\n<p id=\'root\'>{{tip}}</p>\n</body>\n<script text=\"javascript\">\n        const v = new Vue({\n            data(){  //第二种data方式是使用了函数体返回，是Vue实例自身的函数调用，涉及到组件的使用之后，必须使用函数式，第一种会报错\n                return{\n                    tip:\'学习Vue\'\n                }\n            }\n            v.$mount(\'#root\') //第二种el方法比较灵活\n        })\n</script>\n```\n\n**注意：只要是Vue管理的函数（例如这里的data(){...})，一定不要使用箭头函数，不然this对象指代的不再是Vue实例！！！**\n\n\n\n**4.MVVM模型（Vue参考了该模型）**\n\n![image.png](http://cdn.noloafing.icu/2022/11/026a4fea6cbe794d2592a1e281c815ea32.png)\n\n```vue\n<body>\n    <div id=\'root\'> <!-- body里的dom元素组成的内容是view -->\n        <p>{{username}}</p>\n   		<p>{{gender}}</p>\n    </div>\n	\n</body>\n<script text=\"javascript\">\n        const v = new Vue({ //这里Vue实例就是 ViewModel 负责管理model以及监听Dom进行二者的交互\n            el:\'#root\',\n            data:{\n                username:\'李华\', //data是一个对象，封装了model\n                gender:\'男\'\n            }\n        })\n</script>\n```\n\n**注意：Vue实例中的所有属性都可以直接使用**\n\n\n\n**5. 数据代理**\n\nES6关于数据代理的一个重要的方法(Object.defineProperty)：\n\n```vue\n//Object.defineProperty(对象,属性,{value,配置,方法})\n//例子：\n<script text=\"javascript\">\nlet number = 21\nlet person = {\n    name:\'李华\',\n    gender:\'男\',\n}\n    \nObject.defineProperty(person,\'age\',{\n    value:21,\n    //常用属性配置 默认FALSE\n    enumerable:true //是否开启可枚举，开启代表：遍历时有该值，关闭则遍历是没有该值\n    writable:true //是否该值能被修改\n    configurable:true //是否属性可以被删除\n    \n    //get(){...}:当该属性被读取时，返回一个属性值\n    get(){\n    	return number\n	}\n    //set(value){...}:当该属性被修改（value）时，返回一个属性值\n    set(value){\n    	number = value\n	}\n})\n</script>\n\n```\n\n**数据代理：通过一个对象代理访问与修改另一个对象的属性**\n\n```javascript\n//数据代理例子：\n        let obj1 = {\n            x:100\n        }\n        let obj2 = {\n            y:200\n        }\n        Object.defineProperty(obj2,\'x\',{\n            get(){\n                return obj1.x\n            },\n            set(value){\n                obj1.x = value\n            }\n        })\n```\n\n**Vue中的数据代理：**\n\n当新建一个vm(Vue的实例)，在data中定义数据时(data{message:\'\'})，vm将data进行了数据代理，因此vm能够直接读取data中的属性，即 vm.message=\'\' 而此时的data也仍然存在于vm实例中，只是经过了一次处理，使得data对象变为了_data对象，这其中添加了数据劫持的方法用于数据响应与渲染。实际上：在插值语法中:```{{ _data.message }}```与```{{ message }}```是一样的，但是vm对此做了数据代理以便于代码的书写简洁。\n\n#### 二、事件处理\n\n**1.事件的基本使用：**\n\n- 使用v-on:Xxx或@xxx绑定事件，Xxx是事件名；\n- 事件的回调需要配置在methods.对象中，最终会在vm上；\n- **methods中配置的函数，不要用箭头函数！否则this就不是vm了,而是window对象；**\n- methods中配置的函数，都是被Vue所管理的函数，this的指向是vm或组件实例对象；\n- **5.@click=\"demo\"和@click=-\"demo($event)\"效果一致，但后者可以传参，参数event是一个事件对象，可以传入其他参数，这里$是event占位符，代表必须传入event，而不加$（number,event）event不会被传入;**\n- data中可以写方法，但方法写入data中Vue也会错误的对方法进行数据代理，所以不用这种方式\n\n**2.事件修饰符**\n\n用处：使用修饰符可以不用在方法中传入event事件然后对其操作，直接在事件处理后```.修饰符```\n\nVue中的事件修饰符：\n1.prevent: 阻止默认事件(常用)\n2.stop: 阻止事件冒泡(常用)\n3.once: 事件只触发一次(常用）\n4.capture: 使用事件的捕获模式\n5.self: 只有event.target是当前操作的元素时才触发事件\n6.passive: 事件的默认行为立即执行，**无需等待事件回调执行完毕**\n\n**注意：修饰符是可以连写的：```xxx.prevent.once...```**\n\n例如：\n\n```vue\n//不使用事件修饰符\n<a :href=\'url\' @click.prevent=\"showTip($event,name)\">点我去博客</a>\n<script>\n	new Vue({\n        el:#id,\n        data:{\n            name:\'...\',\n            url:\'...‘\n        }\n        methods:{\n        	showTip($event,name){\n        		event.preventDefault()//阻止事件默认行为\n        		alert(name)\n    		}\n    	}\n    })\n</script>\n\n//使用修饰符\n//.prevent阻止标签的默认行为（跳转）\n<a :href=\'url\' @click.prevent=\"showTip(name)\">点我去博客</a>\n<script>\n	new Vue({\n        el:#id,\n        data:{\n            name:\'...\',\n            url:\'...‘\n        }\n        methods:{\n        	showTip(name){\n        		alert(name)\n    		}\n    	}\n    })\n</script>\n```\n\n**3.键盘事件**\n\n用法：@keydown/up.键盘修饰符=\'事件方法\'\n\n```vue\n<input type=\"text\" placeholder=\"按下回车录入信息\" @keyup.enter=\"showTip\">\n```\n\n**注意：修饰符是可以连写的：```xxx.ctrl.y...```**\n\n@keydown键按下就触发  \n\n@keyup键按下并释放才触发\n\n9个键盘修饰符(别名)：\n\n回车 => enter\n删除 => delete（捕获“删除”和“退格”键）\n退出 => esc\n空格 => space\n\n换行 => tab(特殊，必须配合keydown去使用，因为tab本身具有切换焦点的作用)\n上 => up  下 => down  左 => left  右 => right\n\n特别：\n\n- alt , ctrl,shift,win为系统修饰件（配合keyup必须按下其他键并且将其释放才能触发）所以用keydown\n- 像CapsClock键名是单词组合的形式为：caps-clock\n\n#### 三、Vue的计算属性\n\n**示例：由姓，名计算全名**\n\ncomputed:{}将data中的属性进行计算，最终返回的属性放到Vue实例中\n\n**重点：computed方法主要是从已有的属性中计算需要的属性时使用，并且computed相较于使用methods写函数实现，其优势在于，computed计算结果会进行缓存，如果在computed中其计算属性（例子中：get方法中使用到的属性）没有修改，则直接从缓存读取，而不会再次进行同样的运算，而后者使用method则会在属性用到的地方都进行函数调用，浪费资源。**\n\n```vue\n<body>\n    <div id=\"root\">\n        姓：<input type=\"text\" v-model=\'firstName\'><br>\n        名：<input type=\"text\" v-model=\'lastName\'><br>\n        <p>全名：{{ fullname }}</p>\n    </div>\n    <script text=\"javascript\">\n        const vm = new Vue({\n            data(){\n                return{\n                    firstName:\'李\',\n                    lastName:\'华\'\n                }\n            },\n            //计算属性：计算之后得到的属性返回放到vm当中\n            computed:{\n                fullname:{\n                    //getter 与 setter 本质上是通过数据代理实现的操作\n                    get(){\n                        //此处this是指Vue实例对象vm\n                        return this.firstName+\'-\'+this.lastName\n                    },\n                    //此处如果需要设置修改的方法 需要写set()方法\n                    set(value){ // value接修改后的属性\n                		arr = value.slice(\'-\')\n                		this.firstName = arr[0]\n        				this.lastName = arr[1]\n            		}\n                }\n            }\n        })\n        vm.$mount(\'#root\')\n    </script>\n</body>\n```\n\n**计算属性简写方式：**\n\n#### 四、监视属性\n\n监视属性watch:\n\n- 1.当被监视的属性变化时，回调函数自动调用，进行相关操作\n\n- **2.监视的属性必须存在，才能进行监视！！**\n\n- 3.监视的两种写法：\n\n  **(1).new Vuel时传入watch配置**\n  **(2),通过vm.$watch监视**\n\n```vue\n<body>\n    <div id=\"root\">\n        <h4>今天天气很{{ info }}</h4>\n        <button @click=\"changeInfo\">点击</button>\n    </div>\n    <script text=\"javascript\">\n        const vm = new Vue({\n            data() {\n                return {\n                    isHot: true\n                }\n            },\n            methods: {\n                changeInfo() {\n                    this.isHot = !this.isHot\n                }\n            },\n            computed: {\n                info() {\n                    return this.isHot == true ? \'炎热\' : \'凉爽\'\n                }\n            },\n            //watch写法一：\n            watch: {\n                isHot: {\n                    handler(newValue, oldValue) {\n                        console.log(oldValue, newValue)\n                    }\n                }\n            }\n        })\n        vm.$mount(\'#root\')\n        //监视属性书写方式二：\n        vm.$watch(\'isHot\', {\n//immediate,这个默认FALSE,开启true 代表初始化时 就执行handler函数一次\n//handler函数：参数1：代表改变后的属性值，2:改变前的值 \n//函数的作用是一旦改变，就会执行handler函数\n            handler(newValue, oldValue) {\n                console.log(oldValue, newValue)\n            }\n        }) \n    </script>\n</body>示例：\n\n```\n\n**深度监视：**\n\n**当数据具有多层结构想要监视其内部的属性的方法**\n\n示例：\n\n```vue\n <script text=\"javascript\">\n        const vm = new Vue({\n            data() {\n                return {\n                    numbers:{\n                        a:1,\n                        b:1\n                    }      \n                }\n            },\n            watch:{\n                //当要监视属性内部的某个值的时候，这里必须加引号，否则无法识别表达式\n                \'numbers.a\':{\n                    handler(newValue,oldValue){\n                        console.log(\'a改变了\')\n                    }\n                },\n                //如果要监视内部任意个属性的变化，需要开启深度监视\n                numbers:{\n                    deep:true,\n                    handler(newValue,oldValue){\n                        console.log(\'numbers改变了\')\n                    }\n                }\n            }\n        })\n        vm.$mount(\'#root\')\n    </script>\n```\n\n**监视属性简写的前提：不修改默认配置，只使用handler()**\n\n简写形式：\n\n```vue\n方式一：watch:{\n		isHot(newValue,oldValue){...}\n	}\n方式二：vm.$watch(\'isHot\',function(newValue,oldValue){...})\n```\n\n| 比较 | computed                                     | watch                                  |\n| ---- | -------------------------------------------- | -------------------------------------- |\n|      | 代码简介效率高，但不能开启异步任务，例如延时 | 代码功能需要自定义，但可以开启异步任务 |\n\n**computed和watch.之间的区别：**\n1.computedi能完成的功能，watch都可以完成。\n2.watch能完成的功能，computed.不一定能完成，例如：watch可以进行异步操作。\n**两个重要的小原则：**\n\n- 1.所被Vue管理的函数，最好写成普通函数，这样this的指向才是vm或组件实例对象。\n- 2,所有不被Vue所管理的函数（定时器的回调函数、ajax的回调函数等），最好写成箭头函数，\n  这样this的指向才是vm或组件实例对象。\n\n#### **五、绑定样式**\n\n绑定样式：\n\n- 1.c1ass样式\n  写法：classa=\"Xxx” Xxx**可以是字符串、对象、数组**\n  字符串写法适用于：类名不确定，要动态获取。\n  对象写法适用于：要绑定多个样式，个数不确定，名字也不确定。\n  数组写法适用于：要绑定多个样式，个数确定，名字也确定，但不确定用不用。\n- 2.sty1e样式\n  :style=\"{fontsize:Xxx)\"其中xxx是动态值。\n  :style=\"[a,b]\"其中a、b是样式对象。（很少用）\n\n#### **六、条件渲染**\n\n条件渲染两种操作：\n\n- v-if : 如果为false,则标签会从结构中移去，因此考虑到**如果有其他操作导致v-if的标签消失会影响其他内容则建议不要使用或尽量避免此情况**，使用了v-if如果为false,源码中看不到标签，与v-if同时使用的还有v-else-if,以及v-else,但要记住这三者一起使用时必须整体在一快，不能在中间插入其他标签，否则无法识别到整体的v-if-else结构。\n- v-show : 如果为false,标签不会从结构中移去，而是标签的display属性为：none,**如果对于标签的展示操作比较频繁，一般建议选择v-show。**\n\n**注意：当我们要对几个标签同时共用一个条件作为判断显示与否，这时最好使用v-if+template**\n\n因为如果使用 div 标签套住这些标签内容 会影响结构和这几个标签在dom中的层级，而**使用template则只会对范围内的标签实现解析，不影响结构。** template在这里只能与v-if使用，v-show用不了。\n\n示例：\n\n```vue\n<body>\n    <template v-if=\"...\">\n		<h2>...<h2><br>\n        <h2>...<h2><br>\n        <h2>...<h2><br>\n    </template>\n</body>\n```\n\n#### **七、列表渲染**\n\n**v-for指令（一般用于数据渲染）：**\n\n- 1.用于展示列表数据\n- 2.语法：v-for=\"(item,index)in xxx\'\":key=\"yyy\"\n- 3.可遍历：数组、对象、字符串（用的很少）、指定次数（用的很少）\n\n示例：\n\n```vue\n<body>\n    <div id=\"list\">\n        <ul v-for=\"book,index in books\" :key=\"index\">\n            <li>{{book}}---{{index}}</li>\n        </ul>\n        <hr>\n        <ul v-for=\"val,key in person\" :key=\"key\">\n            <li>{{key}}---{{val}}</li>\n        </ul>\n    </div>\n    <script text=\"javascript\">\n        new Vue({\n            el:\'#list\',\n            data() {\n                return {\n                    books:[\n                        {\n                            id:\'001\',\n                            name:\'明朝那些事儿\',\n                            author:\'当年明月\',\n                            price:\'45.00\'\n                        },\n                        {\n                            id:\'002\',\n                            name:\'java开发手册\',\n                            author:\'孤尽\',\n                            price:\'35.00\'\n                        },\n                        {\n                            id:\'003\',\n                            name:\'白夜行\',\n                            author:\'东野圭吾\',\n                            price:\'59.00\'\n                        },\n                        {\n                            id:\'004\',\n                            name:\'挪威的森林\',\n                            author:\'村上春树\',\n                            price:\'59.00\'\n                        }\n                    ],\n                    person:{\n                        name:\'李华\',\n                        gender:\'男\',\n                        age:21,\n                        phone:\'1234567\'\n                    }\n                }\n            }\n        })\n    </script>\n</body>\n```\n\n注意：使用v-for遍历时，遍历的参数有两个：\n\n- 遍历数组时：参数为数组元素、索引 index ，而 :key=index 或者 元素中的 唯一标识 例如 id\n- 遍历对象时：对象由key:value组成，因此参数为val,key，val为属性，key为索引，:key=key\n\n- 遍历字符串，则是每个字符，以及对应的索引...\n\n**key的作用以及原理**\n\n```vue\n面试题：react、vue中的key有什么作用？(key的内部原理)\n\n1.虚拟DoM中key的作用：\nkey是虚拟DOM对象的标识，当数据发生变化时，Vue会根据【新数据】生成【新的虚拟DoM】,\n随后Vue进行【新虚拟DoM】与【旧虚拟DoM】的差异比较，比较规则如下：\n\n2.对比规则：\n(1).旧虚拟DoM中找到了与新虚拟DoM相同的key:\n-虚拟DOM中内容没变，直接使用之前的真实DOM!\n-若虚拟D0M中内容变了，则生成新的真实D0M,随后替换掉页面中之前的真实D0M。\n\n(2).旧虚拟DoM中未找到与新虚拟DoM相同的key：创建新的真实D0M,随后渲染到到页面。\n3.用index作为key可能会引发的问题：\n	1.若对数据进行：逆序添加、逆序删除等破环顺序操作：会产生没有必要的真实D0M更新=>界面效果没问题，但效率低。\n	\n	2.如果结构中还包含输入类的DOM:会产生错误D0M更新=>界面有问题。\n4.开发中如何选择key?:\n   (1)最好使用每条数据的唯一标识作为key,比如id、手机号、身份证号、学号等唯一值。\n   (2)如果不存在对数据的逆序添加、逆序删除等破坏顺序操作，仅用于渲染列表用于展示，使用index作为key是没有问题的。\n```\n\n#### 八、Vue监视数据的原理\n\n**Vue监视数据的原理：**\n\n1. **vue会监视data中所有层次的数据。**\n\n2. **如何监测对象中的数据？**\n\n   通过setter实现监视，且要在new Vue时就传入要监测的数据。\n\n​        (1). **对象中后追加的属性**，Vue默认不做响应式处理\n\n​        (2). 如需给后添加的属性做响应式，请使用如下API：\n\n​                 Vue.set (target，propertyName/index，value) 或 \n\n​                 vm.$set (target，propertyName/index，value)\n\n​    3.  **如何监测数组中的数据？**\n\n​        通过包裹数组更新元素的方法实现，本质就是做了两件事：\n\n​        (1). 调用原生对应的方法对数组进行更新。\n\n​        (2). 重新解析模板，进而更新页面。\n\n4. 在**Vue修改数组中的某个元素**一定要用如下方法：\n\n​              **1.使用这些API:push()、pop()、shift()、unshift()、splice()、sort()、reverse()**\n\n​              2.Vue.set() 或 vm.$set()\n\n​        **特别注意：Vue.set() 和 vm.$set() 不能给vm 或 vm的根数据对象 添加属性！！！**\n\n','本文主要简单的介绍Vue2的基础语法和属性以及相关方法的作用，没有涉及实际项目开发过程中的使用。没有涉及Vuex和脚手架等组件化的知识。',15,'http://cdn.noloafing.icu/2022/11/0287b2292bd64f42a38f45518a59d5467e.png','0','0',5,'0',1,'2022-11-02 03:58:19',NULL,'2022-11-02 22:20:00',0);
/*!40000 ALTER TABLE `nl_article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nl_article_tag`
--

DROP TABLE IF EXISTS `nl_article_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nl_article_tag` (
  `article_id` bigint NOT NULL AUTO_INCREMENT COMMENT '文章id',
  `tag_id` bigint NOT NULL DEFAULT '0' COMMENT '标签id',
  PRIMARY KEY (`article_id`,`tag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章标签关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nl_article_tag`
--

LOCK TABLES `nl_article_tag` WRITE;
/*!40000 ALTER TABLE `nl_article_tag` DISABLE KEYS */;
INSERT INTO `nl_article_tag` VALUES (1,1),(1,4),(1,5),(2,4),(2,5),(3,4),(3,5),(16,1),(16,4),(16,5),(17,4),(17,5),(18,4),(19,5),(20,1),(20,4),(21,1),(21,5),(22,5),(22,6),(23,4),(24,5);
/*!40000 ALTER TABLE `nl_article_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nl_category`
--

DROP TABLE IF EXISTS `nl_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nl_category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL COMMENT '分类名',
  `pid` bigint DEFAULT '-1' COMMENT '父分类id，如果没有父分类为-1',
  `description` varchar(512) DEFAULT NULL COMMENT '描述',
  `status` char(1) DEFAULT '0' COMMENT '状态0:正常,1禁用',
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='分类表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nl_category`
--

LOCK TABLES `nl_category` WRITE;
/*!40000 ALTER TABLE `nl_category` DISABLE KEYS */;
INSERT INTO `nl_category` VALUES (1,'Java',-1,'Java相关','0',NULL,NULL,NULL,'2022-10-03 10:59:34',0),(2,'PHP',-1,'PHP相关的内容','1',NULL,NULL,NULL,'2022-10-03 12:01:52',0),(15,'Vue',-1,'Vue相关','0',NULL,NULL,NULL,'2022-10-03 10:31:12',0),(16,'C++',-1,'','1',NULL,NULL,NULL,'2022-10-03 09:20:59',1),(17,'Vue',-1,'vvv','1',NULL,NULL,NULL,'2022-10-03 09:22:29',1),(18,'Vue',-1,'vvv','1',1,'2022-10-03 08:53:05',NULL,'2022-10-03 09:22:31',1),(19,'C#',-1,'C#相关的内容','0',1,'2022-10-03 10:29:50',NULL,'2022-10-03 12:00:27',0),(20,'12',-1,NULL,'0',1,'2022-10-03 11:02:56',NULL,'2022-10-03 11:18:43',1),(21,'13',-1,NULL,'0',1,'2022-10-03 11:02:59',NULL,'2022-10-03 11:13:04',1),(22,'11',-1,NULL,'0',1,'2022-10-03 11:18:18',NULL,'2022-10-03 11:18:43',1),(23,'11',-1,NULL,'0',1,'2022-10-03 11:18:22',NULL,'2022-10-03 11:18:27',1),(24,'22',-1,NULL,'0',1,'2022-10-03 11:18:33',NULL,'2022-10-03 11:18:43',1),(25,'12',-1,NULL,'0',1,'2022-10-03 11:18:36',NULL,'2022-10-03 11:18:43',1),(26,'Python',-1,'Python学得好，牢饭吃得早','1',1,'2022-10-03 12:01:13',NULL,'2022-10-04 09:34:56',0);
/*!40000 ALTER TABLE `nl_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nl_comment`
--

DROP TABLE IF EXISTS `nl_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nl_comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `type` char(1) DEFAULT '0' COMMENT '评论类型（0代表文章评论，1代表友链评论）',
  `article_id` bigint DEFAULT NULL COMMENT '文章id',
  `root_id` bigint DEFAULT '-1' COMMENT '根评论id',
  `content` varchar(512) DEFAULT NULL COMMENT '评论内容',
  `to_comment_user_id` bigint DEFAULT '-1' COMMENT '所回复的目标评论的userid',
  `to_comment_id` bigint DEFAULT '-1' COMMENT '回复目标评论id',
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
  `avatar` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='评论表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nl_comment`
--

LOCK TABLES `nl_comment` WRITE;
/*!40000 ALTER TABLE `nl_comment` DISABLE KEYS */;
INSERT INTO `nl_comment` VALUES (57,'1',1,-1,'网站名称：开发者搜索\n网址：https://kaifa.baidu.com/\n描述：百度开发者搜索引擎\nlogo: https://kaifa.baidu.com/images/logo.png\n',-1,-1,1,'2022-11-02 20:07:12',1,'2022-11-02 20:07:12',0,'http://cdn.noloafing.icu/2022/09/29cf89721d2af142d9abd3cb71476a755c.png'),(60,'1',1,-1,'哈哈哈[太开心]',-1,-1,14787164048689,'2022-11-02 20:49:30',14787164048689,'2022-11-02 20:49:30',0,''),(61,'1',1,-1,'[思考]Test',-1,-1,14787164048689,'2022-11-02 20:57:59',14787164048689,'2022-11-02 20:57:59',0,'http://cdn.noloafing.icu/2022/11/023c4b6775b30b476f8a8e0174f5e89d55.jpg'),(62,'1',1,-1,'发送评论[哈哈]',-1,-1,14787164048688,'2022-11-02 21:32:42',14787164048688,'2022-11-02 21:32:42',0,'http://cdn.noloafing.icu/2022/11/021ee3c7ee67854f3794d06205afed5764.jpg');
/*!40000 ALTER TABLE `nl_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nl_link`
--

DROP TABLE IF EXISTS `nl_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nl_link` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL,
  `logo` varchar(256) DEFAULT NULL,
  `description` varchar(512) DEFAULT NULL,
  `address` varchar(128) DEFAULT NULL COMMENT '网站地址',
  `status` char(1) DEFAULT '2' COMMENT '审核状态 (0代表审核通过，1代表审核未通过，2代表未审核)',
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='友链';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nl_link`
--

LOCK TABLES `nl_link` WRITE;
/*!40000 ALTER TABLE `nl_link` DISABLE KEYS */;
INSERT INTO `nl_link` VALUES (1,'Noloafing','http://www.noloafing.icu/wp-content/uploads/2022/01/cropped-%E5%8F%AE%E5%BD%93%E7%8C%AB.png','Noloafing博客','http://noloafing.icu','0',NULL,'2022-01-13 08:25:47',NULL,'2022-10-06 12:35:05',0),(2,'CSDN','https://logovtor.com/wp-content/uploads/2019/11/csdn-logo-vector.png','CSDN个人空间','https://blog.csdn.net/m0_51972565?spm=1000.2115.3001.5343','0',NULL,'2022-01-13 09:06:10',NULL,'2022-10-06 12:35:05',0),(3,'sa','https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fn1.itc.cn%2Fimg8%2Fwb%2Frecom%2F2016%2F05%2F10%2F146286696706220328.PNG&refer=http%3A%2F%2Fn1.itc.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1646205529&t=f942665181eb9b0685db7a6f59d59975','da','https://www.taobao.com','1',NULL,'2022-01-13 09:23:01',NULL,'2022-10-22 11:04:10',0),(4,'开发者搜索','https://kaifa.baidu.com/assets/favicon.ico','百度的开发者搜索引擎','https://kaifa.baidu.com/','0',1,'2022-10-06 11:05:03',NULL,'2022-10-06 12:35:05',0);
/*!40000 ALTER TABLE `nl_link` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nl_resourcewall`
--

DROP TABLE IF EXISTS `nl_resourcewall`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nl_resourcewall` (
  `resource_name` varchar(256) DEFAULT NULL COMMENT '资源说明',
  `address` varchar(512) DEFAULT NULL COMMENT '资源的网址',
  `id` int NOT NULL AUTO_INCREMENT COMMENT '资源id',
  `del_flag` int DEFAULT '0' COMMENT '0：未删除 1：删除',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='资源墙';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nl_resourcewall`
--

LOCK TABLES `nl_resourcewall` WRITE;
/*!40000 ALTER TABLE `nl_resourcewall` DISABLE KEYS */;
INSERT INTO `nl_resourcewall` VALUES ('NoloafingBlog','https://noloafing.icu',1,0,'个人博客'),('RSA+AES混合加密','https://www.cnblogs.com/huanzi-qch/p/10913636.html',2,0,'前后端接口信息加密，思路好，缺点就是没有公钥信任体系，不然也不会用SSL了'),('TryRedis','https://try.redis.io/',3,0,'适合学习Redis的基本命令'),('认识单点登录','https://www.cnblogs.com/niceyoo/p/11305143.html',4,0,'一篇理解单点登录的文章，适合初步认识'),('正则表达式生成','https://www.mklab.cn/utils/regex',5,0,'验证和生成正则表达式，实现数据校验'),('HashMap底层原理','https://mrbird.cc/Java-HashMap%E5%BA%95%E5%B1%82%E5%AE%9E%E7%8E%B0%E5%8E%9F%E7%90%86.html',6,0,'讲的不错'),('Linux命令工具','https://wangchujiang.com/linux-command/#!kw=ls',7,0,'快速查找你所需要的Linux命令');
/*!40000 ALTER TABLE `nl_resourcewall` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nl_tag`
--

DROP TABLE IF EXISTS `nl_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nl_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL COMMENT '标签名',
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='标签';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nl_tag`
--

LOCK TABLES `nl_tag` WRITE;
/*!40000 ALTER TABLE `nl_tag` DISABLE KEYS */;
INSERT INTO `nl_tag` VALUES (1,'Mybatis',NULL,NULL,NULL,'2022-09-29 10:41:31',0,''),(2,'SpringBoot',NULL,'2022-01-11 09:20:55',NULL,'2022-01-11 09:20:55',1,'weqw'),(3,'MySQL',NULL,'2022-01-11 09:21:07',NULL,'2022-01-11 09:21:07',1,'qweqwe'),(4,'Java',NULL,NULL,NULL,'2022-08-28 11:55:09',0,'Java学习笔记'),(5,'Vue',NULL,NULL,NULL,'2022-08-28 11:54:25',0,'Vue笔记'),(6,'C++',1,'2022-08-28 08:04:44',1,'2022-08-28 08:04:44',0,'C++心得'),(9,'Spring                  ',1,'2022-08-28 09:07:38',1,'2022-08-28 09:07:38',0,NULL);
/*!40000 ALTER TABLE `nl_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `parent_id` bigint DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int DEFAULT '0' COMMENT '显示顺序',
  `path` varchar(200) DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) DEFAULT NULL COMMENT '组件路径',
  `is_frame` int DEFAULT '1' COMMENT '是否为外链（0是 1否）',
  `menu_type` char(1) DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `del_flag` char(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2045 DEFAULT CHARSET=utf8mb3 COMMENT='菜单权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES (1,'系统管理',0,1,'system',NULL,1,'M','0','0','','system',0,'2021-11-12 10:46:19',0,NULL,'系统管理目录','0'),(100,'用户管理',1,1,'user','system/user/index',1,'C','0','0','system:user:list','user',0,'2021-11-12 10:46:19',1,'2022-07-31 15:47:58','用户管理菜单','0'),(101,'角色管理',1,2,'role','system/role/index',1,'C','0','0','system:role:list','peoples',0,'2021-11-12 10:46:19',0,NULL,'角色管理菜单','0'),(102,'菜单管理',1,3,'menu','system/menu/index',1,'C','0','0','system:menu:list','tree-table',0,'2021-11-12 10:46:19',0,NULL,'菜单管理菜单','0'),(1001,'用户查询',100,1,'','',1,'F','0','0','system:user:query','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1002,'用户新增',100,2,'','',1,'F','0','0','system:user:add','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1003,'用户修改',100,3,'','',1,'F','0','0','system:user:edit','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1004,'用户删除',100,4,'','',1,'F','0','0','system:user:remove','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1005,'用户导出',100,5,'','',1,'F','0','0','system:user:export','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1006,'用户导入',100,6,'','',1,'F','0','0','system:user:import','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1007,'重置密码',100,7,'','',1,'F','0','0','system:user:resetPwd','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1008,'角色查询',101,1,'','',1,'F','0','0','system:role:query','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1009,'角色新增',101,2,'','',1,'F','0','0','system:role:add','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1010,'角色修改',101,3,'','',1,'F','0','0','system:role:edit','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1011,'角色删除',101,4,'','',1,'F','0','0','system:role:remove','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1012,'角色导出',101,5,'','',1,'F','0','0','system:role:export','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1013,'菜单查询',102,1,'','',1,'F','0','0','system:menu:query','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1014,'菜单新增',102,2,'','',1,'F','0','0','system:menu:add','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1015,'菜单修改',102,3,'','',1,'F','0','0','system:menu:edit','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(1016,'菜单删除',102,4,'','',1,'F','0','0','system:menu:remove','#',0,'2021-11-12 10:46:19',0,NULL,'','0'),(2017,'内容管理',0,4,'content',NULL,1,'M','0','0',NULL,'table',NULL,'2022-01-08 02:44:38',1,'2022-07-31 12:34:23','','0'),(2018,'分类管理',2017,1,'category','content/category/index',1,'C','0','0','content:category:list','example',NULL,'2022-01-08 02:51:45',NULL,'2022-01-08 02:51:45','','0'),(2019,'文章管理',2017,0,'article','content/article/index',1,'C','0','0','content:article:list','build',NULL,'2022-01-08 02:53:10',NULL,'2022-01-08 02:53:10','','0'),(2021,'标签管理',2017,6,'tag','content/tag/index',1,'C','0','0','content:tag:index','button',NULL,'2022-01-08 02:55:37',NULL,'2022-01-08 02:55:50','','0'),(2022,'友链管理',2017,4,'link','content/link/index',1,'C','0','0','content:link:list','404',NULL,'2022-01-08 02:56:50',NULL,'2022-01-08 02:56:50','','0'),(2023,'写博文',0,0,'write','content/article/write/index',1,'C','0','0','content:article:writer','build',NULL,'2022-01-08 03:39:58',NULL,'2022-11-02 07:43:22','','0'),(2024,'友链新增',2022,0,'',NULL,1,'F','0','0','content:link:add','#',NULL,'2022-01-16 07:59:17',NULL,'2022-01-16 07:59:17','','0'),(2025,'友链修改',2022,1,'',NULL,1,'F','0','0','content:link:edit','#',NULL,'2022-01-16 07:59:44',NULL,'2022-01-16 07:59:44','','0'),(2026,'友链删除',2022,1,'',NULL,1,'F','0','0','content:link:remove','#',NULL,'2022-01-16 08:00:05',NULL,'2022-01-16 08:00:05','','0'),(2027,'友链查询',2022,2,'',NULL,1,'F','0','0','content:link:query','#',NULL,'2022-01-16 08:04:09',NULL,'2022-01-16 08:04:09','','0'),(2028,'导出分类',2018,1,'',NULL,1,'F','0','0','content:category:export','#',NULL,'2022-01-21 07:06:59',NULL,'2022-01-21 07:06:59','','0'),(2036,'Bug',2017,5,'bug',NULL,1,'M','0','0',NULL,'bug',NULL,NULL,NULL,'2022-10-07 12:22:48','','1'),(2038,'404',0,5,'404','content/tag/index',1,'C','0','0',NULL,'404',NULL,NULL,NULL,'2022-10-07 12:22:10','','1'),(2043,'code',2036,3,'code','bug/code',1,'C','0','0','system:bug:code','code',1,'2022-10-07 11:35:37',NULL,'2022-10-07 12:22:41','','1'),(2044,'资源墙管理',2017,7,'resourceWall','content/resourceWall/index',1,'C','0','0','','international',1,'2022-11-02 07:36:52',NULL,'2022-11-02 07:44:26','','0');
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) NOT NULL COMMENT '角色权限字符串',
  `role_sort` int NOT NULL COMMENT '显示顺序',
  `status` char(1) NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb3 COMMENT='角色信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'超级管理员','admin',1,'0','0',0,'2021-11-12 10:46:19',0,NULL,'超级管理员'),(2,'普通角色','common',2,'0','0',0,'2021-11-12 10:46:19',0,'2022-01-01 22:32:58','普通角色'),(11,'嘎嘎嘎','aggag',5,'0','0',NULL,'2022-01-06 14:07:40',NULL,'2022-01-07 03:48:48','嘎嘎嘎'),(12,'友链审核员','link',1,'0','0',NULL,'2022-01-16 06:49:30',NULL,'2022-01-16 08:05:09',NULL),(18,'test','aaa',0,'0','0',1,'2022-10-23 04:27:55',1,'2022-10-23 04:27:55',NULL);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_menu` (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='角色和菜单关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` VALUES (0,0),(2,1),(2,102),(2,1013),(2,1014),(2,1015),(2,1016),(2,2000),(3,2),(3,3),(3,4),(3,100),(3,101),(3,103),(3,104),(3,105),(3,106),(3,107),(3,108),(3,109),(3,110),(3,111),(3,112),(3,113),(3,114),(3,115),(3,116),(3,500),(3,501),(3,1001),(3,1002),(3,1003),(3,1004),(3,1005),(3,1006),(3,1007),(3,1008),(3,1009),(3,1010),(3,1011),(3,1012),(3,1017),(3,1018),(3,1019),(3,1020),(3,1021),(3,1022),(3,1023),(3,1024),(3,1025),(3,1026),(3,1027),(3,1028),(3,1029),(3,1030),(3,1031),(3,1032),(3,1033),(3,1034),(3,1035),(3,1036),(3,1037),(3,1038),(3,1039),(3,1040),(3,1041),(3,1042),(3,1043),(3,1044),(3,1045),(3,1046),(3,1047),(3,1048),(3,1049),(3,1050),(3,1051),(3,1052),(3,1053),(3,1054),(3,1055),(3,1056),(3,1057),(3,1058),(3,1059),(3,1060),(3,2000),(11,1),(11,100),(11,101),(11,102),(11,103),(11,104),(11,105),(11,106),(11,107),(11,108),(11,500),(11,501),(11,1001),(11,1002),(11,1003),(11,1004),(11,1005),(11,1006),(11,1007),(11,1008),(11,1009),(11,1010),(11,1011),(11,1012),(11,1013),(11,1014),(11,1015),(11,1016),(11,1017),(11,1018),(11,1019),(11,1020),(11,1021),(11,1022),(11,1023),(11,1024),(11,1025),(11,1026),(11,1027),(11,1028),(11,1029),(11,1030),(11,1031),(11,1032),(11,1033),(11,1034),(11,1035),(11,1036),(11,1037),(11,1038),(11,1039),(11,1040),(11,1041),(11,1042),(11,1043),(11,1044),(11,1045),(11,2000),(11,2003),(11,2004),(11,2005),(11,2006),(11,2007),(11,2008),(11,2009),(11,2010),(11,2011),(11,2012),(11,2013),(11,2014),(12,2017),(12,2022),(12,2024),(12,2025),(12,2026),(12,2027);
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '用户名',
  `nick_name` varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '昵称',
  `password` varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '密码',
  `type` char(1) DEFAULT '0' COMMENT '用户类型：0代表普通用户，1代表管理员',
  `status` char(1) DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `phonenumber` varchar(32) DEFAULT NULL COMMENT '手机号',
  `sex` char(1) DEFAULT NULL COMMENT '用户性别（0男，1女，2未知）',
  `avatar` varchar(128) DEFAULT NULL COMMENT '头像',
  `create_by` bigint DEFAULT NULL COMMENT '创建人的用户id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` int DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14787164048690 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,'Noloafing0705','Noloafing','$2a$10$tbIdcNBk9Fa1mzoJPnY3WOFxXIaWTam8zTuSlWKfeyTM43HO/ZtNe','1','0','23412332@qq.com','18888888888','0','http://cdn.noloafing.icu/2022/09/29cf89721d2af142d9abd3cb71476a755c.png',NULL,'2022-01-05 09:01:56',NULL,'2022-11-02 12:03:41',0),(3,'sg3','weqe','$2a$10$ydv3rLkteFnRx9xelQ7elOiVhFvXOooA98xCqk/omh7G94R.K/E3O','1','0',NULL,NULL,'0',NULL,NULL,'2022-01-05 13:28:43',NULL,'2022-01-05 13:28:43',1),(4,'sg2','dsadd','$2a$10$kY4T3SN7i4muBccZppd2OOkhxMN6yt8tND1sF89hXOaFylhY2T3he','1','0','23412332@qq.com','19098790742','0',NULL,NULL,NULL,NULL,NULL,0),(5,'sg2233','tteqe','','1','0','mufei9131520@163.com','18246845873','0',NULL,NULL,'2022-01-06 03:51:13',NULL,'2022-11-01 02:38:04',0),(6,'sangeng','sangeng','$2a$10$Jnq31rRkNV3RNzXe0REsEOSKaYK8UgVZZqlNlNXqn.JeVcj2NdeZy','1','0','2312321','17777777777','0',NULL,NULL,'2022-01-16 06:54:26',NULL,'2022-01-16 07:06:34',0),(14787164048662,'weixin','weixin','$2a$10$y3k3fnMZsBNihsVLXWfI8uMNueVXBI08k.LzWYaKsW8CW7xXy18wC','0','0','weixin@qq.com',NULL,NULL,NULL,-1,'2022-01-30 17:18:44',-1,'2022-01-30 17:18:44',0),(14787164048663,'Noloafing','那必须是我了','$2a$10$slqGozgYRzFO9gdvrTEznuhwNIN00Vrl4LqDs4T/fOew3mg3O/wEe','0','0','123456@test.com',NULL,'0','http://cdn.noloafing.icu/2022/08/15757db789e58e400cb1dcefa134bf3d50.png',-1,'2022-08-15 17:26:59',NULL,'2022-08-15 21:51:54',1),(14787164048669,'qwer','qwer','$2a$10$0pejV7NAuhninMH1g59/oeliTf0MrPMF7TFnzI8fZoCrhIljrWkye','0','0','qwer@1234.com',NULL,NULL,NULL,-1,'2022-08-15 17:59:00',-1,'2022-08-15 17:59:00',1),(14787164048670,'qwer1','qwer1','$2a$10$ggfvVcd3d.FDMcIYlPaSt.nbxlrcpW/kHEkD3vuCLK3p8BiAqprE6','0','0','qwer123@aa.com',NULL,NULL,NULL,-1,'2022-08-15 18:20:47',-1,'2022-08-15 18:20:47',1),(14787164048672,'haha','Noloafing2','$2a$10$AOWUyEKxCZKoJ4O6jGS4suE4pqcp./M7kJRLbHs4soH5IEPO2NYZW','0','0','1845008982@qq.com','15281619587','0',NULL,1,'2022-10-21 11:02:52',NULL,'2022-10-22 09:48:32',1),(14787164048688,'wagnyi163','网易163','$2a$10$/wHulmrwjxNkh33TjHzFQuY8.t5jeOrD6pUUdGlKhlZyYAjFKQ6TK','0','0','yoona1845008982@163.com',NULL,'0','http://cdn.noloafing.icu/2022/11/021ee3c7ee67854f3794d06205afed5764.jpg',-1,'2022-11-01 01:34:29',NULL,'2022-11-02 21:31:55',0),(14787164048689,'QQmail','QQ邮箱','$2a$10$tbIdcNBk9Fa1mzoJPnY3WOFxXIaWTam8zTuSlWKfeyTM43HO/ZtNe','0','0','1845008982@qq.com',NULL,'0','http://cdn.noloafing.icu/2022/11/023c4b6775b30b476f8a8e0174f5e89d55.jpg',-1,'2022-11-01 11:17:55',NULL,'2022-11-02 20:10:43',0);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_role` (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='用户和角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES (1,1),(2,2),(5,2),(6,12),(14787164048673,2);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-03 18:37:17
