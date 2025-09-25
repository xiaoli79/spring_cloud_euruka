# Spring Cloud Eureka 微服务项目总结

## 核心概念

本项目是一个基于 Spring Cloud Eureka 实现的微服务架构，用于演示服务的 **注册与发现** 机制。

---

## 项目模块

整个项目由三个核心模块组成：

1.  **`eureka-server` (服务注册中心)**
    * **角色**: 类似一个服务 "通讯录"，负责管理所有注册上来的微服务实例。
    * **关键技术**: 使用 `@EnableEurekaServer` 注解启动。

2.  **`product-service` (服务提供者)**
    * **角色**: 提供商品信息查询的微服务。
    * **工作流程**: 启动后，将自己的服务地址、服务名称 (`product-service`) 等信息注册到 `eureka-server`。

3.  **`order-service` (服务消费者)**
    * **角色**: 提供订单信息查询的微服务，在查询过程中需要调用 `product-service`。
    * **工作流程**:
        * 同样将自己注册到 `eureka-server`。
        * 通过配置带有 `@LoadBalanced` 注解的 `RestTemplate`，可以直接使用服务名 (`http://product-service/...`) 来调用商品服务，而无需关心其具体的 IP 和端口。

---

## 整体流程

1.  启动 `eureka-server`。
2.  启动 `product-service` 和 `order-service`，它们会自动注册到 `eureka-server`。
3.  当外部请求访问 `order-service` 时，`order-service` 会通过 Eureka 查找 `product-service` 的地址，并发起远程调用以获取商品数据，最后将订单和商品信息组合后返回。
