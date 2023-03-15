<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="layoutSidenav_content">
    <main>
        <div class="container-fluid px-4">
            <div class="pagetitle">
                <h1 class="mt-4">사용자 관리</h1>
                <nav>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="/cms/main"><i class="bi bi-house-door"></i></a></li>
                        <li class="breadcrumb-item">시스템관리</li>
                        <li class="breadcrumb-item active">사용자 관리</li>
                    </ol>
                </nav>
            </div>
        </div>
        <div class="container-fluid px-4">
            <div class="search-box">
                <div class="search-box-title">
                    <h4>검색 영역</h4>
                </div>
                <div class="search-box-body">
                    <form id="searchForm" name="searchForm" action="./admin-list">
                        <div class="row mb-3">
                            <label for="userId" class="col-sm-1 col-form-label">아이디</label>
                            <div class="col-sm-5">
                                <input type="text" class="form-control" id="userId" name="userId" value="${param.userId}" placeholder="사용자 아이디를 입력하세요." aria-label="사용자 아이디를 입력하세요.">
                            </div>
                            <label for="name" class="col-sm-1 col-form-label">성명</label>
                            <div class="col-sm-5">
                                <input type="text" class="form-control" id="name" name="name" value="${param.name}" placeholder="사용자 성명을 입력하세요." aria-label="사용자 성명을 입력하세요.">
                            </div>
                        </div>
                        <%--<div class="row mb-3">
                            <label for="userId" class="col-sm-2 col-form-label">아이디</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="userId">
                            </div>
                        </div>--%>
                        <div class="row mb-3">
                            <label for="gender" class="col-sm-1 col-form-label">성별</label>
                            <div class="col-sm-5">
                                <select class="form-select" id="gender" name="gender" aria-label="선택">
                                    <option value="" ${empty param.gender? 'selected':''}>선택</option>
                                    <option value="M" ${param.gender eq 'M'? 'selected':''}>남성</option>
                                    <option value="F" ${param.gender eq 'F'? 'selected':''}>여성</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-btn-set text-center">
                            <button type="submit" class="btn btn-primary">검색</button>
                            <button type="reset" class="btn btn-secondary">초기화</button>
                        </div>
                    </form>
                </div>
            </div>
            <div class="icon">
                <i class="bi bi-record-circle-fill"></i><h4 class="card-title">사용자 목록</h4>
            </div>
            <c:if test="${not empty pageInfo}">
                <div>
                    <span class="badge bg-secondary">
                        <h6 style="margin-bottom: 3px;">
                        전체 <span class="badge bg-white text-secondary">${empty pageInfo.totalCount? 0:pageInfo.totalCount}</span> 건
                            <span class="badge bg-white text-secondary">${empty pageInfo.pageNumber? 1:pageInfo.pageNumber}</span>
                            / <span class="badge bg-white text-secondary">${empty pageInfo.totalPageSize? 1:pageInfo.totalPageSize}</span> 페이지
                        </h6>
                    </span>
                </div>
            </c:if>
            <table class="table text-center">
                <thead>
                <tr>
                    <th scope="col" width="50px"><input type="checkbox" class="chk-all"/></th>
                    <th scope="col">순번</th>
                    <th scope="col">성명</th>
                    <th scope="col">아이디</th>
                    <th scope="col">휴대번호</th>
                    <th scope="col">성별</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${not empty resultList}">
                        <c:forEach var="result" items="${resultList}" varStatus="status">
                            <tr>
                                <th><input type="checkbox" class="chk-items" value="${result.id}"/></th>
                                <th scope="row">${pageInfo.totalCount - ((pageInfo.pageNumber-1) * pageInfo.pageOffset + status.index)}</th>
                                <td><a href="/cms/member/user-view/${result.id}">${result.name}</a></td>
                                <td>${result.userId}</td>
                                <td>${result.phone}</td>
                                <td>${empty result.gender? '':result.gender eq 'M'? '남성':'여성'}</td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr class="text-center">
                            <td colspan="6">조회된 데이터가 존재하지 않습니다.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
            <jsp:include page="/WEB-INF/jsp/common/commonPagenation.jsp"/>
            <div class="form-btn-set text-end">
                <button type="button" class="btn btn-success btn-lg" onclick="javascript:location.href='./user-regist';">등록</button>
                <button type="button" class="btn btn-danger btn-lg">삭제</button>
            </div>
        </div>
    </main>
</div>
<script>
    $(function(){
        $(".chk-all").on('click',()=>{
            if($(".chk-all").is(":checked")) {
                $(".chk-items").prop('checked', true);
            }else{
                $(".chk-items").prop('checked', false);
            }
        });
    });
</script>