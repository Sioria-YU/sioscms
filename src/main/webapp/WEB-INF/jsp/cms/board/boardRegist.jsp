<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript" src="/static/se2/js/service/HuskyEZCreator.js" charset="utf-8"></script>
<script>
    const formSubmitEvent = () => {
        //에디터 내용을 textarea에 적용
        oEditors.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
        //입력 값 검증

        $("#registForm").submit();
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
                <table class="table">
                    <tr>
                        <th class="table-title"><label for="title">제목</label></th>
                        <td><input type="text" class="form-control" id="title" name="title" aria-label="제목" placeholder="제목을 입력하세요."></td>
                    </tr>
                    <tr>
                        <th class="table-title"><label for="title">해시태그</label></th>
                        <td><input type="text" class="form-control" id="hashTagBoard" placeholder="#해시태그를 입력하세요."></td>
                    </tr>
                    <tr>
                        <th class="table-title"><label for="files">첨부파일</label></th>
                        <td><input type="file" class="form-control" id="files" name="files" multiple></td>
                    </tr>
                    <tr>
                        <th class="table-title"><label for="option1">옵션1</label></th>
                        <td><input type="text" class="form-control" id="option1" name="option1" aria-label="옵션1" placeholder="옵션1을 입력하세요."></td>
                    </tr>
                    <tr>
                        <th class="table-title"><label for="option2">옵션2</label></th>
                        <td><input type="text" class="form-control" id="option2" name="option2" aria-label="옵션2" placeholder="옵션2을 입력하세요."></td>
                    </tr>
                    <tr>
                        <th class="table-title"><label for="option3">옵션3</label></th>
                        <td><input type="text" class="form-control" id="option3" name="option3" aria-label="옵션3" placeholder="옵션3을 입력하세요."></td>
                    </tr>
                    <tr>
                        <th class="table-title"><label for="option4">옵션4</label></th>
                        <td><input type="text" class="form-control" id="option4" name="option4" aria-label="옵션4" placeholder="옵션4을 입력하세요."></td>
                    </tr>
                    <tr>
                        <th class="table-title"><label for="option5">옵션5</label></th>
                        <td><input type="text" class="form-control" id="option5" name="option5" aria-label="옵션5" placeholder="옵션5을 입력하세요."></td>
                    </tr>
                    <tr>
                        <th class="table-title"><label for="content">내용</label></th>
                        <td>
                            <textarea id="content" name="content" style="width: 100%"></textarea>
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
                </table>
                <div class="form-btn-set text-center">
                    <button type="button" class="btn btn-secondary btn-lg" onclick="location.href='./list?boardMasterId=${param.boardMasterId}';">취소</button>
                    <button type="button" class="btn btn-success btn-lg" onclick="formSubmitEvent();">등록</button>
                </div>
            </form>
        </div>
    </main>
</div>