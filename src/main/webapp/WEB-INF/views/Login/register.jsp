<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:layout>
	<jsp:attribute name="nav">
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<c:url var="loginUrl" value="/login" />
				<li><a href="${loginUrl}">Log in</a></li>
			</ul>
		</div>
	</jsp:attribute>
	<jsp:body>
	<%@ include file="register_form.jsp" %>
	</jsp:body>
</t:layout>