<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <link rel="stylesheet" type="text/css" href="/css/mainStyle.css">
    <script type="text/javascript"
            src="https://api.map.baidu.com/api?v=1.0&type=webgl&ak=HpA4MtzOdUZ9Ywnum4HHwtnbwcIrn0Hc">
    </script>
</head>

<body>

<div id="map"></div>
<script>
    // 创建地图实例
    let map = new BMapGL.Map("map", {
        enableRotate: false,
        enableTilt: false
    });
    // 创建点坐标
    let point = new BMapGL.Point(114.422152, 23.117972);
    map.enableScrollWheelZoom(true);
    // 初始化地图，设置中心点坐标和地图级别
    map.centerAndZoom(point, 15);
    //创建控件，添加控件
    let scaleCtrl = new BMapGL.ScaleControl();
    map.addControl(scaleCtrl);
    let opts = {
        anchor: BMAP_ANCHOR_TOP_LEFT
    }
    let zoomCtrl = new BMapGL.ZoomControl(opts);  // 添加缩放控件
    map.addControl(zoomCtrl);

</script>

</body>
</html>
