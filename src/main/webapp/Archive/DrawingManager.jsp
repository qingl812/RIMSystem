<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <style type="text/css">
        #allmap {
			position: absolute;
			top: 0px;
			bottom: 30px;
			left: 0px;
			right:0px;
            overflow: hidden;
        }

        #result {
			position: absolute;
			bottom: 0px;
			height: 30px;
			width: 100%;
        }
    </style>
    <script type="text/javascript"
            src="http://api.map.baidu.com/api?v=2.0&ak=HpA4MtzOdUZ9Ywnum4HHwtnbwcIrn0Hc"></script>
    <!--加载鼠标绘制工具-->
    <script type="text/javascript" src="/DrawingManager/DrawingManager.min.js"></script>
    <link rel="stylesheet" href="/DrawingManager/DrawingManager.min.css"/>
    <link rel="stylesheet" href="/css/mainStyle.css"/>
    <title>鼠标绘制工具</title>
</head>

<body>
<div id="drawingManager">

<div id="allmap">
    <div id="drawingMap" style="height:100%;-webkit-transition: all 0.5s ease-in-out;transition: all 0.5s ease-in-out;">
    </div>
</div>
<div id="result">
    <input type="button" value="获取绘制的覆盖物个数" onclick="alert(overlays.length)"/>
    <input type="button" value="清除所有覆盖物" onclick="clearAll()"/>
    <input type="button" value="获取最后一个覆盖物信息" id="getLastOverLay"/>
</div>

</div>
<script type="text/javascript">
    // 百度地图API功能
    var map = new BMap.Map('drawingMap');
    var poi = new BMap.Point(116.307852, 40.057031);
    map.centerAndZoom(poi, 16);
    map.enableScrollWheelZoom();
    var overlays = [];
    var overlaycomplete = function (e) {
        overlays.push(e.overlay);
        console.log(e.overlays.getPath())
        var result = "";
        var overlays_lines = e.overlay.getPath().length;
        if (e.drawingMode == BMAP_DRAWING_POLYLINE) {
            if (e.drawingMode == BMAP_DRAWING_POLYLINE || e.drawingMode == BMAP_DRAWING_POLYGON || e.drawingMode == BMAP_DRAWING_RECTANGLE) {
                result += ' 所画的点个数：' + e.overlay.getPath().length;
            }
        };
        for (var i = 0; i < overlays_lines.length; i++) {//循环连线个数
            for (var j = 0; j < overlays_lines[i].getPath().length; j++) {//循环每一个连线上的点个数
                result += overlays_lines[i].getPath()[j].lng;
                result += (overlays_lines[i].getPath()[j].lat + "#");
            }
        }

    }
    var styleOptions = {
        strokeColor: "red",    //边线颜色。
        fillColor: "red",      //填充颜色。当参数为空时，圆形将没有填充效果。
        strokeWeight: 3,       //边线的宽度，以像素为单位。
        strokeOpacity: 0.8,	   //边线透明度，取值范围0 - 1。
        fillOpacity: 0.6,      //填充的透明度，取值范围0 - 1。
        strokeStyle: 'solid' //边线的样式，solid或dashed。
    }
    //实例化鼠标绘制工具
    var drawingManager = new BMapLib.DrawingManager(map, {
        isOpen: false, //是否开启绘制模式
        enableDrawingTool: true, //是否显示工具栏
        drawingToolOptions: {
            anchor: BMAP_ANCHOR_TOP_RIGHT, //位置
            offset: new BMap.Size(5, 5), //偏离值
            drawingModes : [BMAP_DRAWING_MARKER, BMAP_DRAWING_POLYLINE]
        },
        drawingTypes : [
            BMAP_DRAWING_MARKER,
            BMAP_DRAWING_POLYLINE,

        ],
        circleOptions: styleOptions, //圆的样式
        polylineOptions: styleOptions, //线的样式
        polygonOptions: styleOptions, //多边形的样式
        rectangleOptions: styleOptions //矩形的样式
    });
    //添加鼠标绘制工具监听事件，用于获取绘制结果
    drawingManager.addEventListener('overlaycomplete', overlaycomplete);

    function clearAll() {
        map.clearOverlays();
        overlays.length=0;
    }
</script>
</body>

</html>