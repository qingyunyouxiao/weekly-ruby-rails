# 自述文件

这个 README 通常用于记录启动和运行应用程序所需的步骤。

你可能想要涵盖的内容：

* Ruby 版本

* 系统依赖

* 配置

* 数据库创建

* 数据库初始化

* 如何运行测试套件

* 服务（任务队列、缓存服务器、搜索引擎等）

* 部署说明

* ...

  

**准备开始**

在这个 Rails 项目中，我们创建 hello / goodbye 控制器，打印当前时间和日期，输出当前文件目录。

我们花五分钟的时间就能实现上面所说的功能。

由于我们新建了一个项目，先把代码弄进来...

新建项目的命令是： `rails new {project_name} --minimal --api --skip-test`



**关于打印时间和日期**







**关于输出文件目录**

这是一个用 Ruby on Rails（见 Gemfile 中的 rails ~> 8.1.2）搭建的示例/演示应用。

主要功能：提供一个简单的“文件列表”页面，路由 file_list 指向 files#file_list（参见 routes.rb 和 files_controller.rb）。控制器读取项目根目录下的文件并在视图中列出（参见 file_list.html.erb）。

前端/现代特性：使用 importmap、Turbo 和 Stimulus（见 Gemfile），并包含 PWA 支持模板与 service worker（参见 service-worker.js 和 pwa 相关视图）。

开发环境：默认使用 SQLite（见 Gemfile 的 sqlite3）。

用于演示 Rails 基本用法（控制器/视图/路由）、结合 Hotwire/Stimulus 的小练习仓库，并演示如何集成 PWA 资源。

