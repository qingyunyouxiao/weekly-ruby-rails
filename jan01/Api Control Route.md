在Rails中构建API时，版本控制是一个非常重要的设计决策。以下是几种常见的API版本控制方案及其实现方式：

## 1. **URL路径版本控制**（最常用）

### 路由配置
```ruby
# config/routes.rb
Rails.application.routes.draw do
  namespace :api do
    namespace :v1 do
      resources :users, only: [:index, :show]
      resources :posts
    end

    namespace :v2 do
      resources :users, only: [:index, :show]
      resources :posts
    end
  end
end
```

### 控制器示例
```ruby
# app/controllers/api/v1/users_controller.rb
class Api::V1::UsersController < ApplicationController
  def index
    users = User.all
    render json: users, each_serializer: V1::UserSerializer
  end
end

# app/controllers/api/v2/users_controller.rb
class Api::V2::UsersController < ApplicationController
  def index
    users = User.includes(:posts).all
    render json: users, each_serializer: V2::UserSerializer
  end
end
```

## 2. **请求头版本控制**（Accept Header）

### 路由配置
```ruby
# config/routes.rb
Rails.application.routes.draw do
  namespace :api do
    resources :users, only: [:index]
  end
end
```

### 约束器实现
```ruby
# lib/api_version_constraint.rb
class ApiVersionConstraint
  def initialize(version:)
    @version = version
  end

  def matches?(request)
    request.headers['Accept']&.include?("application/vnd.myapp.v#{@version}+json")
  end
end
```

### 路由中使用
```ruby
# config/routes.rb
Rails.application.routes.draw do
  namespace :api do
    constraints(ApiVersionConstraint.new(version: 1)) do
      resources :users, controller: 'v1/users'
    end

    constraints(ApiVersionConstraint.new(version: 2)) do
      resources :users, controller: 'v2/users'
    end
  end
end
```

## 3. **动态版本分发**

### 基础控制器
```ruby
# app/controllers/api/base_controller.rb
class Api::BaseController < ApplicationController
  before_action :set_api_version

  def set_api_version
    @api_version = request.headers['Accept']&.match(/version=(\d+)/)&.[](1) || '1'
  end

  def dynamic_controller
    versioned_class = "#{controller_path.classify}::V#{@api_version}"
    versioned_class.constantize
  rescue NameError
    raise "Version #{@api_version} not supported"
  end
end
```

## 4. **使用版本控制的Gem**

### versionist gem
```ruby
# Gemfile
gem 'versionist'

# 生成新版本
rails generate versionist:new_api_version v2 V2
```

## 5. **模块化版本组织**

### 目录结构
```
app/
├── controllers/
│   └── api/
│       ├── base_controller.rb
│       ├── v1/
│       │   ├── users_controller.rb
│       │   └── posts_controller.rb
│       └── v2/
│           ├── users_controller.rb
│           └── posts_controller.rb
├── serializers/
│   ├── v1/
│   │   ├── user_serializer.rb
│   │   └── post_serializer.rb
│   └── v2/
│       ├── user_serializer.rb
│       └── post_serializer.rb
└── services/
    ├── v1/
    │   └── user_service.rb
    └── v2/
        └── user_service.rb
```

## 6. **版本回退策略**

```ruby
# app/controllers/api/v2/users_controller.rb
class Api::V2::UsersController < Api::BaseController
  def index
    # 尝试使用v2的新特性，如果失败则回退到v1
    begin
      users = User.with_advanced_features
    rescue NoMethodError
      users = User.all
    end
    
    render json: users
  end
end
```

## 7. **测试版本控制**

```ruby
# test/controllers/api/v1/users_controller_test.rb
require 'test_helper'

class Api::V1::UsersControllerTest < ActionDispatch::IntegrationTest
  test "should get users with v1 format" do
    get api_v1_users_path
    assert_response :success
  end
end

# test/controllers/api/v2/users_controller_test.rb
class Api::V2::UsersControllerTest < ActionDispatch::IntegrationTest
  test "should get users with v2 format" do
    get api_v2_users_path
    assert_response :success
  end
end
```

## 8. **版本迁移策略**

```ruby
# db/migrate/xxxxxx_add_v2_fields_to_users.rb
class AddV2FieldsToUsers < ActiveRecord::Migration[6.0]
  def change
    add_column :users, :display_name, :string
    add_column :users, :avatar_url, :string
  end
end

# app/models/user.rb
class User < ApplicationRecord
  # v1 特性
  def v1_attributes
    attributes.slice('id', 'name', 'email')
  end

  # v2 特性
  def v2_attributes
    attributes.slice('id', 'name', 'display_name', 'avatar_url')
  end
end
```

## 最佳实践建议

1. **选择简单方案**：URL路径版本控制是最简单、最直观的方案
2. **文档化版本**：清晰记录每个版本的API变更
3. **设置版本生命周期**：明确版本的支持期限和弃用计划
4. **保持向后兼容**：新版本尽量不要破坏旧版本的功能
5. **版本默认值**：始终提供默认版本，当版本未指定时使用

## 完整示例

```ruby
# config/routes.rb
Rails.application.routes.draw do
  scope module: :api, defaults: { format: :json } do
    scope module: :v1, constraints: ApiVersionConstraint.new(version: 1, default: true) do
      resources :users, only: [:index, :show]
    end
    
    scope module: :v2, constraints: ApiVersionConstraint.new(version: 2) do
      resources :users, only: [:index, :show] do
        resources :posts, only: [:index]
      end
    end
  end
end

# lib/api_version_constraint.rb
class ApiVersionConstraint
  def initialize(version:, default: false)
    @version = version
    @default = default
  end

  def matches?(request)
    @default || request.headers['Accept'].to_s.include?("version=#{@version}")
  end
end
```

选择合适的版本控制方案取决于你的具体需求。对于大多数项目，URL路径版本控制就足够了，它简单、直观且易于维护。