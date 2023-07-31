<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script>
    const formCheck = () =>{
        if($("#modalBoardName").val() === ''){
            alert("게시판 명을 입력하세요.");
            $("#modalBoardName").focus();
            return false;
        }

        if(confirm("등록하시겠습니까?")){
            let formData = new FormData(document.getElementById("boardMasterForm"));

            fetch("/cms/api/board-master/save",
                {
                    method: 'POST',
                    cache: 'no-cache',
                    body: formData
                }
            ).then((response) =>{
                return response.json();
            }).then((data) => {
                if(!!data){
                    alert("정상 처리 되었습니다.");
                    location.reload();
                }
            }).catch((error) => {
                console.error(error);
                alert("오류가 발생하였습니다.");
            });

        }
    }

    $(function (){
        $("#checkAll").on('click',function(){
            if($("#checkAll").is(":checked")){
                $(".checkItem").prop("checked", true);
            }else{
                $(".checkItem").prop("checked", false);
            }
        });

        $(".checkItem").on('click',function(){
            if(!$(this).is(":checked")){
                $("#checkAll").prop("checked", false);
            }
        });

        $('#boardMasterRegistModal').on('show.bs.modal', function () {
            $("#modalBoardName").val('');
            $("#modalBoardTypeCodeId").val($("#modalBoardTypeCodeId option:first-child").val());
        })
    });
</script>

<div id="layoutSidenav_content">
    <main>
        <div class="container-fluid px-4">
            <div class="pagetitle">
                <h1 class="mt-4">게시판 관리</h1>
                <nav>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="/cms/main"><i class="bi bi-house-door"></i></a></li>
                        <li class="breadcrumb-item">사이트 관리</li>
                        <li class="breadcrumb-item active">게시판 관리</li>
                    </ol>
                </nav>
            </div>

            <div class="container-fluid px-4">
                <div class="search-box">
                    <div class="search-box-title">
                        <h4>검색 영역</h4>
                    </div>
                    <div class="search-box-body">
                        <form id="searchForm" name="searchForm" action="./master-list">
                            <input type="hidden" id="pageNumber" name="pageNumber" value="${empty param.pageNumber? 1:param.pageNumber}">
                            <input type="hidden" id="pageOffset" name="pageOffset" value="${empty param.pageOffset? 10:param.pageOffset}">
                            <input type="hidden" id="pageSize" name="pageSize" value="${empty param.pageSize? 5:param.pageSize}">
                            <div class="row mb-3">
                                <label for="boardName" class="col-sm-2 col-form-label">게시판 명</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" id="boardName" name="boardName" value="${param.boardName}" placeholder="게시판 명을 입력하세요." aria-label="게시판 명을 입력하세요.">
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="boardTypeCode.id" class="col-sm-2 col-form-label">게시판 유형</label>
                                <div class="col-sm-3">
                                    <select class="form-select" id="boardTypeCode.id" name="boardTypeCode.id" aria-label="게시판 유형을 선택하세요.">
                                        <option value="">전체</option>
                                        <c:forEach var="cd" items="${boardTypeCodeList}">
                                            <option value="${cd.id}" ${param.get("boardTypeCode.id") eq cd.id? 'selected':''}>${cd.codeLabel}</option>
                                        </c:forEach>
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
                    <i class="bi bi-record-circle-fill"></i><h4 class="card-title">게시판 목록</h4>
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
                        <th scope="col">게시판 명</th>
                        <th scope="col">게시판 유형</th>
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
                                    <td><a href="/cms/board/master-view/${result.id}">${result.boardName}</a></td>
                                    <td><a href="/cms/board/master-view/${result.id}">${result.boardTypeCode.codeLabel}</a></td>
                                    <td>${result.createdBy}</td>
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
                    <button type="button" class="btn btn-danger btn-lg" onclick="">선택 삭제</button>
                    <button type="button" class="btn btn-success btn-lg" data-bs-toggle="modal" data-bs-target="#boardMasterRegistModal">등록</button>
                </div>
            </div>
        </div>

        <!-- Modal -->
        <div class="modal fade" id="boardMasterRegistModal" tabindex="-1" aria-labelledby="boardMasterRegistModalTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="boardMasterRegistModalTitle">게시판 등록</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form id="boardMasterForm" name="boardMasterForm" method="post" action="./save">
                            <div class="row mb-3">
                                <label for="modalBoardName" class="col-sm-3 col-form-label">게시판 명</label>
                                <div class="col-sm-7">
                                    <input type="text" class="form-control-small" id="modalBoardName" name="boardName" value="" placeholder="게시판 명을 입력하세요." aria-label="게시판 명을 입력하세요.">
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="modalBoardTypeCodeId" class="col-sm-3 col-form-label">게시판 유형</label>
                                <div class="col-sm-5">
                                    <select class="form-select" id="modalBoardTypeCodeId" name="boardTypeCode.id" aria-label="게시판 유형을 선택하세요.">
                                        <c:forEach var="cd" items="${boardTypeCodeList}">
                                            <option value="${cd.id}">${cd.codeLabel}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                        <button type="button" class="btn btn-success" onclick="formCheck();">등록</button>
                    </div>
                </div>
            </div>
        </div>
    </main>
</div>