<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>
<head>
    <title>${not empty title? title : 'TITLE'}${not empty subTitle? ' ' + subTitle : ''}</title>
</head>
<body>
    <tiles:insertAttribute name="body"/>
</body>
</html>
