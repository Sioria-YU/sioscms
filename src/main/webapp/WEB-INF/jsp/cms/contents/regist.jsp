<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script>
    const formSubmitEvent = () => {

        $("#registForm").submit();
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
            <i class="bi bi-record-circle-fill"></i><h4 class="card-title">콘텐츠 등록</h4>
        </div>

        <div class="container-fluid px-4">
            <form id="registForm" name="registForm" method="post" enctype="multipart/form-data" action="./save">
                <table class="table">
                    <colgroup>
                        <col style="width: 15%">
                        <col>
                    </colgroup>
                    <tbody>
                    <tr>
                        <th class="table-title"><label for="title">제목</label></th>
                        <td><input type="text" class="form-control" id="title" name="title" value="" aria-label="제목"
                                   placeholder="제목을 입력하세요."></td>
                    </tr>
                    <tr>
                        <th class="table-title"><label for="title">콘텐츠 파일명</label></th>
                        <td><input type="text" class="form-control" id="contentsName" name="contentsName" value=""
                                   aria-label="콘텐츠 파일명" placeholder="콘텐츠 파일명을 입력하세요."></td>
                    </tr>
                    <tr>
                        <th class="table-title"><label for="files">이미지 첨부</label></th>
                        <td>
                            <input type="file" class="form-control" id="files" name="files" multiple>
                        </td>
                    </tr>
                    <tr>
                        <th class="table-title"><label for="content">내용</label></th>
                        <td>
                            <textarea id="content" name="content" style="width: 100%" rows="15"></textarea>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <div class="form-btn-set text-center">
                    <button type="button" class="btn btn-secondary btn-lg" onclick="location.href='./list';">취소</button>
                    <button type="button" class="btn btn-success btn-lg" onclick="formSubmitEvent();">등록</button>
                </div>
            </form>
        </div>
    </main>
</div>