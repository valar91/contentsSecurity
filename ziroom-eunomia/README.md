# 内容安全项目---Ziroom-Eunomia


提供一站式**内容安全服务**解决方案

---

## 前言
项目名字背景：
```
赫西俄德的著作《神谱》中，她们是宙斯和忒弥斯的女儿，为命运三女神—摩伊赖的姊妹。
她们是强调“正确的秩序”观念的时序三女神，其职责比起以前来说扩大到掌管社会的法律与秩序。她们如下：

1. 欧诺弥亚（Εὐνομία，Eunomia，或译尤诺米阿，意指“〈法治下的〉秩序”），代表良好法则和秩序，并维护社会的安定。

```



## 项目背景、目标、价值

1. 背景
    --
    
> 内容不安全有什么影响？

- “不安全”的文本可能触及法律，造成侵权违规
- 对公司形象造成不必要的影响
- **总结：更有可能造成亡羊补牢**

> 现在一些相关内容安全处理方式？

- 人工审核大量文本信息
- 人工记录每天都可能新增的广告词，黄反词，以及竞争对手的一些相关词汇量
- 有部分工具帮助解决敏感词提示，但稳定投入生产线还需要很大处理工作
- **总结：人工操作过多**

> 我们能做什么？

- 提升内容审核效率，节约公司成本（面对公司日益壮大，产品增多，必不可少的会增加产品（房源）介绍等信息维护及审核的时间成本，通过eunomia系统，我们可节约过半的成本（还未调研，不精确））
- 提升内容审核精准度，更安全的避免不必要的损失
- 敏感词库及其数据量的持续增长，对后期分析萃取有用成份，人工智能预测及优选文本等保证了基础数据，在某种程度上提升同行业竞争力，保证稳定的品牌度
- **总结：精准，高效，节约成本，更安全，提升科技化竞争力，百利而无一害**




2. 目标
    --


> 本次目标

稳定提供自如产品线，关于文本内容使用场景的**安全性**校验服务，
目前划分三个类型的敏感词库，词库作为文本违规排查等业务的基础：
- **黄反词**
- **广告词**
- **竟品词**
1. 敏感词库的维护功能
2. 提示包含敏感词文本，统计出现次数等功能！
3. 提供在线api，即时做安全性文本校验
4. **接入现有的业务**
    - **房源描述及周边介绍(民宿、自如寓、自如驿、友家、整租)**  
    - **评价内容校验 (民宿、自如网)**
    - **营销活动校验(可以人工或者系统接入)**


> 中长期目标

- **图片安全识别**
- 自然语言语义分析处理
    - 维护情感语义字典
    - 神经网络学习，提高精准的文本质量分析，精选文本
- 数据安全趋势相关分析
    - 敏感词云图
    - 产品内容违规趋势分析
    - 产品精选


3. 价值
    --



```
sequenceDiagram
词库量->>价值: more
接入应用->>价值: more
```

## ~~项目规划~~


~~（待定）具体需要参考详细排期~~


## 项目框架

1. 项目结构
    ---

```
-- Zrioom-Eunomia
    --  eunomia-common      公共包
            --  exception
            --  pojo
            --  util
            --  ...
    --  eunomia-core      核心包，分词，OCR，算法等
            --  participle
            --  training
            --  ...
    --  eunomia-dashboard       管理后台
            --  dao
            --  model
            --  service
                --  impl
            --  web
                -- client       提供接口服务
            EunomiaDashboardApplication.java        启动入口
        --  resources       配置文件
            application.properties
            application-dev.properties
            application-prod.properties
            application-stage.properties
        --  test        单元测试
            ...

```
2. 项目技术
    ---
    
基础依赖框架：***SpringBoot*** -`version:1.5.8.RELEASE`
> web端:dashboard

    - BaseFramework: SpringMvc
    - ORM:  MyBatis
    - DB:   Mysql
    - ... : ...

### 2.3 开发环境

> 插件-Plugin

- 项目**编译**依赖插件`lombok`, 根据不同IDE手动配置该插件
- `maven plugin`, 无需手动配置

> 版本-Version

```
JDK : 1.8+
Maven : 3.0+
... : ...

```
> 



### 2.4 启动

项目依赖于`SpringBoot`, `Maven`管理

> Compile

```
mvn clean install

```
> Run & Debug

- 开发
    - **IDE** : **Springboot Plugin** 配置 Active Profiles : `dev`, `EunomiaDashboardApplication.java` 右键 `run` or `debug`
    - **Mvn** : `clean spring-boot:run`
    - **Bash** : `java -Xms256m -Xmx256m -Dspring.profiles.active=dev -jar eunomia-dashboard-0.0.1.jar`

> Deploy

- **部署**: `java -Xms256m -Xmx256m -Dspring.profiles.active=dev -jar eunomia-dashboard-0.0.1.jar`

## 数据库表

---
### 3.1 数据库表描述

table | depiction
---|---
sensitive_word | 敏感词表
sensitive_word_examine_log | 敏感词审核日志表
organization | 授权应用表
organization_text_log | 授权应用调用信息日志表
r_org_text_word | 授权应用查询文本与命中敏感词关系表


### 3.2 数据库表脚本

```sql 
-- 敏感词库表
-- table : sensitive_word

CREATE TABLE `word` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bid` varchar(64) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '业务Id',
  `type` tinyint(2) NOT NULL COMMENT '词库类型',
  `status` tinyint(2) NOT NULL COMMENT '审核状态',
  `content` varchar(128) NOT NULL COMMENT '词文本',
  `enable` tinyint(1) NOT NULL COMMENT '是否启用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```


```sql
-- 授权应用表
-- table : organization

CREATE TABLE `organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bid` varchar(64) NOT NULL DEFAULT '' COMMENT '业务Id',
  `depth` tinyint(2) NOT NULL DEFAULT '0' COMMENT '节点层级',
  `parent_bid` varchar(64) NOT NULL COMMENT '上级组织Bid',
  `org_name` varchar(255) NOT NULL DEFAULT '' COMMENT '组织名称',
  `domian` varchar(128) NOT NULL COMMENT '组织域名',
  `token` varchar(64) NOT NULL COMMENT '授权码',
  `is_del` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除（0：否，1：是）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='授权应用表';

```

```sql
-- 敏感词库审核日志表
CREATE TABLE `sensitive_word_examine_log` (
  `id` bigint(20) NOT NULL,
  `bid` varchar(64) NOT NULL,
  `sensitive_word_bid` varchar(64) NOT NULL COMMENT '审核敏感词业务Id',
  `operator_code` varchar(64) NOT NULL COMMENT '操作员工号',
  `previous_status` tinyint(2) NOT NULL,
  `current_status` tinyint(2) NOT NULL,
  `remark` varchar(255) DEFAULT NULL COMMENT '备注，审核驳回理由',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='敏感词审核日志表';


```

```sql
-- 应用调用文本记录

CREATE TABLE `organization_text_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bid` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '业务bid',
  `content` tinytext NOT NULL COMMENT '授权应用查询文本',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='授权应用查询文本记录表';


```
```sql
-- 关系表：授权应用查询文本与命中敏感词关系

CREATE TABLE `r_org_text_word` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bid` varchar(64) NOT NULL COMMENT '业务id',
  `organization_text_bid` varchar(64) NOT NULL COMMENT '应用方文本业务bid',
  `sensitive_word_bid` varchar(64) NOT NULL COMMENT '敏感词bid',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='关系表：授权应用查询文本与命中敏感词关系';

```


## 约定

开发过程中，可能存在开发不顺手的地方，为此我们调节约定如下

### 4.1 配置文件

- 请勿提交 **application-dev.properties** 文件，该配置文件属于个人配置，方便开发人员随意配置，如端口，回调地址等，基础配置启动项具体可参考 **application-dev.properties.example**;
- 开发中默认公共配置都存储于`application.properties`,个人自定义配置在dev中修改，新增配置项，请更新并注释到`application-dev.properties.example`




----

**具体以当前开发工作为准**