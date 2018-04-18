<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2018/4/10
  Time: 12:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
   <form action="/import/form" enctype="multipart/form-data" method="post">
       <input type="text" name="name" placeholder="用户姓名"/><br>
       <input type="file" name="file" placeholder="请输入文件"/><br>
       <input type="submit" value="上传"/>
   </form>
</body>
</html>
