<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>sios cms main page</title>

    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <link href="https://getbootstrap.com/docs/4.0/examples/signin/signin.css" rel="stylesheet" crossorigin="anonymous">

    <script>
        const redirectCmsLoginPage = () =>{
            location.href = "/cms/auth/login";
        }

        const memberJoinPageEvent = () =>{
            location.href = "/site/member/join/member-join";
        }
    </script>
</head>
<body>
    <div class="container">
        <h2>메인 페이지</h2>
        <button type="button" class="btn btn-lg btn-success btn-block" onclick="redirectCmsLoginPage()">관리자 로그인</button>
        <button type="button" class="btn btn-lg btn-dark btn-block" onclick="memberJoinPageEvent()">회원가입</button>

    </div>
</body>
</html>