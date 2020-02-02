<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<t:layout>
	<jsp:attribute name="nav">
<!-- 		<div id="navbar" class="navbar-collapse collapse"> -->
<%-- 			<c:url var="logoutUrl" value="/logout"/> --%>
<%-- 			<form action="${logoutUrl}" method="post" --%>
<%-- 				class="navbar-form navbar-right"> --%>
<%-- 				<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/> --%>
<!-- 				<button type="submit" class="btn btn-success">Log out</button> -->
<%-- 			</form> --%>
<!-- 		</div> -->
	
			<%@ include file="../Menu/menu_logged.jsp" %>
	</jsp:attribute>
	<jsp:body>
	<div class="row">
		<div class="col-md-3"></div>
		<div class="col-md-6">
			<%@ include file="../Post/post_form.jsp" %>
			<div id="posts">
				<%@ include file="../Post/posts.jsp" %>
			</div>
		</div>
		<div class="col-md-6"></div>
	</div>
	</jsp:body>
</t:layout>