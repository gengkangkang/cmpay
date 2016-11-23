<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    String path = request.getScheme() + "://" + request.getServerName()
            + ":" + request.getServerPort() + request.getContextPath()
            + "/";
    session.setAttribute("path", path);
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>统一支付</title>
    <meta charset="utf-8">
    <link href="${path}pay_files/pay.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="${path}js/jquery-1.7.2.min.js"></script>

</head>
<body>

    <div class="wrap_header">
        <div class="header clearfix">
            <div class="logo_panel clearfix">
                <div class="logo fl"><img src="${path}pay_files/cmiglogo.png" alt=""></div>
                <div class="lg_txt">| 商户界面</div>
            </div>
            <div class="fr tip_panel">
                <div class="txt">欢迎使用统一支付付款</div>
                <a href="">常见问题</a>
            </div>
        </div>
    </div>

    <div id="box" class="">
                                               付款成功跳转界面
    </div>

    <div class="footer w100">
        <div class="container">
            <ul class="con-content">
                <li><br/></li>
            </ul>
            <ul class="con-content">
            </ul>
            <ul class="con-content">
            </ul>
        </div>
        <div class="copyright">Copyright © 2015-2016 中民物业有限责任公司</div>
        <p class="yue"><a href="http://www.cm-inv.com" target="_blank">沪ICP备00001号</a></p>
    </div>
</body>
</html>
