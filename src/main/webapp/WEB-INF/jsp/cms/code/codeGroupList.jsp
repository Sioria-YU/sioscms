<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script>
    const formCheck = () =>{
        if($("#isCodeGroupIdChk").val() === 'F'){
            alert("코드그룹 아이디 중복체크를 실행해 주세요.");
            $("#codeGroupIdCheckEventButton").focus();
            return false;
        }

        if(confirm("등록하시겠습니까?")){
            let formData = new FormData(document.getElementById("codeGroupForm"));

            fetch("/cms/api/code-group/save",
                {
                    method: 'POST',
                    cache: 'no-cache',
                    body: formData
                }
            ).then((response) =>{
                if(response.ok){
                    alert("정상 처리 되었습니다.");
                    location.reload();
                }else{
                    console.log(data);
                    alert("오류가 발생하였습니다.");
                }
            }).catch((error) => console.error(error));

        }

        // let codeGroupLabel = formData.get("codeGroupLabel");
        // let codeGroupId = formData.get("codeGroupId");
        // let isUsed = formData.get("isUsed");
        // let codeGroupNote = formData.get("codeGroupNote");
        // let isCodeGroupIdChk = formData.get("isCodeGroupIdChk");



    }

    const codeGroupIdCheckEvent = () => {
        let formData = new FormData(document.getElementById("codeGroupForm"));

        $.post("/cms/api/code-group/duplication-check", {codeGroupId:formData.get("codeGroupId")}, function(data){
            if(!!data){
                if(data){
                    $("#isCodeGroupIdChk").val("T");
                    alert("사용할 수 있는 아이디입니다.");
                }else{
                    alert("사용할 수 없는 아이디입니다.");
                }
            }
        });
    }

</script>

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
                            <label for="codeGroupId" class="col-sm-2 col-form-label">코드그룹 아이디</label>
                            <div class="col-sm-3">
                                <input type="text" class="form-control" id="codeGroupId" name="codeGroupId" value="${param.userId}" placeholder="코드그룹 아이디를 입력하세요." aria-label="코드그룹 아이디를 입력하세요.">
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label for="codeGroupLabel" class="col-sm-2 col-form-label">코드그룹 명</label>
                            <div class="col-sm-3">
                                <input type="text" class="form-control" id="codeGroupLabel" name="codeGroupLabel" value="${param.name}" placeholder="코드그룹 명을 입력하세요." aria-label="코드그룹 명을 입력하세요.">
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
                <i class="bi bi-record-circle-fill"></i><h4 class="card-title">공통코드 목록</h4>
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
                    <th><input type="checkbox" class="form-check-input" id="checkAll"></th>
                    <th scope="col">순번</th>
                    <th scope="col">코드그룹 아이디</th>
                    <th scope="col">코드그룹 명</th>
                    <th scope="col">코드그룹 설명</th>
                    <th scope="col">작성일</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${not empty resultList}">
                        <c:forEach var="result" items="${resultList}" varStatus="status">
                            <fmt:parseDate var="createdDateTime" value="${result.createdDateTime}" pattern="yyyy-MM-dd" type="both"/>
                            <tr>
                                <td><input type="checkbox" class="form-check-input" name="codeGroupCheck" value="${result.codeGroupId}"></td>
                                <th scope="row">${pageInfo.totalCount - ((pageInfo.pageNumber-1) * pageInfo.pageOffset + status.index)}</th>
                                <td><a href="/cms/code/code-group/view/${result.codeGroupId}">${result.codeGroupId}</a></td>
                                <td><a href="/cms/code/code-group/view/${result.codeGroupId}">${result.codeGroupLabel}</a></td>
                                <td>${result.codeGroupNote}</td>
                                <td><fmt:formatDate value="${createdDateTime}" pattern="yyyy-MM-dd"/></td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr class="text-center">
                            <td colspan="5">조회된 데이터가 존재하지 않습니다.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
            <jsp:include page="/WEB-INF/jsp/common/commonPagenation.jsp"/>

            <div class="form-btn-set text-end">
                <button type="button" class="btn btn-danger btn-lg" onclick="alert('미구현');">선택 삭제</button>
                <button type="button" class="btn btn-success btn-lg" data-bs-toggle="modal" data-bs-target="#codeGroupRegistModal">등록</button>
            </div>
        </div>

        <!-- Modal -->
        <div class="modal fade" id="codeGroupRegistModal" tabindex="-1" aria-labelledby="codeGroupRegistModalTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="codeGroupRegistModalTitle">코드그룹 등록</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form id="codeGroupForm" name="codeGroupForm" method="post" action="./save">
                            <input type="hidden" id="isCodeGroupIdChk" name="isCodeGroupIdChk" value="F">
                            <div class="row mb-3">
                                <label for="codeGroupLabel" class="col-sm-3 col-form-label">코드그룹 명</label>
                                <div class="col-sm-7">
                                    <input type="text" class="form-control-small" name="codeGroupLabel" value="" placeholder="코드그룹 명을 입력하세요." aria-label="코드그룹 명을 입력하세요." maxlength="100">
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="codeGroupId" class="col-sm-3 col-form-label">코드그룹 아이디</label>
                                <div class="col-sm-7">
                                    <input type="text" class="form-control-small" name="codeGroupId" value="" placeholder="코드그룹 아이디를 입력하세요." aria-label="코드그룹 아이디를 입력하세요." onchange="$('#isCodeGroupIdChk').val('F');" maxlength="100">
                                    <button type="button" class="btn-dark" id="codeGroupIdCheckEventButton" onclick="codeGroupIdCheckEvent();">중복체크</button>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="codeGroupId" class="col-sm-3 col-form-label">사용 여부</label>
                                <div class="col-sm-7">
                                    <label for="isUsed_Y"><input type="radio" class="form-check-input" id="isUsed_Y" name="isUsed" value="TRUE" placeholder="사용" aria-label="사용" checked>사용</label>
                                    <label for="isUsed_N"><input type="radio" class="form-check-input ms-1" id="isUsed_N" name="isUsed" value="FALSE" placeholder="미사용" aria-label="미사용">미사용</label>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="codeGroupId" class="col-sm-3 col-form-label">설명</label>
                                <div class="col-sm-7">
                                    <textarea class="textarea form-control" name="codeGroupNote" ></textarea>
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

