<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div id="layoutSidenav_content">
    <main>
        <div class="container-fluid px-4">
            <div class="pagetitle">
                <h1 class="mt-4">공통코드 관리</h1>
                <nav>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="/cms/main"><i class="bi bi-house-door"></i></a></li>
                        <li class="breadcrumb-item">시스템관리</li>
                        <li class="breadcrumb-item active">공통코드 관리</li>
                    </ol>
                </nav>
            </div>
        </div>

        <div class="container-fluid px-4">
            <div class="icon">
                <i class="bi bi-record-circle-fill"></i><h4 class="card-title">공통코드 정보</h4>
            </div>

            <div class="row mb-3">
                <label for="codeGroupLabel" class="col-sm-2 col-form-label">코드그룹 명</label>
                <div class="col-sm-5">
                    <input type="text" class="form-control" id="codeGroupLabel" name="codeGroupLabel" value="${codeGroupInfo.codeGroupLabel}" placeholder="코드그룹 명을 입력하세요." aria-label="코드그룹 명을 입력하세요.">
                </div>
            </div>
            <div class="row mb-3">
                <label for="codeGroupId" class="col-sm-2 col-form-label">코드그룹 아이디</label>
                <div class="col-sm-5">
                    <span id="codeGroupId">${codeGroupInfo.codeGroupId}</span>
                </div>
            </div>
            <div class="row mb-3">
                <label for="codeGroupId" class="col-sm-2 col-form-label">사용 여부</label>
                <div class="col-sm-5">
                    <label for="isUsed_Y"><input type="radio" class="form-check-input" id="isUsed_Y" name="isUsed" value="TRUE" placeholder="사용" aria-label="사용" ${codeGroupInfo.isUsed eq 'TRUE'? 'checked':''}>사용</label>
                    <label for="isUsed_N"><input type="radio" class="form-check-input ms-1" id="isUsed_N" name="isUsed" value="FALSE" placeholder="미사용" aria-label="미사용" ${codeGroupInfo.isUsed eq 'FALSE'? 'checked':''}>미사용</label>
                </div>
            </div>
            <div class="row mb-3">
                <label for="codeGroupId" class="col-sm-2 col-form-label">설명</label>
                <div class="col-sm-5">
                    <textarea class="textarea form-control" id="codeGroupNote" name="codeGroupNote" >${codeGroupInfo.codeGroupNote}</textarea>
                </div>
            </div>
            <div class="row mb-3">
                <div class="col-sm-7 form-btn-set text-end">
                    <button type="button" class="btn btn-success btn-lg">수정</button>
                    <button type="button" class="btn btn-dark btn-lg" onclick="location.href='/cms/code/code-group/list'">뒤로 가기</button>
                </div>
            </div>

            <div class="icon mt-3">
                <i class="bi bi-record-circle-fill"></i><h4 class="card-title">코드 목록</h4>
            </div>
            <table class="table text-center">
                <thead>
                <tr>
                    <th><input type="checkbox" class="form-check-input" id="checkAll"></th>
                    <th scope="col">순번</th>
                    <th scope="col">코드 명</th>
                    <th scope="col">코드</th>
                    <th scope="col">옵션1</th>
                    <th scope="col">옵션2</th>
                    <th scope="col">옵션3</th>
                    <th scope="col">옵션4</th>
                    <th scope="col">작성자</th>
                    <th scope="col">작성일</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${not empty codeList}">
                        <c:forEach var="result" items="${codeList}" varStatus="status">
                            <fmt:parseDate var="createdDateTime" value="${result.createdDateTime}" pattern="yyyy-MM-dd" type="both"/>
                            <tr>
                                <td><input type="checkbox" class="form-check-input" name="codeGroupCheck" value="${result.codeId}"></td>
                                <th scope="row">${fn:length(codeList) - status.index}</th>
                                <td>${result.codeLabel}</td>
                                <td>${result.codeId}</td>
                                <td>${result.option1}</td>
                                <td>${result.option2}</td>
                                <td>${result.option3}</td>
                                <td>${result.option4}</td>
                                <td>${result.createdBy}</td>
                                <td><fmt:formatDate value="${createdDateTime}" pattern="yyyy-MM-dd"/></td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr class="text-center">
                            <td colspan="10">조회된 데이터가 존재하지 않습니다.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>

            <div class="form-btn-set text-end">
                <button type="button" class="btn btn-danger btn-lg" onclick="alert('미구현');">선택 삭제</button>
                <button type="button" class="btn btn-success btn-lg" onclick="alert('미구현');">추가</button>
            </div>
        </div>
    </main>
</div>