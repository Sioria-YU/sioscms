<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script>
    const formSubmitEvent = () => {
        if($("#title").val() === ""){
            alert("콘텐츠 제목을 입력하세요");
            $("#title").focus();
            return false;
        }

        if($("#contentsName").val() === ""){
            alert("콘텐츠 파일명을 입력하세요");
            $("#contentsName").focus();
            return false;
        }

        if($("#isCheckedContentsName").val() === "F"){
            alert("콘텐츠 파일 명 중복 검사를 실행해 주세요.");
            $("#isCheckedContentsName").focus();
            return false;
        }

        $("#registForm").submit();
    }

    const onChangeContentsName = () => {
        $("#isCheckedContentsName").val('F');
        $("#contentsNameCheckButton").show();
    }

    const onCheckContentsName = () => {
        $.ajax({
            url: '/api/contents/contents-name-duplication-check',
            type: 'GET',
            async: false,
            data: {
                contentsName: $("#contentsName").val()
            },
            success: function (data) {
                if(data){
                    alert("사용 가능한 파일명입니다.");
                    $("#isCheckedContentsName").val('T');
                    $("#contentsNameCheckButton").hide();
                }else{
                    alert("이미 사용 중인 파일명입니다.");
                    $("#isCheckedContentsName").val('F');
                    $("#contentsNameCheckButton").show();
                }
            },
            error: function (request, status, error) {
                console.error(error);
                alert("오류가 발생하였습니다.");
            }
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

        <div class="container-fluid px-4">
            <div class="icon">
                <i class="bi bi-record-circle-fill"></i><h4 class="card-title">콘텐츠 등록</h4>
            </div>

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
                        <td>
                            <div class="row">
                            <div class="col-sm-5">
                            <input type="text" class="form-control" id="contentsName" name="contentsName" value="" onchange="onChangeContentsName()"
                                   aria-label="콘텐츠 파일명" placeholder="콘텐츠 파일명을 입력하세요.">
                            </div>
                            <div class="col-sm-2">
                                <input type="hidden" id="isCheckedContentsName" value="F">
                                <button type="button" class="btn btn-dark" id="contentsNameCheckButton" onclick="onCheckContentsName();">중복 검사</button>
                            </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th class="table-title"><label for="files">이미지 첨부</label></th>
                        <td>
                            <input type="file" class="form-control" id="files" name="files" accept="image/gif, image/jpeg, image/png" multiple>
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