<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
    const passwordChangeEvent = () => {
        if($("#userPwd").val() === ''){
            alert("비밀번호를 입력하세요.");
            $("#userPwd").focus();
            return false;
        }

        if($("#userPwdCheck").val() === ''){
            alert("비밀번호 확인을 입력하세요.");
            $("#userPwdCheck").focus();
            return false;
        }

        if($("#userPwd").val() != $("#userPwdCheck").val()){
            alert("비밀번호가 일치하지 않습니다.");
            $("#userPwdCheck").val('');
            $("#userPwdCheck").focus();
            return false;
        }

        if('${result.userId}' === ''){
            alert("계정 정보가 잘못되었습니다.\n새로고침 후 다시 이용해주세요.");
            return false;
        }

        if(confirm("비밀번호를 변경하시겠습니까?")){
            $.ajax({
                url: '/api/account/password-change',
                type: 'PUT',
                async: false,
                data: {
                    userId: '${result.userId}',
                    userPwd: $("#userPwd").val()
                },
                success: function (data) {
                    if (!!data) {
                        alert("정상 처리 되었습니다.");
                        location.reload();
                    } else {
                        alert("오류가 발생하였습니다.");
                    }
                },
                error: function (request, status, error) {
                    console.error(error);
                    alert("오류가 발생하였습니다.");
                }
            });
        }
    }

    const formCheck = ()=>{
        return confirm("수정하시겠습니까?");
    }
</script>

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
                        <form id="userRegistForm" name="userRegistForm" action="/cms/member/user-update" method="post" autocomplete="off" onsubmit="return formCheck();">
                            <div class="row mb-3">
                                <label for="userId" class="col-sm-1 col-form-label text-center">아이디</label>
                                <div class="col-sm-5">
                                    <input type="hidden" name="id" value="${result.id}"/>
                                    <input type="text" class="form-control" id="userId" name="userId" value="${result.userId}" aria-label="아이디" readonly/>
                                </div>
                                <label for="passwordChangeButton" class="col-sm-1 col-form-label text-center">비밀번호</label>
                                <div class="col-sm-5">
                                    <button type="button" class="btn btn-dark align-top" id="passwordChangeButton" data-bs-toggle="modal" data-bs-target="#passwordChangeModal">비밀번호 변경</button>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="name" class="col-sm-1 col-form-label text-center">성명</label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control" id="name" name="name" value="${result.name}" aria-label="성명" required/>
                                </div>
                                <label for="phone" class="col-sm-1 col-form-label text-center">연락처</label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control" id="phone" name="phone" value="${result.phone}" aria-label="연락처" required/>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="gender" class="col-sm-1 col-form-label text-center">성별</label>
                                <div class="col-sm-5">
                                    <select class="form-control" id="gender" name="gender" required>
                                        <option value="">선택</option>
                                        <option value="M" ${result.gender eq 'M'? 'selected':''}>남성</option>
                                        <option value="F" ${result.gender eq 'F'? 'selected':''}>여성</option>
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
                                <button type="submit" class="btn btn-primary">수정</button>
                                <a href="/cms/member/user-list" class="btn btn-secondary">취소</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </main>
</div>

<!-- Modal -->
<div class="modal fade" id="passwordChangeModal" tabindex="-1" aria-labelledby="passwordChangeModalTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="passwordChangeModalTitle">비밀번호 변경</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="passwordChangeForm" name="passwordChangeForm">
                    <div class="row mb-3">
                        <label for="userPwd" class="col-sm-3 col-form-label">비밀번호</label>
                        <div class="col-sm-9">
                            <input type="password" class="form-control-small" id="userPwd" name="userPwd" value="" placeholder="비밀번호를 입력하세요." aria-label="비밀번호를 입력하세요." maxlength="100" autocomplete="off">
                        </div>
                    </div>
                    <div class="row mb-3">
                        <label for="userPwdCheck" class="col-sm-3 col-form-label">비밀번호 확인</label>
                        <div class="col-sm-9">
                            <input type="password" class="form-control-small" id="userPwdCheck" name="userPwdCheck" value="" placeholder="비밀번호를 한번 더 입력하세요." aria-label="비밀번호를 한번 더 입력하세요." maxlength="100" autocomplete="off">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                <button type="button" class="btn btn-success" onclick="passwordChangeEvent();">변경</button>
            </div>
        </div>
    </div>
</div>