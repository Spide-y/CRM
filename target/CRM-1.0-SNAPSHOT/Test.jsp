<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet"/>

    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js" ></script>
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js" ></script>
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
    <script>
        $(function (){

            $(".time").datetimepicker({
                minView: "month",
                language:  'zh-CN',
                format: 'yyyy-mm-dd',
                autoclose: true,
                todayBtn: true,
                pickerPosition: "bottom-left"
            });

            $("#TestBtn").click(function (){
                $("#selectTest").val("a")
            })

        })
    </script>
</head>
<body>
$.ajax({
url:"",
data:{

},
type:"",
dataType:"",
success : function (data){

},
});
<button id="TestBtn" >测试</button>
<select id="selectTest" >
    <option value="1">abc</option>
    <option value="a">def</option>
    <option value="3" id="avc" selected>ghi</option>
</select>
<input class="time" id="abc">
</body>
</html>
