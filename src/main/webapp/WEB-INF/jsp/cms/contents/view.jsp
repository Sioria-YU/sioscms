<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--<script src="/static/js/clipboard.min.js"></script>--%>
<script>
    const formSubmitEvent = () => {

        $("#registForm").submit();
    }

    const attachFileDelete = (fileName, selector) => {
        if (confirm("삭제된 파일은 복구되지 않습니다.\n삭제하시겠습니까?")) {
            $.ajax({
                url: '/api/attach/delete',
                type: 'DELETE',
                async: false,
                data: {
                    fileName: fileName,
                    deleteMode: 'D'
                },
                success: function (data) {
                    $("#" + selector).remove();
                    alert("삭제 처리되었습니다.");
                },
                error: function (request, status, error) {
                    console.error(error);
                    alert("오류가 발생하였습니다.");
                }
            });
        }
    }

    //클립보드 복사
    const copyToClipboard = (text) => {
        navigator.clipboard.writeText(text).then(() => {
            alert("복사되었습니다.");
        });
    }
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
        </div>

        <div class="icon">
            <i class="bi bi-record-circle-fill"></i><h4 class="card-title">콘텐츠 조회</h4>
        </div>
        <div class="container-fluid px-4">
            <form id="registForm" name="registForm" method="post" enctype="multipart/form-data" action="./update">
                <table class="table">
                    <colgroup>
                        <col style="width: 15%">
                        <col>
                    </colgroup>
                    <tbody>
                    <tr>
                        <th class="table-title"><label for="title">제목</label></th>
                        <td><input type="text" class="form-control" id="title" name="title" value="${result.title}"
                                   aria-label="제목" placeholder="제목을 입력하세요."></td>
                    </tr>
                    <tr>
                        <th class="table-title"><label for="title">콘텐츠 파일명</label></th>
                        <td><input type="text" class="form-control" id="contentsName" name="contentsName"
                                   value="${result.contentsName}" aria-label="콘텐츠 파일명" placeholder="콘텐츠 파일명을 입력하세요.">
                        </td>
                    </tr>
                    <tr>
                        <th class="table-title"><label for="files">이미지 첨부</label></th>
                        <td>
                            <c:if test="${not empty result.attachFileGroup and not empty result.attachFileGroup.attachFileList}">
                                <input type="hidden" name="attachFileGroupId" value="${result.attachFileGroup.id}">
                                <c:forEach var="attachfile" items="${result.attachFileGroup.attachFileList}"
                                           varStatus="status">
                                    <div class="block mb-1" id="attachFileWrap_${status.count}">
                                        <a href="#" class="me-1" onclick="attachFileDownload('${attachfile.fileName}');"
                                           aria-label="첨부파일${status.count} 다운로드">${attachfile.originFileName}</a>
                                        <i class="bi bi-x-circle-fill"
                                           onclick="attachFileDelete('${attachfile.fileName}', 'attachFileWrap_${status.count}');"
                                           aria-label="첨부파일${status.count} 삭제"></i>
                                        <span style="background-color: rgba(230,227,212,0.56); padding: 1px;">이미지 URL : /api/attach/get-image/${attachfile.fileName}</span>
                                        <button
                                                type="button"
                                                class="btn btn-outline-dark btn-sm"
                                                onclick="copyToClipboard('/api/attach/get-image/${attachfile.fileName}')"
                                                aria-label="첨부파일${status.count} URL 복사하기"><i
                                                class="bi bi-files"></i>복사
                                        </button>
                                    </div>
                                </c:forEach>
                            </c:if>
                            <input type="file" class="form-control" id="files" name="files" multiple>
                        </td>
                    </tr>
                    <tr>
                        <th class="table-title"><label for="content">내용</label></th>
                        <td>
                            <textarea id="content" name="content" style="width: 100%"
                                      rows="15">${result.content}</textarea>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <div class="form-btn-set text-center">
                    <button type="button" class="btn btn-secondary btn-lg"
                            onclick="location.href='/cms/contents-manage/list';">취소
                    </button>
                    <button type="button" class="btn btn-success btn-lg" onclick="formSubmitEvent();">수정</button>
                    <button type="button" class="btn btn-success btn-lg" onclick="alert('미구현');">미리보기</button>
                </div>
            </form>
        </div>

        <div class="icon">
            <i class="bi bi-record-circle-fill"></i><h4 class="card-title">히스토리 조회</h4>
        </div>
        <div class="container-fluid px-4">
            히스토리 버전, 적용여부, 생성일
        </div>
    </main>
</div>