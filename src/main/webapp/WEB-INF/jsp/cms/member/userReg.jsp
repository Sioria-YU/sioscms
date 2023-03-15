<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="layoutSidenav_content">
    <main>
        <div class="container-fluid px-4">
            <div class="pagetitle">
                <h1 class="mt-4">사용자 관리</h1>
                <nav>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="/cms/main"><i class="bi bi-house-door"></i></a></li>
                        <li class="breadcrumb-item">시스템관리</li>
                        <li class="breadcrumb-item active">사용자 관리</li>
                    </ol>
                </nav>
            </div>
        </div>

        <div class="container-fluid px-4">
            <div class="icon">
                <i class="bi bi-record-circle-fill"></i><h4 class="card-title">사용자 등록</h4>
            </div>

            <div class="container-fluid px-4">
                <div class="col-lg-12 card">
                    <div class="card-body">
                        <form id="userRegistForm" name="userRegistForm" action="/cms/member/user-save" method="post" autocomplete="off" onsubmit="return formCheck();">
                            <div class="row mb-3">
                                <label for="userId" class="col-sm-1 col-form-label text-center">아이디</label>
                                <div class="col-sm-5">
                                    <input type="hidden" id="userIdCheckFlag" value="F"/>
                                    <input type="text" class="form-control-small" id="userId" name="userId" aria-label="아이디" onchange="$('#userIdCheckFlag').val('');" autocomplete="off" required/>
                                    <button type="button" class="btn btn-dark align-top" id="userIdCheckButton" onclick="userIdDuplicationCheck();">중복체크</button>
                                </div>
                                <label for="userPassword" class="col-sm-1 col-form-label text-center">비밀번호</label>
                                <div class="col-sm-5">
                                    <input type="password" class="form-control" id="userPassword" name="userPassword" aria-label="비밀번호" autocomplete="off" required/>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="name" class="col-sm-1 col-form-label text-center">성명</label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control" id="name" name="name" aria-label="성명" required/>
                                </div>
                                <label for="phone" class="col-sm-1 col-form-label text-center">연락처</label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control" id="phone" name="phone" aria-label="연락처" required/>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="name" class="col-sm-1 col-form-label text-center">성별</label>
                                <div class="col-sm-5">
                                    <select class="form-control" id="gender" name="gender" required>
                                        <option value="">선택</option>
                                        <option value="M">남성</option>
                                        <option value="F">여성</option>
                                    </select>
                                </div>
                                <label for="role" class="col-sm-1 col-form-label text-center">회원구분</label>
                                <div class="col-sm-5">
                                    <select class="form-control" id="role" name="role" readonly>
                                        <option value="USER">사용자</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-btn-set text-center">
                                <button type="submit" class="btn btn-primary">저장</button>
                                <a href="./user-list" class="btn btn-secondary">취소</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </main>
</div>

<script>
    const userIdDuplicationCheck = ()=>{
        let userId = $("#userId").val();

        $.ajax({
            type: 'get',
            url: '/api/account/id-check/'+userId,
            async: false,
            success: function(data){
                if(data) {
                    alert("사용 가능한 아이디입니다.");
                    $("#userIdCheckFlag").val('T');
                    $("#userPassword").focus();
                }else{
                    alert("이미 사용중인 아이디입니다.");
                    $("#userIdCheckFlag").val('F');
                    $("#userId").val('');
                    $("#userId").focus();
                }
            },
            error: function(request, status, error){
                console.log(error);
            }
        });
    }

    const formCheck = ()=>{
        let check = true;
        if($("#userIdCheckFlag").val() !== 'T'){
            alert("아이디 중복체크를 실행해 주세요.");
            $("#userIdCheckButton").focus();
            check = false;
        }

        return check && confirm("등록하시겠습니까?");
    }
</script>