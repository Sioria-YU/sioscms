<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

        <div class="icon">
            <i class="bi bi-record-circle-fill"></i><h4 class="card-title">관리자 등록</h4>
        </div>

        <div class="container-fluid px-4">
            <div class="col-lg-12 card">
                <div class="card-body">
                    <form id="adminRegistForm" name="adminRegistForm" action="/cms/member/admin-save" method="post">
                        <div class="row mb-3">
                            <label for="userId" class="col-sm-1 col-form-label text-center">아이디</label>
                            <div class="col-sm-5">
                                <input type="text" class="form-control-mid" id="userId" name="userId" aria-label="아이디" required/>
                                <button type="button" class="btn btn-dark align-top" onclick="">중복체크</button>
                            </div>
                            <label for="userPassword" class="col-sm-1 col-form-label text-center">비밀번호</label>
                            <div class="col-sm-5">
                                <input type="password" class="form-control" id="userPassword" name="userPassword" aria-label="비밀번호" required/>
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
                            <label for="role" class="col-sm-1 col-form-label text-center">관리자여부</label>
                            <div class="col-sm-5">
                                <select class="form-control" id="role" name="role" readonly>
                                    <option value="ADMIN">관리자</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-btn-set text-center">
                            <button type="submit" class="btn btn-primary">저장</button>
                            <a href="./admin-list" class="btn btn-secondary">취소</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </main>
</div>