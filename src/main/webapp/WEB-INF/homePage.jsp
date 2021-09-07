<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <title>广州省惠州市道路信息管理系统</title>
    <script type="text/javascript" src="https://api.map.baidu.com/api?v=1.0&type=webgl&ak=HpA4MtzOdUZ9Ywnum4HHwtnbwcIrn0Hc">
    </script>
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
    <script src="/js/jquery-3.6.0.js"></script>
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
        <h1>News headline 2</h1></div>
    <div id="main">
        <script>
            // 创建地图实例
            var map = new BMapGL.Map("main",{
                enableRotate: false,
                enableTilt: false
            });
            // 创建点坐标
            var point = new BMapGL.Point(114.422152,23.117972);
            map.enableScrollWheelZoom(true);
            // 初始化地图，设置中心点坐标和地图级别
            map.centerAndZoom(point, 15);
            //创建控件，添加控件
            var scaleCtrl = new BMapGL.ScaleControl();
            map.addControl(scaleCtrl);
            var opts={
                anchor: BMAP_ANCHOR_TOP_LEFT
            }
            var zoomCtrl = new BMapGL.ZoomControl(opts);  // 添加缩放控件
            map.addControl(zoomCtrl);

        </script>
    </div>
    <div id="status">
        <h1>News headline 4</h1></div>
</div>
</body>

</html>