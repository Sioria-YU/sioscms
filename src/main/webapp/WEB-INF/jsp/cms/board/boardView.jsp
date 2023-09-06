<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script>
    const moveUpdateEvent = () => {
        location.href='/cms/board/regist?boardMasterId=${result.boardMaster.id}&id=${result.id}';
    }

    const attachFileDownload = (fileName) => {
        window.open("/api/attach/download/"+fileName);
    }
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
                <div class="icon">
                    <i class="bi bi-record-circle-fill"></i><h4 class="card-title">게시글 상세</h4>
                </div>
                <table class="table">
                    <colgroup>
                        <col style="width: 15%">
                        <col>
                    </colgroup>
                    <tbody>
                        <tr>
                            <th class="table-title">제목</th>
                            <td>${result.title}</td>
                        </tr>
                        <tr>
                            <th class="table-title">해시태그</th>
                            <td>
                                <c:if test="${not empty result.boardHashtagSet}">
                                    <c:forEach var="boardHashtag" items="${result.boardHashtagSet}">
                                        <span class="hashtag-item">${boardHashtag.hashtag.hashtagName}</span>
                                    </c:forEach>
                                </c:if>
                            </td>
                        </tr>
                        <tr>
                            <th class="table-title">첨부파일</th>
                            <td>
                                <c:if test="${not empty result.attachFileGroup and not empty result.attachFileGroup.attachFileList}">
                                    <c:forEach var="attachfile" items="${result.attachFileGroup.attachFileList}" varStatus="index">
                                        <a href="#" onclick="attachFileDownload('${attachfile.fileName}');" aria-label="첨부파일1 다운로드">${attachfile.originFileName}</a>
                                        <c:if test="${!index.last}">
                                            <br>
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                            </td>
                        </tr>
                        <tr>
                            <th class="table-title">옵션1</th>
                            <td>${result.option1}</td>
                        </tr>
                        <tr>
                            <th class="table-title">옵션2</th>
                            <td>${result.option2}</td>
                        </tr>
                        <tr>
                            <th class="table-title">옵션3</th>
                            <td>${result.option3}</td>
                        </tr>
                        <tr>
                            <th class="table-title">옵션4</th>
                            <td>${result.option4}</td>
                        </tr>
                        <tr>
                            <th class="table-title">옵션5</th>
                            <td>${result.option5}</td>
                        </tr>
                        <tr>
                            <th class="table-title">내용</th>
                            <td>
                                ${result.content}
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div class="form-btn-set text-center">
                    <button type="button" class="btn btn-secondary btn-lg" onclick="location.href='/cms/board/list?boardMasterId=${result.boardMaster.id}';">목록</button>
                    <button type="button" class="btn btn-success btn-lg" onclick="moveUpdateEvent();">수정</button>
                </div>
            </div>
        </div>
    </main>
</div>