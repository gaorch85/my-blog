# 前端
该项目基于 `Vue` 框架实现，部分使用 `element ui` 简化开发

**运行步骤：**
1. 安装 `node.js`  
    官网安装即可  
    验证是否安装成功，命令行执行：  
    ```bash
    npm --version
    ```
    如果出现版本信息，即安装成功
2. 安装 `Vue CLI`  
    命令行执行以下命令，全局安装 `Vue CLI`  
    ```bash
    npm install -g @vue/cli
    ```
    验证是否安装成功，命令行执行：  
    ```bash
    vue --version
    ```
    如果出现版本信息，即安装成功
3. 下载依赖 `module`  
   在 `/front_end` 目录下，命令行执行：  
   ```bash
   npm install
   ```
4. 运行项目，命令行执行（二者选其一即可）：  
    - 先启动本地后端程序，连接本地后端：  
    ```bash
    npm run serve:dev
    ```  
    - 连接作者服务器上运行的后端程序：  
    ```bash
    npm run serve:pro
    ```  
 