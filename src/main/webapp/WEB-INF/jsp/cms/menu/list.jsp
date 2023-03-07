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
                        <li class="breadcrumb-item active">메뉴 관리</li>
                    </ol>
                </nav>
            </div>
        </div>

        <div class="container-fluid px-4">
            <div class="row">
                <div class="col-lg-5">
                    <div class="icon">
                        <i class="bi bi-record-circle-fill"></i><h4 class="card-title">메뉴 관리</h4>
                    </div>
                    <div class="btn-group mb-1">
                        <button type="button" class="btn btn-outline-success btn-sm treeopen"><i class="bi bi-plus-square-fill"></i> 펼치기</button>
                        <button type="button" class="btn btn-outline-danger btn-sm ms-1 treeclose"><i class="bi bi-dash-square-fill"></i> 접기</button>
                    </div>
                    <div class="col-md-8 card menu-card">
                        <div class="card-body" id="menu-tree-contents"></div>
                    </div>
                </div>
                <div class="col-lg-7">
                    <div class="icon">
                        <i class="bi bi-record-circle-fill"></i><h4 class="card-title">메뉴 상세</h4>
                    </div>
                    <div class="btn-group mb-1">
                        <button type="button" class="btn btn-outline-success ms-1" onclick="appendedRootMenu();"><i class="bi bi-folder-plus"></i> 최상위 메뉴 추가</button>
                        <button type="button" class="btn btn-outline-primary ms-1" onclick="appendedLowerMenu();"><i class="bi bi-file-earmark-plus"></i> 하위 메뉴 추가</button>
                    </div>
                    <div class="col-md-8 card">
                        <div class="card-body">
                            <form id="menuEditForm" name="menuEditForm" action="" method="post">
                                <input type="hidden" id="upperMenuId" name="upperMenuId" value="">
                                <input type="hidden" name="isRoot" value="false">
                                <div class="row mb-1">
                                    <label for="menuName" class="col-sm-2 col-form-label">메뉴 번호</label>
                                    <div class="col-sm-8">
                                        <input type="text" class="form-control" id="menuId" name="menuId" aria-label="메뉴번호" readonly/>
                                    </div>
                                </div>
                                <div class="row mb-1">
                                    <label for="menuName" class="col-sm-2 col-form-label">메뉴 명</label>
                                    <div class="col-sm-8">
                                        <input type="text" class="form-control" id="menuName" name="menuName" placeholder="메뉴 명을 입력하세요." aria-label="메뉴 명을 입력하세요."/>
                                    </div>
                                </div>
                                <div class="row mb-1">
                                    <label for="menuType" class="col-sm-2 col-form-label">메뉴 타입</label>
                                    <div class="col-sm-8">
                                        <select class="form-select" id="menuType" name="menuType" aria-label="선택">
                                            <option value="">선택</option>
                                            <option value="LINK">링크</option>
                                            <option value="BOARD">게시판</option>
                                            <option value="PROGRAM">프로그램</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="row mb-1">
                                    <label for="menuLink" class="col-sm-2 col-form-label">링크 주소</label>
                                    <div class="col-sm-8">
                                        <input type="text" class="form-control" id="menuLink" name="menuLink" placeholder="Link URL을 입력하세요." aria-label="Link URL을 입력하세요."/>
                                    </div>
                                </div>
                                <div class="row mb-1">
                                    <label for="isUsed1" class="col-sm-2 col-form-label">사용 여부</label>
                                    <div class="col-sm-8 align-self-center">
                                        <div class="col-sm-10">
                                                <input class="form-check-input" type="radio" name="isUsed" id="isUsed1" value="true" checked="">
                                                <label class="form-check-label" for="isUsed1">
                                                    사용
                                                </label>
                                                <input class="form-check-input" type="radio" name="isUsed" id="isUsed2" value="false">
                                                <label class="form-check-label" for="isUsed2">
                                                    미사용
                                                </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-btn-set text-center">
                                    <button type="button" class="btn btn-success" id="saveButton" onclick="saveMenu()">저장</button>
                                    <button type="button" class="btn btn-success" id="modifyButton" onclick="saveMenu()" style="display: none;">수정</button>
                                    <button type="button" class="btn btn-danger" id="deleteButton" onclick="" style="display: none;">삭제</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
</div>
<link rel="stylesheet" href="/static/js/jstree/3.3.10/themes/default/style.min.css" />
<script src="/static/js/jstree/3.3.10/jstree.min.js"></script>
<script>
    $('#menu-tree-contents').jstree({
        'core' : {
            'data' : [
                { "id" : "ajson1", "parent" : "#", "text" : "Simple root node" },
                { "id" : "ajson2", "parent" : "#", "text" : "Root node 2" },
                { "id" : "ajson201", "parent" : "ajson2", "text" : "Child 1" },
                { "id" : "ajson202", "parent" : "ajson2", "text" : "Child 2" },
                { "id" : "ajson203", "parent" : "ajson2", "text" : "Child 3" },
                { "id" : "ajson204", "parent" : "ajson2", "text" : "Child 4" },
                { "id" : "ajson205", "parent" : "ajson2", "text" : "Child 5" },
                { "id" : "ajson3", "parent" : "#", "text" : "Root node 3" },
                { "id" : "ajson4", "parent" : "#", "text" : "Root node 4" },
                { "id" : "ajson401", "parent" : "ajson4", "text" : "Child 1" },
                { "id" : "ajson402", "parent" : "ajson4", "text" : "Child 2" },
                { "id" : "ajson403", "parent" : "ajson4", "text" : "Child 3" },
                { "id" : "ajson404", "parent" : "ajson4", "text" : "Child 4" },
                { "id" : "ajson405", "parent" : "ajson4", "text" : "Child 5" },
                { "id" : "ajson5", "parent" : "#", "text" : "Root node 5" },
                { "id" : "ajson501", "parent" : "ajson5", "text" : "Child 1" },
                { "id" : "ajson50101", "parent" : "ajson501", "text" : "Child 1-1" },
                { "id" : "ajson50102", "parent" : "ajson501", "text" : "Child 1-2" },
                { "id" : "ajson50103", "parent" : "ajson501", "text" : "Child 1-3" },
                { "id" : "ajson50104", "parent" : "ajson501", "text" : "Child 1-4" },
                { "id" : "ajson50105", "parent" : "ajson501", "text" : "Child 1-5" },
                { "id" : "ajson502", "parent" : "ajson5", "text" : "Child 2" },
                { "id" : "ajson503", "parent" : "ajson5", "text" : "Child 3" },
                { "id" : "ajson504", "parent" : "ajson5", "text" : "Child 4" },
                { "id" : "ajson505", "parent" : "ajson5", "text" : "Child 5" },
                { "id" : "ajson6", "parent" : "#", "text" : "Root node 6" },
                { "id" : "ajson601", "parent" : "ajson6", "text" : "Child 1" },
                { "id" : "ajson602", "parent" : "ajson6", "text" : "Child 2" },
                { "id" : "ajson603", "parent" : "ajson6", "text" : "Child 3" },
                { "id" : "ajson604", "parent" : "ajson6", "text" : "Child 4" },
                { "id" : "ajson605", "parent" : "ajson6", "text" : "Child 5" },
                { "id" : "ajson7", "parent" : "#", "text" : "Root node 7" },
                { "id" : "ajson8", "parent" : "#", "text" : "Root node 8" },
                { "id" : "ajson9", "parent" : "#", "text" : "Root node 9" },
                { "id" : "ajson10", "parent" : "#", "text" : "Root node 10" },
            ]
        }
    });

    const appendedRootMenu = () =>{
        <%-- 최초 생성 시 루트 메뉴가 없다면 insert 처리 해야함. --%>
        document.getElementById("upperMenuId").value = '1';
        clearMenuEditFormData();
    }

    const appendedLowerMenu = () =>{
        document.getElementById("upperMenuId").value = ''; //메뉴 트리에서 선택한 값으로 변경
        clearMenuEditFormData();
    }

    const clearMenuEditFormData = () =>{
        document.getElementById("menuId").value = '';
        document.getElementById("menuName").value = '';
        document.getElementById("menuType").value = '';
        document.getElementById("linkUrl").value = '';
        document.getElementById("isUsed1").checked = true;
        document.getElementById("isUsed2").checked = false;
    }

    const saveMenu = () =>{
        let form = document.getElementById("menuEditForm");
        form.action = "./save-menu";
        form.submit();
    }

    const deleteMenu = () =>{
        let form = document.getElementById("menuEditForm");
        form.action = "./delete-menu";
        form.submit();
    }

    $(function (){
        $(".treeopen").on('click',function() {
            $("#menu-tree-contents").jstree('open_all');
        });
        $(".treeclose").on('click',function() {
            $("#menu-tree-contents").jstree('close_all');
        });
    });
</script>