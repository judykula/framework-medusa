
# 概要

整套架构"MY"的项目名称以DOTA2中的英雄名称命名。
「medusa」作为"MY"架构的核心实现部分，承载主要架构逻辑的实现，引入机制、规范下游开发。
另有其他"组件"，比如jdbc、redis、log等扩展功能需单独引入。

# medusa

"MY"架构核心代码实现，自动装配入口: MyCoreAutoConfiguration

## 1.0.0

### 可插拔功能：
- consul扩展：流量分流、consul发现服务管理等功能支持。 
  - 默认开启状态，你可以通过设置my.extension.consul=false来关闭此功能
  - ！如果关闭了此功能，将会对"my"微服务架构造成不可预见破坏，请勿自主决定关闭此功能
- WebMvc扩展：统一输出、请求log、异常标准化等功能支持。 
  - 默认开启状态，你可以通过设置my.extension.mvc=false来关闭此功能。
  - ！如果关闭此功能，将失去如上功能支持
  - 设置logging.level.com.jwy.medusa.mvc.MyAccessLogFilter=debug来log输出请求日志
- Feign扩展：实现异常机制传递(参考下一点)以及必要的"头"信息过滤
    - 默认开启状态，你可以设置my.extension.feign=false来关闭此功能
    - ！如果关闭了此功能，将会对"my"微服务架构造成不可预见破坏，请勿自主决定关闭此功能
- 异常机制：业务异常标准化、在内部rpc(feign)请求时的异常传递功能实现。
  - 异常标准化依赖WebMvc同步实现
  - 异常传递机制默认关闭状态。包括server(服务方)的发送开关，以及client(调用房)的请求开关。异常机制可以保证在feign调用的时候，由server产出的异常被client捕获。！开启异常传递机制会由性能影响，请在合适的场景使用，具体描述请参考相关类注释。想要开启功能需要如下配置：
    - my.extension.mvc=true(默认)
    - my.extension.feign=true(默认)
    - my.exception.transfer.send=true
    - my.exception.transfer.receive=true
- SaaS基础内容("多租户)实现
  - 默认开启状态，你可以设置my.extension.saas=false来关闭。
- 流量灰度/路由功能实现
  - 默认开启状态，你可以设置my.extension.feature=false来关闭。
  - ！灰度路由功能同时依赖负载均衡扩展，确保my.extension.load-balance=true(默认)来开启
- 系统上下文工具扩展：MyContextUtils。注入此工具类可以获取"MY"架构内的上下文内容，包括：
  - 统一JSON工具
  - Feature、SaaS Tenant、UserInfo 等上下文内容
  - Spring上下文工具
  
### 技术选型

"MY"微服务架构使用如下技术实现重点功能：

- 使用consul为发现服务中心
- 使用Spring WebMVC 为提供web服务
- 使用feign提供内部RPC调用
- 使用load balance实现负载均衡
- 使用sleuth提供链路追踪
- 使用actuator提供服务状态信息
- 使用swagger提供API可视化
- 使用prometheus提供监控
- 使用commons 、guava提供统一通用工具实现

关于Circuit Breaker相关内容目前在"网管"层主要处理

关于spring cloud的版本使用"2021.0.8"，即spring boot2.x / spring framework 5版本，同时会保持更新进度