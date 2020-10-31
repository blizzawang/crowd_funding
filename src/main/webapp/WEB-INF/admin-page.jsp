<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="zh-CN">
<%@include file="/WEB-INF/inculde-head.jsp" %>
<script src="jquery/jquery-2.1.1.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<script src="script/docs.min.js"></script>
<body>
<%@include file="/WEB-INF/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form action="admin/get/page" method="post" class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input name="keyword" class="form-control has-success" type="text"
                                       placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="submit" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i
                            class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <a href="admin/to/add/page" class="btn btn-primary" style="float:right;">
                        <i class="glyphicon glyphicon-plus"></i> 新增
                    </a>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox"></th>
                                <th>账号</th>
                                <th>名称</th>
                                <th>邮箱地址</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:if test="${empty requestScope.pageInfo.list}">
                                <tr>
                                    <td colspan="6" align="center">抱歉!没有查询到您要的数据!</td>
                                </tr>
                            </c:if>
                            <c:if test="${!empty requestScope.pageInfo.list}">
                                <c:forEach items="${requestScope.pageInfo.list}" var="admin" varStatus="myStatus">
                                    <tr>
                                        <td>${myStatus.count}</td>
                                        <td><input type="checkbox"></td>
                                        <td>${admin.loginAcct}</td>
                                        <td>${admin.userName}</td>
                                        <td>${admin.email}</td>
                                        <td>
                                            <a class="btn btn-success btn-xs">
                                                <i class=" glyphicon glyphicon-check"></i>
                                            </a>
                                            <a class="btn btn-primary btn-xs">
                                                <i class=" glyphicon glyphicon-pencil"></i>
                                            </a>
                                            <a href="admin/remove/${admin.id}/${requestScope.pageInfo.pageNum}/${param.keyword}"
                                               class="btn btn-danger btn-xs">
                                                <i class=" glyphicon glyphicon-remove"></i>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                            </tbody>
                            <tfoot>
                            <tr>
                                <%-- 分页导航条 --%>
                                <td colspan="6" align="center">
                                    <ul class="pagination">
                                        <%-- 点击跳转至首页 --%>
                                        <li>
                                            <a href="admin/get/page?pageNum=1&keyword=${param.keyword}">首页</a>
                                        </li>
                                        <%-- 是否含有上一页 --%>
                                        <c:if test="${pageInfo.hasPreviousPage}">
                                            <%-- 上一页按钮 --%>
                                            <li>
                                                <a href="admin/get/page?pageNum=${pageInfo.pageNum - 1}&keyword=${param.keyword}">上一页</a>
                                            </li>
                                        </c:if>
                                        <c:if test="${!pageInfo.hasPreviousPage}">
                                            <%-- 禁用上一页按钮 --%>
                                            <li class="disabled">
                                                <a href="javascript:void(0);">上一页</a>
                                            </li>
                                        </c:if>
                                        <%-- 共有navigatepageNums页 --%>
                                        <c:forEach items="${pageInfo.navigatepageNums}" var="pn">
                                            <%-- 若页码处于当前页，则激活按钮状态 --%>
                                            <c:if test="${pn == pageInfo.pageNum}">
                                                <li class="active"><a href="#">${pn}</a></li>
                                            </c:if>
                                            <c:if test="${pn != pageInfo.pageNum}">
                                                <%-- 点击页码，跳转至对应页数 --%>
                                                <li>
                                                    <a href="admin/get/page?pageNum=${pn}&keyword=${param.keyword}">${pn}</a>
                                                </li>
                                            </c:if>
                                        </c:forEach>
                                        <%-- 是否含有下一页 --%>
                                        <c:if test="${pageInfo.hasNextPage}">
                                            <%-- 显示下一页按钮 --%>
                                            <li>
                                                <a href="admin/get/page?pageNum=${pageInfo.pageNum + 1}&keyword=${param.keyword}">下一页</a>
                                            </li>
                                        </c:if>
                                        <c:if test="${!pageInfo.hasNextPage}">
                                            <%-- 禁用下一页按钮 --%>
                                            <li class="disabled">
                                                <a href="javascript:void(0);">下一页</a>
                                            </li>
                                        </c:if>
                                        <%-- 点击跳转至最后一页 --%>
                                        <li>
                                            <a href="admin/get/page?pageNum=${pageInfo.pages}&keyword=${param.keyword}">末页</a>
                                        </li>
                                    </ul>
                                </td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
