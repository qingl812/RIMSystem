<%--
  Created by IntelliJ IDEA.
  User: 17379
  Date: 2021/9/7
  Time: 14:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        table{
            background-color:green;
            border:0;
        }
        #whole {
            padding: 0px;

        }
        #head {
            float: top;
            height: 15%;
            background-color: aliceblue;
        }
        #navigation {
            float: top;
            height: 5%;
            background-color: aliceblue;
        }

        #search {
            float: left;
            width: 20%;
            height: 75%;
            background-color: antiquewhite;
        }

        #main {
            float: right;
            width: 80%;
            height: 75%;
            background-color: aqua;
        }

        #status {
            float: bottom;
            height: 5%;
            background-color: aquamarine;

        }
    </style>
</head>
<body>
<div id="whole">
    <div id="head">
        <h1 style="text-align: center">广东省惠州市道路信息管理系统</h1>
    </div>
    <div id="navigation">
        <table>
            <tr id="nav">
                <td>首页</td>
                <td>现场签证记录</td>
                <td>道路资料</td>
                <td>道路检测</td>
                <td>维修管理</td>
                <td>资金管理</td>
                <td>工作沟通</td>
                <td>系统管理</td>
                <td>注销</td>
            </tr>
        </table>
    </div>
    <div id="search">
        道路资料 <br>
        <hr>
        +道路信息
        <hr>
        +文档资料
    </div>
    <div id="main">

    </div>
    <div id="status">
        <h1>News headline 4</h1></div>
</div>
</body>
</html>
