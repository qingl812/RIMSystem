<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <link rel="stylesheet" type="text/css" href="/css/mainStyle.css">
    <script src="/js/jquery-3.6.0.js"></script>
</head>

<body>

<!-- main.jsp !-->
<div id="main">
    <!-- search.jsp !-->
    <jsp:include page="../mapDir/search.jsp"></jsp:include>
    <!-- map.jsp !-->
    <jsp:include page="../mapDir/map.jsp"></jsp:include>
    <!-- mapStatus.jsp !-->
    <jsp:include page="../mapDir/mapStatus.jsp"></jsp:include>
</div>

</body>
</html>
