<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:layout>
	<jsp:attribute name="nav">
		<div id="navbar" class="navbar-collapse collapse">
			<c:url var="loginUrl" value="/login" />
			<form action="${loginUrl}" method="post"
				class="navbar-form navbar-right">
				<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
				<div class="form-group">
					<input type="email" placeholder="Email" class="form-control"
						id="username" name="email" required="required">
				</div>
				<div class="form-group">
					<input type="password" placeholder="Password" class="form-control"
						id="password" name="password">
				</div>
				<button type="submit" class="btn btn-success">Sign in</button>
			</form>
		</div>
	</jsp:attribute>
	<jsp:body>
	<%@ include file="register_form.jsp" %>
	</jsp:body>
</t:layout>