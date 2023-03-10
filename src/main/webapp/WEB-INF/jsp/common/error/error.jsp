<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="mobile-web-app-capable" content="yes">

    <title>SIOSCMS</title>

    <style type="text/css">

        .errorBack{
            display: inline-block;
            padding: 0 20px;
            line-height: 35px;
            /* 		    background-color: #003067; */
            border-radius: 5px;
            /* 		    color: #fff; */
            text-align: center;
            font-size: 16px;
            font-weight: 600;

            border: 1px solid #003067;
            background: #fff;
            color: #003067;
        }


    </style>
</head>
<body>
<div class="loginLogo">
    <a href="/main">
<%--        <img src="/smvf/static/firefighter/img/logo.svg" alt="로고">--%>
        <p>SIOSCMS</p>
    </a>
</div>
<div class="errorWrap">
    <div class="errorBox">
        <h1>PAGE NOT FOUND</h1>
        <p>요청하신 페이지를 찾을 수 없습니다.<br>서비스 이용에 불편을 드려 죄송합니다.</p>
        <p><a href="javascript:history.back(-1)" class="errorBack">←이전페이지</a>
        </p>
    </div>
</div>
</body>
</html>