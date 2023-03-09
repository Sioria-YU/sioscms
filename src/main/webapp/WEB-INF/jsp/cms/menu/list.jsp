<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="layoutSidenav_content">
    <main>
        <div class="container-fluid px-4">
            <div class="pagetitle">
                <h1 class="mt-4">메뉴 관리</h1>
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
                    <div class="col-md-8 btn-group justify-content-between">
                        <div class="btn-group mb-1">
                            <button type="button" class="btn btn-outline-success btn-sm treeopen"><i class="bi bi-plus-square-fill"></i> 펼치기</button>
                            <button type="button" class="btn btn-outline-danger btn-sm ms-1 treeclose"><i class="bi bi-dash-square-fill"></i> 접기</button>
                        </div>
                        <div class="btn-group mb-1">
                            <button type="button" class="btn btn-outline-dark btn-sm ms-1 arrow-up"><i class="bi bi bi-arrow-up-square-fill"></i> 위로</button>
                            <button type="button" class="btn btn-outline-dark btn-sm ms-1 arrow-down"><i class="bi bi-arrow-down-square-fill"></i> 아래로</button>
                        </div>
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

    const converToJsTreeData = (menuList) => {
        let treeData = [];
        // console.log(menuList);
        menuList.map((v)=>{
            let node = {
                "id":v.id
                , "parent":!!v.upperMenu? v.upperMenu?.id:'#'
                , "text":v.menuName
                , "type":!v.upperMenu? 'root':'default'
                , "menuType":v.menuType
                , "menuLink":v.menuLink
                , "isUsed":v.isUsed
                , "orderNum":v.orderNum
            }
            treeData.push(node);
        });
        return treeData;
    }

    const initJstree = (menuList) => {
        $('#menu-tree-contents').jstree({
            'core': {
                'check_callback': true,
                'data':converToJsTreeData(menuList)
            },
            'themes' : {
                "responsive": false
            },
            "types" : {
                "#" : {
                    "max_children" : 1
                },
                "default" : {
                    "icon" : "fa fa-folder text-primary",
                },
                "file" : {
                    "icon" : "fa fa-file text-primary"
                }
            },
            "plugins": [
                // "checkbox",
                // "contextmenu",
                "dnd",
                // "massload",
                // "search",
                // "sort",
                "state",
                "types",
                // "unique",
                // "wholerow",
                // "changed",
                // "conditionalselect"
            ]
        }).on("select_node.jstree", function (event, data) {
            if(data?.node?.original?.id === 1){
                //루트메뉴 수정 불가능하도록 변경
                $("#menuName").prop("readOnly", true);
                $("#menuType").prop("disabled", true);
                $("#menuLink").prop("readOnly", true);
                $("#isUsed1").prop("disabled", true);
                $("#isUsed2").prop("disabled", true);
                $("#saveButton").hide();
                $("#modifyButton").hide();
                $("#deleteButton").hide();
            }else{
                // 노드가 선택된 뒤 처리할 이벤트
                $("#menuName").prop("readOnly", false);
                $("#menuType").prop("disabled", false);
                $("#menuLink").prop("readOnly", false);
                $("#isUsed1").prop("disabled", false);
                $("#isUsed2").prop("disabled", false);
                $("#saveButton").hide();
                $("#modifyButton").show();
                $("#deleteButton").show();
            }

            $("#upperMenuId").val(data?.node?.original?.parent);   //상위 메뉴 번호
            $("#menuId").val(data?.node?.original?.id);   //메뉴번호
            $("#menuName").val(data?.node?.original?.text);   //메뉴명
            $("#menuType").val(data?.node?.original?.menuType);   //메뉴타입
            $("#menuLink").val(data?.node?.original?.menuLink);   //링크주소
            if(data?.node?.original?.isUsed){
                document.getElementById("isUsed1").checked = true;
                document.getElementById("isUsed2").checked = false;
            }else{
                document.getElementById("isUsed1").checked = false;
                document.getElementById("isUsed2").checked = true;
            }
        }).on("node_move.jstree", function (event, data) {
            console.log(event); //event
            console.log(data); //node
            let oldItemId = data.old_instance._model.data[data.parent].children_d[data.position]; //이동하기 전 위치에 있던 객체ID
            let oldNode = data.old_instance._model.data[oldItemId]; //이동하기 전 위치에 있던 객체 정보
            let orderNum = oldNode.original.orderNum; //이동할 위치의 객체의 정렬번호(교체할 번호)

            console.log("oldItemId ::::::: >>>>>> ", oldItemId);
            console.log("oldNode ::::::: >>>>>> ", oldNode);
            console.log("orderNum ::::::: >>>>>> ", orderNum);
            // updateOrder(data.node.id, data.parent, orderNum);
        })
        ;
    }

    const getMenus = () =>{
        let returnData;
        $.ajaxSetup({async: false});
        $.post("/cms/api/menu/list",{},function (data){
            returnData = data;
        })
        return returnData;
    }

    const appendedRootMenu = () =>{
        <%-- 최초 생성 시 루트 메뉴가 없다면 insert 처리 해야함. --%>
        //선택 노드 해제
        $("#menu-tree-contents").jstree("deselect_all");
        document.getElementById("upperMenuId").value = 1;
        clearMenuEditFormData();
        $("#saveButton").show();
        $("#modifyButton").hide();
        $("#deleteButton").hide();
    }

    const appendedLowerMenu = () =>{
        let upperMenuId = $("#menuId").val();
        if(!!upperMenuId) {
            document.getElementById("upperMenuId").value = upperMenuId; //메뉴 트리에서 선택한 값으로 변경
            clearMenuEditFormData();
            $("#saveButton").show();
            $("#modifyButton").hide();
            $("#deleteButton").hide();
        }else{
            alert("메뉴를 선택해주세요.");
            return false;
        }
    }

    const clearMenuEditFormData = () =>{
        document.getElementById("menuId").value = '';
        document.getElementById("menuName").value = '';
        document.getElementById("menuType").value = '';
        document.getElementById("menuLink").value = '';
        document.getElementById("isUsed1").checked = true;
        document.getElementById("isUsed2").checked = false;
    }

    const saveMenu = () =>{
        let formData = new FormData(document.getElementById("menuEditForm"));
        /*const obj = {};
        formData.forEach((v,k) => obj[k] = v);
        console.log(obj);*/
        let url = "";
        if($("#menuId").val() !== ''){
            url = "/cms/api/menu/update";
        }else{
            url = "/cms/api/menu/save";
        }

        fetch(url,
            {
                method: 'PUT',
                cache: 'no-cache',
                body: formData
            }
        ).then((response) =>{
            if(response.ok){
                alert("정상 처리 되었습니다.");
                $('#menu-tree-contents').jstree(true).settings.core.data = converToJsTreeData(getMenus());
                $('#menu-tree-contents').jstree("refresh");
            }else{
                console.log(data);
                alert("오류");
            }
        }).catch((error) => console.error(error));
    }

    const deleteMenu = () =>{
        let form = document.getElementById("menuEditForm");
        form.action = "./delete-menu";
        form.submit();
    }

    /**
     *
     * @param id {number}
     * @param orderNum {number}
     */
    const updateOrder = (id, upperMenuId, orderNum) => {
        if(!id || !orderNum){
            alert("이동할 메뉴를 선택하세요.");
            return false;
        }
        let formData = new FormData()
        formData.append('id', id);
        formData.append('upperMenuId', upperMenuId);
        formData.append('orderNum', orderNum);

        let url = "/cms/api/menu/update-order";
        fetch(url,
            {
                method: 'PUT',
                cache: 'no-cache',
                body: formData
            }
        ).then((response) =>{
            if(response.ok){
                alert("정상 처리 되었습니다.");
                $('#menu-tree-contents').jstree(true).settings.core.data = converToJsTreeData(getMenus());
                $('#menu-tree-contents').jstree("refresh");
            }else{
                console.log(data);
                alert("오류");
            }
        }).catch((error) => console.error(error));
    }

    $(function (){
        initJstree(getMenus());

        $(".treeopen").on('click',function() {
            $("#menu-tree-contents").jstree('open_all');
        });
        $(".treeclose").on('click',function() {
            $("#menu-tree-contents").jstree('close_all');
        });
        $(".arrow-up").on('click',function() {
            alert("위로");
        });
        $(".arrow-down").on('click',function() {
            alert("아래로");
        });
    });
</script>