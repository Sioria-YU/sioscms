<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>관리자 로그인 페이지</title>
</head>
<body>
<div class="container">
    <form class="form-signin" method="post" action="/login-process">
        <h2 class="form-signin-heading">로그인 페이지</h2>
        <div class="alert alert-success" role="alert">You have been signed out</div>
        <p>
            <label for="userId" class="sr-only">아이디</label>
            <input type="text" id="userId" name="userId" class="form-control" placeholder="Username" required=""
                   autofocus="">
        </p>
        <p>
            <label for="userPw" class="sr-only">비밀번호</label>
            <input type="password" id="userPw" name="userPw" class="form-control" placeholder="Password" required="">
        </p>
        <button type="submit" class="btn btn-lg btn-primary btn-block">로그인</button>
        <button type="button" class="btn btn-lg btn-primary btn-block" onclick="">회원가입</button>
        <button type="button" class="btn btn-lg btn-primary btn-block" onclick="">로그아웃</button>
    </form>
</div>
</body>
</html>
