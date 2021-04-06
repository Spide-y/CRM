<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Map<String,String> pMap = (Map<String, String>) application.getAttribute("pMap");
    Set<String> set = pMap.keySet();
%>
<%@taglib prefix="u" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <script type="text/javascript" src="../../jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="../../jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../../jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript" src="../../jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
    <link href="../../jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
    <link href="../../jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
    <script>
        var json = {

            <%
                for (String key:set){
                    String value = pMap.get(key);
            %>
            "<%=key%>" : <%=value%>,
            <%
                }
            %>
        };

        $(function (){

            //刷新可能性
            getPossibility()
            //得到可能性
            $("#edit-stage").change(function (){
                getPossibility()
            })

            //更新
            $("#update-Btn").click(function (){
                $.ajax({
                    url:"updateTran.do",
                    data:{
                        'id':$.trim($("#edit-id").val()),
                        'owner':$.trim($("#edit-owner").val()),
                        'money':$.trim($("#edit-money").val()),
                        'name':$.trim($("#edit-name").val()),
                        'expectedDate':$.trim($("#edit-expectedDate").val()),
                        'customerId':$.trim($("#edit-customerId").val()),
                        'stage':$.trim($("#edit-stage").val()),
                        'type':$.trim($("#edit-type").val()),
                        'source':$.trim($("#edit-source").val()),
                        'activityId':$.trim($("#edit-activityId").val()),
                        'contactsId':$.trim($("#edit-contactsId").val()),
                        'description':$.trim($("#edit-description").val()),
                        'contactSummary':$.trim($("#edit-contactSummary").val()),
                        'nextContactTime':$.trim($("#edit-nextContactTime").val())
                    },
                    type:"post",
                    dataType:"json",
                    success : function (data){
                        if (data.success){
                            window.location.href='index.jsp';
                            pageList();
                        }else{
                            alert("更新交易失败")
                        }
                    },
                });
            })

        })

        function getPossibility(){
            var stage = $("#edit-stage").val();
            var possibility = json[stage];
            $("#edit-possibility").val(possibility)
        }


    </script>

</head>
<body>

<!-- 查找市场活动 -->
<div class="modal fade" id="findMarketActivity" role="dialog">
    <div class="modal-dialog" role="document" style="width: 80%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title">查找市场活动</h4>
            </div>
            <div class="modal-body">
                <div class="btn-group" style="position: relative; top: 18%; left: 8px;">
                    <form class="form-inline" role="form">
                        <div class="form-group has-feedback">
                            <input type="text" class="form-control" style="width: 300px;" placeholder="请输入市场活动名称，支持模糊查询">
                            <span class="glyphicon glyphicon-search form-control-feedback"></span>
                        </div>
                    </form>
                </div>
                <table id="activityTable4" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
                    <thead>
                    <tr style="color: #B3B3B3;">
                        <td></td>
                        <td>名称</td>
                        <td>开始日期</td>
                        <td>结束日期</td>
                        <td>所有者</td>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td><input type="radio" name="activity"/></td>
                        <td>发传单</td>
                        <td>2020-10-10</td>
                        <td>2020-10-20</td>
                        <td>zhangsan</td>
                    </tr>
                    <tr>
                        <td><input type="radio" name="activity"/></td>
                        <td>发传单</td>
                        <td>2020-10-10</td>
                        <td>2020-10-20</td>
                        <td>zhangsan</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<!-- 查找联系人 -->
<div class="modal fade" id="findContacts" role="dialog">
    <div class="modal-dialog" role="document" style="width: 80%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title">查找联系人</h4>
            </div>
            <div class="modal-body">
                <div class="btn-group" style="position: relative; top: 18%; left: 8px;">
                    <form class="form-inline" role="form">
                        <div class="form-group has-feedback">
                            <input type="text" class="form-control" style="width: 300px;" placeholder="请输入联系人名称，支持模糊查询">
                            <span class="glyphicon glyphicon-search form-control-feedback"></span>
                        </div>
                    </form>
                </div>
                <table id="activityTable" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
                    <thead>
                    <tr style="color: #B3B3B3;">
                        <td></td>
                        <td>名称</td>
                        <td>邮箱</td>
                        <td>手机</td>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td><input type="radio" name="activity"/></td>
                        <td>李四</td>
                        <td>lisi@bjpowernode.com</td>
                        <td>12345678901</td>
                    </tr>
                    <tr>
                        <td><input type="radio" name="activity"/></td>
                        <td>李四</td>
                        <td>lisi@bjpowernode.com</td>
                        <td>12345678901</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>


<div style="position:  relative; left: 30px;">
    <h3>更新交易</h3>
    <div style="position: relative; top: -40px; left: 70%;">
        <button type="button" class="btn btn-primary" id="update-Btn">更新</button>
        <button type="button" class="btn btn-default" onclick="window.location.href='index.jsp'">取消</button>
    </div>
    <hr style="position: relative; top: -40px;">
</div>
<form class="form-horizontal" role="form" style="position: relative; top: -30px;">
    <input type="hidden" id="edit-id" value="${tran.id}">
    <div class="form-group">
        <label for="edit-owner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
        <div class="col-sm-10" style="width: 300px;">
            <select class="form-control" id="edit-owner" >
                <u:forEach items="${users}" var="a">
                    <option value="${a.id}">${a.name}</option>
                </u:forEach>
            </select>
        </div>
        <label for="edit-money" class="col-sm-2 control-label">金额</label>
        <div class="col-sm-10" style="width: 300px;">
            <input type="text" class="form-control" id="edit-money" value="${tran.money}">
        </div>
    </div>

    <div class="form-group">
        <label for="edit-name" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
        <div class="col-sm-10" style="width: 300px;">
            <input type="text" class="form-control" id="edit-name" value="${tran.name}">
        </div>
        <label for="edit-expectedDate" class="col-sm-2 control-label">预计成交日期<span style="font-size: 15px; color: red;">*</span></label>
        <div class="col-sm-10" style="width: 300px;">
            <input type="text" class="form-control" id="edit-expectedDate" value="${tran.expectedDate}">
        </div>
    </div>

    <div class="form-group">
        <label for="edit-customerId" class="col-sm-2 control-label">客户名称<span style="font-size: 15px; color: red;">*</span></label>
        <div class="col-sm-10" style="width: 300px;">
            <input type="text" class="form-control" id="edit-customerId" value="${tran.customerId}" placeholder="支持自动补全，输入客户不存在则新建">
        </div>
        <label for="edit-stage" class="col-sm-2 control-label">阶段<span style="font-size: 15px; color: red;">*</span></label>
        <div class="col-sm-10" style="width: 300px;">
            <select class="form-control" id="edit-stage">
                <option></option>
                <option selected>${tran.stage}</option>
                <option>01资质审查</option>
                <option>02需求分析</option>
                <option>03价值建议</option>
                <option>04确定决策者</option>
                <option>05提案/报价</option>
                <option>06谈判/复审</option>
                <option>07成交</option>
                <option>08丢失的线索</option>
                <option>09因竞争丢失关闭</option>
            </select>
        </div>
    </div>

    <div class="form-group">
        <label for="edit-type" class="col-sm-2 control-label">类型</label>
        <div class="col-sm-10" style="width: 300px;">
            <select class="form-control" id="edit-type">
                <option></option>
                <option selected>${tran.type}</option>
                <option>已有业务</option>
                <option selected>新业务</option>
            </select>
        </div>
        <label for="edit-possibility" class="col-sm-2 control-label">可能性</label>
        <div class="col-sm-10" style="width: 300px;">
            <input type="text" class="form-control" id="edit-possibility">
        </div>
    </div>

    <div class="form-group">
        <label for="edit-source" class="col-sm-2 control-label">来源</label>
        <div class="col-sm-10" style="width: 300px;">
            <select class="form-control" id="edit-source">
                <option></option>
                <option selected>${tran.source}</option>
                <option>广告</option>
                <option>推销电话</option>
                <option>员工介绍</option>
                <option>外部介绍</option>
                <option>在线商场</option>
                <option>合作伙伴</option>
                <option>公开媒介</option>
                <option>销售邮件</option>
                <option>合作伙伴研讨会</option>
                <option>内部研讨会</option>
                <option>交易会</option>
                <option>web下载</option>
                <option>web调研</option>
                <option>聊天</option>
            </select>
        </div>
        <label for="edit-activityId" class="col-sm-2 control-label">市场活动源&nbsp;&nbsp;<a href="javascript:void(0);" data-toggle="modal" data-target="#findMarketActivity"><span class="glyphicon glyphicon-search"></span></a></label>
        <div class="col-sm-10" style="width: 300px;">
            <input type="text" class="form-control" id="edit-activityId" value="${tran.activityId}">
        </div>
    </div>

    <div class="form-group">
        <label for="edit-contactsId" class="col-sm-2 control-label">联系人名称&nbsp;&nbsp;<a href="javascript:void(0);" data-toggle="modal" data-target="#findContacts"><span class="glyphicon glyphicon-search"></span></a></label>
        <div class="col-sm-10" style="width: 300px;">
            <input type="text" class="form-control" id="edit-contactsId" value="${tran.contactsId}">
        </div>
    </div>

    <div class="form-group">
        <label for="edit-description" class="col-sm-2 control-label">描述</label>
        <div class="col-sm-10" style="width: 70%;">
            <textarea class="form-control" rows="3" id="edit-description">${tran.description}</textarea>
        </div>
    </div>

    <div class="form-group">
        <label for="edit-contactSummary" class="col-sm-2 control-label">联系纪要</label>
        <div class="col-sm-10" style="width: 70%;">
            <textarea class="form-control" rows="3" id="edit-contactSummary">${tran.contactSummary}</textarea>
        </div>
    </div>

    <div class="form-group">
        <label for="edit-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
        <div class="col-sm-10" style="width: 300px;">
            <input type="text" class="form-control" id="edit-nextContactTime" value="${tran.nextContactTime}">
        </div>
    </div>

</form>
</body>
</html>
