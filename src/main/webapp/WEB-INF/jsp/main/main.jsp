<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>sios cms main page</title>
</head>
<body>
    <h2>● jstl test</h2><br>
    <c:out value="c:out!!!!!!!"/><br>
    ${not empty result? result:'result is empty!!!'}
</body>
</html>