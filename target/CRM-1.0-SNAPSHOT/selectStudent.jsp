
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>浏览学生</title>
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript">
        $(function (){
            //在当前页面dom对象加载后，执行loadStudentData()
            loadStudentData();
            $("#btn").click(function (){
                loadStudentData();
            })
        })

        function loadStudentData(){
            $.ajax({
                url:"selectUser.do",
                type:"get",
                dataType:"json",
                success:function (data){
                    //清除旧数据
                    $("#info").html("");
                    $.each(data,function (i,n){
                        $("#info").append("<tr>")
                            .append("<td>"+n.id+"</td>")
                            .append("<td>"+n.name+"</td>")
                            .append("</tr>")
                    })
                }
            })
        }
    </script>
</head>
<body>
    <div align="center>">
        <table align="center">
            <thead>
            <tr>
                <td>学号</td>
                <td>姓名</td>
            </tr>
            </thead>
            <tbody id="info">

            </tbody>
        </table>
        <button align="center" type="button" id="btn">显示数据</button>
    </div>
</body>
</html>
