<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 공통 페이징 --%>
<c:if test="${not empty pageInfo}">
    <c:set var="startPageNumber" value="${(((pageInfo.pageNumber - 1) / pageInfo.pageSize) - ((pageInfo.pageNumber - 1) / pageInfo.pageSize % 1)) * pageInfo.pageSize + 1}"/>
    <c:set var="endPageNumber" value="${startPageNumber + pageInfo.pageSize -1}"/>
    <c:if test="${endPageNumber > pageInfo.totalPageSize}">
        <c:set var="endPageNumber" value="${pageInfo.totalPageSize}"/>
    </c:if>

    <nav aria-label="Page navigation">
        <ul class="pagination">
            <c:if test="${pageInfo.isPrev eq true}">
            <li class="page-item">
                <button type="button" class="page-link" onclick="pageMoveEvent(1)" aria-label="처음">
                    <span aria-hidden="true">«</span>
                </button>
            </li>
            <li class="page-item">
                <button type="button" class="page-link" onclick="pageMoveEvent(${startPageNumber - 10})" aria-label="이전">
                    <span aria-hidden="true"><</span>
                </button>
            </li>
            </c:if>
            <c:forEach var="i" begin="${startPageNumber}" end="${endPageNumber}" step="1">
                <c:if test="${i eq pageInfo.pageNumber}">
                    <li class="page-item"><button type="button" class="page-link">${i}</button></li>
                </c:if>
                <c:if test="${i ne pageInfo.pageNumber}">
                    <li class="page-item"><button type="button" class="page-link" onclick="pageMoveEvent(${i})">${i}</button></li>
                </c:if>
            </c:forEach>
            <c:if test="${pageInfo.isNext eq true}">
            <li class="page-item">
                <button type="button" class="page-link" onclick="pageMoveEvent(${endPageNumber + 1})" aria-label="다음">
                    <span aria-hidden="true">></span>
                </button>
            </li>
            <li class="page-item">
                <button type="button" class="page-link" onclick="pageMoveEvent(${pageInfo.totalPageSize})" aria-label="마지막">
                    <span aria-hidden="true">»</span>
                </button>
            </li>
            </c:if>
        </ul>
    </nav>

    <script>
        const pageMoveEvent = (pageNumber)=>{
            $("#pageNumber").val(pageNumber);
            $("#searchForm").submit();
        }
    </script>
</c:if>