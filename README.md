# 					基于云计算的生活随笔记系统部署文档

## 1、安装项目运行环境

```txt
需要安装JDK1.8、MySQL5.7、elasticsearch6.3.2、redis，请自己前往官网下载安装。
```

## 2、application.yml关键配置

- MySQL5.7配置

  ```txt
  datasource:
    url: jdbc:mysql://xxxxxxxx:3306/life_with_notes?useUnicode=true&characterEncoding=utf-8&useSSL=false
    username: root
    password: xxxxxxxx
    driver-class-name: com.mysql.jdbc.Driver
  ```

- elasticsearch6.3.2配置

  ```txt
  data:
    elasticsearch:
      cluster-nodes: xxxxxxxx:9300
      cluster-name: my-application
  ```

- redis配置

  ```txt
  host: localhost
    password: xxxxxxxx
    database: 1
    port: 6379
    timeout: 500ms
    lettuce:
      pool:
        max-active: 8
        max-idle: 8
        max-wait: -1ms
        min-idle: 0
  ```

- 阿里云、腾讯云连接配置

  ```txt
  aliyun:
    accessKeyId: xxxxxxxxxxxxxxxx
    secret: xxxxxxxxxxxxxxxx

  tencent:
    cloud:
      appid: xxxxxxxx
      appkey: xxxxxxxxxxxxxxxx
  ```
  
  - 注释：为了安全，所有相关敏感信息做xxxxxxxx处理，需要自己手动更改为自己的信息。
## 3、项目运行效果图

![效果图1](https://ganj-blog.oss-cn-chengdu.aliyuncs.com/rendering/%E6%95%88%E6%9E%9C%E5%9B%BE1.png)

![效果图2](https://ganj-blog.oss-cn-chengdu.aliyuncs.com/rendering/%E6%95%88%E6%9E%9C%E5%9B%BE2.png)

![效果图3](https://ganj-blog.oss-cn-chengdu.aliyuncs.com/rendering/%E6%95%88%E6%9E%9C%E5%9B%BE3.png)

![效果图4](https://ganj-blog.oss-cn-chengdu.aliyuncs.com/rendering/%E6%95%88%E6%9E%9C%E5%9B%BE4.png)

![效果图5](https://ganj-blog.oss-cn-chengdu.aliyuncs.com/rendering/%E6%95%88%E6%9E%9C%E5%9B%BE5.png)

![效果图6](https://ganj-blog.oss-cn-chengdu.aliyuncs.com/rendering/%E6%95%88%E6%9E%9C%E5%9B%BE6.png)

![效果图7](https://ganj-blog.oss-cn-chengdu.aliyuncs.com/rendering/%E6%95%88%E6%9E%9C%E5%9B%BE7.png)

![效果图8](https://ganj-blog.oss-cn-chengdu.aliyuncs.com/rendering/%E6%95%88%E6%9E%9C%E5%9B%BE8.png)
