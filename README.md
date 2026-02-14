# Fullstack Security Login

主题：使用 Spring Boot 3 + Angular 19 构建的全栈安全登录应用。

项目来源：[链接](https://github.com/serlesen/fullstack-jwt)

## Chapter 1

第一节是关于如何编写 Spring Boot 控制器（Controller），编写请求映射方法，处理前端页面的访问请求。

我们将在 Spring Boot 中使用 Thymeleaf 构建网页。在项目 pom.xml 中引入 Thymeleaf 相关依赖，接着在 application.properties/yml 中配置 Thymeleaf 基础参数，比如模板文件存放路径（默认 classpath:/templates/）。

完成基础配置后，利用 Thymeleaf 的表达式（变量表达式 ${}、选择表达式 *{} 等）绑定后端传递的用户登录提示信息、表单数据；通过模板片段th:fragment 抽离页面公共部分（导航栏、页脚），再通过 th:insert/th:replace 复用，提升页面开发效率。

## Chapter 1.2

本章的第二节搭建 MVC 三层结构，为后端实现 Jwt 认证授权。

在基础网页架构之上，针对登录场景对 Thymeleaf 页面进行交互和安全层面的优化。首先是表单交互增强：基于 Thymeleaf 完善登录表单的校验逻辑，前端通过 Thymeleaf 绑定后端返回的校验错误信息，在表单提交失败时，将错误提示（如用户名为空、密码格式错误）动态展示在对应表单字段下方；利用 Thymeleaf 的条件表达式（th:if/th:unless）控制错误提示的显示与隐藏，提升用户交互体验。

其次是安全相关的页面处理：为防止跨站请求伪造（CSRF）攻击，在 Thymeleaf 表单中自动集成 Spring Security 提供的 CSRF 令牌（Thymeleaf 会自动解析并渲染 `_csrf` 参数到表单中），确保登录请求的安全性；针对登录状态的页面权限控制，通过 Thymeleaf 结合 Spring Security 的表达式（sec:authorize），实现不同登录状态下页面元素的动态展示——例如未登录时显示“登录”按钮，已登录时隐藏登录入口并展示用户信息。

此外，优化 Thymeleaf 页面的资源引用：通过 th:href、th:src 正确引入 CSS、JavaScript 等静态资源，结合 Spring Boot 的静态资源映射规则，确保登录页面的样式、交互脚本正常加载，为用户提供流畅的登录操作界面。

## Chapter 1.3

第三节重点是与前端 Angular 19 配合。

作为全栈应用的前端核心，Angular 19 负责构建登录功能的交互界面与前端安全逻辑。首先完成 Angular 19 项目的基础搭建：通过 Angular CLI 创建登录模块（LoginModule），抽离登录相关组件（登录表单组件、登录状态组件）、服务（登录请求服务、权限守卫服务），遵循 Angular 模块化、组件化设计思想，降低代码耦合度。

在登录表单实现上，利用 Angular 表单模块（ReactiveFormsModule）创建响应式表单，实现前端本地校验（如密码长度、邮箱格式校验），减少无效的后端请求；通过 HttpClient 模块封装登录请求，与 Spring Boot 后端的登录接口进行通信，并配置拦截器（HttpInterceptor）统一处理请求头（如添加 JWT 令牌）、响应异常（如 token 过期跳转登录页）。

前端安全层面，集成 Angular 自带的安全机制：利用 DomSanitizer 处理用户输入的敏感内容，防止 XSS 攻击；通过路由守卫（CanActivate）校验用户登录状态，未登录用户禁止访问受保护路由；结合环境变量区分开发/生产环境的 API 地址，避免硬编码泄露敏感信息。同时，将登录状态存储在 Angular 的服务中（或 localStorage/sessionStorage），并通过 BehaviorSubject 实现登录状态的全局响应式更新。

## Chapter 1.4

第四节围绕 Spring Boot 后端安全和数据层整合进行。

作为后端安全基石，Spring Security 为登录功能提供全维度防护：首先配置用户认证逻辑，整合 MongoDB 实现用户信息的持久化存储（自定义 UserDetailsService，从 MongoDB 中查询用户账号、密码、权限信息），并通过 BCrypt 加密算法对密码进行加密存储；配置认证过滤器，解析前端传递的 JWT 令牌，完成无状态登录认证；设置权限拦截规则，基于 URL 或方法级别的权限控制（@PreAuthorize），限制不同角色用户的访问范围。

同时，完善 CSRF 防护、会话管理（如设置会话超时时间、防止会话固定攻击）、跨域（CORS）配置，确保前后端交互的安全性；自定义认证失败/成功处理器，返回统一格式的 JSON 响应，便于前端处理登录结果。

实现 Spring Boot 与 MongoDB 的无缝整合：通过 Spring Data MongoDB 简化数据操作，配置 application.yml 中的 MongoDB 连接信息（地址、端口、数据库名、认证信息）；创建用户实体类（Document）映射 MongoDB 中的用户集合，定义 Repository 接口（继承 MongoRepository），封装用户的增删改查操作；针对登录场景，优化查询性能（如为用户名字段创建索引），提升用户认证时的查询效率。

在数据安全层面，对 MongoDB 连接进行加密配置（使用 SSL 连接），避免明文传输；对敏感字段（如用户手机号、邮箱）在存入数据库前进行脱敏处理，查询时按需还原；同时，通过 MongoDB 的事务特性（需副本集环境），确保用户信息操作的原子性（如注册、修改密码时的多文档操作）。

## Chapter 1.5

第五节集成 Log4j2 实现后端日志的规范化管理：在 pom.xml 中引入 Log4j 依赖（排除 Spring Boot 默认的 Logback），配置 log4j2.xml 文件，定义日志输出级别（DEBUG/INFO/WARN/ERROR）、输出目标（控制台/文件）、日志格式（包含时间、线程、类名、日志内容）；针对登录核心模块（认证、授权、数据访问）定制日志规则，记录关键操作（如用户登录成功/失败、权限校验异常）、异常堆栈信息，便于问题排查。







