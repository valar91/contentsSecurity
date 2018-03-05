内容安全项目---Ziroom-Eunomia

提供一站式内容安全服务解决方案
前言

项目名字背景：

赫西俄德的著作《神谱》中，她们是宙斯和忒弥斯的女儿，为命运三女神—摩伊赖的姊妹。
她们是强调“正确的秩序”观念的时序三女神，其职责比起以前来说扩大到掌管社会的法律与秩序。她们如下：

1. 欧诺弥亚（Εὐνομία，Eunomia，或译尤诺米阿，意指“〈法治下的〉秩序”），代表良好法则和秩序，并维护社会的安定。

项目背景、目标、价值

    背景 --

    内容不安全有什么影响？

    “不安全”的文本可能触及法律，造成侵权违规
    对公司形象造成不必要的影响
    总结：更有可能造成亡羊补牢

    现在一些相关内容安全处理方式？

    人工审核大量文本信息
    人工记录每天都可能新增的广告词，黄反词，以及竞争对手的一些相关词汇量
    有部分工具帮助解决敏感词提示，但稳定投入生产线还需要很大处理工作
    总结：人工操作过多

    我们能做什么？

    提升内容审核效率，节约公司成本（面对公司日益壮大，产品增多，必不可少的会增加产品（房源）介绍等信息维护及审核的时间成本，通过eunomia系统，我们可节约过半的成本（还未调研，不精确））
    提升内容审核精准度，更安全的避免不必要的损失
    敏感词库及其数据量的持续增长，对后期分析萃取有用成份，人工智能预测及优选文本等保证了基础数据，在某种程度上提升同行业竞争力，保证稳定的品牌度
    总结：精准，高效，节约成本，更安全，提升科技化竞争力，百利而无一害

    目标 --

    本次目标

稳定提供自如产品线，关于文本内容使用场景的安全性校验服务， 目前划分三个类型的敏感词库，词库作为文本违规排查等业务的基础：

    黄反词
    广告词
    竟品词
    敏感词库的维护功能
    提示包含敏感词文本，统计出现次数等功能！
    提供在线api，即时做安全性文本校验
    接入现有的业务
        房源描述及周边介绍(民宿、自如寓、自如驿、友家、整租)
        评价内容校验 (民宿、自如网)
        营销活动校验(可以人工或者系统接入)

    中长期目标

    图片安全识别
    自然语言语义分析处理
        维护情感语义字典
        神经网络学习，提高精准的文本质量分析，精选文本
    数据安全趋势相关分析
        敏感词云图
        产品内容违规趋势分析
        产品精选

    价值 --

sequenceDiagram
词库量->>价值: more
接入应用->>价值: more

项目规划

（待定）具体需要参考详细排期
项目框架

    项目结构 ---

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

    项目技术 ---

基础依赖框架：SpringBoot -version:1.5.8.RELEASE

    web端:dashboard

- BaseFramework: SpringMvc
- ORM:  MyBatis
- DB:   Mysql
- ... : ...

2.3 开发环境

    插件-Plugin

    项目编译依赖插件lombok, 根据不同IDE手动配置该插件
    maven plugin, 无需手动配置

    版本-Version

JDK : 1.8+
Maven : 3.0+
... : ...

2.4 启动

项目依赖于SpringBoot, Maven管理

    Compile

mvn clean install

    Run & Debug

    开发
        IDE : Springboot Plugin 配置 Active Profiles : dev, EunomiaDashboardApplication.java 右键 run or debug
        Mvn : clean spring-boot:run
        Bash : java -Xms256m -Xmx256m -Dspring.profiles.active=dev -jar eunomia-dashboard-0.0.1.jar

    Deploy

    部署: java -Xms256m -Xmx256m -Dspring.profiles.active=dev -jar eunomia-dashboard-0.0.1.jar

数据库表
3.1 数据库表描述
table 	depiction
sensitive_word 	敏感词表
sensitive_word_examine_log 	敏感词审核日志表
organization 	授权应用表
organization_text_log 	授权应用调用信息日志表
r_org_text_word 	授权应用查询文本与命中敏感词关系表
约定

开发过程中，可能存在开发不顺手的地方，为此我们调节约定如下
4.1 配置文件

    请勿提交 application-local.properties 文件，该配置文件属于个人配置，方便开发人员随意配置，如端口，回调地址等，基础配置启动项具体可参考 application-local.properties.example;
    开发中默认公共配置都存储于application.properties,个人自定义配置在dev中修改，新增配置项，请更新并注释到application-local.properties.example

IKAnalyzer

致敬：七年前的停止维护的项目 感谢作者林良益 提供的友好的高效中文分词技术！

    以下摘自作者开源项目 README 

IKAnalyzer是一个开源的，基于java语言开发的轻量级的中文分词工具包。从2006年12月推出1.0版开始，IKAnalyzer已经推出了3个大版本。最初，它是以开源项目Luence为应用主体的，结合词典分词和文法分析算法的中文分词组件。新版本的 IKAnalyzer3.0则发展为面向Java的公用分词组件，独立于Lucene项目，同时提供了对Lucene的默认优化实现。

IKAnalyzer3.0特性:

采用了特有的“正向迭代最细粒度切分算法“，支持细粒度和最大词长两种切分模式；具有83万字/秒（1600KB/S）的高速处理能力。

采用了多子处理器分析模式，支持：英文字母、数字、中文词汇等分词处理，兼容韩文、日文字符

优化的词典存储，更小的内存占用。支持用户词典扩展定义

针对Lucene全文检索优化的查询分析器IKQueryParser(作者吐血推荐)；引入简单搜索表达式，采用歧义分析算法优化查询关键字的搜索排列组合，能极大的提高Lucene检索的命中率。

IKAnalyzer的作者为林良益（linliangyi2007@gmail.com），项目网站为http://code.google.com/p/ik-analyzer/。

Maven工程由王坤山创建（wks1986@gmail.com）。创建的目的是为了方便用于其他Maven工程。你可以在 https://github.com/wks/ik-analyzer 网站找到本工程。

Maven用法：

将以下依赖加入工程的pom.xml中的...部分。 org.wltea.ik-analyzer ik-analyzer 3.2.8

在IK Analyzer加入Maven Central Repository之前，你需要手动安装，安装到本地的repository，或者上传到自己的Maven repository服务器上。

要安装到本地Maven repository，使用如下命令，将自动编译，打包并安装： mvn install -Dmaven.test.skip=true

