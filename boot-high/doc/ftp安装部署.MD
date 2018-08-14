LINUX安装

1.1 安装 VSFTPD
使用 yum 安装 vsftpd

yum install -y vsftpd

1.2 启动 VSFTPD
安装完成后，启动 FTP 服务：

service vsftpd start

启动后，可以看到系统已经监听了 21 端口：

netstat -nltp | grep 21
此时，访问 ftp:// 192.168.19.170 可在本机浏览机器上访问了

2、配置 FTP 权限
2.1 了解 VSFTP 配置
vsftpd 的配置目录为 /etc/vsftpd，包含下列的配置文件：

vsftpd.conf 为主要配置文件
ftpservers 配置禁止访问 FTP 服务器的用户列表
user_list 配置用户访问控制

2.2 阻止匿名访问和切换根目录
匿名访问和切换根目录都会给服务器带来安全风险，我们把这两个功能关闭。

编辑 /etc/vsftpd/vsftpd.conf，找到下面两处配置并修改：

# 禁用匿名用户  YES 改为NO
anonymous_enable=NO

# 禁止切换根目录 删除#
chroot_local_user=YES
编辑完成后保存配置，重新启动 FTP 服务

service vsftpd restart
2.3 创建 FTP 用户
创建一个用户 ftpserver

useradd ftpserver
为用户 ftpserver 设置密码

echo "ftp@123456" | passwd ftpserver --stdin
2.4 限制该用户仅能通过 FTP 访问
限制用户 ftpserver只能通过 FTP 访问服务器，而不能直接登录服务器：

usermod -s /sbin/nologin ftpserver
2.5 为用户分配主目录
为用户 ftpserver创建主目录并约定：

/data/ftp 为主目录, 该目录不可上传文件 
/data/ftp/excelDir文件只能上传到该目录下

在/data中创建相关的目录

mkdir -p /data/ftp/excelDir
2.5.1 创建登录欢迎文件
 
echo "Welcome to use FTP service." > /data/ftp/welcome.txt
设置访问权限

chmod a-w /data/ftp && chmod 777 -R /data/ftp/excelDir
设置为用户的主目录：

usermod -d /data/ftp ftpserver
3、访问FTP
根据您个人的工作环境，选择一种方式来访问已经搭建的 FTP 服务

注意：记得关闭防火墙或者开放FTP默认端口(21)

# 关闭SELinux服务
setenforce  0 
# 关闭防火墙
service iptables stop 或 iptables  -F 
通过 Windows 资源管理器访问
Windows 用户可以复制下面的链接 
到资源管理器的地址栏访问：
ftp:// 192.168.19.170 
其中ftpserver为登录FTP的用户名，ftp@123456为登录FTP的密码


WINDOWS安装
 1.安装FTP服务
开始--》管理工具--》服务器管理器
 
 
2.安装IIS/FTP角色
打开服务器管理器，找到添加角色，然后点击，弹出添加角色对话框，选择下一步
选择Web服务器（IIS），然后选择FTP服务，直到安装完成。
 
 
 
 
3.在IIS中查看，如果能够右键创建FTP站点，则表明FTP服务安装成功

 
 
4.创建Windows用户名和密码，用于FTP使用。
开始--》管理工具--》服务器管理器，添加用户，如下图：本实例使用ftptest
 
注意：图片创建的用户为ftpTest,为了和我们项目代码一致，在创建用户时使用ftpserver
密码设置为ftp@123456。

在服务器磁盘上创建一个供FTP使用的文件夹，创建FTP站点，指定刚刚创建的用户ftpserver，赋予读写权限。ip地址选择下拉，选择本地ip地址，图片端口为81，将其改为21
 
指定允许访问用户，这里我们设置为ftpserver用户才有读写权限

 


设置站点名称，指定物理路径地址，如设置为：D://data/ftp，并在ftp目录下再新建一个excelDir目录


站点创建成功后，设置用户的身份验证，选中下图的“FTP身份验证”，禁用匿名身份验证和基本身份验证，保证只能指定用户和密码进行访问
 


客户端测试。直接使用ftp://服务器ip地址:ftp端口，如图。弹出输入用户名和密码的对话框表示配置成功，正确的输入用户名和密码后，即可对FTP文件进行相应权限的操作。
 

