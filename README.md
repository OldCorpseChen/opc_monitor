# OPC_Monitor

#### 介绍
使用OPCDA连接ibaPDA进行远程数采及监控
码云地址：https://gitee.com/OldCorpseChen/opc_monitor

#### 软件架构
前后端分离
后端：主体框架：springboot
     数据库：spring-data-redis、mybatis
    opc：utgard
前端：layui

程序分为两块：
1. 以西门子PCS7与模块为原型，做模型实体类，一输出多输入，按这个逻辑将“自定义模型名称:输出信号点名”作为Key，“输入信号点1:输入信号点2:输入信号点3”作为Value存入Redis Hash类型中，名称为BLOCK
2. 将模型从Redis中读出，开线程同步监控信号点，逻辑为
![输入图片说明](https://images.gitee.com/uploads/images/2021/0617/195106_a0e54d55_1958900.png "Screenshot 2021-06-17 195026.png")
3.服务连接采用自动重连，并且线程中为同步读取数据

#### 安装教程

1.  修改application.properties中的redis数据库设置和opc server的ip
2.  opc server主机增加用户，用户名和密码自定义，但是必须与application.properties中配置的一致
3.  opc server主机设置如下（win7，win10下没安装过ibaPDA，未测试）：
    1. 桌面计算机右键--管理--本地用户和组--用户--新建用户：用户名密码自定义，取消勾选 **“用户下次登陆必须修改密码”** ，勾选 **“用户不能改变密码”** 和 **“密码从不失效”** 
    2. 桌面计算机右键--管理--本地用户和组--组：在 **“Distributed COM Users”** 右键--属性--添加，将你新建的用户添加。在“Users”右键--属性，将你新建的用户删去。
    3. 控制面板--管理工具--组件服务--组件服务--计算机--我的计算机--DCOM Config：在“OpcEnum”右键--属性，常规选项卡：验证等级：连接；安全选项卡：全选自定义，并且添加你新建的用户，赋予所有权限。
    4. 控制面板--管理工具--组件服务--组件服务--计算机--我的计算机--DCOM Config：在“ibaPDA OPC server”右键--属性，常规选项卡：验证等级：连接；安全选项卡：全选自定义，并且添加你新建的用户，赋予所有权限。复制好常规选项卡下的应用ID（貌似都是一样的），粘贴到application.properties中的“opcClsid=”后面。
    5. 打开ibaPDA，点击“I/O Management”，在General--Remote configuration中勾选“Active”。

#### 使用说明

1.  配置完信息，想咋整咋整
2.  前端就在项目文件夹下后面带html的文件夹里，请自行部署

#### 最后
仅仅是爱好兴趣，非专业程序员，不专业和不对的地方请轻喷，谢啦 :pray: 


