# 大事件后端项目
## 项目介绍
基于SpringBoot + MyBatis实现的文章管理系统后端，采用RBAC权限控制，区分管理员与普通用户。
- 管理员：文章分类增删改查，文章管理
- 普通用户：查看全局共享文章分类，仅可操作自己发布的文章
前后端分离项目，前端使用Vue3 + Element Plus。

## 技术栈
- 后端：SpringBoot、MyBatis、MySQL、Redis、JWT、PageHelper
- 工具：阿里云OSS文件上传

## 功能模块
1. 用户模块：注册、登录，JWT令牌校验，获取用户信息
2. 文章分类模块：分类全局共享，管理员拥有增删改权限，普通用户仅查看
3. 文章模块：用户只能管理自己发布的文章，分页查询
4. 文件上传模块：阿里云OSS上传图片

## 环境准备
1. MySQL：创建big_event数据库，导入sql脚本
2. Redis：本地启动Redis服务
3. 修改application.yml数据库连接信息
4. 修改AliOssUtil.java中阿里云OSS密钥

## 启动方式
1. 配置数据库、Redis
2. 运行BigEventApplication主类
3. 默认端口8080
