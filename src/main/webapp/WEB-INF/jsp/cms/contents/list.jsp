<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script>
    const deleteBoards = () => {
        if($(".checkItem:checked").length < 1){
            alert("최소 1개 이상 선택해야합니다.");
            return false;
        }

        if(confirm("삭제하시겠습니까?")) {
            let ids = [];
            $(".checkItem:checked").each(
                function(){
                    ids.push(this.value)
                });

            $.ajax({
                url: '/api/contents/multi-delete',
                type: 'DELETE',
                async: false,
                data: {
                    ids : ids
                },
                success: function (data) {
                    if(data) {
                        alert("삭제 처리되었습니다.");
                        location.reload();
                    }else{
                        alert("삭제 처리 중 오류가 발생하였습니다.")
                    }
                },
                error: function (request, status, error) {
                    console.error(error);
                    alert("오류가 발생하였습니다.");
                }
            });
        }
    }

    $(function () {
        $("#checkAll").on('click', function () {
            if ($("#checkAll").is(":checked")) {
                $(".checkItem").prop("checked", true);
            } else {
                $(".checkItem").prop("checked", false);
            }
        });

        $(".checkItem").on('click', function () {
            if (!$(this).is(":checked")) {
                $("#checkAll").prop("checked", false);
            }
        });
    });
</script>

<div id="layoutSidenav_content">
    <main>
        <div class="container-fluid px-4">
            <div class="pagetitle">
                <h1 class="mt-4">콘텐츠 관리</h1>
                <nav>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="/cms/main"><i class="bi bi-house-door"></i></a></li>
                        <li class="breadcrumb-item">사이트 관리</li>
                        <li class="breadcrumb-item active">콘텐츠 관리</li>
                    </ol>
                </nav>
            </div>

            <div class="container-fluid px-4">
                <div class="search-box">
                    <div class="search-box-title">
                        <h4>검색 영역</h4>
                    </div>
                    <div class="search-box-body">
                        <form id="searchForm" name="searchForm" action="./list">
                            <input type="hidden" id="pageNumber" name="pageNumber" value="${empty param.pageNumber? 1:param.pageNumber}">
                            <input type="hidden" id="pageOffset" name="pageOffset" value="${empty param.pageOffset? 10:param.pageOffset}">
                            <input type="hidden" id="pageSize" name="pageSize" value="${empty param.pageSize? 5:param.pageSize}">
                            <div class="row mb-3">
                                <label for="title" class="col-sm-2 col-form-label">제목</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="title" name="title" value="${param.title}" placeholder="제목을 입력하세요." aria-label="제목을 입력하세요.">
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="startDate" class="col-sm-2 col-form-label">기간</label>
                                <div class="col-sm-1">
                                    <input type="date" class="form-control" id="startDate" name="startDate" value="${param.startDate}" placeholder="시작일을 선택하세요." aria-label="시작일을 선택하세요.">
                                </div>
                                <div class="col-sm-1">
                                    <input type="date" class="form-control" id="endDate" name="endDate" value="${param.endDate}" placeholder="종료일을 선택하세요." aria-label="종료일을 선택하세요.">
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
                    <i class="bi bi-record-circle-fill"></i><h4 class="card-title">콘텐츠 목록</h4>
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
                        <th><label for="checkAll"><input type="checkbox" class="form-check-input" id="checkAll"/></label></th>
                        <th scope="col">순번</th>
                        <th scope="col">제목</th>
                        <th scope="col">작성자</th>
                        <th scope="col">작성일</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:choose>
                        <c:when test="${not empty resultList}">
                            <c:forEach var="result" items="${resultList}" varStatus="status">
                                <fmt:parseDate var="createdDateTime" value="${result.createdDateTime}" pattern="yyyy-MM-dd" type="both"/>
                                <tr>
                                    <td><input type="checkbox" class="form-check-input checkItem" name="boardMasterCheck" value="${result.id}"></td>
                                    <th scope="row">${pageInfo.totalCount - ((pageInfo.pageNumber-1) * pageInfo.pageOffset + status.index)}</th>
                                    <td><a href="/cms/board/view/${result.id}">${result.title}</a></td>
                                    <td>${result.createdBy.name}</td>
                                    <td><fmt:formatDate value="${createdDateTime}" pattern="yyyy-MM-dd"/></td>
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
                    <button type="button" class="btn btn-danger btn-lg" onclick="deleteBoards();">선택 삭제</button>
                    <button type="button" class="btn btn-success btn-lg" onclick="location.href='./regist?boardMasterId=${param.boardMasterId}';">등록</button>
                </div>
            </div>
        </div>
    </main>
</div>