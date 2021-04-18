<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

    <title>上传文件</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="plugin/easyui/themes/bootstrap/easyui.css" rel="stylesheet">
    <link href="css/mycss.css" rel="stylesheet">

    <style type="text/css">
        .file {
            position: relative;/*绝对定位!*/
            display: inline-block;/*设置为行内元素*/
            background: #D0EEFF;
            border: 1px solid #99D3F5;
            border-radius: 4px;
            padding: 4px 12px;
            overflow: hidden;
            color: #1E88C7;
            text-decoration: none;
            text-indent: 0;
            line-height: 20px;
        }
        .file input {
            position: absolute;/*相对定位*/
            right: 0;
            top: 0;
            opacity: 0;/*将上传组件设置为透明的*/
            font-size: 100px;
        }
        .file:hover {
            background: #AADDFF;
            border-color: #78C3F3;
            color: #004974;
            text-decoration: none;
        }
    </style>

    <style type="text/css">
        html,body {
            width: 100%;
            height: 100%;
            position: relative;
        }
        .son {
            /* 写样式时尽量先写定位哦，有利于页面渲染 */
            position: absolute;
            top:60%;
            left: 50%;
            margin:-250px 0 0 -100px;
            /* 这里宽高写成了px，要是百分比就直接差值除以二就可以啦，不用再多一步margin */
            width: 400px;
            height: 500px;
            background: white;
        }
    </style>
</head>
<body>
<%--<form method="POST" enctype="multipart/form-data" action="/stu/stuManager/importExcel.do">
    <input type="file" name="file" value="请选择文件"><br />
    <input type="submit" id="submit">
</form>--%>
<div class="son">
        <div class="file">
        <input type="file" class="file"  id="files" value="请选择文件" ><h3>上传文件：请选择文件</h3><br />
        </div><br />
    <br /><br />
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button  id="submit" type="button" > <h5>导入</h5></button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <button id="fan"> <h5>返回</h5></button>
    <br />
    &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; <h5>点击上传文件，选择所要上传的文件！</h5>
</div>
<script src="js/jquery-2.1.1.min.js"></script>
<script src="plugin/easyui/jquery.easyui.min.js"></script>
<script src="js/bootstrap.js"></script>
<script src="js/input.js"></script>

</html>
