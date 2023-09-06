<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript" src="/static/se2/js/service/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/javascript" src="/static/tagify/tagify.js" charset="utf-8"></script>
<link type="text/css" rel="stylesheet" href="/static/tagify/tagify.css"/>
<script>
    const formSubmitEvent = () => {
        //에디터 내용을 textarea에 적용
        oEditors.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
        //입력 값 검증

        //해시태그
        if(tagify.value.length > 0){
            for(let i=0; i < tagify.value.length; i++){
                let tag = document.createElement("input");
                tag.setAttribute("type", "hidden");
                tag.setAttribute("name", "hashtagList");
                tag.setAttribute("value", tagify.value[i].value);
                $("#registForm").append(tag);
            }
        }

        <c:if test="${not empty result}">
            $("#registForm").attr("action", "./update");
        </c:if>

        $("#registForm").submit();
    }

    const attachFileDelete = (fileName, selector) => {
        if(confirm("삭제된 파일은 복구되지 않습니다.\n삭제하시겠습니까?")) {
            $.ajax({
                url: '/api/attach/delete',
                type: 'DELETE',
                async: false,
                data: {
                    fileName     : fileName,
                    deleteMode : 'D'
                },
                success: function (data) {
                    $("#"+selector).remove();
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
                <h1 class="mt-4">게시판 관리</h1>
                <nav>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="/cms/main"><i class="bi bi-house-door"></i></a></li>
                        <li class="breadcrumb-item">사이트 관리</li>
                        <li class="breadcrumb-item active">게시판 관리</li>
                    </ol>
                </nav>
            </div>
        </div>

        <div class="container-fluid px-4">
            <div class="icon">
                <i class="bi bi-record-circle-fill"></i><h4 class="card-title">게시글 등록</h4>
            </div>

            <form id="registForm" name="registForm" method="post" enctype="multipart/form-data" action="./save">
                <input type="hidden" name="boardMasterId" value="${param.boardMasterId}">
                <c:if test="${not empty result}">
                    <input type="hidden" name="id" value="${result.id}">
                </c:if>
                <table class="table">
                    <colgroup>
                        <col style="width: 15%">
                        <col>
                    </colgroup>
                    <tbody>
                        <tr>
                            <th class="table-title"><label for="title">제목</label></th>
                            <td><input type="text" class="form-control" id="title" name="title" value="${result.title}" aria-label="제목" placeholder="제목을 입력하세요."></td>
                        </tr>
                        <tr>
                            <th class="table-title"><label for="hashTagBoard">해시태그</label></th>
                            <td>
                                <input type="text" class="form-control" id="hashTagBoard" name="hashTagBoard" placeholder="#해시태그를 입력하세요.(최대 5개)"
                                    <c:if test="${not empty result.boardHashtagSet}">
                                        value="<c:forEach var="boardHashtag" items="${result.boardHashtagSet}" varStatus="status">
                                                    ${boardHashtag.hashtag.hashtagName}
                                                    ${!status.last? ',':''}
                                                </c:forEach>"
                                    </c:if>
                                >
                            </td>
                        </tr>
                        <tr>
                            <th class="table-title"><label for="files">첨부파일</label></th>
                            <td>
                                <c:if test="${not empty result.attachFileGroup and not empty result.attachFileGroup.attachFileList}">
                                    <input type="hidden" name="attachFileGroupId" value="${result.attachFileGroup.id}">
                                    <c:forEach var="attachfile" items="${result.attachFileGroup.attachFileList}" varStatus="status">
                                        <div class="block mb-1" id="attachFileWrap_${status.count}">
                                            <a href="#" class="me-1" onclick="attachFileDownload('${attachfile.fileName}');" aria-label="첨부파일${status.count} 다운로드">${attachfile.originFileName}</a>
                                            <i class="bi bi-x-circle-fill" onclick="attachFileDelete('${attachfile.fileName}', 'attachFileWrap_${status.count}');" aria-label="첨부파일${status.count} 삭제"></i>
                                        </div>
                                    </c:forEach>
                                </c:if>
                                <input type="file" class="form-control" id="files" name="files" multiple>
                            </td>
                        </tr>
                        <tr>
                            <th class="table-title"><label for="option1">옵션1</label></th>
                            <td><input type="text" class="form-control" id="option1" name="option1" value="${result.option1}" aria-label="옵션1" placeholder="옵션1을 입력하세요."></td>
                        </tr>
                        <tr>
                            <th class="table-title"><label for="option2">옵션2</label></th>
                            <td><input type="text" class="form-control" id="option2" name="option2" value="${result.option2}" aria-label="옵션2" placeholder="옵션2을 입력하세요."></td>
                        </tr>
                        <tr>
                            <th class="table-title"><label for="option3">옵션3</label></th>
                            <td><input type="text" class="form-control" id="option3" name="option3" value="${result.option3}" aria-label="옵션3" placeholder="옵션3을 입력하세요."></td>
                        </tr>
                        <tr>
                            <th class="table-title"><label for="option4">옵션4</label></th>
                            <td><input type="text" class="form-control" id="option4" name="option4" value="${result.option4}" aria-label="옵션4" placeholder="옵션4을 입력하세요."></td>
                        </tr>
                        <tr>
                            <th class="table-title"><label for="option5">옵션5</label></th>
                            <td><input type="text" class="form-control" id="option5" name="option5" value="${result.option5}" aria-label="옵션5" placeholder="옵션5을 입력하세요."></td>
                        </tr>
                        <tr>
                            <th class="table-title"><label for="content">내용</label></th>
                            <td>
                                <textarea id="content" name="content" style="width: 100%">${result.content}</textarea>
                                <script type="text/javascript">
                                    var oEditors = [];
                                    nhn.husky.EZCreator.createInIFrame({
                                        oAppRef: oEditors,
                                        elPlaceHolder: "content",
                                        sSkinURI: "/static/se2/SmartEditor2Skin.html",
                                        fCreator: "createSEditor2"
                                    });
                                </script>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div class="form-btn-set text-center">
                    <button type="button" class="btn btn-secondary btn-lg" onclick="location.href='./list?boardMasterId=${boardMasterId}';">취소</button>
                    <button type="button" class="btn btn-success btn-lg" onclick="formSubmitEvent();">${empty result? '등록':'수정'}</button>
                </div>
            </form>
        </div>
    </main>
</div>
<script>
    //region 해시태그 생성
    const tagInput = document.querySelector('input[name=hashTagBoard]');
    let tagify = new Tagify(tagInput, {
        trim : false,
        delimiters : ",| ",
        maxTags : 5
    });

    //태그 추가 이벤트
    tagify.on('add', function() {
        // console.log(tagify.value);
    });
    //endregion
</script>