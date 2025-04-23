
# 学生数据库管理系统

## 项目概述

这是一个基于JavaFX和MySQL的学生数据库管理系统，旨在实现对学生和教师信息的管理。这是我学习JavaFX时开发的一个项目，当时网上关于JavaFX的资料较少，学习和开发过程中遇到了不少困难。通过这个项目，我掌握了JavaFX的基础知识，并学习了如何将Java与MySQL数据库结合使用。

## 功能特点

- **学生管理**：支持对学生信息的增删改查操作。
- **教师管理**：支持对教师信息的增删改查操作。
- **学生注册**：允许学生注册新账户。
- **数据库交互**：通过MySQL存储和管理学生和教师数据。
- **图形界面**：使用JavaFX实现用户友好的界面，支持简单交互。

## 文件结构

- **src/com.student_manageSystem/**：包含项目的主要Java源代码。
  - **dao/**：
    - `ManageDao.java`：管理数据访问接口。
    - `StudentDao.java`：学生数据访问接口。
    - `TeacherDao.java`：教师数据访问接口。
  - **pojo/**：
    - `Student.java`：学生实体类。
    - `Teacher.java`：教师实体类。
  - **utils/**：
    - `CommonUtils.java`：通用工具类。
    - `MD5Utils.java`：MD5加密工具类。
    - `SQLUtils.java`：SQL操作工具类。
    - `ViewUtils.java`：界面工具类。
  - `Main.java`：程序入口。
  - `Manager_operate.java`：管理操作类。
  - `Teacher_operate.java`：教师操作类。
- **student_manage_system.sql**：MySQL数据库脚本文件，用于创建数据库和表结构。
- **out/**：编译后的字节码文件目录。
- **lib/**：项目依赖的库文件目录。
- **student-management-system.iml**：IntelliJ IDEA项目配置文件。

## 使用技术

- **JavaFX**：用于构建图形用户界面，提供交互功能。
- **MySQL**：用于存储和管理学生和教师数据。
- **Java**：使用Java实现业务逻辑，包括面向对象编程（类、对象、继承等）。
- **JDBC**：通过JDBC连接MySQL数据库，实现数据操作。

## 如何运行

1. 确保你的电脑已安装Java开发环境（JDK）和MySQL数据库。
2. 在MySQL中运行`student_manage_system.sql`脚本，创建数据库和表结构。
3. 将此仓库克隆到本地。
4. 确保项目依赖库（`lib`文件夹）已正确配置。
5. 打开项目，使用IntelliJ IDEA或其他IDE运行`Main.java`文件。
6. 按照界面提示进行操作。

## 开发心得

这个项目对我来说是一个挑战，因为当时网上关于JavaFX的资料很少，学习和调试的过程比较困难。通过不断尝试和查阅文档，我最终完成了这个
