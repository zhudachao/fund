# FUND

### 介绍

​	 fund是基于springboot搭建的基金净值爬取后台项目，目前提供了爬取天天基金基金列表、基金概况以及基金历史净值等数据。后期可以构建统计模型，对基金数据进行统计分析。

### 功能点

1. 爬取基金代码
2. 基金详细信息登记
3. 基金历史净值登记接口
4. 定时任务获取最新净值信息

### 表设计

1. fund_data	基金净值表
2. fund_list       基金列表      
3. scheduled_corn  定时任务表
4. sys_sequence   流水定义表

### 提供接口

​	http://localhost:8088/swagger-ui.html#/![1610334134569](C:\Users\zdchao\AppData\Roaming\Typora\typora-user-images\1610334134569.png)

### 项目架构

-  使用druid数据源配置连接池，mybatis操作数据库
- 引入swagger文档支持，方便编写API接口文档。
-  引入jsoup页面解析
- 使用assembly打tar包,配置分离
- 使用log4j2封装日志，实现日志友好展示

### 构建统计模型  

 示例 1.统计某只基金当天增长点与第二日的长/跌的概率
