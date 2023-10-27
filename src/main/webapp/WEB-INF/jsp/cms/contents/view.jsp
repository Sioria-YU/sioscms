<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--<script src="/static/js/clipboard.min.js"></script>--%>
<script>
    const formSubmitEvent = () => {
        if ($("#title").val() === '') {
            alert("제목을 입력하세요.");
            $("#title").focus();
            return false;
        }

        $("#registForm").submit();
    }

    const fileSaveEvent = () => {
        if ($("#files").val() === '') {
            alert("업로드할 이미지 파일이 없습니다.");
            return false;
        }
        if (confirm("이미지 첨부를 저장하시겠습니까?")) {
            let form = $("#registForm")[0];
            let formData = new FormData(form);

            $.ajax({
                url: '/api/contents/save-attach-files',
                type: 'PUT',
                async: false,
                data: formData,
                contentType: false,
                processData: false,
                success: function (data) {
                    alert("등록되었습니다.");
                    $("#files").val('');

                    let appendHtml = '';

                    for (let attachFile of data) {
                        appendHtml += '<div class="block mb-1" id="attachFileWrap_' + attachFile.id + '">';
                        appendHtml += '<a href="#" class="me-1" onclick="attachFileDownload(\'' + attachFile.fileName + '\');"';
                        appendHtml += 'aria-label="첨부파일' + attachFile.id + ' 다운로드">' + attachFile.originFileName + '</a> ';
                        appendHtml += '<i class="bi bi-x-circle-fill"';
                        appendHtml += '     onclick="attachFileDelete(\'' + attachFile.fileName + '\', \'attachFileWrap_' + attachFile.id + '\');"';
                        appendHtml += '      aria-label="첨부파일' + attachFile.id + ' 삭제"></i> ';
                        appendHtml += '<span style="background-color: rgba(230,227,212,0.56); padding: 1px;">이미지 URL : /api/attach/get-image/' + attachFile.fileName + '</span> ';
                        appendHtml += '<button';
                        appendHtml += '         type="button"';
                        appendHtml += '         class="btn btn-outline-dark btn-sm"';
                        appendHtml += '          onclick="copyToClipboard(\'/api/attach/get-image/' + attachFile.fileName + '\')"';
                        appendHtml += '          aria-label="첨부파일' + attachFile.id + ' URL 복사하기"><i class="bi bi-files"></i>복사';
                        appendHtml += '</button>';
                        appendHtml += '</div>';
                        appendHtml += '\n';
                    }
                    $("#attachFilesArea").html(appendHtml);
                },
                error: function (request, status, error) {
                    console.error(error);
                    alert("오류가 발생하였습니다.");
                }
            });
        }
    }

    const onChangeUsed = (historyId) => {
        if (confirm("현재 작성중인 내용은 저장되지 않습니다.\n선택한 버전으로 적용하시겠습니까?")) {
            $.ajax({
                url: '/api/contents/change-version',
                type: 'PUT',
                async: false,
                data: {
                    id: ${result.id},
                    historyId: historyId
                },
                success: function (data) {
                    alert("변경되었습니다.");
                    location.reload();
                },
                error: function (request, status, error) {
                    console.error(error);
                    alert("오류가 발생하였습니다.");
                }
            });
        }
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

        <div class="container-fluid px-4">
            <div class="icon">
                <i class="bi bi-record-circle-fill"></i><h4 class="card-title">콘텐츠 조회</h4>
            </div>
            <form id="registForm" name="registForm" method="post" action="/cms/contents-manage/save-new-version">
                <input type="hidden" name="id" value="${result.id}">
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
                        <th class="table-title"><label for="contentsName">콘텐츠 파일명</label></th>
                        <td><input type="text" class="form-control" id="contentsName" name="contentsName"
                                   value="${result.contentsName}" aria-label="콘텐츠 파일명" placeholder="콘텐츠 파일명을 입력하세요."
                                   readonly>
                        </td>
                    </tr>
                    <tr>
                        <th class="table-title"><label for="files">이미지 첨부</label></th>
                        <td>
                            <input type="hidden" name="attachFileGroupId" value="${result.attachFileGroup.id}">
                            <div id="attachFilesArea">
                                <c:if test="${not empty result.attachFileGroup and not empty result.attachFileGroup.attachFileList}">
                                    <c:forEach var="attachfile" items="${result.attachFileGroup.attachFileList}"
                                               varStatus="status">
                                        <div class="block mb-1" id="attachFileWrap_${attachfile.id}">
                                            <a href="#" class="me-1"
                                               onclick="attachFileDownload('${attachfile.fileName}');"
                                               aria-label="첨부파일${attachfile.id} 다운로드">${attachfile.originFileName}</a>
                                            <i class="bi bi-x-circle-fill"
                                               onclick="attachFileDelete('${attachfile.fileName}', 'attachFileWrap_${attachfile.id}');"
                                               aria-label="첨부파일${attachfile.id} 삭제"></i>
                                            <span style="background-color: rgba(230,227,212,0.56); padding: 1px;">이미지 URL : /api/attach/get-image/${attachfile.fileName}</span>
                                            <button
                                                    type="button"
                                                    class="btn btn-outline-dark btn-sm"
                                                    onclick="copyToClipboard('/api/attach/get-image/${attachfile.fileName}')"
                                                    aria-label="첨부파일${attachfile.id} URL 복사하기"><i
                                                    class="bi bi-files"></i>복사
                                            </button>
                                        </div>
                                    </c:forEach>
                                </c:if>
                            </div>
                            <input type="file" class="form-control" id="files" name="files"
                                   accept="image/gif, image/jpeg, image/png" multiple>
                            <button type="button" class="form-control btn btn-success btn-lg mt-1"
                                    onclick="fileSaveEvent();">이미지 첨부 저장
                            </button>
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
            </form>
            <div class="form-btn-set text-center">
                <button type="button" class="btn btn-secondary btn-lg"
                        onclick="location.href='/cms/contents-manage/list';">취소
                </button>
                <button type="button" class="btn btn-success btn-lg" onclick="formSubmitEvent();">새 버전으로 저장</button>
                <button type="button" class="btn btn-lg btn-outline-dark"
                        onclick="alert('미구현');"><i class="bi bi-cast"></i> 미리보기
                </button>
            </div>
        </div>

        <div class="container-fluid px-4" style="margin-bottom: 100px;">
            <div class="icon">
                <i class="bi bi-record-circle-fill"></i><h4 class="card-title">히스토리 조회</h4>
            </div>

            <c:if test="${not empty contentsHistoryList}">
                <table class="table text-center">
                    <thead>
                    <tr>
                        <th scope="col">버전</th>
                        <th scope="col">작성일</th>
                        <th scope="col">사용여부</th>
                        <th scope="col">사용/미사용 전환</th>
                        <th scope="col">미리보기</th>
                        <th scope="col">비교하기</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="history" items="${contentsHistoryList}">
                        <tr>
                            <td>
                                Ver. ${history.version}
                            </td>
                            <td>
                                <fmt:parseDate var="createdDateTime" value="${history.createdDateTime}"
                                               pattern="yyyy-MM-dd"
                                               type="both"/>
                                <fmt:formatDate value="${createdDateTime}" pattern="yyyy-MM-dd"/>
                            </td>
                            <td>${history.isUsed? '사용':'미사용'}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${history.isUsed}">
                                        <span class="btn btn-danger btn-sm">현재버전</span>
                                    </c:when>
                                    <c:otherwise>
                                        <button type="button" class="btn btn-success btn-sm" onclick="onChangeUsed(${history.id});">사용</button>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <button type="button" class="btn btn-sm btn-outline-dark"
                                        onclick=""><i class="bi bi-cast"></i></button>
                            </td>
                            <td>
                                <button type="button" class="btn btn-sm btn-success"
                                        onclick=""><i class="bi bi-arrow-left-right"></i></button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </main>
</div>