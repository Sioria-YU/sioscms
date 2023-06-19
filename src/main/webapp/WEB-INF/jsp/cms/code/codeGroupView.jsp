<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script>
    const codeIdCheckEvent = () => {
        let formData = new FormData(document.getElementById("codeForm"));

        $.post("/cms/api/code/duplication-check"
            , {
                codeGroupId: '${codeGroupInfo.codeGroupId}',
                codeId: formData.get("codeId")
            }, function(data){
            if(!!data){
                $("#isCodeChk").val("T");
                alert("사용할 수 있는 코드입니다.");
            }else{
                alert("사용할 수 없는 코드입니다.");
                $("#codeId").val('');
                $("#codeId").focus();
            }
        });
    }

    const codeGroupUpdateEvent = () => {
        if($("#codeGroupLabel").val() == ''){
            alert("코드그룹 명을 입력하세요.");
            $("#codeGroupLabel").focus();
            return false;
        }

        if(confirm("수정하시겠습니까?")) {
            $.ajax({
                url: '/cms/api/code-group/update',
                type: 'PUT',
                async: false,
                data: {
                    codeGroupId     : '${codeGroupInfo.codeGroupId}',
                    codeGroupLabel  : $("#codeGroupLabel").val(),
                    isUsed          : $("#isUsed_Y").is(":checked"),
                    codeGroupNote   : $("#codeGroupNote").val()
                },
                success: function (data) {
                    if (!!data) {
                        alert("정상 처리 되었습니다.");
                        location.reload();
                    } else {
                        alert("오류가 발생하였습니다.");
                        return false;
                    }
                },
                error: function (request, status, error) {
                    console.error(error);
                    alert("오류가 발생하였습니다.");
                }
            });
        }
    }

    const codeDeleteEvent = () => {
        if($(".checkItem:checked").length > 0) {
            let codeIdList = [];

            $(".checkItem:checked").each(function () {
                codeIdList.push($(this).val());
            });

            if(confirm("삭제하시겠습니까?")){
                $.ajax({
                    url: '/cms/api/code/mulitple-delete',
                    type: 'DELETE',
                    async: false,
                    data: {
                        codeGroupId: '${codeGroupInfo.codeGroupId}',
                        codeIdList: codeIdList
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
        }else{
            alert("선택된 목록이 없습니다.");
        }
    }

    const modifyModalOpenEvent = (codeLabel, codeId, option1, option2, option3, option4, isUsed) => {
        let codeModifyModal = new bootstrap.Modal(document.getElementById('codeModifyModal'), {
            keyboard: false
        });

        $("#modifyCodeLabel").val(codeLabel);
        $("#modifyCodeId").val(codeId);
        $("#modifyOption1").val(option1);
        $("#modifyOption2").val(option2);
        $("#modifyOption3").val(option3);
        $("#modifyOption4").val(option4);

        isUsed? $("#modifyCodeIsUsed_Y").prop("checked", true) : $("#modifyCodeIsUsed_N").prop("checked", true);

        codeModifyModal.show();
    }

    const formCheck = () => {
        if($("#isCodeChk").val() === 'F'){
            alert("코드그룹 아이디 중복체크를 실행해 주세요.");
            $("#codeCheckEventButton").focus();
            return false;
        }

        if($("#codeLabel").val() == ''){
            alert("코드 명을 입력하세요.");
            $("#codeLabel").focus();
            return false;
        }

        if($("#codeId").val() == ''){
            alert("코드를 입력하세요.");
            $("#codeId").focus();
            return false;
        }

        if(confirm("등록하시겠습니까?")){
            let formData = new FormData(document.getElementById("codeForm"));

            fetch("/cms/api/code/save",
                {
                    method: 'POST',
                    cache: 'no-cache',
                    body: formData
                }
            ).then((response) =>{
                return response.json();
            }).then((data) => {
                if(!!data){
                    alert("정상 처리 되었습니다.");
                    location.reload();
                }
            }).catch((error) => {
                <%-- return data가 null일 경우 response.json() 메서드에서 error를 발생시켜서 여기로 옴 --%>
                console.error(error);
                alert("오류가 발생하였습니다.");
            });
        }
    }

    const modifyFormCheck = () => {
        let formData = new FormData(document.getElementById("codeModifyForm"));

        if(formData.get("codeGroupId") === ''){
            alert("코드그룹 아이디가 잘못 입력 되었습니다.\n새로고침 후 다시 이용해주세요.");
            return false;
        }

        if(formData.get("codeId") === ''){
            alert("코드 값이 잘못 입력 되었습니다.\n새로고침 후 다시 이용해주세요.");
            return false;
        }

        if(formData.get("codeLabel") === ''){
            alert("코드 명을 입력하세요.");
            $("#modifyCodeLabel").focus();
            return false;
        }

        if(formData.get("isUsed") === ''){
            alert("사용 여부를 선택하세요");
            $("#modifyCodeIsUsed_Y").focus();
            return false;
        }

        if(confirm("수정하시겠습니까?")){
            fetch("/cms/api/code/update",
                {
                    method: 'PUT',
                    cache: 'no-cache',
                    body: formData
                }
            ).then((response) =>{
                return response.json();
            }).then((data) => {
                if(!!data){
                    alert("정상 처리 되었습니다.");
                    location.reload();
                }
            }).catch((error) => {
                <%-- return data가 null일 경우 response.json() 메서드에서 error를 발생시켜서 여기로 옴 --%>
                console.error(error);
                alert("오류가 발생하였습니다.");
            });
        }
    }

    const orderSwapEvent = (codeId1, codeId2) => {
        $.ajax({
            url: '/cms/api/code/order-swap',
            type: 'PUT',
            async: false,
            data: {
                codeGroupId: '${codeGroupInfo.codeGroupId}',
                codeId1: codeId1,
                codeId2: codeId2
            },
            success: function (data) {
                if (!!data) {
                    alert("코드 순서가 변경되었습니다.");
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

    $(function (){
        $("#checkAll").on('click',function(){
            if($("#checkAll").is(":checked")){
                $(".checkItem").prop("checked", true);
            }else{
                $(".checkItem").prop("checked", false);
            }
        });

        $(".checkItem").on('click',function(){
            if(!$(this).is(":checked")){
                $("#checkAll").prop("checked", false);
            }
        });

        function handleDragStart(e) {
            this.style.opacity = '0.4';

            dragSrcEl = this;

            e.dataTransfer.effectAllowed = 'move';
            e.dataTransfer.setData('text/html', this.innerHTML);
        }

        function handleDragEnd(e) {
            this.style.opacity = '1';

            moveItems.forEach(function (item) {
                item.classList.remove('over');
            });
        }

        function handleDragOver(e) {
            if (e.preventDefault) {
                e.preventDefault();
            }
            return false;
        }

        function handleDragEnter(e) {
            this.classList.add('over');
        }

        function handleDragLeave(e) {
            this.classList.remove('over');
        }

        function handleDrop(e) {
            e.stopPropagation(); // stops the browser from redirecting.

            let thisCode = null;
            let dragSrcElCode = null;

            if (dragSrcEl !== this) {
                dragSrcEl.innerHTML = this.innerHTML;
                this.innerHTML = e.dataTransfer.getData('text/html');

                thisCode = $(this).find(".checkItem").val();
                dragSrcElCode = $(dragSrcEl).find(".checkItem").val();

                orderSwapEvent(thisCode, dragSrcElCode);
            }

            return false;
        }

        let moveItems = document.querySelectorAll('.move-item');
        moveItems.forEach(function(item) {
            item.addEventListener('dragstart', handleDragStart);
            item.addEventListener('dragover', handleDragOver);
            item.addEventListener('dragenter', handleDragEnter);
            item.addEventListener('dragleave', handleDragLeave);
            item.addEventListener('dragend', handleDragEnd);
            item.addEventListener('drop', handleDrop);
        });
    });
</script>

<style>
    .move-item.over {
        border: 3px dotted #ffc107;
    }
</style>

<div id="layoutSidenav_content">
    <main>
        <div class="container-fluid px-4">
            <div class="pagetitle">
                <h1 class="mt-4">공통코드 관리</h1>
                <nav>
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="/cms/main"><i class="bi bi-house-door"></i></a></li>
                        <li class="breadcrumb-item">시스템관리</li>
                        <li class="breadcrumb-item active">공통코드 관리</li>
                    </ol>
                </nav>
            </div>
        </div>

        <div class="container-fluid px-4">
            <div class="icon">
                <i class="bi bi-record-circle-fill"></i><h4 class="card-title">공통코드 정보</h4>
            </div>

            <div class="row mb-3">
                <label for="codeGroupLabel" class="col-sm-2 col-form-label">코드그룹 명</label>
                <div class="col-sm-5">
                    <input type="text" class="form-control" id="codeGroupLabel" name="codeGroupLabel" value="${codeGroupInfo.codeGroupLabel}" placeholder="코드그룹 명을 입력하세요." aria-label="코드그룹 명을 입력하세요.">
                </div>
            </div>
            <div class="row mb-3">
                <label class="col-sm-2 col-form-label">코드그룹 아이디</label>
                <div class="col-sm-5">
                    <label class="col-form-label">${codeGroupInfo.codeGroupId}</label>
                </div>
            </div>
            <div class="row mb-3">
                <label for="isUsed_Y" class="col-sm-2 col-form-label">사용 여부</label>
                <div class="col-sm-5">
                    <label for="isUsed_Y" class="col-form-label"><input type="radio" class="form-check-input me-1" id="isUsed_Y" name="isUsed" value="TRUE" placeholder="사용" aria-label="사용" ${codeGroupInfo.isUsed eq 'TRUE'? 'checked':''}>사용</label>
                    <label for="isUsed_N" class="col-form-label ms-1"><input type="radio" class="form-check-input me-1" id="isUsed_N" name="isUsed" value="FALSE" placeholder="미사용" aria-label="미사용" ${codeGroupInfo.isUsed eq 'FALSE'? 'checked':''}>미사용</label>
                </div>
            </div>
            <div class="row mb-3">
                <label for="codeGroupNote" class="col-sm-2 col-form-label">설명</label>
                <div class="col-sm-5">
                    <textarea class="textarea form-control" id="codeGroupNote" name="codeGroupNote" >${codeGroupInfo.codeGroupNote}</textarea>
                </div>
            </div>
            <div class="row mb-3">
                <div class="col-sm-7 form-btn-set text-end">
                    <button type="button" class="btn btn-success btn-lg" onclick="codeGroupUpdateEvent();">수정</button>
                    <button type="button" class="btn btn-dark btn-lg" onclick="location.href='/cms/code/code-group/list'">뒤로 가기</button>
                </div>
            </div>

            <div class="icon mt-3">
                <i class="bi bi-record-circle-fill"></i><h4 class="card-title">코드 목록</h4>
            </div>
            <table class="table text-center">
                <thead>
                <tr>
                    <th scope="col"></th>
                    <th scope="col"><input type="checkbox" class="form-check-input" id="checkAll"></th>
                    <th scope="col">순번</th>
                    <th scope="col">코드명</th>
                    <th scope="col">코드</th>
                    <th scope="col">옵션1</th>
                    <th scope="col">옵션2</th>
                    <th scope="col">옵션3</th>
                    <th scope="col">옵션4</th>
                    <th scope="col">사용여부</th>
                    <th scope="col">작성자</th>
                    <th scope="col">작성일</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${not empty codeList}">
                        <c:forEach var="result" items="${codeList}" varStatus="status">
                            <fmt:parseDate var="createdDateTime" value="${result.createdDateTime}" pattern="yyyy-MM-dd" type="both"/>
                            <tr class="move-item" draggable="true">
                                <td><i class="bi bi-list toggle-sidebar-btn"></i></td>
                                <td><input type="checkbox" class="form-check-input checkItem" name="codeCheck" value="${result.codePk.codeId}"></td>
                                <th scope="row">${fn:length(codeList) - status.index}</th>
                                <td>
                                    <a href="#"
                                       onclick="modifyModalOpenEvent('${result.codeLabel}', '${result.codePk.codeId}', '${result.option1}', '${result.option2}' ,'${result.option3}' ,'${result.option4}' ,${result.isUsed});">
                                        ${result.codeLabel}
                                    </a>
                                </td>
                                <td>${result.codePk.codeId}</td>
                                <td>${result.option1}</td>
                                <td>${result.option2}</td>
                                <td>${result.option3}</td>
                                <td>${result.option4}</td>
                                <td>${result.isUsed eq 'TRUE'? '사용':'미사용'}</td>
                                <td>${result.createdBy}</td>
                                <td><fmt:formatDate value="${createdDateTime}" pattern="yyyy-MM-dd"/></td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr class="text-center">
                            <td colspan="11">조회된 데이터가 존재하지 않습니다.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>

            <div class="form-btn-set text-end">
                <button type="button" class="btn btn-danger btn-lg" onclick="codeDeleteEvent();">선택 삭제</button>
                <button type="button" class="btn btn-success btn-lg" data-bs-toggle="modal" data-bs-target="#codeRegistModal">추가</button>
            </div>
        </div>

        <!-- Modal -->
        <div class="modal fade" id="codeRegistModal" tabindex="-1" aria-labelledby="codeRegistModalTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="codeRegistModalTitle">코드그룹 등록</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form id="codeForm" name="codeForm" method="post" action="">
                            <input type="hidden" name="codeGroupId" value="${codeGroupInfo.codeGroupId}">
                            <input type="hidden" id="isCodeChk" name="isCodeChk" value="F">

                            <div class="row mb-3">
                                <label for="codeLabel" class="col-sm-3 col-form-label">코드 명</label>
                                <div class="col-sm-7">
                                    <input type="text" class="form-control-small" id="codeLabel" name="codeLabel" value="" placeholder="코드 명을 입력하세요." aria-label="코드 명을 입력하세요." maxlength="100">
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="codeId" class="col-sm-3 col-form-label">코드</label>
                                <div class="col-sm-7">
                                    <input type="text" class="form-control-small" id="codeId" name="codeId" value="" placeholder="코드를 입력하세요." aria-label="코드를 입력하세요." onchange="$('#isCodeChk').val('F');" maxlength="100">
                                    <button type="button" class="btn-dark" id="codeIdCheckEventButton" onclick="codeIdCheckEvent();">중복체크</button>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="option1" class="col-sm-3 col-form-label">옵션1</label>
                                <div class="col-sm-7">
                                    <input type="text" class="form-control-small" id="option1" name="option1" value="" placeholder="옵션1을 입력하세요." aria-label="옵션1을 입력하세요." maxlength="100">
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="option2" class="col-sm-3 col-form-label">옵션2</label>
                                <div class="col-sm-7">
                                    <input type="text" class="form-control-small" id="option2" name="option2" value="" placeholder="옵션2를 입력하세요." aria-label="옵션2를 입력하세요." maxlength="100">
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="option3" class="col-sm-3 col-form-label">옵션3</label>
                                <div class="col-sm-7">
                                    <input type="text" class="form-control-small" id="option3" name="option3" value="" placeholder="옵션3을 입력하세요." aria-label="옵션3을 입력하세요." maxlength="100">
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="option4" class="col-sm-3 col-form-label">옵션4</label>
                                <div class="col-sm-7">
                                    <input type="text" class="form-control-small" id="option4" name="option4" value="" placeholder="옵션4를 입력하세요." aria-label="옵션4를 입력하세요." maxlength="100">
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="codeIsUsed_Y" class="col-sm-3 col-form-label">사용 여부</label>
                                <div class="col-sm-7">
                                    <label for="codeIsUsed_Y" class="col-form-label"><input type="radio" class="form-check-input me-1" id="codeIsUsed_Y" name="isUsed" value="TRUE" placeholder="사용" aria-label="사용" checked>사용</label>
                                    <label for="codeIsUsed_N" class="col-form-label ms-1"><input type="radio" class="form-check-input me-1" id="codeIsUsed_N" name="isUsed" value="FALSE" placeholder="미사용" aria-label="미사용">미사용</label>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                        <button type="button" class="btn btn-success" onclick="formCheck();">등록</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="codeModifyModal" tabindex="-1" aria-labelledby="codeModifyModalTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="codeModifyModalTitle">코드그룹 등록</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form id="codeModifyForm" name="codeModifyForm" method="post" action="">
                            <input type="hidden" name="codeGroupId" value="${codeGroupInfo.codeGroupId}">

                            <div class="row mb-3">
                                <label for="codeLabel" class="col-sm-3 col-form-label">코드 명</label>
                                <div class="col-sm-7">
                                    <input type="text" class="form-control-small" id="modifyCodeLabel" name="codeLabel" value="" placeholder="코드 명을 입력하세요." aria-label="코드 명을 입력하세요." maxlength="100">
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="codeId" class="col-sm-3 col-form-label">코드</label>
                                <div class="col-sm-7">
                                    <input type="text" class="form-control form-control-small" id="modifyCodeId" name="codeId" value="" maxlength="100" readonly>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="option1" class="col-sm-3 col-form-label">옵션1</label>
                                <div class="col-sm-7">
                                    <input type="text" class="form-control-small" id="modifyOption1" name="option1" value="" placeholder="옵션1을 입력하세요." aria-label="옵션1을 입력하세요." maxlength="100">
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="option2" class="col-sm-3 col-form-label">옵션2</label>
                                <div class="col-sm-7">
                                    <input type="text" class="form-control-small" id="modifyOption2" name="option2" value="" placeholder="옵션2를 입력하세요." aria-label="옵션2를 입력하세요." maxlength="100">
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="option3" class="col-sm-3 col-form-label">옵션3</label>
                                <div class="col-sm-7">
                                    <input type="text" class="form-control-small" id="modifyOption3" name="option3" value="" placeholder="옵션3을 입력하세요." aria-label="옵션3을 입력하세요." maxlength="100">
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="option4" class="col-sm-3 col-form-label">옵션4</label>
                                <div class="col-sm-7">
                                    <input type="text" class="form-control-small" id="modifyOption4" name="option4" value="" placeholder="옵션4를 입력하세요." aria-label="옵션4를 입력하세요." maxlength="100">
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="modifyCodeIsUsed_Y" class="col-sm-3 col-form-label">사용 여부</label>
                                <div class="col-sm-7">
                                    <label for="modifyCodeIsUsed_Y" class="col-form-label"><input type="radio" class="form-check-input me-1" id="modifyCodeIsUsed_Y" name="isUsed" value="TRUE" placeholder="사용" aria-label="사용" checked>사용</label>
                                    <label for="modifyCodeIsUsed_N" class="col-form-label ms-1"><input type="radio" class="form-check-input me-1" id="modifyCodeIsUsed_N" name="isUsed" value="FALSE" placeholder="미사용" aria-label="미사용">미사용</label>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                        <button type="button" class="btn btn-success" onclick="modifyFormCheck();">수정</button>
                    </div>
                </div>
            </div>
        </div>
    </main>
</div>