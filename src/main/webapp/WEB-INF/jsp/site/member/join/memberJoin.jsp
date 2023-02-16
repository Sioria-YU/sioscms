<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>회원가입</title>

    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <link href="https://getbootstrap.com/docs/4.0/examples/signin/signin.css" rel="stylesheet" crossorigin="anonymous">

    <style>
        body {
            min-height: 100vh;

            background: -webkit-gradient(linear, left bottom, right top, from(#92b5db), to(#1d466c));
            background: -webkit-linear-gradient(bottom left, #92b5db 0%, #1d466c 100%);
            background: -moz-linear-gradient(bottom left, #92b5db 0%, #1d466c 100%);
            background: -o-linear-gradient(bottom left, #92b5db 0%, #1d466c 100%);
            background: linear-gradient(to top right, #92b5db 0%, #1d466c 100%);
        }

        .input-form {
            max-width: 680px;

            margin-top: 80px;
            padding: 32px;

            background: #fff;
            -webkit-border-radius: 10px;
            -moz-border-radius: 10px;
            border-radius: 10px;
            -webkit-box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
            -moz-box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
            box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15)
        }
    </style>
</head>
<body>
<div class="container">
    <div class="input-form col-md-12 mx-auto">
        <h2>회원가입</h2>
        <form class="form-signup" method="post" action="/site/member/join/member-join-proc">
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="userId">아이디</label>
                    <input type="text" class="form-control" id="userId" name="userId" placeholder="아이디를 입력해주세요." maxlength="20" required>
                    <div class="invalid-feedback">
                        아이디를 입력해주세요.
                    </div>
                </div>
                <div class="col-md-6 mb-3">
                    <label for="userPassword">비밀번호</label>
                    <input type="password" class="form-control" id="userPassword" name="userPassword" placeholder="비밀번호를 입력해주세요." required>
                    <div class="invalid-feedback">
                        비밀번호를 입력해주세요.
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="name">이름</label>
                    <input type="text" class="form-control" id="name" name="name" placeholder="이름을 입력해주세요." required>
                    <div class="invalid-feedback">
                        이름을 입력해주세요.
                    </div>
                </div>
                <div class="col-md-6 mb-3">
                    <label for="phone">휴대번호</label>
                    <input type="text" class="form-control" id="phone" name="phone" placeholder="휴대번호를 입력해주세요." maxlength="13" required>
                    <div class="invalid-feedback">
                        휴대번호를 입력해주세요.
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="gender">성별</label>
                    <select class="form-control" id="gender" name="gender" required>
                        <option value="">선택</option>
                        <option value="M">남성</option>
                        <option value="G">여성</option>
                    </select>
                    <div class="invalid-feedback">
                        이름을 입력해주세요.
                    </div>
                </div>
                <div class="col-md-6 mb-3">
                    <label for="role">관리자여부</label>
                    <select class="form-control" id="role" name="role" required>
                        <option value="">선택</option>
                        <option value="ADMIN">관리자</option>
                        <option value="USER">비관리자</option>
                    </select>
                    <div class="invalid-feedback">
                        휴대번호를 입력해주세요.
                    </div>
                </div>
            </div>
            <button type="submit" class="btn btn-lg btn-success btn-block">회원가입</button>
            <button type="button" class="btn btn-lg btn-dark btn-block" onclick="history.back()">취소</button>
        </form>
    </div>
</div>

<script>
    window.addEventListener('load', () => {
        const forms = document.getElementsByClassName('form-signup');

        Array.prototype.filter.call(forms, (form) => {
            form.addEventListener('submit', function (event) {
                if (form.checkValidity() === false) {
                    event.preventDefault();
                    event.stopPropagation();
                }

                form.classList.add('was-validated');
            }, false);
        });
    }, false);
</script>

</body>
</html>
