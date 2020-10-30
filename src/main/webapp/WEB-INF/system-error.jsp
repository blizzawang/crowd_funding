<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/10/30 0030
  Time: 22:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>出错了!</h1>

    <%-- 输出异常信息 --%>
    ${requestScope.exception.message}
</body>
</html>
