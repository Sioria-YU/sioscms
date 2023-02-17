<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>관리자 메인페이지</title>

    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <link href="https://getbootstrap.com/docs/4.0/examples/signin/signin.css" rel="stylesheet" crossorigin="anonymous">
</head>
<body>
  <h2>관리자 메인페이지!</h2>
  accountDto :::: ${accountDto}<br>
  sessionScope :::: ${sessionScope}<br>

  <div class="container">
      <form class="form-logout" method="post" action="/logout">
          <button type="submit" class="btn btn-lg btn-dark btn-block">로그아웃</button>
      </form>
  </div>
</body>
</html>
