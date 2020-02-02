<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:url var="forgotUrl" value="/forgotpassword" />
<t:layout>
	<jsp:attribute name="nav">
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<c:url var="loginUrl" value="/login" />
				<li><a href="${loginUrl}">Log in</a></li>
				<c:url var="registerUrl" value="/register" />
	            <li><a href="${registerUrl}">Register</a></li>
			</ul>
		</div>
	</jsp:attribute>
	<jsp:body>
	<div class="row">
		<div class="col-md-4"></div>
		<div class="col-md-4">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">New password</h3>
				</div>
				<div class="panel-body">
				<c:choose>
					<c:when test="${tokenError != null}">
						<div class="alert alert-danger">
						  <ul>
						  	<li>You can not reset your password, please <a href="${forgotUrl }">Forgot password</a></li>
						  </ul>
						</div>
					</c:when>
					<c:otherwise>
						<c:if test="${errors.size() > 0}">
							<div class="alert alert-danger">
							  <ul>
							  	<c:forEach items="${errors}" var="error">
							  		<li>${error}</li>
							  	</c:forEach>
							  </ul>
							</div>
						</c:if>
						<c:url var="resetpasswordUrl" value="/resetpassword" />
						<form:form class="form-signin" action="${resetpasswordUrl}" method="post" modelAttribute="user">
							<form:label path="password" class="sr-only" for="registerPassword">Password</form:label>
							<form:password path="password" required="required" class="form-control" id="registerPassword" placeholder="Password" />
							<form:hidden path="token"/>
							<form:hidden path="email"/>
							<form:button class="btn btn-lg btn-primary btn-block" type="submit">Send</form:button>
						</form:form>
					</c:otherwise>
				</c:choose>
					
				</div>
			</div>

		</div>
		<div class="col-md-4"></div>
	</div>
	</jsp:body>
</t:layout>