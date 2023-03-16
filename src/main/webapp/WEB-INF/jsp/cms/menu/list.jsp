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
                                    <label for="menuId" class="col-sm-2 col-form-label">메뉴 번호</label>
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
    let moveOrderNum;
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
                'check_callback': (operation, node, node_parent, node_position, more) => {
                    /*
                    * operation : 동작 상태('create_node', 'rename_node', 'delete_node', 'move_node', 'copy_node' or 'edit')
                    * node : 선택된 노드 정
                    * node_parent : Drop 된 트리의 부모 노드 정보
                    * node_position : Drop 된 위치
                    * more : 기타 정보
                    */
                    // 트리에서 특정 노드를 Drag 하여 Drop 하는 시점
                    if (operation === "move_node" && more.ref === undefined) {
                        // console.log("move_node asdklaskdjaksldjaskljdklsajd");
                        /*console.log("node::::::::>>>>>> ", node);
                        console.log("node_parent::::::::>>>>>> ", node_parent);
                        console.log("node_position::::::::>>>>>> ", node_position);
                        console.log("more::::::::>>>>>> ", more);*/

                        /* move_node event가 수행되어 모델 객체가 변경되기 전에 이동할 위치의 정렬 번호를 가져온다!
                         * dnd event에서 순서가 위로 올라갈 경우에는 position이 0부터 계산되어 나오고,
                         * 아래로 내려갈 경우 +1 값으로 전달되어오므로 indexOutOfException이 발생하므로
                         * node_position-1에 해당하는 자식 객체를 불러 비교 후 정렬 값을 보정함
                         */

                        //하위메뉴가 없는 노드의 하위로 들어갈 경우
                        if(more.origin._model.data[node_parent.id].children.length === 0){
                            moveOrderNum = node_parent.original.orderNum +1;
                        }else {
                            //인덱스 유효 범위를 초과하지 않기 위해 예외처리
                            let position = node_position;
                            if (node_position <= 0) {
                                position = 0;
                            } else if (node_position >= more.origin._model.data[node_parent.id].children.length) {
                                position--;
                            }
                            let moveIndexId = more.origin._model.data[node_parent.id].children[position];
                            let item = more.origin._model.data[moveIndexId];
                            moveOrderNum = item.original.orderNum;
                            //아래로 드래그 이동했을 경우 위치가 +1 되기 때문에 감소처리
                            if(item.original.orderNum > node.original.orderNum && node_position < more.origin._model.data[node_parent.id].children.length){
                                moveOrderNum--;
                            }

                            //부모는 바뀌고, 순서는 그대로인 경우 예외처리(아래에서 위로 깊이만 달라진 경우)
                            if(node.parent !== node_parent && node_position === more.origin._model.data[node_parent.id].children.length && item.original.orderNum+1 === node.original.orderNum){
                                moveOrderNum = node.original.orderNum;
                            }//부모가 바뀌고, 순서도 바뀌지만, 바뀐 부모의 마지막 위치로 이동할 경우
                            else if(node.parent !== node_parent && node_position === more.origin._model.data[node_parent.id].children.length){
                                moveOrderNum = item.original.orderNum+1;
                            }
                        }

                        // console.log("moveOrderNum Set :::::>>>> ",moveOrderNum);
                    }
                    return true;
                },
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
        }).on("move_node.jstree", function (event, data) {
            // console.log(data); //node
            // $('#menu-tree-contents').jstree("refresh");
            // console.log("refresh after :::::: ",data.instance._model.data[1].children_d);
            // console.log("update data ::::::::: >>>>>>> ", data.node.id, data.parent, moveOrderNum);
            updateOrder(data.node.id, data.parent, moveOrderNum);
            moveOrderNum = 999; //정렬 변수 임시값으로 변경
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

    const menuMoveEvent = (menuOrderType) => {
        let menuId = $("#menuId").val();
        if(!menuId){
            alert("이동할 메뉴를 선택하세요.");
            return false;
        }

        $.ajax({
            type: 'PUT',
            url: '/cms/api/menu/update-updown-order',
            async: false,
            data: {
                menuId: menuId,
                menuOrderType: menuOrderType
            },
            success: function (data) {
                if (data) {
                    alert("정상 처리 되었습니다.");
                    $('#menu-tree-contents').jstree(true).settings.core.data = converToJsTreeData(getMenus());
                    $('#menu-tree-contents').jstree("refresh");
                } else {
                    alert("오류가 발생하였습니다.");
                    return false;
                }
            },
            error: function (request, status, error) {
                console.log(error);
            }
        });
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
            menuMoveEvent('UP');
        });
        $(".arrow-down").on('click',function() {
            menuMoveEvent('DOWN');
        });
    });
</script>