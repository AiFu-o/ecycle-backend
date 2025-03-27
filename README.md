<div align="center">

[![Gitee star](https://gitee.com/mindskip/xzs-mysql/badge/star.svg?theme=gitee)](https://gitee.com/wang-weichen/ecycle-backend)
[![Gitee fork](https://gitee.com/mindskip/xzs-mysql/badge/fork.svg?theme=gitee)](https://gitee.com/wang-weichen/ecycle-backend)
[![Github stars](https://img.shields.io/github/stars/mindskip/xzs-mysql?logo=github)](https://github.com/AiFu-o/ecycle-backend)
[![Github forks](https://img.shields.io/github/forks/mindskip/xzs-mysql?logo=github)](https://github.com/AiFu-o/ecycle-backend)
[![Github license](https://img.shields.io/badge/license-AGPL-yellow)](https://gitee.com/wang-weichen/ecycle-backend/blob/master/LICENSE)

</div>
这个项目有个悲伤的故事，两个程序员🐶在北京艰苦奋斗，早中晚在认真搬砖之余摸鱼写这个，本想创业做一个自己的小程序，结果被人放鸽子，创始人去卖车了。所以打算把这个项目开源，希望这个项目能发挥一点点的价值。

管理端前端有些不完善，但是主流程(支付、商品发布、拍卖)是通的

如果这个项目让你有所收获，记得 Star 关注哦。

### 项目简介
**有价**，后端基于 SpringMvc 构建，小程序基于 uniapp 构建，个人可以直接使用，如要商用请联系微信 w569111843。

### 项目结构
~~~
|-- ecycle-auth 用户、权限管理
|-- ecycle-commodity 商品、订单管理
|-- ecycle-gateway 网关
|-- ecycle-message 消息推送
|-- ecycle-pay 支付(目前只有微信支付)
|-- ecycle-storage 附件管理
~~~

### 项目启动教程
* 配置环境(java11、nacos2、mysql8、redis)
* 将项目导入 idea
* 第一次启动服务前需要执行 **init.sql** 创建数据库表
* 启动 nacos，并创建配置文件，配置文件名根据 bootstrap.yaml中的配置创建

  ***具体配置项参考各个服务下的 application.yaml-temp***

  配置文件名称示例:
    ~~~
    spring:
      application:
        name: ecycle-commodity
      profiles:
        active: dev
    ## 配置文件名称 = 
    ## ecycle-commodity 或 ecycle-commodity-dev
  
    ~~~
* 按顺序启动服务
 
  ***gateway > auth > commodity > pay > message > storage***

### gitee仓库地址
* 管理端前端：https://gitee.com/wang-weichen/ecycle-manage-frontend
* 小程序端：https://gitee.com/JoKing_93/ecycle-frontend
* 后端：https://gitee.com/wang-weichen/ecycle-backend

### github仓库地址
* 管理端前端：https://github.com/AiFu-o/ecycle-manage-frontend
* 小程序端：https://github.com/AiFu-o/ecycle-frontend
* 后端：https://github.com/AiFu-o/ecycle-backend