<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <title>广州省惠州市道路信息管理系统</title>
    <link rel="stylesheet" type="text/css" href="/css/homeStyle.css">
    <link rel="stylesheet" type="text/css" href="/css/mainStyle.css">
    <script src="/js/jquery-3.6.0.js"></script>
</head>

<body>
<div id="whole" style="height: 100%; width: 100%;">
    <!-- head.jsp !-->
    <jsp:include page="homeDir/head.jsp"></jsp:include>
    <!-- navigation.jsp !-->
    <jsp:include page="homeDir/navigation.jsp"></jsp:include>
    <!-- main.jsp !-->
    <jsp:include page="homeDir/main.jsp"></jsp:include>
    <!-- status.jsp !-->
    <jsp:include page="homeDir/status.jsp"></jsp:include>
</div>
</body>

</html>