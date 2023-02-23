<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>${not empty title? title : 'SiosCMS'}${not empty subTitle? ' ' + subTitle : ''}</title>
    <tiles:insertAttribute name="include"/>
</head>
<body class="sb-nav-fixed">
    <tiles:insertAttribute name="header"/>
    <div id="layoutSidenav">
        <tiles:insertAttribute name="side"/>
        <tiles:insertAttribute name="body"/>
    </div>
    <tiles:insertAttribute name="footer"/>
</body>
</html>
