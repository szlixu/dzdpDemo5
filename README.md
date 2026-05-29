# dzdpDemo5 — 大众点评仿站

一个仿大众点评的本地生活服务平台，支持商户展示、分类筛选、关键词搜索、用户评价等功能。

## 技术栈

| 层级 | 技术 | 版本 |
|------|------|------|
| 后端框架 | Spring Boot | 3.2.5 |
| ORM | MyBatis-Plus | 3.5.5 |
| 数据库 | MySQL | 8.0 |
| 前端框架 | Vue 3 | 3.5+ |
| UI 组件库 | Element Plus | 2.14+ |
| 构建工具 | Vite | 8.x |
| 状态管理 | Pinia | 3.x |
| 路由 | Vue Router | 5.x |
| HTTP 客户端 | Axios | 1.x |

## 功能模块

### 商户搜索与展示
- 按分类筛选商户（美食、休闲娱乐、酒店住宿、丽人美发、生活服务）
- 关键词搜索商户名称
- 按评分排序（默认 / 评分高到低 / 评分低到高）
- 分页浏览商户列表
- 商户卡片展示封面图、名称、评分、人均消费、地址

### 商户详情
- 商户封面图、名称、评分、人均消费
- 商户地址与联系电话
- 响应式布局，移动端适配

### 用户评价
- 查看商户下所有用户评价
- 发表评价（评分 + 文字内容）
- 评价图片展示与预览
- 评价时间格式化显示

### 分类管理
- 分类列表查询
- 分类的增删改（CRUD）

## 项目结构

```
dzdpDemo5/
├── backend/                        # 后端工程
│   ├── src/main/java/com/dzdp/
│   │   ├── common/
│   │   │   ├── exception/          # 全局异常处理
│   │   │   └── result/             # 统一响应 Result<T>
│   │   ├── config/                 # 配置类（CORS、MyBatis-Plus）
│   │   ├── controller/             # 控制器层
│   │   ├── dto/                    # 请求传输对象
│   │   ├── entity/                 # 数据库实体
│   │   ├── mapper/                 # 数据访问层
│   │   ├── service/                # 业务逻辑层
│   │   │   └── impl/               # 业务实现
│   │   └── vo/                     # 视图响应对象
│   ├── src/main/resources/
│   │   ├── mapper/                 # MyBatis XML 映射
│   │   ├── application.yml         # 应用配置
│   │   ├── schema.sql              # 建表脚本
│   │   └── data.sql                # 测试数据
│   └── pom.xml
├── frontend/                       # 前端工程
│   ├── src/
│   │   ├── api/                    # API 请求封装
│   │   ├── assets/                 # 静态资源
│   │   ├── layouts/                # 布局组件
│   │   ├── pages/                  # 页面组件
│   │   ├── router/                 # 路由配置
│   │   ├── stores/                 # Pinia 状态管理
│   │   ├── styles/                 # 全局样式
│   │   └── utils/                  # 工具函数（Axios 封装）
│   └── package.json
├── sql/                            # 独立 SQL 脚本
│   ├── schema.sql
│   └── data.sql
└── docs/                           # 项目文档
```

## 数据库设计

### tb_category（分类表）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键自增 |
| name | VARCHAR(64) | 分类名称 |
| icon | VARCHAR(256) | 分类图标 URL |
| sort | INT | 排序号 |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |
| is_deleted | TINYINT | 逻辑删除标记 |

### tb_shop（商户表）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键自增 |
| category_id | BIGINT | 所属分类 ID |
| name | VARCHAR(128) | 商户名称 |
| address | VARCHAR(256) | 商户地址 |
| longitude | DECIMAL(10,6) | 经度 |
| latitude | DECIMAL(10,6) | 纬度 |
| phone | VARCHAR(32) | 联系电话 |
| cover_url | VARCHAR(256) | 封面图 URL |
| avg_price | DECIMAL(10,2) | 人均消费 |
| rating | DECIMAL(2,1) | 评分 |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |
| is_deleted | TINYINT | 逻辑删除标记 |

### tb_review（评价表）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键自增 |
| shop_id | BIGINT | 关联商户 ID |
| user_id | BIGINT | 评论用户 ID |
| content | TEXT | 评论内容 |
| rating | TINYINT | 评分 1-5 |
| images | VARCHAR(1024) | 评论图片 URL（逗号分隔） |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |
| is_deleted | TINYINT | 逻辑删除标记 |

## API 接口

所有接口统一响应格式：

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {}
}
```

### 商户接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/shops?categoryId={id} | 按分类查询商户列表 |
| GET | /api/shops/search?keyword=&categoryId=&page=&pageSize=&sortBy=&sortOrder= | 搜索商户（分页） |
| GET | /api/shops/{id} | 获取商户详情 |
| POST | /api/shops | 新增商户 |
| PUT | /api/shops/{id} | 更新商户 |
| DELETE | /api/shops/{id} | 删除商户 |

### 分类接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/categories | 获取分类列表 |
| GET | /api/categories/{id} | 获取分类详情 |
| POST | /api/categories | 新增分类 |
| PUT | /api/categories/{id} | 更新分类 |
| DELETE | /api/categories/{id} | 删除分类 |

### 评价接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/reviews?shopId={id} | 获取商户评价列表 |
| POST | /api/reviews | 发表评价 |
| DELETE | /api/reviews/{id} | 删除评价 |

## 快速开始

### 环境要求

- JDK 17+
- Maven 3.8+
- Node.js 18+
- MySQL 8.0

### 1. 初始化数据库

创建 MySQL 数据库并执行建表和测试数据脚本：

```bash
mysql -u root -p < sql/schema.sql
mysql -u root -p < sql/data.sql
```

脚本会自动创建 `dzdp5` 数据库及 3 张表，并插入 5 条分类、10 条商户、11 条评价作为测试数据。

### 2. 配置后端

修改 `backend/src/main/resources/application.yml` 中的数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/dzdp5?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai
    username: your_username
    password: your_password
```

### 3. 启动后端

```bash
cd backend
mvn spring-boot:run
```

后端服务默认运行在 `http://localhost:8080`。

### 4. 启动前端

```bash
cd frontend
npm install
npm run dev
```

前端开发服务器运行在 `http://localhost:5173`，已配置代理将 `/api` 请求转发到后端 8080 端口。

### 5. 访问应用

浏览器打开 http://localhost:5173 即可使用。

## 页面预览

- **商户列表页** (`/shops`) — 分类标签筛选 + 搜索框 + 排序 + 卡片网格 + 分页
- **商户详情页** (`/shops/:id`) — 商户信息 + 评价列表 + 发表评价表单
