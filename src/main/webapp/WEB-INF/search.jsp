<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <script src="/js/jquery-3.6.0.js"></script>
</head>

<body>
    <div align="center"> 请选择查询条件</div>
    <hr>
    <div id="allSearch">
        <form action="/road/">
            <div>
                <input type="radio" name="classification" value="1" checked onclick="">区属
                <input type="radio" name="classification" value="2">管理单位
                <input type="radio" name="classification" value="3"> 名称
            </div>
            <div align="center">
                <select id="roadResp" style="width: 70%">
                    <option>-------------道路-------------</option>
                </select>
            </div>
            <div align="center">
                <select id="classificationResp" style="width: 70%">
                    <option>-------------市区-------------</option>
                </select>
            </div>
            <div hidden id="roadName">
                道路名称:<input type="text" name="roadName" style="width: 58%; height: 20px;">
            </div>
        </form>
    </div>
    <script>
        $('input[type=radio][name=classification]').change(function () {
            var optionTOAdd;
            var classificationResp = $('#classificationResp');
            var roadName = $('#roadName');
            if (this.value == 1) {
                roadName.hide();
                classificationResp.show();
                optionTOAdd = $('<option>--------------市区-------</option>')
                classificationResp.empty();
                classificationResp.append(optionTOAdd);
            }
            if (this.value == 2) {
                roadName.hide();
                classificationResp.show();
                optionTOAdd = $('<option>--------管理单位-------</option>')
                classificationResp.empty();
                classificationResp.append(optionTOAdd);
            }
            if (this.value == 3) {
                classificationResp.hide();
                roadName.show();
            }
        })
    </script>

    <div style="height: 5%"><button name="doSearch" type="submit" style="float: right">搜索</button><img
            src="/img/search.jpg" style="float: right ;height: 24.15px"></div>
    </form>


    <div>
        <table align="center" style="background-color: hotpink;background-color: cyan;height: 65%;width: 80%">
            <tr>
                <td></td>
            </tr>
        </table>
    </div>
</body>

</html>