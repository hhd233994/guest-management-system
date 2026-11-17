# guest-management-system · 会展嘉宾信息管理系统（开发中）

## 项目背景

做这个项目的起因和实习时接触到的会展嘉宾信息有关：2026 年数博会期间，我负责嘉宾证件信息的录入；在会展系统做测试时，也见过工作人员把嘉宾名单整理成 Excel 批量导入，导入前系统会逐行校验，把有问题的行列出来。

当时我不清楚这套校验是怎么实现的，就想自己从零做一个类似的系统，把"读 Excel、逐行校验、把第几行有什么问题返回给用户"这一步真正写出来，同时把 Spring Boot + MyBatis + MySQL 从接口到数据库的整套流程走一遍。

## 当前进度

- [x] 图书增删改查 REST 接口（MyBatis + MySQL），作为项目的起点
- [x] 按作者查询接口 `GET /books/search?author=`
- [x] MockMvc 接口测试 12 个，使用独立测试库，每个测试结束后事务回滚
- [ ] 重构为 Controller → Service → Mapper 三层
- [ ] 切换到嘉宾领域：活动、嘉宾的增删改查，分页和搜索
- [ ] 统一返回格式、全局异常处理、参数校验
- [ ] Excel 批量导入嘉宾，逐行校验并返回错误行
- [ ] 部署到 Linux 服务器
- [ ] 登录

## 技术栈

- Java 17
- Spring Boot 4.1.1（Spring Web）
- MyBatis（mybatis-spring-boot-starter 4.0.0，注解写 SQL）
- MySQL
- Maven
- JUnit + MockMvc

## 接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/books` | 查询全部图书 |
| GET | `/books/search?author=作者` | 按作者查询 |
| POST | `/books` | 新增图书，请求体为 JSON：`{"id":1,"title":"书名","author":"作者"}` |
| PUT | `/books/{id}` | 修改图书 |
| DELETE | `/books/{id}` | 删除图书 |

`/hello`、`/status`、`/greet`、`/double` 是入门练习接口，重构时删除。

## 本地运行

1. 建库建表（开发库 `day14_db`，测试库 `day14_test_db`，两个库建同样的表）：

   ```sql
   -- TODO：在 MySQL 里运行 SHOW CREATE TABLE springboot_books; 把结果贴在这里
   ```

2. 在项目目录下新建 `application-local.properties`，写入本机数据库密码（已被 `.gitignore` 忽略，不会提交）：

   ```properties
   spring.datasource.password=你的密码
   ```

3. 运行测试 / 启动：

   ```bash
   mvn test
   mvn spring-boot:run
   ```

## 测试说明

- 测试启用 `test` profile，连接独立的测试库 `day14_test_db`，不影响开发数据
- 测试类加了 `@Transactional`，每个测试结束后自动回滚，测试库不残留数据
