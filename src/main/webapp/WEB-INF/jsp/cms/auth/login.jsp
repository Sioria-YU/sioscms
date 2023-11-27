<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>관리자 로그인 페이지</title>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
        <link href="https://getbootstrap.com/docs/4.0/examples/signin/signin.css" rel="stylesheet" crossorigin="anonymous">
    </head>
    <body>
        <div class="container">
            <form class="form-signin" method="post" action="/cms/auth/login-process">
                <h2 class="form-signin-heading">로그인 페이지</h2>
                <div class="alert alert-success" role="alert">로그인</div>
                <p>
                    <label for="userId" class="sr-only">아이디</label>
                    <input type="text" id="userId" name="userId" class="form-control" placeholder="Username" required=""
                           autofocus="">
                </p>
                <p>
                    <label for="userPw" class="sr-only">비밀번호</label>
                    <input type="password" id="userPw" name="userPw" class="form-control" placeholder="Password" required="">
                </p>
                <c:if test="${not empty exceptionMsg}">
                    <p style="color:red">
                        ${exceptionMsg}
                    </p>
                </c:if>
<%--                <input type="text" name="${_csrf.parameterName} " value="${_csrf.token}">--%>
                <button type="submit" class="btn btn-lg btn-success btn-block">로그인</button>
                <button type="button" class="btn btn-lg btn-info btn-block" onclick="moveToJoinpage()">회원가입</button>
<%--                <button type="button" class="btn btn-lg btn-dark btn-block" onclick="">로그아웃</button>--%>
            </form>
        </div>
    <script>
        const moveToJoinpage = () => {
            location.href="/site/member/join/member-join";
        }
    </script>
    </body>
</html>
