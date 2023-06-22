<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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

    const userDeleteEvent = () => {
        if($("input[name=chk-items]:checked").length > 0) {
            let idList = [];
            $("input[name=chk-items]:checked").each((index,item) => {
                console.log(index,': => ', item.value);
                idList.push(parseInt(item.value));
            });

            if(idList.length > 0){
                if(confirm("삭제하시겠습니까?")) {
                    $.ajax({
                        type: 'PUT',
                        url: '/api/account/delete',
                        async: false,
                        data: {
                            idList:idList
                        },
                        success: function (data) {
                            if (data) {
                                alert("삭제되었습니다.");
                                location.reload();
                            } else {
                                alert("오류가 발생하였습니다.");
                                return false;
                            }
                        },
                        error: function (request, status, error) {
                            console.log(error);
                        }
                    });
                }else {
                    return false;
                }
            }else{
                console.log("id 목록 생성 실패");
            }
        }else{
            alert("삭제할 목록을 선택하세요.");
            return false;
        }
    }
</script>

<div id="layoutSidenav_content">
    <main>
        <div class="container-fluid px-4">
            <div class="pagetitle">
                <h1 class="mt-4">관리자 관리</h1>
                <nav>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="/cms/main"><i class="bi bi-house-door"></i></a></li>
                        <li class="breadcrumb-item">시스템관리</li>
                        <li class="breadcrumb-item active">관리자 관리</li>
                    </ol>
                </nav>
            </div>
        </div>
        <%--<div class="container-fluid px-4">
            <h1 class="mt-4">관리자 관리</h1>
            <ol class="breadcrumb mb-4">
                <li class="breadcrumb-item active">Administrator management</li>
            </ol>
        </div>--%>
        <div class="container-fluid px-4">
            <div class="search-box">
                <div class="search-box-title">
                    <h4>검색 영역</h4>
                </div>
                <div class="search-box-body">
                    <form id="searchForm" name="searchForm" action="./admin-list">
                        <input type="hidden" id="pageNumber" name="pageNumber" value="${empty param.pageNumber? 1:param.pageNumber}">
                        <input type="hidden" id="pageOffset" name="pageOffset" value="${empty param.pageOffset? 10:param.pageOffset}">
                        <input type="hidden" id="pageSize" name="pageSize" value="${empty param.pageSize? 5:param.pageSize}">
                        <div class="row mb-3">
                            <label for="userId" class="col-sm-1 col-form-label">아이디</label>
                            <div class="col-sm-5">
                                <input type="text" class="form-control" id="userId" name="userId" value="${param.userId}" placeholder="관리자 아이디를 입력하세요." aria-label="관리자 아이디를 입력하세요.">
                            </div>
                            <label for="name" class="col-sm-1 col-form-label">성명</label>
                            <div class="col-sm-5">
                                <input type="text" class="form-control" id="name" name="name" value="${param.name}" placeholder="관리자 성명을 입력하세요." aria-label="관리자 성명을 입력하세요.">
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
                <i class="bi bi-record-circle-fill"></i><h4 class="card-title">관리자 목록</h4>
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
                                <th><input type="checkbox" class="chk-items" name="chk-items" value="${result.id}"/></th>
                                <th scope="row">${pageInfo.totalCount - ((pageInfo.pageNumber-1) * pageInfo.pageOffset + status.index)}</th>
                                <td><a href="/cms/member/admin-view/${result.id}">${result.name}</a></td>
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
                <button type="button" class="btn btn-success btn-lg" onclick="javascript:location.href='./admin-regist';">등록</button>
                <button type="button" class="btn btn-danger btn-lg" onclick="userDeleteEvent();">삭제</button>
            </div>
        </div>
    </main>
</div>