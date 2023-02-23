<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>${not empty title? title : 'TITLE'}${not empty subTitle? ' ' + subTitle : ''}</title>
    <tiles:insertAttribute name="include"/>
</head>
<body class="sb-nav-fixed">
    <tiles:insertAttribute name="header"/>
    <tiles:insertAttribute name="side"/>
    <tiles:insertAttribute name="body"/>
    <tiles:insertAttribute name="footer"/>
</body>
</html>
