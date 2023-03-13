<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="layoutSidenav_nav">
    <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
        <div class="sb-sidenav-menu" id="sb-sidenav-menu">
            <%--<div class="nav">
                <div class="sb-sidenav-menu-heading">시스템관리</div>
                <a class="nav-link" href="/cms/main">
                    <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                    대시보드
                </a>
                <a class="nav-link" href="/cms/menu/list">
                    <div class="sb-nav-link-icon"><i class="fas fa-stream"></i></div>
                    메뉴 관리
                </a>
                <a class="nav-link" href="/cms/member/admin-list">
                    <div class="sb-nav-link-icon"><i class="fas fa-address-card"></i></div>
                    관리자 관리
                </a>
                <a class="nav-link" href="/cms/member/user-list">
                    <div class="sb-nav-link-icon"><i class="far fa-address-card"></i></div>
                    사용자 관리
                </a>
                <div class="sb-sidenav-menu-heading">Interface</div>
                <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapseLayouts" aria-expanded="false" aria-controls="collapseLayouts">
                    <div class="sb-nav-link-icon"><i class="fas fa-columns"></i></div>
                    Layouts
                    <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                </a>
                <div class="collapse" id="collapseLayouts" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordion">
                    <nav class="sb-sidenav-menu-nested nav">
                        <a class="nav-link" href="#">Static Navigation</a>
                        <a class="nav-link" href="#">Light Sidenav</a>
                    </nav>
                </div>
                <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapsePages" aria-expanded="false" aria-controls="collapsePages">
                    <div class="sb-nav-link-icon"><i class="fas fa-book-open"></i></div>
                    Pages
                    <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                </a>
                <div class="collapse" id="collapsePages" aria-labelledby="headingTwo" data-bs-parent="#sidenavAccordion">
                    <nav class="sb-sidenav-menu-nested nav accordion" id="sidenavAccordionPages">
                        <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#pagesCollapseAuth" aria-expanded="false" aria-controls="pagesCollapseAuth">
                            회원
                            <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                        </a>
                        <div class="collapse" id="pagesCollapseAuth" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordionPages">
                            <nav class="sb-sidenav-menu-nested nav">
                                <a class="nav-link" href="/cms/auth/login">로그인</a>
                                <a class="nav-link" href="/site/member/join/member-join">회원가입</a>
                                <a class="nav-link" href="#">비밀번호 찾기</a>
                            </nav>
                        </div>
                        <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#pagesCollapseError" aria-expanded="false" aria-controls="pagesCollapseError">
                            Error
                            <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                        </a>
                        <div class="collapse" id="pagesCollapseError" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordionPages">
                            <nav class="sb-sidenav-menu-nested nav">
                                <a class="nav-link" href="#">401 Page</a>
                                <a class="nav-link" href="#">404 Page</a>
                                <a class="nav-link" href="#">500 Page</a>
                            </nav>
                        </div>
                    </nav>
                </div>
                <div class="sb-sidenav-menu-heading">Addons</div>
                <a class="nav-link" href="charts.html">
                    <div class="sb-nav-link-icon"><i class="fas fa-chart-area"></i></div>
                    Charts
                </a>
                <a class="nav-link" href="tables.html">
                    <div class="sb-nav-link-icon"><i class="fas fa-table"></i></div>
                    Tables
                </a>
            </div>--%>
        </div>
        <div class="sb-sidenav-footer">
            <div class="small">Logged in as:</div>
            Start Bootstrap
        </div>
    </nav>
</div>
<script>
    $(function(){
        $.ajaxSetup({async: false});
        $.post("/cms/api/menu/list",{isRoot:false},function (data){
            const firstHtml =   " <div class=\"nav\">";
            const lastHtml =    " </div>";
            let menuHtml = "";

            if(!!data?.length > 0){
                console.log(data);
                //1depth
                let depthLevel1 = data.filter(i => i.upperMenu.id === 1);
                depthLevel1.forEach(i => {
                    let depthLevel2 = data.filter(j => j.upperMenu.id === i.id);
                    if(depthLevel2.length > 0){
                        menuHtml += "<div class=\"sb-sidenav-menu-heading\">" + i.menuName + "</div>";
                    }else{
                        menuHtml += "<a class=\"nav-link\" href=\"" + i.menuLink + "\">";
                        menuHtml += "    <div class=\"sb-nav-link-icon\"><i class=\"fas fa-stream\"></i></div>";
                        menuHtml += i.menuName;
                        menuHtml += "</a>";
                    }
                    //2depth
                    depthLevel2.forEach(j => {
                        let depthLevel3 = data.filter(k => k.upperMenu.id === j.id);
                        if(depthLevel3.length > 0){
                            menuHtml += "<a class=\"nav-link collapsed\" href=\"#\" data-bs-toggle=\"collapse\" data-bs-target=\"#collapse_" + j.id + "\" aria-expanded=\"false\" aria-controls=\"collapse_" + j.id + "\">";
                            menuHtml += "<div class=\"sb-nav-link-icon\"><i class=\"fas fa-columns\"></i></div>";
                            menuHtml += j.menuName;
                            menuHtml += "<div class=\"sb-sidenav-collapse-arrow\"><i class=\"fas fa-angle-down\"></i></div>";
                            menuHtml += "</a>";

                            //3depth
                            menuHtml += "<div class=\"collapse\" id=\"#collapse_" + j.id + "\" aria-labelledby=\"headingOne\" data-bs-parent=\"#sidenavAccordion\">";
                            menuHtml += "    <nav class=\"sb-sidenav-menu-nested nav\">";
                            depthLevel3.forEach(k => {
                                menuHtml += "        <a class=\"nav-link\" href=\"" + k.menuLink + "\">" + k.menuName + "</a>";
                            });
                            menuHtml += "    </nav>";
                            menuHtml += "</div>";
                        }else{
                            menuHtml += "<a class=\"nav-link\" href=\"" + j.menuLink + "\">";
                            menuHtml += "    <div class=\"sb-nav-link-icon\"><i class=\"fas fa-stream\"></i></div>";
                            menuHtml += j.menuName;
                            menuHtml += "</a>";
                        }
                    });
                });
            }
            console.log(firstHtml + menuHtml + lastHtml);
            $("#sb-sidenav-menu").html(firstHtml + menuHtml + lastHtml);
        })
    });
</script>