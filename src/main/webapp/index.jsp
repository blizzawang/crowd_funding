<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/10/30 0030
  Time: 12:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%-- 配置url模板，将ip、端口号、项目名抽取出来 --%>
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
</head>
<body>
    <a href="test/ssm">测试SSM整合环境</a>

    <hr>

    <h3>测试@RequestBody</h3>
    <button id="btn1">Send [1,2,3] First</button>
    <button id="btn2">Send [1,2,3] Second</button>
</body>

<script src="jquery/jquery-2.1.1.min.js"></script>
<script>
    $(function () {
        $("#btn1").click(function () {
            // $.get(); $.post();
            $.ajax({
                "url":"send/array/first",
                "type":"post",
                "data":{
                    "array":[1,2,3]
                },
                "dataType":"text",  //解析服务器返回的数据，这里将其作为普通文本解析
                "success":function (response) {
                    alert(response);
                },
                "error":function (response) {
                    alert(response);
                }
            });
        });

        $("#btn2").click(function () {
            // $.get(); $.post();
            $.ajax({
                "url":"send/array/second",
                "type":"post",
                "data":{
                    "array[0]":1,
                    "array[1]":2,
                    "array[2]":3
                },
                "dataType":"text",  //解析服务器返回的数据，这里将其作为普通文本解析
                "success":function (response) {
                    alert(response);
                },
                "error":function (response) {
                    alert(response);
                }
            });
        });
    });
</script>

</html>
