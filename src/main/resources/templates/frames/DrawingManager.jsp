<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <style type="text/css">
        #allmap {
			position: absolute;
			top: 0px;
			bottom: 0px;
			left: 0px;
			right:0px;
            overflow: hidden;
        }


        #up-map-div{
            width:200px;
            height:100px;
            top:0;
            right: 0;
            position:absolute;
            z-index:9999;
            border:1px dashed #000;
            background-color: rgba(0,0,0,0.5)
            opacity:0.6;
        }
    </style>
    <script type="text/javascript"
            src="http://api.map.baidu.com/api?v=2.0&ak=HpA4MtzOdUZ9Ywnum4HHwtnbwcIrn0Hc"></script>
    <!--加载鼠标绘制工具-->
    <script type="text/javascript" src="/static/DrawingManager/DrawingManager.js"></script>
    <link rel="stylesheet" href="/static/DrawingManager/DrawingManager.min.css"/>
    <link rel="stylesheet" href="/css/mainStyle.css"/>
    <title>鼠标绘制工具</title>
</head>

<body>
<div id="drawingManager">

<div id="allmap">
    <div id="drawingMap" style="height:100%;-webkit-transition: all 0.5s ease-in-out;transition: all 0.5s ease-in-out;">
    </div>
    <div id="up-map-div">
        绘制道路 <br>
        ----------------------------<br>
        <input type="radio" name="ifDraw" id="openDraw" style="font-size: small" onclick="openDrawing()">开始绘制 <input type="radio" id="closeDraw" name="ifDraw" style="font-size: small" onclick="drawingManager.close()"> 停止绘制
        <button type="button" value="清除结果" onclick="clearAll()">清除结果</button> <button value="保存结果" onclick="realOverlaycomplete()">保存结果</button>
    </div>
</div>

</div>
<script type="text/javascript">
    // 百度地图API功能
    var path;
    var map = new BMap.Map('drawingMap');
    var poi = new BMap.Point(116.307852, 40.057031);
    map.centerAndZoom(poi, 16);
    map.enableScrollWheelZoom();
    var overlays = [];
    var overlaycomplete = function (e) {
        overlays.push(e.overlay);
        $("input[name='ifDraw']").attr('checked','false');
        path= e.overlay.getPath();
        }
    var realOverlaycomplete = function (e){
        alert("保存成功");
        var jsonObj = {"lng":0,"lat":0};
        var coordinate=[];

        for(var i =0;i<path.length;i++){
            jsonObj.lng= path[i].lng;
            jsonObj.lat= path[i].lat;
            coordinate.push(jsonObj);
            console.log(jsonObj)
        }
        $.ajax({
            type:"POST",
            dataType:"json",
            url:"/road/updateCoordinate",
            data:JSON.stringify(coordinate),
            contentType:"application/json;charset=UTF-8"
        })
    }
    function openDrawing() {
        if(overlays.length==1){
            $("input[name='ifDraw']").get(0).checked=false;
            $("input[name='ifDraw']").get(1).checked=true;
            alert("只能有一条道路");

        }else {
            drawingManager.open();
            drawingManager.setDrawingMode(BMAP_DRAWING_POLYLINE)


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
        enableDrawingTool: false, //是否显示工具栏
        drawingToolOptions: {
            anchor: BMAP_ANCHOR_TOP_RIGHT, //位置
            offset: new BMap.Size(5, 5), //偏离值
        },
        drawingTypes : [
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